package com.example.gallerysample.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gallerysample.models.GalleryModel

class GalleryViewModel(private val galleryModel: GalleryModel) : ViewModel(),
    GalleryModel by galleryModel