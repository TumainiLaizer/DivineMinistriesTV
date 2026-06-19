package com.gospel.divineministriestv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "videos")
data class Video(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val duration: String? = null,
    val viewCount: String? = null,
    val isLive: Boolean = false,
    val category: String? = null
)

@Serializable
data class Playlist(
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    val videoCount: Int
)
