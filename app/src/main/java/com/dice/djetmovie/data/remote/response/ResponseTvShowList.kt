package com.dice.djetmovie.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseTvShowList(

        @field:SerializedName("page")
        val page: Int,

        @field:SerializedName("results")
        val results: List<ResponseTvShow>,
)