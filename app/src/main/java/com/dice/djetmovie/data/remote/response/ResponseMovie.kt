package com.dice.djetmovie.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("popularity")
    val popularity: Float?
)