package com.example.exercise_2_music_player

import android.os.Bundle
import android.widget.Toast
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

    // UI elements
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var songStatusTextView: TextView

    // URL retrieve from the intent
    private var songUrl = ""

    // setup the exoplayer
    private lateinit var player: ExoPlayer

    // Track if this is the first resume
    private var isFirstResume = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }

        // Retrieve the song URL from the intent and extract just the URL part
        val fullString = intent.getStringExtra("SONG_URL") ?: ""
        songUrl = fullString.substringAfter(" - ")

        // Setup the button functions
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songStatusTextView = findViewById(R.id.songStatusTextView)

        // Setup the Song title (use substringBefore to get the name)
        val songTitle = songUrl.substringBefore(".mp3").substringAfterLast("/")
        songStatusTextView.text = songTitle

        // Setup the button listeners
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
            // Reset the music
            player.seekTo(0)
        }
    }

    override fun onStart() {
        super.onStart()

        // Setup the ExoPlayer
        player = ExoPlayer.Builder(this).build()

        // Put the song URL to the media Item
        val mediaItem = MediaItem.fromUri(songUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

        // Complete the addListener function - update text view based on player state
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                val songName = songUrl.substringBefore(".mp3").substringAfterLast("/")
                if (isPlaying) {
                    songStatusTextView.text = "Playing: $songName"
                } else {
                    songStatusTextView.text = "Paused: $songName"
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                val songName = songUrl.substringBefore(".mp3").substringAfterLast("/")
                when (state) {
                    Player.STATE_BUFFERING -> {
                        songStatusTextView.text = "Buffering: $songName"
                    }
                    Player.STATE_READY -> {
                        songStatusTextView.text = "Ready: $songName"
                    }
                    Player.STATE_IDLE -> {
                        songStatusTextView.text = "Idle: $songName"
                    }
                    Player.STATE_ENDED -> {
                        songStatusTextView.text = "Ended: $songName"
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        // Pause the Music
        player.pause()
    }

    override fun onResume() {
        super.onResume()
        // Play the music (but not on first resume)
        if (!isFirstResume) {
            player.play()
        }
        isFirstResume = false
    }

    override fun onDestroy() {
        super.onDestroy()
        // Call the release() method of the player
        player.release()
    }
}