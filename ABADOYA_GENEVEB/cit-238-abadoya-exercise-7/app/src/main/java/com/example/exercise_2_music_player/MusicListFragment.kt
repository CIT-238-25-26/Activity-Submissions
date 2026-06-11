package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.exercise_2_music_player.databinding.FragmentMusicListBinding

class MusicListFragment : Fragment() {

    interface OnSongSelectedListener {
        fun onSongSelected(index: Int)
    }

    private var listener: OnSongSelectedListener? = null
    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MusicViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSongSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSongSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicListBinding.inflate(inflater, container, false)
        
        val adapter = MusicAdapter(
            onSongClick = { song ->
                val index = viewModel.songs.value?.indexOf(song) ?: -1
                if (index != -1) listener?.onSongSelected(index)
            },
            onFavoriteClick = { song ->
                viewModel.toggleFavorite(song)
            }
        )
        
        binding.songsRecyclerView.adapter = adapter
        
        viewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.submitList(songs)
        }

        return binding.root
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
