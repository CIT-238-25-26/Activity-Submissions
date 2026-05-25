package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class ManageSong : AppCompatActivity() {
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var songTitle: TextView
    private var songUrl = ""
    private var songName = ""
    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }


        val songData = intent.getStringExtra("song") ?: ""
        songName = songData.substringBefore(" - ")
        songUrl = songData.substringAfter(" - ")


        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songTitle = findViewById(R.id.songTitle)
        songTitle.text = songName
        playButton.setOnClickListener {
            player?.let {
                if (!it.isPlaying && it.playbackState == Player.STATE_IDLE) {
                    it.prepare()
                }
                it.play()
            }
        }

        pauseButton.setOnClickListener {
            player?.pause()
        }

        stopButton.setOnClickListener {
            player?.let {
                it.stop()
                it.seekTo(0)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(this).build().also { exoPlayer ->
                val mediaItem = MediaItem.fromUri(songUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        updateStatus()
                    }

                    override fun onPlaybackStateChanged(state: Int) {
                        updateStatus()
                    }
                })
            }
        }
    }

    private fun updateStatus() {
        val status = when {
            player?.playbackState == Player.STATE_BUFFERING -> "Buffering"
            player?.playbackState == Player.STATE_ENDED -> "Ended"
            player?.playbackState == Player.STATE_IDLE -> "Idle"
            player?.isPlaying == true -> "Playing"
            else -> "Paused"
        }
        songTitle.text = "$songName - $status"
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

}
