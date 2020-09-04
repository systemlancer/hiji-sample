package com.example.gallerysample.data

import android.content.ContentResolver
import android.net.Uri
import androidx.paging.PositionalDataSource
import com.example.gallerysample.utilities.getPhotoUriList

class GalleryDataSource(private val contentResolver: ContentResolver) :
    PositionalDataSource<Uri>() {

    /**
     * 初期データリストを読み込みます.
     */
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Uri>) {
        callback.onResult(
            contentResolver.getPhotoUriList(
                params.requestedLoadSize,
                params.requestedStartPosition
            ), 0
        )
    }

    /**
     * 一定範囲のデータをロードするために呼び出されます.
     */
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Uri>) {
        callback.onResult(contentResolver.getPhotoUriList(params.loadSize, params.startPosition))
    }
}