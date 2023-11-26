package com.shibuyaxpress.kotohabot.network

import com.shibuyaxpress.kotohabot.network.models.TraceMoeResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TraceMeAPI {
    companion object {
        const val API_BASE_URL = "https://api.trace.moe/"
    }
    @Multipart
    @POST("search?anilistInfo")
    fun getRelatedAnimeByImageAsync(@Part image: MultipartBody.Part) : Call<TraceMoeResponse>
}