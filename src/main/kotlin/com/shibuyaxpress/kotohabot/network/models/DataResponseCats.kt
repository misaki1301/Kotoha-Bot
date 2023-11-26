package com.shibuyaxpress.kotohabot.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponseCats(
    @field:Json(name = "file")
    var file: String
)
