package com.dice.djetmovie.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    var id: Int,
    var title: String,
    var posterRes: Int,
    var releaseDate: String,
    var overview: String
): Parcelable