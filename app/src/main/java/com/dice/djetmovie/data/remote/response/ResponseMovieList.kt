package com.dice.djetmovie.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseMovieList(

        @field:SerializedName("page")
        val page: Int,

        @field:SerializedName("total_pages")
        val totalPages: Int,

        @field:SerializedName("results")
        val results: List<ResponseMovie>,

        @field:SerializedName("total_results")
        val totalResults: Int
)