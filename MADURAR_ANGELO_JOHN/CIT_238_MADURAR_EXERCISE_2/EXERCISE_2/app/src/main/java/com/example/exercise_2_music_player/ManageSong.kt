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

    //    UI elements
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var songTitleTextView: TextView
    private lateinit var statusTextView: TextView

    //    URL retrieved from the intent
    private var songUrl = ""

    //    Setup the ExoPlayer
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }

//        a. Retrieve the song URL from the intent
        songUrl = intent.getStringExtra("SONG_URL") ?: ""

//        b. Setup the button references
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songTitleTextView = findViewById(R.id.songTitle)
        statusTextView = findViewById(R.id.statusTextView)

//        c. Setup the Song title — use substringBefore to get the name (text before " - ")
        songTitleTextView.text = songUrl.substringBefore(" - ")

//        Setup the button listeners
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
//            Reset the music
            player.seekTo(0)
        }
    }

    //    3a. onStart — initialize ExoPlayer here (moved from onCreate)
    override fun onStart() {
        super.onStart()

        player = ExoPlayer.Builder(this).build()

//        d. Put the song URL to the media item
        val mediaItem = MediaItem.fromUri(songUrl.substringAfter(" - "))
        player.setMediaItem(mediaItem)
        player.prepare()

//        e. addListener — update the statusTextView based on the player's state
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    statusTextView.text = "Playing"
                } else {
                    // Only show "Paused" when the player is ready but not playing
                    if (player.playbackState == Player.STATE_READY) {
                        statusTextView.text = "Paused"
                    }
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> statusTextView.text = "Buffering..."
                    Player.STATE_READY     -> statusTextView.text = if (player.isPlaying) "Playing" else "Paused"
                    Player.STATE_IDLE      -> statusTextView.text = "Stopped"
                    Player.STATE_ENDED     -> statusTextView.text = "Ended"
                }
            }
        })
    }

    //    3b. onPause — pause the music
    override fun onPause() {
        super.onPause()
        player.pause()
    }

    //    3c. onResume — play the music
    override fun onResume() {
        super.onResume()
        player.play()
    }

    //    3d. onDestroy — release the player
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}