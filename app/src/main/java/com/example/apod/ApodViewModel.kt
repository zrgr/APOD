package com.example.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apod.models.Apod
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import com.example.apod.network.RetrofitInstance
import com.example.apod.repository.ApodRepository
import kotlinx.coroutines.launch

class ApodViewModel : ViewModel() {

    private val _photo = MutableLiveData<Apod>()
    val photos: LiveData<Apod> = _photo

    private val _repo = ApodRepository()

    init {
        getApod()
    }

    private fun getApod() {
        viewModelScope.launch {
            try {
                _photo.value = _repo.getApod()
            } catch (e: Exception){
                //Log
            }
        }
    }
}