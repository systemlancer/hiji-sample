package com.example.gallerysample.viewmodels

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gallerysample.constants.PAGED_LIST_MAX_SIZE
import com.example.gallerysample.constants.PAGED_LIST_SIZE
import com.example.gallerysample.data.GalleryDataSourceFactory

class GalleryViewModel(
    private val contentResolver: ContentResolver
) : ViewModel() {

    lateinit var uriList: LiveData<PagedList<Uri>>

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
}