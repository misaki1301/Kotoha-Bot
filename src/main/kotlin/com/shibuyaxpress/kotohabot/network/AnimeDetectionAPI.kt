package com.shibuyaxpress.kotohabot.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AnimeDetectionAPI {
    val client by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(TraceMeAPI.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return@lazy retrofit.create(TraceMeAPI::class.java)
    }

    val meowClient:CatsAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(CatsAPI.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return@lazy retrofit.create(CatsAPI::class.java)
    }
}