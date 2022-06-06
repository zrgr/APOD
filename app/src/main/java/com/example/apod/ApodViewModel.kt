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
import java.util.Locale;

class ApodViewModel : ViewModel() {

    private val _photo = MutableLiveData<Apod>()
    val photos: LiveData<Apod> = _photo

    private val _repo = ApodRepository()

    private val _day = MutableLiveData<String>()
    var day: LiveData<String> = _day

    private val _monthYear = MutableLiveData<String>()
    var monthYear: LiveData<String> = _monthYear

    init {
        getApod()
    }

    private fun getApod() {
        viewModelScope.launch {
            try {
                _photo.value = _repo.getApod()
                convertDate(photos.value!!.date)
            } catch (e: Exception){
                //Log
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
}
