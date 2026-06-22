package com.gospel.divineministriestv.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gospel.divineministriestv.data.model.Video

@Database(entities = [Video::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}
