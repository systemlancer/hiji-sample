package com.example.gallerysample.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallerysample.models.GalleryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryViewModel(private val galleryModel: GalleryModel) : ViewModel() {

    val uriList: MutableList<Uri> = galleryModel.getPhotoUriList()

    fun clear() {
        galleryModel.clearMemory()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                galleryModel.clearDiskCache()
            }
        }
    }
}