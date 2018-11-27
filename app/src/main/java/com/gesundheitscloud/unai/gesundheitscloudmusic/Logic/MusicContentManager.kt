package com.gesundheitscloud.unai.gesundheitscloudmusic.Logic

import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.FilterTypes
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.MusicClientModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ServerModels.MusicServerModel
import java.util.logging.Filter

object MusicContentManager
{
    val ITEM_MAP: MutableMap<Int, MusicClientModel> = HashMap()

    var ITEMS_SEARCH : MutableList<MusicClientModel> = ArrayList()

    var searchText : String? = ""

    var ITEMS_SEARCH_ACTIVE : MutableList<MusicClientModel> = ArrayList()

    var FILTER_SELECTED : FilterTypes  = FilterTypes.None

    fun setPlanetsServerAndFactoryFromSearch(musicServerModel: MusicServerModel)
    {
        //Factory from server model to ClientModel and setting to vals
        ITEMS_SEARCH = ArrayList()
        musicServerModel.results.forEach {

            val musicClientModel: MusicClientModel = MusicClientModel()
            musicClientModel.artistId = it.artistId
            musicClientModel.artistName =it.artistName
            musicClientModel.artworkUrl100 = it.artworkUrl100
            musicClientModel.artworkUrl30 =it.artworkUrl30
            musicClientModel.artworkUrl60 = it.artworkUrl60
            musicClientModel.collectionArtistName = it.collectionArtistName
            musicClientModel.collectionCensoredName = it.collectionCensoredName
            musicClientModel.collectionId = it.collectionId
            musicClientModel.collectionName = it.collectionName
            musicClientModel.kind = it.kind
            musicClientModel.previewUrl = it.previewUrl
            musicClientModel.trackCensoredName = it.trackCensoredName
            musicClientModel.trackId = it.trackId
            musicClientModel.trackName = it.trackName
            musicClientModel.wrapperType = it.wrapperType
            musicClientModel.trackPrice = it.trackPrice
            musicClientModel.primaryGenreName = it.primaryGenreName
            musicClientModel.trackTimeMillis = it.trackTimeMillis

            ITEMS_SEARCH.add(musicClientModel)
            ITEM_MAP.put(musicClientModel.trackId!!, musicClientModel)
        }
        ITEMS_SEARCH_ACTIVE = ITEMS_SEARCH
    }

    fun reorderBy(filterType : FilterTypes) : List<MusicClientModel>
    {
        if(filterType == FilterTypes.Duration)
        {
            val sortedList = ITEMS_SEARCH.sortedWith(compareBy(MusicClientModel::trackTimeMillis))
            ITEMS_SEARCH_ACTIVE = sortedList as MutableList<MusicClientModel>
            return sortedList
        }
        else  if(filterType == FilterTypes.Genre)
        {
            val sortedList = ITEMS_SEARCH.sortedWith(compareBy(MusicClientModel::primaryGenreName))
            ITEMS_SEARCH_ACTIVE = sortedList as MutableList<MusicClientModel>
            return sortedList
        }
        else  if(filterType == FilterTypes.Price)
        {
            val sortedList = ITEMS_SEARCH.sortedWith(compareBy(MusicClientModel::trackPrice))
            ITEMS_SEARCH_ACTIVE = sortedList as MutableList<MusicClientModel>
            return sortedList
        }
        else
        {
            ITEMS_SEARCH_ACTIVE = ITEMS_SEARCH
            return ITEMS_SEARCH
        }
    }

}