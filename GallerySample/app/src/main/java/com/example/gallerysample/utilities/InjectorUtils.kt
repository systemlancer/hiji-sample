package com.example.gallerysample.utilities

import android.content.Context
import com.example.gallerysample.models.GalleryModelImpl
import com.example.gallerysample.viewmodels.GalleryViewModelFactory

object InjectorUtils {
    fun provideGalleryViewModelFactory(context: Context): GalleryViewModelFactory {
        return GalleryViewModelFactory(GalleryModelImpl(context))
    }
}