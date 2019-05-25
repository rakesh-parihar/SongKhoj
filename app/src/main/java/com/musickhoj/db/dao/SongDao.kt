package com.musickhoj.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.musickhoj.api.model.Songs
import com.musickhoj.db.entity.SongEntity


@Dao
interface SongDao {

    @Query("SELECT * FROM song_table")
    fun getSongs(): LiveData<List<Songs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongData(song: Array<out SongEntity>)

    @Query("DELETE FROM song_table")
    fun removeSongData()
}