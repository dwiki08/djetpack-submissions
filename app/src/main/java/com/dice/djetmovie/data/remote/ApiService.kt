package com.dice.djetmovie.data.remote

import com.dice.djetmovie.data.remote.response.ResponseMovie
import com.dice.djetmovie.data.remote.response.ResponseMovieList
import com.dice.djetmovie.data.remote.response.ResponseTvShow
import com.dice.djetmovie.data.remote.response.ResponseTvShowList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<ResponseMovieList>

    @GET("discover/tv")
    suspend fun getTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<ResponseTvShowList>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<ResponseMovieList>

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<ResponseTvShowList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<ResponseMovie>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetail(
        @Path("tv_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<ResponseTvShow>
}