package com.dice.djetmovie.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    var id: Int,
    var type: TYPE,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var overview: String
) : Parcelable {
    enum class TYPE {
        MOVIE,
        TV_SHOW
    }
}