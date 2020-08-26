package com.example.gallerysample.utilities

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GalleryModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean = false
}