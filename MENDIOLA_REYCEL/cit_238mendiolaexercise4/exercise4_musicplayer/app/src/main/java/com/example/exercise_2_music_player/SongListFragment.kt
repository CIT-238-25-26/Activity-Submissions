package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment

class SongListFragment : Fragment(R.layout.fragment_song_list) {
    private var listener: SelectedSongListener? = null
    private val songs = listOf(
        "Aetheric",
        "Dagored",
        "Lukrembo",
        "Moavii"
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectedSongListener) listener = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listView = view.findViewById<ListView>(R.id.songsListView)
        val adapter = SongAdapter(requireContext(), songs) { position ->
            listener?.onSongSelected(position)
        }
        listView.adapter = adapter
    }
}
