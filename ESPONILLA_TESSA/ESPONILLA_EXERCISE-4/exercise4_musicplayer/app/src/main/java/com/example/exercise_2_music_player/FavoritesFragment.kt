package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise_2_music_player.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFavoritesList()
    }

    private fun updateFavoritesList() {
        val activity = requireActivity() as MainActivity
        val favorites = activity.songs.filter { it.isFavorite }

        val adapter = SongAdapter(
            requireContext(),
            favorites,
            onFavoriteClick = { position ->
                // Find the actual song in the main list and toggle it
                val song = favorites[position]
                song.isFavorite = !song.isFavorite
                updateFavoritesList()
            },
            onSongClick = { position ->
                val song = favorites[position]
                val mainIndex = activity.songs.indexOf(song)
                activity.onSongSelected(mainIndex)
            }
        )
        binding.favoritesListView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}