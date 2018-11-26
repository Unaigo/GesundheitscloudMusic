package com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels

data class MusicClientModel(
    var artistId: Int?,
    var artistName: String?,
    var artworkUrl100: String?,
    var artworkUrl30: String?,
    var artworkUrl60: String?,
    var collectionArtistName: String?,
    var collectionCensoredName: String?,
    var collectionId: Int?,
    var collectionName: String?,
    var kind: String?,
    var previewUrl: String?,
    var trackCensoredName: String?,
    var trackId: Int?,
    var trackName: String?,
    var wrapperType: String?,
    var trackPrice : String?,
    var trackTimeMillis : String?,
    var primaryGenreName : String?

){
    constructor() : this(-1, "",
        "", "","",
        "", "", -1,
        "", "","",
        "",-1,"",
        "","","",""
    )
}