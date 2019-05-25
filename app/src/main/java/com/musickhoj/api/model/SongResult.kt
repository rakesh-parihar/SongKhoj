package com.musickhoj.api.model

data class SongResult(
    val resultCount: Int,
    val results: List<Songs>
)