package com.example.apod.ui.fragments

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.apod.ApodViewModel
import com.example.apod.databinding.FragmentSetBackgroundBinding
import java.io.IOException

class SetBackgroundFragment: Fragment() {

    private val viewModel: ApodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSetBackgroundBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.title.setOnClickListener{ setWallpaper( binding.wallpaperPreview ) }

        return binding.root
    }

    private fun setWallpaper(wallpaper: ImageView) {

        var bitmap = (wallpaper.drawable as BitmapDrawable).bitmap
        val wallpaperManager = WallpaperManager.getInstance(context)

        try {
            wallpaperManager.setBitmap(bitmap)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}