package com.gesundheitscloud.unai.gesundheitscloudmusic.Views


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.gesundheitscloud.unai.gesundheitscloudmusic.Logic.ItemMusicListInteractor
import com.gesundheitscloud.unai.gesundheitscloudmusic.Logic.MusicContentManager
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.MusicClientModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.Presenters.ItemMusicListPresenter
import com.gesundheitscloud.unai.gesundheitscloudmusic.R
import kotlinx.android.synthetic.main.activity_itemmusic_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemMusicDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemMusicListActivity : AppCompatActivity() {

    var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
    var itemMusicListPresenter : ItemMusicListPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_itemmusic_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        itemMusicListPresenter= ItemMusicListPresenter(this,window.decorView.rootView, ItemMusicListInteractor())

        itemMusicListPresenter!!.initLinearLayoutManagerRecyclerView(linearLayoutManager)

        itemMusicListPresenter!!.initSearchView()
        itemMusicListPresenter!!.initFilter()
        if(MusicContentManager.ITEMS_SEARCH.any()) {
            if(itemMusicListPresenter!!.isFilterSelected())
            {
                itemMusicListPresenter!!.updateRecyclerViewWithFilter()
            }
            else
            {
                itemMusicListPresenter!!.updateRecyclerView(MusicContentManager.ITEMS_SEARCH as ArrayList<MusicClientModel>);
            }
            itemMusicListPresenter!!.updateSearchView(MusicContentManager.searchText)
        }
    }
}
