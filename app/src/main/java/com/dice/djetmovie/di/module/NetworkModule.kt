package com.dice.djetmovie.di.module

import android.content.Context
import com.dice.djetmovie.BuildConfig
import com.dice.djetmovie.data.Constants
import com.dice.djetmovie.data.remote.ApiService
import com.dice.djetmovie.data.remote.utils.ResponseHandler
import com.dice.djetmovie.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
    single { provideResponseHandler() }
    single { provideNetworkHelper(androidContext()) }
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

private fun provideResponseHandler(): ResponseHandler = ResponseHandler()

private fun provideNetworkHelper(context: Context): NetworkHelper = NetworkHelper(context)