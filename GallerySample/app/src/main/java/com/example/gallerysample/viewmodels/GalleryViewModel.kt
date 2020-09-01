package com.example.gallerysample.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerysample.constants.PAGED_LIST_MAX_SIZE
import com.example.gallerysample.constants.PAGED_LIST_SIZE
import com.example.gallerysample.data.GalleryDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel(
    application: Application,
    private val contentResolver: ContentResolver
) : AndroidViewModel(application) {

    lateinit var uriList: LiveData<PagedList<Uri>>
    val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            clear()
        }
    }

    init {
        loadPhotoUris()
    }

    private fun loadPhotoUris() {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGED_LIST_SIZE)
            .setMaxSize(PAGED_LIST_MAX_SIZE)
            .setEnablePlaceholders(false)
            .build()


        uriList =
            LivePagedListBuilder<Int, Uri>(GalleryDataSourceFactory(contentResolver), config)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<Uri>() {
                    override fun onZeroItemsLoaded() {
                        super.onZeroItemsLoaded()
                    }

                    override fun onItemAtFrontLoaded(itemAtFront: Uri) {
                        super.onItemAtFrontLoaded(itemAtFront)
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Uri) {
                        super.onItemAtEndLoaded(itemAtEnd)
                    }
                })
                .build()
    }

    fun invalidateDataSource() {
        uriList.value?.dataSource?.invalidate()
    }


    fun clear() {
        with(Glide.get(getApplication())) {
            this.clearMemory()
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    this@with.clearDiskCache()
                }
            }
        }
    }
}