package com.example.gallery.models

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore

class GalleryModelImpl(private val context: Context): GalleryModel {

    private fun createCursor(galleryPath: String): Cursor? {
        val projection: Array<String>?
        val selection: String?
        val selectionArgs: Array<String>?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.RELATIVE_PATH
            )

            selection = "${MediaStore.MediaColumns.RELATIVE_PATH} = ?"
            selectionArgs = arrayOf(galleryPath)
        } else {
            @Suppress("DEPRECATION")
            projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATA
            )

            @Suppress("DEPRECATION")
            selection = "${MediaStore.MediaColumns.DATA} like ?"
            selectionArgs = arrayOf("%${galleryPath}%")
        }

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