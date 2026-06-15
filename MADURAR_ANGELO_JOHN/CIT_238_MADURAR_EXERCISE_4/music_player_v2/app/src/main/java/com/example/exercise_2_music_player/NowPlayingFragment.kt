package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NowPlayingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle  = view.findViewById<TextView>(R.id.tvNowPlayingTitle)
        val tvArtist = view.findViewById<TextView>(R.id.tvNowPlayingArtist)
        val btnFav   = view.findViewById<ImageView>(R.id.btnFavorite)

        val rvQueue = view.findViewById<RecyclerView>(R.id.rvNowPlayingQueue)
        rvQueue.layoutManager = LinearLayoutManager(requireContext())

        val allSongs = SongRepository.songs + SongRepository.favoriteSongs
        rvQueue.adapter = SongAdapter(allSongs) { song ->
            tvTitle.text  = song.title
            tvArtist.text = song.artist

            val intent = Intent(requireContext(), ManageSong::class.java)
            intent.putExtra("SONG_URL", "${song.title} - ${song.url}")
            startActivity(intent)
        }

        // Use ContextCompat.getColor — works on all API levels
        btnFav.setOnClickListener {
            btnFav.setImageResource(R.drawable.ic_favorite)
            btnFav.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.spotify_green)
            )
        }
    }
}
