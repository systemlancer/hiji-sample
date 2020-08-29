package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerysample.databinding.ItemPhotoBinding
import com.example.gallerysample.listeners.requestListener

class PhotoAdapter(
    var photoUriList: PagedList<Uri>
) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photoUriList.size

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
        photoUriList[position]?.let {
            viewHolder.bind(it)
        }
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(uri: Uri) {
            with(binding.photoImage) {
                Glide.with(context)
                    .load(uri)
                    .listener(requestListener)
                    .centerInside()
                    .into(this)
            }
        }
    }
}