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

    lateinit var photoUriList: LiveData<PagedList<Uri>>

    init {
        loadPhotoUriList()
    }

    fun pathToPosition(path: String): Int? {
        return photoUriList.value?.indexOfFirst { it.path == path }
    }

    fun invalidateDataSource() {
        photoUriList.value?.dataSource?.invalidate()
    }

    private fun loadPhotoUriList() {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGED_LIST_SIZE)
            .setMaxSize(PAGED_LIST_MAX_SIZE)
            .setEnablePlaceholders(false)
            .build()

        photoUriList =
            LivePagedListBuilder(GalleryDataSourceFactory(contentResolver), config).build()
    }
}