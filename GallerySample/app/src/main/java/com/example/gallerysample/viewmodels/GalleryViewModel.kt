package com.example.gallerysample.viewmodels

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gallerysample.data.GalleryDataSourceFactory

class GalleryViewModel(
    private val contentResolver: ContentResolver
) : ViewModel() {

    /**
     * 画像のコンテンツURIリスト
     */
    lateinit var photoUriList: LiveData<PagedList<Uri>>

    init {
        loadPhotoUriList()
    }

    /**
     * データソースを無効化する.
     */
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

    companion object {
        private const val PAGED_LIST_MAX_SIZE = 60
        private const val PAGED_LIST_SIZE = 20
    }
}