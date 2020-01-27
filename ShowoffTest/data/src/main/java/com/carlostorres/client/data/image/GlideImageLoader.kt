package com.carlostorres.client.data.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class GlideImageLoader(val context: Context): ImageLoader {

    private val requestManager: RequestManager = Glide.with(context)

    override fun load(image: ImageView, url: String) {
        requestManager.load(url).into(image)
    }

}
