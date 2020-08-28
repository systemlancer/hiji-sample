package com.example.gallerysample.models

import android.net.Uri

interface GalleryModel {
    fun getPhotoUriList(): MutableList<Uri>

    fun clearMemory()
    suspend fun clearDiskCache()
}