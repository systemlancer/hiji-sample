package com.example.gallerysample.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerysample.databinding.ItemPhotoBinding
import com.example.gallerysample.listeners.requestListener

class PhotoDetailAdapter(
    private val deleteOnClickListener: DeleteOnClickListener
) :
    PagedListAdapter<Uri, PhotoDetailAdapter.UriViewHolder>(DiffCallback) {

    private val differ = AsyncPagedListDiffer(object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count, payload)
        }

    }, AsyncDifferConfig.Builder(DiffCallback).build())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UriViewHolder {
        return UriViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            deleteOnClickListener
        )
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class UriViewHolder(
        val binding: ItemPhotoBinding,
        private val deleteOnClickListener: DeleteOnClickListener
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
                    .dontAnimate()
                    .dontTransform()
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