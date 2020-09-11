package com.example.gallerysample.viewmodels

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.gallerysample.data.GalleryDataSourceFactory

class GalleryViewModel(
    contentResolver: ContentResolver
) : ViewModel() {

    /**
     * 画像のコンテンツURIリスト
     */
    var photoUriList: LiveData<PagedList<Uri>>

    private var dataSourceFactory = GalleryDataSourceFactory(contentResolver)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGED_LIST_SIZE)
            .setMaxSize(PAGED_LIST_MAX_SIZE)
            .setEnablePlaceholders(true)
            .build()

        photoUriList = LivePagedListBuilder(dataSourceFactory, config)
            .build()
    }

    /**
     * データソースを無効化する.
     */
    fun invalidateDataSource() {
        dataSourceFactory.latestSource.invalidate()
    }

    companion object {
        private const val PAGED_LIST_MAX_SIZE = 60
        private const val PAGED_LIST_SIZE = 20
    }
}