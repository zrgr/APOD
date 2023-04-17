package com.example.apod

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.apod.models.Apod
import com.example.apod.ui.GalleryAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Apod>?) {
    val adapter = recyclerView.adapter as GalleryAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageStatus")
fun bindStatus(statusImageView: ImageView,
               status: ImageStatus) {
    when (status) {
        ImageStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ImageStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ImageStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("loadingImagesTitle")
fun bindStatus(loadingTextView: TextView,
               status: ImageStatus) {
    when (status) {
        ImageStatus.LOADING -> {
            loadingTextView.visibility = View.VISIBLE
        }
        ImageStatus.ERROR -> {
            loadingTextView.visibility = View.GONE
        }
        ImageStatus.DONE -> {
            loadingTextView.visibility = View.GONE
        }
    }
}
