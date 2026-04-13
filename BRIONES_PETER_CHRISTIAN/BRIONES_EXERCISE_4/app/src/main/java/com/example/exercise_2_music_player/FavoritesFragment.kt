package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class FavoritesFragment : Fragment() {

    private var listener: MusicPlayerInterface? = null
    private lateinit var listView: ListView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MusicPlayerInterface) {
            listener = context
        } else {
            throw RuntimeException("$context must implement MusicPlayerInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        listView = view.findViewById(R.id.favoritesListView)

        refreshFavorites()

        listView.setOnItemClickListener { _, _, position, _ ->
            val favorites = (activity as? MainActivity)?.getFavorites() ?: emptyList()
            if (position < favorites.size) {
                listener?.onSongSelected(favorites[position])
            }
        }

        return view
    }

    fun refreshFavorites() {
        if (!::listView.isInitialized) return
        val favorites = (activity as? MainActivity)?.getFavorites() ?: emptyList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, favorites)
        listView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}