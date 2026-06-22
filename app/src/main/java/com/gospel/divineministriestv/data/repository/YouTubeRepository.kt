package com.gospel.divineministriestv.data.repository

import com.gospel.divineministriestv.data.local.VideoDao
import com.gospel.divineministriestv.data.model.ChannelStats
import com.gospel.divineministriestv.data.model.Comment
import com.gospel.divineministriestv.data.model.Playlist
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.data.remote.YouTubeApiService
import com.gospel.divineministriestv.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YouTubeRepository @Inject constructor(
    private val apiService: YouTubeApiService,
    private val videoDao: VideoDao
) {
    private val apiKey = com.gospel.divineministriestv.BuildConfig.YOUTUBE_API_KEY

    suspend fun getLatestVideos(): List<Video> {
        return searchVideos(null)
    }

    suspend fun searchVideos(query: String?): List<Video> {
        val response = apiService.searchVideos(
            channelId = Constants.YOUTUBE_CHANNEL_ID,
            query = query,
            apiKey = apiKey
        )
        val videoIds = response.items.joinToString(",") { it.id.videoId }
        if (videoIds.isEmpty()) return emptyList()
        return getVideoDetails(videoIds)
    }

    suspend fun getVideoDetails(videoIds: String): List<Video> {
        val response = apiService.getVideoDetails(
            videoId = videoIds,
            apiKey = apiKey
        )
        return response.items.map { item ->
            Video(
                id = item.id,
                title = item.snippet.title,
                description = item.snippet.description,
                thumbnailUrl = item.snippet.thumbnails.high.url,
                publishedAt = item.snippet.publishedAt,
                duration = parseDuration(item.contentDetails.duration),
                viewCount = formatCount(item.statistics.viewCount),
                likeCount = formatCount(item.statistics.likeCount),
                commentCount = formatCount(item.statistics.commentCount)
            )
        }
    }

    suspend fun checkLiveStatus(): Video? {
        val response = apiService.getLiveStatus(
            channelId = Constants.YOUTUBE_CHANNEL_ID,
            apiKey = apiKey
        )
        val liveItem = response.items.firstOrNull() ?: return null
        
        // Fetch full details for the live video as well
        return getVideoDetails(liveItem.id.videoId).firstOrNull()?.copy(isLive = true)
    }

    suspend fun getPlaylists(): List<Playlist> {
        val response = apiService.getPlaylists(
            channelId = Constants.YOUTUBE_CHANNEL_ID,
            apiKey = apiKey
        )
        return response.items.map { item ->
            Playlist(
                id = item.id,
                title = item.snippet.title,
                thumbnailUrl = item.snippet.thumbnails.high.url,
                videoCount = item.contentDetails.itemCount
            )
        }
    }

    suspend fun getPlaylistVideos(playlistId: String): List<Video> {
        val response = apiService.getPlaylistItems(
            playlistId = playlistId,
            apiKey = apiKey
        )
        val videoIds = response.items.joinToString(",") { it.snippet.resourceId?.videoId ?: "" }
        if (videoIds.replace(",", "").isEmpty()) return emptyList()
        return getVideoDetails(videoIds)
    }

    suspend fun getChannelStats(): ChannelStats? {
        val response = apiService.getChannelDetails(
            channelId = Constants.YOUTUBE_CHANNEL_ID,
            apiKey = apiKey
        )
        val item = response.items.firstOrNull() ?: return null
        return ChannelStats(
            id = item.id,
            subscriberCount = formatCount(item.statistics.subscriberCount),
            viewCount = formatCount(item.statistics.viewCount),
            videoCount = formatCount(item.statistics.videoCount),
            bannerUrl = item.brandingSettings.image?.bannerExternalUrl
        )
    }

    suspend fun getVideoComments(videoId: String): List<Comment> {
        val response = apiService.getComments(
            videoId = videoId,
            apiKey = apiKey
        )
        return response.items.map { item ->
            val snippet = item.snippet.topLevelComment.snippet
            Comment(
                authorName = snippet.authorDisplayName,
                authorImageUrl = snippet.authorProfileImageUrl,
                text = snippet.textDisplay,
                likeCount = snippet.likeCount,
                publishedAt = snippet.publishedAt
            )
        }
    }

    private fun parseDuration(duration: String): String {
        // Simple conversion for ISO 8601 duration (e.g., PT1H2M10S to 1:02:10)
        return duration.replace("PT", "")
            .replace("H", ":")
            .replace("M", ":")
            .replace("S", "")
            .let { 
                val parts = it.split(":")
                when (parts.size) {
                    1 -> "0:${parts[0].padStart(2, '0')}"
                    else -> parts.joinToString(":") { p -> p.padStart(2, '0') }
                }
            }
    }

    private fun formatCount(count: String?): String {
        if (count == null) return "0"
        val value = count.toLongOrNull() ?: return count
        return when {
            value >= 1_000_000 -> "${value / 1_000_000}M"
            value >= 1_000 -> "${value / 1_000}K"
            else -> count
        }
    }

    // Favorites Logic
    fun getFavoriteVideos(): Flow<List<Video>> = videoDao.getAllFavorites()
    
    fun isFavorite(videoId: String): Flow<Boolean> = videoDao.isFavorite(videoId)

    suspend fun toggleFavorite(video: Video, isCurrentlyFavorite: Boolean) {
        if (isCurrentlyFavorite) {
            videoDao.deleteFavorite(video)
        } else {
            videoDao.insertFavorite(video)
        }
    }
}
