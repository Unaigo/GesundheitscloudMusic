package com.gesundheitscloud.unai.gesundheitscloudmusic.Logic

import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ServerModels.MusicServerModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.ServerConnection.INetworkAPI
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ItemMusicListInteractor {

    interface OnFinishedListener {
        fun onResultFail(strError: String)
        fun onResultSuccessRequestSearch(planetServer: MusicServerModel)
    }

    fun requestSearch(onFinishedListener: OnFinishedListener,searchText:String)
    {
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://itunes.apple.com/").build()

        val postsApi = retrofit.create(INetworkAPI::class.java)

        val response = postsApi.search(searchText)

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe {
            onFinishedListener.onResultSuccessRequestSearch(it)
        }
    }


}