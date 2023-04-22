package com.example.apod

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apod.models.Apod
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.apod.repository.ApodRepository
import kotlinx.coroutines.launch
import java.io.IOException
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

    private val _day = MutableLiveData<String>()
    var day: LiveData<String> = _day

    private val _monthYear = MutableLiveData<String>()
    var monthYear: LiveData<String> = _monthYear

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
                Log.e(TAG, "getApod() photosApi call failed");
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

    private fun getDate(days: Long) = LocalDate.now().minusDays(days).toString()

    fun onApodClicked(apod: Apod) {
        _photo.value = apod
        convertDate(apod.date)
    }
}
