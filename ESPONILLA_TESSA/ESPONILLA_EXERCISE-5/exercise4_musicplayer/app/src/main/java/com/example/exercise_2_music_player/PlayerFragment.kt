package com.example.exercise_2_music_player

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.exercise_2_music_player.databinding.FragmentPlayerBinding

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var player: ExoPlayer
    private var currentIndex: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        player = ExoPlayer.Builder(requireContext()).build()
        
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    binding.songStatusTextView.text = "Playing"
                    binding.playButton.visibility = View.GONE
                    binding.pauseButton.visibility = View.VISIBLE
                } else {
                    if (player.playbackState != Player.STATE_IDLE) {
                        binding.songStatusTextView.text = "Paused"
                    }
                    binding.playButton.visibility = View.VISIBLE
                    binding.pauseButton.visibility = View.GONE
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                binding.songStatusTextView.text = when (state) {
                    Player.STATE_BUFFERING -> "Buffering..."
                    Player.STATE_READY -> "Ready"
                    Player.STATE_IDLE -> "Idle"
                    Player.STATE_ENDED -> {
                        binding.playButton.visibility = View.VISIBLE
                        binding.pauseButton.visibility = View.GONE
                        "Ended"
                    }
                    else -> ""
                }
            }
        })

        binding.playButton.setOnClickListener {
            when (player.playbackState) {
                Player.STATE_IDLE -> { player.prepare(); player.play() }
                Player.STATE_ENDED -> { player.seekTo(0); player.play() }
                else -> player.play()
            }
        }

        binding.pauseButton.setOnClickListener { player.pause() }

        binding.stopButton.setOnClickListener {
            player.stop()
            player.seekTo(0)
            binding.playButton.visibility = View.VISIBLE
            binding.pauseButton.visibility = View.GONE
            binding.songStatusTextView.text = "Stopped"
        }

        binding.prevButton.setOnClickListener { playPrevious() }
        binding.nextButton.setOnClickListener { playNext() }
        
        binding.favoriteButton.setOnClickListener {
            toggleFavorite()
        }
        
        val songIndex = arguments?.getInt("songIndex") ?: -1
        if (songIndex != -1) {
            currentIndex = songIndex
            loadSongByIndex(currentIndex)
        }
    }

    private fun loadSongByIndex(index: Int) {
        val activity = requireActivity() as MainActivity
        if (index in activity.songs.indices) {
            val song = activity.songs[index]
            binding.songTitleTextView.text = song.title
            updateFavoriteIcon(song.isFavorite)
            
            player.stop()
            player.setMediaItem(MediaItem.fromUri(song.url))
            player.prepare()
            player.play()
        }
    }

    private fun toggleFavorite() {
        val activity = requireActivity() as MainActivity
        if (currentIndex in activity.songs.indices) {
            val song = activity.songs[currentIndex]
            song.isFavorite = !song.isFavorite
            updateFavoriteIcon(song.isFavorite)
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.favoriteButton.setImageResource(R.drawable.ic_favorite)
            binding.favoriteButton.setColorFilter(Color.RED)
        } else {
            binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            binding.favoriteButton.setColorFilter(Color.BLACK)
        }
    }
    
    private fun playPrevious() {
        val activity = requireActivity() as MainActivity
        currentIndex = (currentIndex - 1 + activity.songs.size) % activity.songs.size
        loadSongByIndex(currentIndex)
    }
    
    private fun playNext() {
        val activity = requireActivity() as MainActivity
        currentIndex = (currentIndex + 1) % activity.songs.size
        loadSongByIndex(currentIndex)
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
        _binding = null
    }
}