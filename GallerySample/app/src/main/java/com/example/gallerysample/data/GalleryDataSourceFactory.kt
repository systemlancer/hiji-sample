package com.example.gallerysample.data

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.DataSource

class GalleryDataSourceFactory(private val contentResolver: ContentResolver) :
    DataSource.Factory<Int, Uri>() {
    lateinit var latestSource: GalleryDataSource

    override fun create(): DataSource<Int, Uri> {
        latestSource = GalleryDataSource(contentResolver)
        return latestSource
    }
}