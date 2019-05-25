package com.musickhoj.api

import com.musickhoj.api.model.SongResult
import com.musickhoj.api.model.Songs
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {
    @GET("search?media=music&limit=30")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun performSearch(@Query("term") term: String, @Query("country") country: String): Call<SongResult>
}