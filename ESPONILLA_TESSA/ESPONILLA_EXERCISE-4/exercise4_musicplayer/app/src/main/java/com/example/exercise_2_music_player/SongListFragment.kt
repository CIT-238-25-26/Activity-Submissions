package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise_2_music_player.databinding.FragmentSongListBinding

class SongListFragment : Fragment() {

    private var _binding: FragmentSongListBinding? = null
    private val binding get() = _binding!!
    
    private var listener: OnSongSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnSongSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateSongList()
    }

    private fun updateSongList() {
        val activity = requireActivity() as MainActivity
        
        val adapter = SongAdapter(
            requireContext(),
            activity.songs,
            onFavoriteClick = { position ->
                val song = activity.songs[position]
                song.isFavorite = !song.isFavorite
                updateSongList()
            },
            onSongClick = { position ->
                listener?.onSongSelected(position)
            }
        )
        binding.songsListView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}