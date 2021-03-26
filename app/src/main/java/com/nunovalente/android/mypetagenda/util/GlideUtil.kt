package com.nunovalente.android.mypetagenda.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.nunovalente.android.mypetagenda.R

object GlideUtil {

    fun ImageView.load(image: Bitmap, onLoadingFinished: () -> Unit = {}) {
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                onLoadingFinished()
                return false
            }
        }

        val requestOptions = RequestOptions.placeholderOf(R.drawable.default_image)
            .dontTransform()

        Glide.with(this)
            .load(image)
            .apply(requestOptions)
            .listener(listener)
            .into(this)
    }
}