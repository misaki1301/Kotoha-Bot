package com.shibuyaxpress.kotohabot.network

import com.shibuyaxpress.kotohabot.network.models.DataResponseCats
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface CatsAPI {
    companion object {
        const val API_BASE_URL = "https://aws.random.cat/"
    }

    @GET("meow")
    fun getRandomCatAsync() : Deferred<DataResponseCats>
}