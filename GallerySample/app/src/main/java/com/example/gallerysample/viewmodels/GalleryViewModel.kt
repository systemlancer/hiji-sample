package com.example.gallerysample.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.gallerysample.models.GalleryModel

class GalleryViewModel(private val galleryModel: GalleryModel) : ViewModel(),
    GalleryModel by galleryModel {

    val uriList: MutableList<Uri> = getPhotoUriList()

}