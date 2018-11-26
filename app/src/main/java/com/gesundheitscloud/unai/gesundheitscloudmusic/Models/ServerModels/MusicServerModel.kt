package com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ServerModels

data class MusicServerModel(
    val resultCount: Int,
    val results: List<Result>
) {
    data class Result(
        val artistId: Int,
        val artistName: String,
        val artworkUrl100: String,
        val artworkUrl30: String,
        val artworkUrl60: String,
        val collectionArtistName: String,
        val collectionCensoredName: String,
        val collectionId: Int,
        val collectionName: String,
        val kind: String,
        val previewUrl: String,
        val trackCensoredName: String,
        val trackId: Int,
        val trackName: String,
        val wrapperType: String,
        val trackPrice : String,
        val trackTimeMillis : String,
        val primaryGenreName : String
    )
}