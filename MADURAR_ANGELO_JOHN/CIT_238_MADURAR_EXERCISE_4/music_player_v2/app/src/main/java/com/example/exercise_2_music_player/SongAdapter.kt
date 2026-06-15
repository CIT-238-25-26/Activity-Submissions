package com.example.exercise_2_music_player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val songs: List<Song>,
    private val onSongClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView  = itemView.findViewById(R.id.tvSongTitle)
        val tvArtist: TextView = itemView.findViewById(R.id.tvSongArtist)
        val btnFav: ImageView  = itemView.findViewById(R.id.btnFavoriteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.tvTitle.text  = song.title
        holder.tvArtist.text = song.artist

        // Reflect current favorite state
        updateHeartIcon(holder.btnFav, song.isFavorite)

        holder.btnFav.setOnClickListener {
            // Toggle in repository (adds/removes from favoriteSongs list)
            SongRepository.toggleFavorite(song)
            // Redraw this row's heart
            updateHeartIcon(holder.btnFav, song.isFavorite)
        }

        holder.itemView.setOnClickListener {
            onSongClick(song)
        }
    }

    private fun updateHeartIcon(view: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            view.setImageResource(R.drawable.ic_favorite)
            view.setColorFilter(
                ContextCompat.getColor(view.context, R.color.spotify_green)
            )
        } else {
            view.setImageResource(R.drawable.ic_favorite_border)
            view.clearColorFilter()
        }
    }

    override fun getItemCount(): Int = songs.size
}