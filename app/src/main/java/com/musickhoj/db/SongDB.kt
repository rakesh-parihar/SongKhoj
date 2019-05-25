package com.musickhoj.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.musickhoj.db.dao.SongDao
import com.musickhoj.db.entity.SongEntity

@Database(entities = [SongEntity::class], version = 1, exportSchema = false)
abstract  class SongDB:RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {

        @Volatile private var INSTANCE: SongDB? = null

        fun getInstance(context: Context): SongDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SongDB::class.java, "songs.db")
                .build()
    }
}