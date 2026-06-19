package com.gospel.divineministriestv.data.repository

import com.gospel.divineministriestv.data.model.Playlist
import com.gospel.divineministriestv.data.model.Video
import com.gospel.divineministriestv.data.remote.YouTubeApiService
import com.gospel.divineministriestv.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YouTubeRepository @Inject constructor(
    private val apiService: YouTubeApiService,
) {
    private val apiKey = com.gospel.divineministriestv.BuildConfig.YOUTUBE_API_KEY

    suspend fun getLatestVideos(): List<Video> {
        val response = apiService.searchVideos(
            channelId = Constants.YOUTUBE_CHANNEL_ID,
            apiKey = apiKey
        )
        val videoIds = response.items.joinToString(",") { it.id.videoId }
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
                viewCount = formatViewCount(item.statistics.viewCount)
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
        return getVideoDetails(videoIds)
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

    private fun formatViewCount(count: String): String {
        val views = count.toLongOrNull() ?: return count
        return when {
            views >= 1_000_000 -> "${views / 1_000_000}M"
            views >= 1_000 -> "${views / 1_000}K"
            else -> count
        }
    }
}
