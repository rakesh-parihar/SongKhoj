package com.musickhoj.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musickhoj.R
import com.musickhoj.api.model.Songs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_song_list.view.*
import java.util.*

class SongAdapter(private val listener: Listener) :
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var dataList: ArrayList<Songs> = arrayListOf()

    interface Listener {
        fun onItemClick(songModel: Songs)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], listener, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_list, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(song: Songs, listener: Listener, position: Int) {

            itemView.txt_title.text = song.trackName
            itemView.txt_artist.text = song.artistName
            itemView.txt_album.text = song.primaryGenreName
            Picasso.get().load(song.artworkUrl100).placeholder(R.drawable.ic_music).into(itemView.img_thumb)

            itemView.setOnClickListener { listener.onItemClick(song) }
        }
    }

    fun setSongs(dataList: List<Songs>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

}