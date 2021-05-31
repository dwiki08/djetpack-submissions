package com.dice.djetmovie.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseTvShow(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_name")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("first_air_date")
    val releaseDate: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("popularity")
    val popularity: Float?
)
