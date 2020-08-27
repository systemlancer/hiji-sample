package com.example.gallerysample.models

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import timber.log.Timber

class GalleryModelImpl(private val context: Context) : GalleryModel {

    override fun getPhotoUriList(): MutableList<Uri> {
        val cursor = createCursor()

        val mediaList = mutableListOf<Uri>()
        cursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            Timber.d("gallery count ${cursor.count}")
            while (cursor.moveToNext()) {
//                Timber.d(
//                    "gallery column start -----------------------------------------------"
//                )
//                cursor.columnNames.forEach {
//                    Timber.d(
//                        "gallery $it ${cursor.getString(cursor.getColumnIndexOrThrow(it))}"
//                    )
//                }
//                Timber.d(
//                    "gallery column end -----------------------------------------------"
//                )
                val id = cursor.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                mediaList.add(contentUri)
            }
            cursor.close()
        }

        return mediaList
    }

    private fun createCursor(): Cursor? {
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        val selection: String? =
            "${MediaStore.MediaColumns.MIME_TYPE} = ?"
        val selectionArgs: Array<String>? = arrayOf("image/jpeg")
        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

        return context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
//            null,
            selection,
            selectionArgs,
            sortOrder
        )
    }
}