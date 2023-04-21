package com.example.apod.ui.fragments

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.request.ImageRequest
import com.example.apod.ApodViewModel
import com.example.apod.ImageStatus
import com.example.apod.R
import com.example.apod.databinding.FragmentSetBackgroundBinding
import java.io.IOException

class SetBackgroundFragment: Fragment() {

    private val viewModel: ApodViewModel by activityViewModels()
    lateinit var binding: FragmentSetBackgroundBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetBackgroundBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.setBackgroundButton.setOnClickListener{ setWallpaper( binding.wallpaperPreview ) }

        viewModel.photo.observe(requireActivity()){ data ->
            loadHdImage(data.hdurl)
        }

        return binding.root
    }

    private fun loadHdImage(url: String) {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        binding.wallpaperPreview.load(imgUri) {
            placeholder(R.drawable.loading_animation)

        }
    }

    private fun setWallpaper(wallpaper: ImageView) {

        var bitmap = (wallpaper.drawable as BitmapDrawable).bitmap
        val wallpaperManager = WallpaperManager.getInstance(context)

        try {
            wallpaperManager.setBitmap(bitmap)
            backgroundStatusToast("Background image changed.")
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            backgroundStatusToast("Image could not be set as background.")
        }
    }

    private fun backgroundStatusToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}