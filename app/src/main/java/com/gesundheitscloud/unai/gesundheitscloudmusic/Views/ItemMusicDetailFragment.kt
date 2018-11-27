package com.gesundheitscloud.unai.gesundheitscloudmusic.Views

import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
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

    private var mediaPlayer : MediaPlayer? = null

    private var positionPlaylist : Int =0

    private var viewLayout : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = MusicContentManager.ITEM_MAP[it.getInt(ARG_ITEM_ID)]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.itemmusic_detail, container, false)
        viewLayout = rootView;
        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.trackNameTextView.text = it.trackName
            rootView.artistNameTextView.text =it.artistName;
            Glide.with(this)
                .load(it.artworkUrl100)
                .into(rootView.trackImageView)
                rootView.shareImageView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                        shareSong()
                }
                })
            rootView.nextButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    nextSong()
                }
            })
            rootView.skipButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    skipSong()
                }
            })

            rootView.resumButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    resumePauseSong(v as ImageView)
                }
            })
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer = MediaPlayer.create(this.context, Uri.parse(item!!.previewUrl))
        mediaPlayer!!.setOnPreparedListener(object : MediaPlayer.OnPreparedListener{
             override fun onPrepared(mp: MediaPlayer?) {
                mp!!.start()
            }
        })
        positionPlaylist=  MusicContentManager.ITEMS_SEARCH_ACTIVE.indexOf(item!!);
    }

     override fun onDestroyView() {
        super.onDestroyView()
         mediaPlayer!!.stop()
    }

    private fun nextSong()
    {
        if(positionPlaylist!=MusicContentManager.ITEMS_SEARCH_ACTIVE.count()-1)
        redrawView(MusicContentManager.ITEMS_SEARCH_ACTIVE.get(positionPlaylist+1))
    }

    private fun skipSong()
    {
        if(positionPlaylist!=0)
            redrawView(MusicContentManager.ITEMS_SEARCH_ACTIVE.get(positionPlaylist-1))
    }

    private fun resumePauseSong(buttonPlay :ImageView)
    {
        if(mediaPlayer!!.isPlaying)
        {
            mediaPlayer!!.pause()
            buttonPlay.setImageResource(android.R.drawable.ic_media_play)

        }
        else
        {
            mediaPlayer!!.start()
            buttonPlay.setImageResource(android.R.drawable.ic_media_pause)
        }
    }

    private fun redrawView(musicClientModel: MusicClientModel)
    {
        positionPlaylist++;
        viewLayout!!.trackNameTextView.text = musicClientModel.trackName
        viewLayout!!.artistNameTextView.text =musicClientModel.artistName;
        Glide.with(this)
            .load(musicClientModel.artworkUrl100)
            .into(viewLayout!!.trackImageView)
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        mediaPlayer = MediaPlayer.create(this.context, Uri.parse(musicClientModel!!.previewUrl))
        mediaPlayer!!.setOnPreparedListener(object : MediaPlayer.OnPreparedListener{
            override fun onPrepared(mp: MediaPlayer?) {
                mp!!.start()
            }
        })
    }

    private fun shareSong()
    {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"I am listening " + MusicContentManager.ITEMS_SEARCH_ACTIVE.get(positionPlaylist)!!.trackName +" with Gesundheitscloud Music app")
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
