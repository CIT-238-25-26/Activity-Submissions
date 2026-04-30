package com.example.exercise_2_music_player

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class SongAdapter(
    context: Context,
    private val songs: List<Song>,
    private val onFavoriteClick: (Int) -> Unit,
    private val onSongClick: (Int) -> Unit
) : ArrayAdapter<Song>(context, R.layout.item_song, songs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        val song = songs[position]

        val titleTextView = view.findViewById<TextView>(R.id.songTitle)
        val favoriteButton = view.findViewById<ImageButton>(R.id.favoriteButton)

        titleTextView.text = song.title
        
        if (song.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
            favoriteButton.setColorFilter(Color.RED)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            favoriteButton.setColorFilter(Color.BLACK)
        }

        favoriteButton.setOnClickListener {
            onFavoriteClick(position)
        }

        view.setOnClickListener {
            onSongClick(position)
        }

        return view
    }
}