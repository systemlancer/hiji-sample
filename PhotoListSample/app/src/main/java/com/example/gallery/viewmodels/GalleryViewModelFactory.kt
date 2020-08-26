package com.example.gallery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gallery.models.GalleryModel

@Suppress("UNCHECKED_CAST")
class GalleryViewModelFactory(private val galleryModel: GalleryModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(galleryModel) as T
    }
}