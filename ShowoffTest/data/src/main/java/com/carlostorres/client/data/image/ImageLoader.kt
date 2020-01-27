package com.carlostorres.client.data.image

import android.widget.ImageView

interface ImageLoader {

    fun load(image: ImageView, url: String)

}