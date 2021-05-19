package com.dice.djetmovie.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dice.djetmovie.R
import com.dice.djetmovie.data.Constants
import java.io.File

object Utils {
    fun setPosterImage(context: Context, view: ImageView, imagePath: String?) {
        if (imagePath == null) return
        Glide.with(context)
                .load(Constants.API_POSTER_URL + imagePath)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_broken)
                .into(view)
    }

    fun setBackdropImage(context: Context, view: ImageView, imagePath: String?) {
        if (imagePath == null) return
        Glide.with(context)
            .load(Constants.API_BACKDROP_URL + imagePath)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_broken)
            .into(view)
    }

    fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}