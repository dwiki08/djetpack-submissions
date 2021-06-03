package com.dice.djetmovie.di.module

import com.dice.djetmovie.BuildConfig
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

private fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        } else
            OkHttpClient.Builder().build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_BASE_URL)
                .client(okHttpClient)
                .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

