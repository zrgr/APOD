package com.example.apod.network

import com.example.apod.models.Apod
import com.example.apod.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApodApi {
    @GET("apod")
    suspend fun getApod(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Apod

    @GET("apod")
    suspend fun getApodPhotos(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("start_date")
        startDate: String
    ): List<Apod>

    @GET("apod")
    suspend fun getApodByDate(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("date")
        date: String
    ): Apod
}