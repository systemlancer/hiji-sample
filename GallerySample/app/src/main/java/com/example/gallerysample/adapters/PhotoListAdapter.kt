package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerysample.R
import com.example.gallerysample.databinding.ListItemPhotoBinding
import com.example.gallerysample.listeners.requestListener

class PhotoListAdapter(
    private val onClickListener: OnClickListener
) :
    PagedListAdapter<Uri, PhotoListAdapter.UriViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UriViewHolder {
        return UriViewHolder(ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        getItem(position)
            ?.apply {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(position)
                }
                holder.bind(this)
            }
    }

    class UriViewHolder(val binding: ListItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            with(binding) {
                Glide.with(binding.photoImage.context)
                    .load(uri)
                    .centerCrop()
                    .listener(requestListener)
                    .placeholder(R.drawable.loading_animation)
                    .dontAnimate()
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

    class OnClickListener(val clickListener: (position: Int) -> Unit) {
        fun onClick(position: Int) = clickListener(position)
    }
}