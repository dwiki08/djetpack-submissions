package com.dice.djetmovie.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
        var id: Int,
        var title: String,
        var posterPath: String,
        var backdropPath: String,
        var releaseDate: String,
        var overview: String
) : Parcelable