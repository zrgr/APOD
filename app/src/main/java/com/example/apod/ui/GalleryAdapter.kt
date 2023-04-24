package com.example.apod.ui

import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.Disposable
import coil.size.Size
import com.example.apod.R
import com.example.apod.databinding.GalleryItemBinding
import com.example.apod.models.Apod
import com.example.apod.ui.fragments.GalleryFragment

private const val TAG = "GalleryAdapter"

class GalleryAdapter(val clickListener: ApodListener): ListAdapter<Apod, GalleryAdapter.ApodViewHolder>(DiffCallback) {

    class ApodViewHolder(private var binding: GalleryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ApodListener, apod: Apod) {
            binding.photo = apod
            binding.clickListener = clickListener
            binding.executePendingBindings()
            Log.e(TAG, "${apod.url}");
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Apod>() {
        override fun areItemsTheSame(oldItem: Apod, newItem: Apod): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Apod, newItem: Apod): Boolean {
            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryAdapter.ApodViewHolder {
        return ApodViewHolder(
            GalleryItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ApodViewHolder, position: Int) {
        val apod = getItem(position)
        holder.bind(clickListener, apod)
    }
}

class ApodListener(val clickListener: (apod: Apod) -> Unit) {
    fun onClick(apod: Apod) = clickListener(apod)
}
