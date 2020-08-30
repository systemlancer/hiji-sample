package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerysample.R
import com.example.gallerysample.databinding.ItemPhotoBinding
import com.example.gallerysample.listeners.requestListener

class PhotoDetailAdapter(
    private val deleteOnClickListener: PhotoAdapter.DeleteOnClickListener
) :
    PagedListAdapter<Uri, PhotoDetailAdapter.UriViewHolder>(DiffCallback) {

    var currentPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UriViewHolder {
        return UriViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ), deleteOnClickListener
        )
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        getItem(position)?.let {
            currentPosition = position
            holder.bind(it)
        }
    }

    class UriViewHolder(
        private val binding: ItemPhotoBinding,
        private val deleteOnClickListener: PhotoAdapter.DeleteOnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            with(binding) {
                deleteButton.setOnClickListener {
                    deleteOnClickListener.onClick(uri)
                }
                Glide.with(binding.photoImage.context)
                    .load(uri)
                    .centerInside()
                    .listener(requestListener)
                    .placeholder(R.drawable.loading_animation)
                    .into(binding.photoImage)
                executePendingBindings()
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem.path == newItem.path
        }

    }

    class DeleteOnClickListener(val clickListener: (uri: Uri) -> Unit) {
        fun onClick(uri: Uri) = clickListener(uri)
    }
}