package com.gesundheitscloud.unai.gesundheitscloudmusic.Views.Adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gesundheitscloud.unai.gesundheitscloudmusic.Models.ClientModels.MusicClientModel
import com.gesundheitscloud.unai.gesundheitscloudmusic.R
import com.gesundheitscloud.unai.gesundheitscloudmusic.Views.ItemMusicDetailActivity
import com.gesundheitscloud.unai.gesundheitscloudmusic.Views.ItemMusicDetailFragment
import com.gesundheitscloud.unai.gesundheitscloudmusic.Views.ItemMusicListActivity
import kotlinx.android.synthetic.main.itemmusic_list_content.view.*
import java.util.concurrent.TimeUnit

class SimpleItemRecyclerViewAdapter(
    private val parentActivity: ItemMusicListActivity,
    private val values: List<MusicClientModel>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as MusicClientModel
            if (twoPane) {
                val fragment = ItemMusicDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemMusicDetailFragment.ARG_ITEM_ID, item.trackId.toString())
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.itemmusic_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemMusicDetailActivity::class.java).apply {
                    putExtra(ItemMusicDetailFragment.ARG_ITEM_ID, item.trackId)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemmusic_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.songTitle.text = item.trackName
        holder.songArtist.text = item.artistName
        holder.songGenre.text = "Genre : "+ item.primaryGenreName;
        holder.songPrice.text = "Price : " + item.trackPrice
        setDuration(item.trackTimeMillis,holder.songDuration)
        Glide.with(this.parentActivity)
            .load(item.artworkUrl100)
            .into(holder.songImageView)
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    private fun setDuration(duration :String?, songDuration : TextView)
    {
        val hms = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(duration!!.toLong()) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration!!.toLong())),
            TimeUnit.MILLISECONDS.toSeconds(duration!!.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration!!.toLong()))
        )

        songDuration.text = "Duration : "+hms
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songTitle: TextView = view.songTitle
        val songArtist: TextView = view.songArtist
        val songImageView: ImageView = view.songImageView
        val songDuration: TextView = view.songDuration
        val songPrice: TextView = view.songPrice
        val songGenre: TextView = view.songGenre
    }
}