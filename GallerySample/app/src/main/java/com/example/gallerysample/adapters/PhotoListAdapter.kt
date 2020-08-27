package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerysample.databinding.ListItemPhotoBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

class PhotoListAdapter(
    private val onClickListener: OnClickListener
) :
    ListAdapter<Uri, PhotoListAdapter.UriViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UriViewHolder {
        return UriViewHolder(ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        val uri = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.bind(uri)
    }

    class UriViewHolder(private val binding: ListItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            with(binding) {
                val request = ImageRequestBuilder.newBuilderWithSource(uri)
//                    .setResizeOptions(ResizeOptions(50, 50))
                    .setProgressiveRenderingEnabled(true)
                    .build()
                val controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(photoImage.controller)
                    .setImageRequest(request)
                    .build()
                photoImage.setImageURI(uri, photoImage.context)
                photoImage.controller = controller

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