package com.example.gallerysample.data

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.PositionalDataSource
import com.example.gallerysample.utilities.getPhotoUriList

class GalleryDataSource(private val contentResolver: ContentResolver) :
    PositionalDataSource<Uri>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Uri>) {
        callback.onResult(
            contentResolver.getPhotoUriList(
                params.requestedLoadSize,
                params.requestedStartPosition
            ), 0
        )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Uri>) {
        callback.onResult(contentResolver.getPhotoUriList(params.loadSize, params.startPosition))
    }
}