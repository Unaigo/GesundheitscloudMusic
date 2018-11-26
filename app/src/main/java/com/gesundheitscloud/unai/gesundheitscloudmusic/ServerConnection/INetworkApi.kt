package com.gesundheitscloud.unai.gesundheitscloudmusic.ServerConnection

import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ServerModels.MusicServerModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface INetworkAPI {

    @GET("search?entity=musicTrack,song")
    fun search(@Query("term") search : String): Observable<MusicServerModel>
}