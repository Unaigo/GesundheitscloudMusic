package com.gesundheitscloud.unai.gesundheitscloudmusic.Views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gesundheitscloud.unai.gesundheitscloudmusic.Logic.MusicContentManager
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.MusicClientModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.R
import kotlinx.android.synthetic.main.activity_itemmusic_detail.*
import kotlinx.android.synthetic.main.itemmusic_detail.view.*

/**
 * A fragment representing a single ItemMusic detail screen.
 * This fragment is either contained in a [ItemMusicListActivity]
 * in two-pane mode (on tablets) or a [ItemMusicDetailActivity]
 * on handsets.
 */
class ItemMusicDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: MusicClientModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = MusicContentManager.ITEM_MAP[it.getInt(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.trackName
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.itemmusic_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.itemmusic_detail.text = it.artistName
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
