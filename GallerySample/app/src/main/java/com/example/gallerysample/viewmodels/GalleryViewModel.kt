package com.example.gallerysample.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gallerysample.models.GalleryModel

class GalleryViewModel(galleryModel: GalleryModel) : ViewModel() {

    val uriList: MutableList<Uri> = galleryModel.getPhotoUriList()

}