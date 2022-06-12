package com.example.apod.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apod.databinding.GalleryItemBinding
import com.example.apod.models.Apod

class GalleryAdapter(val clickListener: ApodListener): ListAdapter<Apod, GalleryAdapter.ApodViewHolder>(DiffCallback) {

    class ApodViewHolder(private var binding: GalleryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ApodListener, apod: Apod) {
            binding.photo = apod
            binding.clickListener = clickListener
            binding.executePendingBindings()
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
