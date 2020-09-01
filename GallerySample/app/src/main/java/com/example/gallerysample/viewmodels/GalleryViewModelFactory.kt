package com.example.gallerysample.viewmodels

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GalleryViewModelFactory(
    private val contentResolver: ContentResolver
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(contentResolver) as T
    }
}