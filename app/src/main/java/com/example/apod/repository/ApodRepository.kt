package com.example.apod.repository

import com.example.apod.models.Apod
import com.example.apod.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApodRepository {
    suspend fun getApod() = RetrofitInstance.api.getApod()
    suspend fun getApodPhotos(date: String) = RetrofitInstance.api.getApodPhotos(startDate=date)
    suspend fun getApodByDate(date: String) = RetrofitInstance.api.getApodByDate(date=date)
}