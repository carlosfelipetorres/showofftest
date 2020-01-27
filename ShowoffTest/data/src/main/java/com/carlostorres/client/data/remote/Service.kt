package com.carlostorres.client.data.remote

import android.content.Context
import android.content.SharedPreferences
import com.carlostorres.client.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Get the remote API service client
 *
 * @param clazz HTTP API interface defined with Retrofit
 * @param endPoint Base URL endpoint
 */
fun <T> createService(context: Context, clazz: Class<T>, endPoint: String, sharedPreferences: SharedPreferences): T = Retrofit.Builder()
    .baseUrl(endPoint)
    .addConverterFactory(getConverterFactory())
    .client(getClient(context, sharedPreferences))
    .build()
    .create(clazz)

fun getConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
}

fun getClient(context: Context, sharedPreferences: SharedPreferences): OkHttpClient {

    return OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
    }
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}