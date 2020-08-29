package com.example.gallerysample.utilities

import android.app.Application
import android.content.Context
import com.example.gallerysample.viewmodels.GalleryViewModelFactory

object InjectorUtils {
    fun provideGalleryViewModelFactory(
        application: Application,
        context: Context
    ): GalleryViewModelFactory {
        return GalleryViewModelFactory(application, context.contentResolver)
    }
}