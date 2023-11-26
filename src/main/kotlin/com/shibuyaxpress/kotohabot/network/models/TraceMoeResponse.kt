package com.shibuyaxpress.kotohabot.network.models

data class TraceMoeResponse(
    var frameCount: Double,
    var error: String,
    var result: List<ResultMoe>
)

data class ResultMoe(
    var anilist: Anilist?,
    var filename: String,
    var episode: Int?,
    var from: Double?,
    var to: Double?,
    var similarity: Double?,
    var video: String,
    var image: String
)

data class Anilist(
    var id: Int?,
    var idMal: Int?,
    var title: AnilistTitle,
    var synonyms: List<String>
)

data class AnilistTitle(
    var native: String?,
    var romaji: String?,
    var english: String?
)