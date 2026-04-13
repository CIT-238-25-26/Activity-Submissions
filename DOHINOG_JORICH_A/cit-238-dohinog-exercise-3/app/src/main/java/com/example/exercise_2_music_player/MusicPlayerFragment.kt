package com.example.exercise_2_music_player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayerFragment : Fragment() {

    interface OnPlayerControlListener {
        fun onNextRequested()
        fun onPreviousRequested()
    }

    private var controlListener: OnPlayerControlListener? = null
    private var player: ExoPlayer? = null
    private lateinit var songStatusTextView: TextView
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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music_player, container, false)

        songStatusTextView = view.findViewById(R.id.songStatusTextView)
        val playButton = view.findViewById<Button>(R.id.playButton)
        val pauseButton = view.findViewById<Button>(R.id.pauseButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)
        val prevButton = view.findViewById<Button>(R.id.prevButton)
        val nextButton = view.findViewById<Button>(R.id.nextButton)

        playButton.setOnClickListener { player?.play() }
        pauseButton.setOnClickListener { player?.pause() }
        stopButton.setOnClickListener {
            player?.stop()
            player?.seekTo(0)
            player?.prepare()
        }
        prevButton.setOnClickListener { controlListener?.onPreviousRequested() }
        nextButton.setOnClickListener { controlListener?.onNextRequested() }

        return view
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
        val url = currentSongUrl ?: return
        val songName = url.substringAfterLast("/").substringBefore(".mp3")
        val state = player?.playbackState
        val isPlaying = player?.isPlaying ?: false

        when {
            isPlaying -> songStatusTextView.text = "Playing: $songName"
            state == Player.STATE_BUFFERING -> songStatusTextView.text = "Buffering: $songName"
            state == Player.STATE_READY -> songStatusTextView.text = "Ready: $songName"
            state == Player.STATE_ENDED -> songStatusTextView.text = "Ended: $songName"
            else -> songStatusTextView.text = "Paused: $songName"
        }
    }

    override fun onDetach() {
        super.onDetach()
        controlListener = null
    }
}
