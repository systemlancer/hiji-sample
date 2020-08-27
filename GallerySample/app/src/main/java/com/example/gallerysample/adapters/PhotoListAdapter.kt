package com.example.gallerysample.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gallerysample.R
import com.example.gallerysample.databinding.ListItemPhotoBinding
import com.example.gallerysample.utilities.GlideApp
import timber.log.Timber

class PhotoListAdapter(
    private val fragment: Fragment,
    private val onClickListener: OnClickListener
) :
    ListAdapter<Uri, PhotoListAdapter.UriViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UriViewHolder {
        return UriViewHolder(
            ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context)),
            fragment
        )
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        val uri = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position)
        }
        holder.bind(uri)
    }

//    override fun onViewRecycled(holder: UriViewHolder) {
//        super.onViewRecycled(holder)
//        GlideApp.with(fragment).clear(holder.binding.photoImage)
//    }

    class UriViewHolder(val binding: ListItemPhotoBinding, private val fragment: Fragment) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            with(binding) {
                GlideApp.with(fragment)
                    .load(uri)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Timber.d("onLoadFailed")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Timber.d("onResourceReady")
                            return false
                        }


                    })
                    .placeholder(R.drawable.loading_animation)
                    .dontAnimate()
                    .into(photoImage)
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