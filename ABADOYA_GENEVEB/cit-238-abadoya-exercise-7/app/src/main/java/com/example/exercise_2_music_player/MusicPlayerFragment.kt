package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.exercise_2_music_player.databinding.FragmentMusicPlayerBinding

class MusicPlayerFragment : Fragment() {

    interface OnPlayerControlListener {
        fun onNextRequested()
        fun onPreviousRequested()
    }

    private var controlListener: OnPlayerControlListener? = null
    private var player: ExoPlayer? = null
    private var _binding: FragmentMusicPlayerBinding? = null
    private val binding get() = _binding!!
    private var currentSongUrl: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlayerControlListener) {
            controlListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicPlayerBinding.inflate(inflater, container, false)

        binding.playButton.setOnClickListener { player?.play() }
        binding.pauseButton.setOnClickListener { player?.pause() }
        binding.prevButton.setOnClickListener { controlListener?.onPreviousRequested() }
        binding.nextButton.setOnClickListener { controlListener?.onNextRequested() }
        binding.stopButton.setOnClickListener {
            player?.stop()
            player?.seekTo(0)
            player?.prepare()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        updateStatus()
                    }
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        updateStatus()
                    }
                })
            }
        }
        currentSongUrl?.let { playSong(it) }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    fun playSong(url: String) {
        currentSongUrl = url
        val player = this.player ?: return
        
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun updateStatus() {
        if (_binding == null) return
        val url = currentSongUrl ?: return
        val songName = url.substringAfterLast("/").substringBefore(".mp3")
        val isPlaying = player?.isPlaying ?: false

        binding.songStatusTextView.text = songName
        
        if (isPlaying) {
            binding.playButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            binding.songProgress.isIndeterminate = true
        } else {
            binding.playButton.visibility = View.VISIBLE
            binding.pauseButton.visibility = View.GONE
            binding.songProgress.isIndeterminate = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        controlListener = null
    }
}
