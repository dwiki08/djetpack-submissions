package com.dice.djetmovie.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.DataMapper
import com.dice.djetmovie.data.model.Film
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.utils.EspressoIdlingResource
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val apiService: ApiService,
    private val query: String?
) : PagingSource<Int, Film>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val API_KEY = Constants.API_KEY
        private const val API_LANG = Constants.API_LANGUAGE
        private const val MSG_ERROR = "Failed to getting data from server."
        private const val MSG_NO_ITEM = "No item found."
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        EspressoIdlingResource.increment()

        try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (query.isNullOrEmpty()) {
                apiService.getMovie(API_KEY, API_LANG, position)
            } else {
                apiService.searchMovies(API_KEY, API_LANG, query, position)
            }

            val listFilm = mutableListOf<Film>()

            if (response.isSuccessful) {
                response.body()?.let {
                    for (responseData in it.results) {
                        listFilm.add(DataMapper.mapMovie(responseData))
                    }
                }
            } else {
                EspressoIdlingResource.decrement()
                return LoadResult.Error(Throwable(MSG_ERROR))
            }

            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1

            EspressoIdlingResource.decrement()
            if (listFilm.isEmpty()) return LoadResult.Error(Throwable(MSG_NO_ITEM))
            return LoadResult.Page(
                data = listFilm,
                prevKey = prevKey,
                nextKey = position.plus(1)
            )
        } catch (exception: IOException) {
            EspressoIdlingResource.decrement()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            EspressoIdlingResource.decrement()
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}