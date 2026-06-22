package com.gospel.divineministriestv.data.remote

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String,
        @Query("q") query: String? = null,
        @Query("maxResults") maxResults: Int = 20,
        @Query("order") order: String = "date",
        @Query("type") type: String = "video",
        @Query("key") apiKey: String
    ): YouTubeSearchResponse

    @GET("search")
    suspend fun getLiveStatus(
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String,
        @Query("type") type: String = "video",
        @Query("eventType") eventType: String = "live",
        @Query("key") apiKey: String
    ): YouTubeSearchResponse

    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String
    ): YouTubePlaylistResponse

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String
    ): YouTubePlaylistItemResponse

    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("id") videoId: String,
        @Query("key") apiKey: String
    ): YouTubeVideoDetailResponse

    @GET("channels")
    suspend fun getChannelDetails(
        @Query("part") part: String = "snippet,statistics,brandingSettings",
        @Query("id") channelId: String,
        @Query("key") apiKey: String
    ): YouTubeChannelResponse

    @GET("commentThreads")
    suspend fun getComments(
        @Query("part") part: String = "snippet",
        @Query("videoId") videoId: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String
    ): YouTubeCommentResponse
}

@JsonClass(generateAdapter = true)
data class YouTubeSearchResponse(
    val items: List<YouTubeSearchItem>
)

@JsonClass(generateAdapter = true)
data class YouTubeSearchItem(
    val id: YouTubeVideoId,
    val snippet: YouTubeSnippet
)

@JsonClass(generateAdapter = true)
data class YouTubeVideoId(
    val videoId: String
)

@JsonClass(generateAdapter = true)
data class YouTubeSnippet(
    val title: String,
    val description: String,
    val thumbnails: YouTubeThumbnails,
    val publishedAt: String,
    val resourceId: YouTubeResourceId? = null
)

@JsonClass(generateAdapter = true)
data class YouTubeResourceId(
    val videoId: String
)

@JsonClass(generateAdapter = true)
data class YouTubeThumbnails(
    val high: YouTubeThumbnail
)

@JsonClass(generateAdapter = true)
data class YouTubeThumbnail(
    val url: String
)

@JsonClass(generateAdapter = true)
data class YouTubePlaylistResponse(
    val items: List<YouTubePlaylistItem>
)

@JsonClass(generateAdapter = true)
data class YouTubePlaylistItem(
    val id: String,
    val snippet: YouTubeSnippet,
    val contentDetails: YouTubePlaylistDetails
)

@JsonClass(generateAdapter = true)
data class YouTubePlaylistDetails(
    val itemCount: Int
)

@JsonClass(generateAdapter = true)
data class YouTubePlaylistItemResponse(
    val items: List<YouTubePlaylistItemContent>
)

@JsonClass(generateAdapter = true)
data class YouTubePlaylistItemContent(
    val snippet: YouTubeSnippet
)

@JsonClass(generateAdapter = true)
data class YouTubeVideoDetailResponse(
    val items: List<YouTubeVideoDetailItem>
)

@JsonClass(generateAdapter = true)
data class YouTubeVideoDetailItem(
    val id: String,
    val snippet: YouTubeSnippet,
    val contentDetails: YouTubeVideoContentDetails,
    val statistics: YouTubeVideoStatistics
)

@JsonClass(generateAdapter = true)
data class YouTubeVideoContentDetails(
    val duration: String
)

@JsonClass(generateAdapter = true)
data class YouTubeVideoStatistics(
    val viewCount: String,
    val likeCount: String? = null,
    val favoriteCount: String? = null,
    val commentCount: String? = null
)

@JsonClass(generateAdapter = true)
data class YouTubeChannelResponse(
    val items: List<YouTubeChannelItem>
)

@JsonClass(generateAdapter = true)
data class YouTubeChannelItem(
    val id: String,
    val snippet: YouTubeSnippet,
    val statistics: YouTubeChannelStatistics,
    val brandingSettings: YouTubeBrandingSettings
)

@JsonClass(generateAdapter = true)
data class YouTubeChannelStatistics(
    val viewCount: String,
    val subscriberCount: String,
    val videoCount: String,
    val hiddenSubscriberCount: Boolean
)

@JsonClass(generateAdapter = true)
data class YouTubeBrandingSettings(
    val image: YouTubeChannelImage? = null
)

@JsonClass(generateAdapter = true)
data class YouTubeChannelImage(
    val bannerExternalUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class YouTubeCommentResponse(
    val items: List<YouTubeCommentThread>
)

@JsonClass(generateAdapter = true)
data class YouTubeCommentThread(
    val snippet: YouTubeCommentThreadSnippet
)

@JsonClass(generateAdapter = true)
data class YouTubeCommentThreadSnippet(
    val topLevelComment: YouTubeCommentItem
)

@JsonClass(generateAdapter = true)
data class YouTubeCommentItem(
    val snippet: YouTubeCommentSnippet
)

@JsonClass(generateAdapter = true)
data class YouTubeCommentSnippet(
    val textDisplay: String,
    val authorDisplayName: String,
    val authorProfileImageUrl: String,
    val likeCount: Int,
    val publishedAt: String
)
