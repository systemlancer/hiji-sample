package com.example.gallerysample.models

import android.database.Cursor

interface GalleryModel {
    fun createCursor(): Cursor?
}