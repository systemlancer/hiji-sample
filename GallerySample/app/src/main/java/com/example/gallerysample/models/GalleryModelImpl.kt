package com.example.gallerysample.models

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class GalleryModelImpl(private val context: Context) : GalleryModel {

    override fun createCursor(): Cursor? {
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        val selection: String? = "${MediaStore.MediaColumns.MIME_TYPE} = ?"
        val selectionArgs: Array<String>? = arrayOf("image/jpeg")
        val sortOrder = "${MediaStore.MediaColumns.DISPLAY_NAME} DESC"

        return context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
    }
}