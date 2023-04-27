package com.example.apod

import android.util.Log
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

private const val TAG = "ApodViewModel"

enum class ImageStatus { LOADING, ERROR, DONE }

class ApodViewModel : ViewModel() {

    private val _photo = MutableLiveData<Apod>()
    val photo: LiveData<Apod> = _photo

    private val _photos = MutableLiveData<List<Apod>>()
    val photos: LiveData<List<Apod>> = _photos

    private val _repo = ApodRepository()

    private val _date = MutableLiveData<String>()
    var date: LiveData<String> = _date

    private val _status = MutableLiveData<ImageStatus>()
    var status: LiveData<ImageStatus> = _status

    init {
        getApodPhotos(getLastWeeksDate())
    }

    private fun getApodPhotos(date: String) {
        _status.value = ImageStatus.LOADING
        viewModelScope.launch {
            try {
                _photos.value = _repo.getApodPhotos(date)
                _status.value = ImageStatus.DONE
            } catch (e: Exception) {
                _photos.value = listOf()
                _status.value = ImageStatus.ERROR
                Log.e(TAG, "getApodPhotos() call failed");
            }
        }
    }

    fun getApodByDate(day: Int, month: Int, year: Int) {
        var date = convertDate(day, month, year)
        viewModelScope.launch {
            try {
                _photo.value = _repo.getApodByDate(date)
            } catch (e: Exception) {
                Log.e(TAG, "getApodByDate() call failed");
            }
        }
    }

    private fun convertDate(date: String) {
        val pictureDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("d MMM y", Locale.ENGLISH)
        val newDate = pictureDate.format(formatter).split(" ")
        _date.value = "${newDate[1]} ${newDate[0]}, ${newDate[2]}"
    }

    private fun getLastWeeksDate() = LocalDate.now().minusDays(7).toString()

    private fun getDate(days: Long) = LocalDate.now().minusDays(days).toString()

    private fun convertDate(day: Int, month: Int, year: Int): String {
        val formattedDay = if (day < 10) "0$day" else day
        val formattedMonth = if (month < 10) "0$month" else month
        val date = "$year$formattedMonth$formattedDay"
        return LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).toString()
    }

    fun onApodClicked(apod: Apod) {
        _photo.value = apod
        convertDate(apod.date)
    }
}
