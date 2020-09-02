package com.example.gallerysample.utilities

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore

/**
 * 外部ストレージからjpeg画像のURIリストを取得する.
 */
fun ContentResolver.getPhotoUriList(limit: Int, offset: Int): MutableList<Uri> {

    val projection = arrayOf(MediaStore.MediaColumns._ID)
    val selection: String? = "${MediaStore.MediaColumns.MIME_TYPE} = ?"
    val selectionArgs: Array<String>? = arrayOf("image/jpeg")
    val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC LIMIT $limit OFFSET $offset"

    val cursor = query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    val mediaList = mutableListOf<Uri>()
    cursor?.use { photoListCursor ->
        val idColumn = photoListCursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
        while (photoListCursor.moveToNext()) {
            val id = photoListCursor.getLong(idColumn)
            val contentUri = Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id.toString()
            )
            mediaList.add(contentUri)
        }
        photoListCursor.close()
    }

    return mediaList
}

/**
 * 指定した画像を削除する.
 */
fun ContentResolver.deletePhoto(uri: Uri): Boolean {
    var hasDeletedPhoto = false
    val deletedRows = delete(uri, null, null)
    if (deletedRows > 0) {
        hasDeletedPhoto = true
    }
    return hasDeletedPhoto
}