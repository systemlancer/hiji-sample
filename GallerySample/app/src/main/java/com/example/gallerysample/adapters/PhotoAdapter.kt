package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerysample.databinding.ItemPhotoBinding
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

class PhotoAdapter(
    private val photoUriList: MutableList<Uri>
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
        viewHolder.bind(photoUriList[position])
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(uri: Uri) {
            with(binding.photoImage) {
                val request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
//                    .setResizeOptions(ResizeOptions(50, 50))
                    .build()
                val controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(this.controller)
                    .setImageRequest(request)
                    .build()
                setImageURI(uri, context)
                this.controller = controller
            }
        }
    }
}