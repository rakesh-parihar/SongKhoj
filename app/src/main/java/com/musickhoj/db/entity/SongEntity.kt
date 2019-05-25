package com.musickhoj.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_table")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val trackId:Int,
    val artistName: String,
    val trackName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val currency: String,
    val primaryGenreName: String
)

