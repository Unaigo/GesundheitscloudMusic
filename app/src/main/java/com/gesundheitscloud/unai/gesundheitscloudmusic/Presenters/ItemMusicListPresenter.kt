package com.gesundheitscloud.unai.gesundheitscloudmusic.Presenters

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.gesundheitscloud.unai.gesundheitscloudmusic.Logic.ItemMusicListInteractor
import com.gesundheitscloud.unai.gesundheitscloudmusic.Logic.MusicContentManager
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.FilterTypes
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.MusicClientModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ServerModels.MusicServerModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.Views.Adapters.SimpleItemRecyclerViewAdapter
import com.gesundheitscloud.unai.gesundheitscloudmusic.Views.ItemMusicListActivity
import kotlinx.android.synthetic.main.itemmusic_list.view.*

//This is the presenter of the main view
//where I manage the data and population of the list
class ItemMusicListPresenter(private var activity: ItemMusicListActivity, private var view: View, private val itemMusicListInteractor: ItemMusicListInteractor)
    : ItemMusicListInteractor.OnFinishedListener {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    var isSearching: Boolean = false

    fun initLinearLayoutManagerRecyclerView(linearLayoutManager: LinearLayoutManager) {
        view.itemmusic_list.layoutManager = linearLayoutManager;
        if (view.itemmusic_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
    }

    fun updateRecyclerView(items: List<MusicClientModel>) {
        view.itemmusic_list.adapter = SimpleItemRecyclerViewAdapter(
            activity,
            items, twoPane
        )
        if(items.any())
        {
            view.tableLayout.setVisibility(View.VISIBLE)
            view.lineSeparator.setVisibility(View.VISIBLE)
        }
        else
        {
            view.tableLayout.setVisibility(View.INVISIBLE)
            view.lineSeparator.setVisibility(View.INVISIBLE)
        }
    }


    fun initSearchView() {
        view.searchView.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        view.searchView.setIconifiedByDefault(false)
        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.toString().isNotEmpty()) {
                    showProgressBarSearch()
                    search(query)
                    isSearching = true;
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.toString().isNotEmpty()) MusicContentManager.searchText = newText.toString() else {
                    MusicContentManager.searchText = ""
                    MusicContentManager.ITEMS_SEARCH = ArrayList()
                    isSearching = false;
                    hideProgressBarSearch()
                    updateRecyclerView(MusicContentManager.ITEMS_SEARCH as ArrayList<MusicClientModel>);
                }
                return false
            }
        })

    }

    fun initFilter()
    {
        view.filterPriceTextView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onClickFilter(v as TextView,FilterTypes.Price)
            }
        })

        view.filterGenreTextView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onClickFilter(v as TextView,FilterTypes.Genre)
            }
        })

        view.filterLenghtTextView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onClickFilter(v as TextView,FilterTypes.Duration)
            }
        })
    }

    fun updateSearchView(searchText: String?)
    {
        view.searchView.setQuery(searchText,false)
    }

    fun isFilterSelected() : Boolean
    {
        if( MusicContentManager.FILTER_SELECTED != FilterTypes.None)
        {
            return true
        }
        return  false
    }

    fun updateRecyclerViewWithFilter()
    {
        if( MusicContentManager.FILTER_SELECTED == FilterTypes.Price)
        {
            view.filterPriceTextView.setSelected(true)
        }
        else  if( MusicContentManager.FILTER_SELECTED == FilterTypes.Duration)
        {
            view.filterLenghtTextView.setSelected(true)
        }
        else  if( MusicContentManager.FILTER_SELECTED == FilterTypes.Genre)
        {
            view.filterGenreTextView.setSelected(true)
        }
        updateRecyclerView(MusicContentManager.reorderBy(MusicContentManager.FILTER_SELECTED ))

    }

    private fun onClickFilter(textView: TextView,filterType: FilterTypes)
    {
        if(textView.isSelected())
        {
            textView.setSelected(false)
            updateRecyclerView(MusicContentManager.ITEMS_SEARCH)
            MusicContentManager.FILTER_SELECTED = FilterTypes.None
        }
        else
        {
            deselectAllFilters()
            textView.setSelected(true)
            updateRecyclerView(MusicContentManager.reorderBy(filterType))
            MusicContentManager.FILTER_SELECTED = filterType
        }
    }

    private fun deselectAllFilters()
    {
        view.filterGenreTextView.setSelected(false)
        view.filterLenghtTextView.setSelected(false)
        view.filterPriceTextView.setSelected(false)
    }

    private fun showProgressBarSearch() {
        view.progressBarSearch.setVisibility(View.VISIBLE);
    }

    private fun hideProgressBarSearch() {
        view.progressBarSearch.setVisibility(View.INVISIBLE);
    }

    private fun search(searchText: String) {
        if(isOnline())
        {
            itemMusicListInteractor.requestSearch(this, searchText)
        }
        else
        {
            Toast.makeText(activity, "Check your network status...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isOnline(): Boolean {
        val cm =activity.
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.getActiveNetworkInfo()
        return netInfo != null && netInfo.isConnectedOrConnecting()
    }

    override fun onResultSuccessRequestSearch(musicServerModel: MusicServerModel) {
        if (musicServerModel != null && musicServerModel.results.any()) {
            MusicContentManager.setPlanetsServerAndFactoryFromSearch(musicServerModel)
            if(isFilterSelected())
            {
                updateRecyclerViewWithFilter()
            }
            else
            {
                updateRecyclerView(MusicContentManager.ITEMS_SEARCH as ArrayList<MusicClientModel>);
            }

        } else {
            Toast.makeText(activity, "No Results...", Toast.LENGTH_SHORT).show()
        }
        hideProgressBarSearch()
    }

    override fun onResultFail(strError: String) {

    }

}