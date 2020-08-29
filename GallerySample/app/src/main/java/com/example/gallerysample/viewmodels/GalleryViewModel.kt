package com.example.gallerysample.viewmodels

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import com.example.gallerysample.data.GalleryDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel(
    application: Application,
    private val contentResolver: ContentResolver
) : AndroidViewModel(application) {

    lateinit var uriList: LiveData<PagedList<Uri>>

    fun loadPhotoUris() {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()


        uriList = LivePagedListBuilder<Int, Uri>(
            GalleryDataSourceFactory(contentResolver),
            config
        ).build()
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