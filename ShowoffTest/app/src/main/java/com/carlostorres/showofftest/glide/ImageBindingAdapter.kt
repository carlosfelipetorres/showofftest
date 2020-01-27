package com.carlostorres.showofftest.glide

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url: String) {
        val requestOptions = RequestOptions()
            .encodeFormat(Bitmap.CompressFormat.JPEG)
            .encodeQuality(10)
            .diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().dontTransform()

        Glide.with(view.context).asBitmap().load(url).into(view)
    }
}