package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    private lateinit var adapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvFavorites = view.findViewById<RecyclerView>(R.id.rvFavorites)
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())

        // Adapter always points at the live favoriteSongs list in the repository
        adapter = SongAdapter(SongRepository.favoriteSongs) { song ->
            val intent = Intent(requireContext(), ManageSong::class.java)
            intent.putExtra("SONG_URL", "${song.title} - ${song.url}")
            startActivity(intent)
        }
        rvFavorites.adapter = adapter
    }

    // Refresh the list every time this tab becomes visible
    // so songs hearted from My Music appear immediately
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}