package com.example.apod.repository

import com.example.apod.models.Apod
import com.example.apod.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodRepository {
    suspend fun getApod() = RetrofitInstance.api.getApod()
    suspend fun getApodPhotos() = RetrofitInstance.api.getApodPhotos()
}