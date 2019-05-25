package com.musickhoj.api.model

data class Songs(
    val trackId: Int,
    val artistName: String,
    val trackName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val currency: String,
    val primaryGenreName: String
)
