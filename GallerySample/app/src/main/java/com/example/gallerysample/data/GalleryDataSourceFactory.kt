package com.example.gallerysample.data

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.DataSource

class GalleryDataSourceFactory(private val contentResolver: ContentResolver) :
    DataSource.Factory<Int, Uri>() {
    override fun create(): DataSource<Int, Uri> {
        return GalleryDataSource(contentResolver)
    }
}