package com.gospel.divineministriestv.data.local

import androidx.room.*
import com.gospel.divineministriestv.data.model.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    fun getAllFavorites(): Flow<List<Video>>

    @Query("SELECT EXISTS(SELECT 1 FROM videos WHERE id = :videoId)")
    fun isFavorite(videoId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(video: Video)

    @Delete
    suspend fun deleteFavorite(video: Video)
}
