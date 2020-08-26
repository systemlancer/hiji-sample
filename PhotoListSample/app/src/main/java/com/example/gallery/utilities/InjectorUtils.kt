package com.example.gallery.utilities

import android.content.Context
import com.example.gallery.models.GalleryModelImpl
import com.example.gallery.viewmodels.GalleryViewModelFactory

object InjectorUtils {

    fun provideGalleryViewModelFactory(context: Context): GalleryViewModelFactory {
        return GalleryViewModelFactory(GalleryModelImpl(context))
    }
}