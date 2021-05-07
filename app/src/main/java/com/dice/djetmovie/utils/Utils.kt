package com.dice.djetmovie.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dice.djetmovie.R

object Utils {
    fun setImage(context: Context, view: ImageView, drawable: Drawable?) {
        Glide.with(context)
            .load(drawable)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .into(view)
    }
}