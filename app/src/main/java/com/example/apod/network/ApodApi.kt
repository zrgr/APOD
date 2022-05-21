package com.example.apod.network

import com.example.apod.models.Apod
import com.example.apod.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {
    @GET
    suspend fun getApod(
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Apod
}