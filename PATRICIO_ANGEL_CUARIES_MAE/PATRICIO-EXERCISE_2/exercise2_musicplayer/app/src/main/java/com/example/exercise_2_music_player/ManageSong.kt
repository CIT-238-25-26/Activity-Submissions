package com.example.exercise_2_music_player

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class ManageSong : AppCompatActivity() {

    // UI elements
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var songTitleTextView: TextView

    // ExoPlayer
    private lateinit var player: ExoPlayer

    // Song data
    private var songUrl: String = ""
    private var songTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve data from intent
        songTitle = intent.getStringExtra("SONG_TITLE") ?: "Unknown Song"
        songUrl = intent.getStringExtra("SONG_URL") ?: ""

        // Initialize views
        songTitleTextView = findViewById(R.id.songTitle)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

        // Set initial title
        songTitleTextView.text = songTitle

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()

        val mediaItem = MediaItem.fromUri(songUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

        // Player state listener
        player.addListener(object : Player.Listener {

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    songTitleTextView.text = "$songTitle - Playing"
                } else {
                    if (player.playbackState != Player.STATE_IDLE &&
                        player.playbackState != Player.STATE_ENDED
                    ) {
                        songTitleTextView.text = "$songTitle - Paused"
                    }
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_IDLE ||
                    state == Player.STATE_ENDED
                ) {
                    songTitleTextView.text = "$songTitle - Stopped"
                }
            }
        })

        // Button listeners
        playButton.setOnClickListener {
            if (player.playbackState == Player.STATE_IDLE) {
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
            songTitleTextView.text = "$songTitle - Stopped"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}