package com.example.gallerysample.listeners

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber

val requestListener = object : RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        Timber.e(e)
        return false
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        if (resource is BitmapDrawable) {
            val bitmap = (resource as BitmapDrawable).bitmap
            with(bitmap) {
                val uri = model as Uri
                Timber.d("bitmap %,d bytes, width $width, height $height path ${uri.path} ${dataSource?.name} $isFirstResource".format(byteCount))
            }

        }
        return false
    }

}