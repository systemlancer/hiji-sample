package com.example.gallerysample

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import timber.log.Timber

class GallerySampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        val config = ImagePipelineConfig.newBuilder(applicationContext)
//            .setDownsampleEnabled(true)
//            .build()
//        Fresco.initialize(applicationContext, config)
        Fresco.initialize(applicationContext)

        Timber.plant(Timber.DebugTree())
    }
}