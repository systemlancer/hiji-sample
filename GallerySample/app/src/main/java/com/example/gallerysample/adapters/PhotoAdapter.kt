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
    var photoUriList: PagedList<Uri>,
    private val deleteOnClickListener: DeleteOnClickListener
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

        photoUriList[position]?.let { photoUri ->
            viewHolder.bind(photoUri)
            viewHolder.binding.deleteButton.setOnClickListener {
                deleteOnClickListener.onClick(photoUri)
            }
        }
    }

    class PhotoViewHolder(val binding: ItemPhotoBinding) :
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

    class DeleteOnClickListener(val clickListener: (uri: Uri) -> Unit) {
        fun onClick(uri: Uri) = clickListener(uri)
    }
}