package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerysample.databinding.ItemPhotoBinding

class PhotoAdapter(
    private val fragment: Fragment,
    private val photoUriList: MutableList<Uri>
) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), fragment
        )
    }

    override fun getItemCount(): Int = photoUriList.size

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
        viewHolder.bind(photoUriList[position])
    }

    class PhotoViewHolder(val binding: ItemPhotoBinding, private val fragment: Fragment) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(uri: Uri) {
            binding.photoImage.setImageURI(uri, fragment)
        }
    }
}