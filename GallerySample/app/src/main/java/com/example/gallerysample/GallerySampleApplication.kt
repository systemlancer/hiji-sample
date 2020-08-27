package com.example.gallerysample

import android.app.Application
import timber.log.Timber

class GallerySampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}