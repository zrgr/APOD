package com.example.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apod.models.Apod
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import com.example.apod.repository.ApodRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ApodViewModel : ViewModel() {

    private val _photo = MutableLiveData<Apod>()
    val photo: LiveData<Apod> = _photo

    private val _photos = MutableLiveData<List<Apod>>()
    val photos: LiveData<List<Apod>> = _photos

    private val _repo = ApodRepository()

    private val _day = MutableLiveData<String>()
    var day: LiveData<String> = _day

    private val _monthYear = MutableLiveData<String>()
    var monthYear: LiveData<String> = _monthYear

    init {
        //getApod()
        getApodPhotos(getLastWeeksDate())
    }

    private fun getApod() {
        viewModelScope.launch {
            try {
                _photo.value = _repo.getApod()
                convertDate(photo.value!!.date)
            } catch (e: Exception){
                //Log
            }
        }
    }

    private fun getApodPhotos(date: String) {
        viewModelScope.launch {

            try {
                _photos.value = _repo.getApodPhotos(date)
                val test = "hello"
            } catch (e: Exception) {
                _photos.value = listOf()
            }
        }
    }

    private fun convertDate(date: String) {
        val pictureDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("d MMM y", Locale.ENGLISH)
        val newDate = pictureDate.format(formatter).split(" ")

        _day.value = newDate[0]
        _monthYear.value = "${newDate[1]} ${newDate[2]}"
    }

    private fun getLastWeeksDate() = LocalDate.now().minusDays(7).toString()

}
