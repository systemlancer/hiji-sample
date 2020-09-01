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
            LivePagedListBuilder(GalleryDataSourceFactory(contentResolver), config)
                .build()
    }

    fun invalidateDataSource() {
        uriList.value?.dataSource?.invalidate()
    }
}