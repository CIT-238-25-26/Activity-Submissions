package com.example.exercise_2_music_player

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class ManageSong : AppCompatActivity() {

    private lateinit var playButton: FrameLayout
    private lateinit var pauseButton: FrameLayout
    private lateinit var stopButton: FrameLayout
    private lateinit var songTitleTextView: TextView
    private lateinit var statusTextView: TextView

    private var songUrl = ""
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        songUrl = intent.getStringExtra("SONG_URL") ?: ""

        playButton        = findViewById(R.id.playButton)
        pauseButton       = findViewById(R.id.pauseButton)
        stopButton        = findViewById(R.id.stopButton)
        songTitleTextView = findViewById(R.id.songTitle)
        statusTextView    = findViewById(R.id.statusTextView)

        songTitleTextView.text = songUrl.substringBefore(" - ")

        playButton.setOnClickListener {
            if (!player.isPlaying && player.playbackState == Player.STATE_IDLE) {
                player.prepare()
            }
            player.play()
        }

        pauseButton.setOnClickListener {
            player.pause()
        }

        stopButton.setOnClickListener {
            player.stop()
            player.seekTo(0)
        }
    }

    override fun onStart() {
        super.onStart()

        player = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri(songUrl.substringAfter(" - "))
        player.setMediaItem(mediaItem)
        player.prepare()

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    statusTextView.text = "Playing"
                } else {
                    if (player.playbackState == Player.STATE_READY) {
                        statusTextView.text = "Paused"
                    }
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> statusTextView.text = "Buffering..."
                    Player.STATE_READY     -> statusTextView.text =
                        if (player.isPlaying) "Playing" else "Ready"
                    Player.STATE_IDLE      -> statusTextView.text = "Stopped"
                    Player.STATE_ENDED     -> statusTextView.text = "Ended"
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        player.pause()
    }

    override fun onResume() {
        super.onResume()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
