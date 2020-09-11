package com.example.gallerysample.utilities

import android.app.RecoverableSecurityException
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi

/**
 * 外部ストレージからjpeg画像のURIリストを取得する.
 */
fun ContentResolver.getPhotoUriList(limit: Int, offset: Int): MutableList<Uri> {

    val projection = arrayOf(MediaStore.MediaColumns._ID)
    val selection: String? = "${MediaStore.MediaColumns.MIME_TYPE} = ?"
    val selectionArgs: Array<String>? = arrayOf("image/jpeg")
    val sortOrder = "${MediaStore.MediaColumns.DATE_MODIFIED} DESC LIMIT $limit OFFSET $offset"

    val cursor = query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )

    val photoUriList = mutableListOf<Uri>()
    cursor?.use { photoListCursor ->
        val idColumn = photoListCursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
        while (photoListCursor.moveToNext()) {
            val id = photoListCursor.getLong(idColumn)
            val contentUri = Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id.toString()
            )
            photoUriList.add(contentUri)
        }
        photoListCursor.close()
    }

    return photoUriList
}

/**
 * 外部ストレージのjpeg画像のトータル件数を取得する
 */
fun ContentResolver.getPhotoTotalCount(): Int {
    val projection = arrayOf(MediaStore.MediaColumns._ID)
    val selection: String? = "${MediaStore.MediaColumns.MIME_TYPE} = ?"
    val selectionArgs: Array<String>? = arrayOf("image/jpeg")

    val cursor = query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        null
    )

    var totalCount: Int = 0
    cursor?.use {
        totalCount = it.count
        it.close()
    }

    return totalCount
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

/**
 * 指定した画像を削除する.
 */
@RequiresApi(Build.VERSION_CODES.Q)
fun ContentResolver.deletePhotoQ(uri: Uri): Boolean {
    var hasDeletedPhoto = false
    val deletedRows = try {
        delete(uri, null, null)
    } catch (e: RecoverableSecurityException) {
        throw e
    }
    if (deletedRows > 0) {
        hasDeletedPhoto = true
    }
    return hasDeletedPhoto
}