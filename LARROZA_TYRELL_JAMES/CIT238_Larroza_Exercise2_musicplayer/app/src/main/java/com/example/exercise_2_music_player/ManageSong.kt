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

//    URL retrieve from the intent
    private var songUrl = ""

    //    setup the exoplayer
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

//        Retrieve the song URL from the intent
        songUrl = intent.getStringExtra("SONG") ?: ""

//        Setup the button functions
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

//        Setup the Song title
        val songTitle = songUrl.substringBefore(" - ")
        val textView = findViewById<TextView>(R.id.songTitle)
        textView.text = songTitle
    }

    override fun onStart() {
        super.onStart()

        // Setup the ExoPlayer
        player = ExoPlayer.Builder(this).build()

        player.stop()

        // Setup the Song title
        songUrl = intent.getStringExtra("SONG") ?: ""
        val songTitle = songUrl.substringBefore(" - ")
        val textView = findViewById<TextView>(R.id.songTitle)

        // Setup the media item to play using the URL retrieved from the intent
        val mediaItem = MediaItem.fromUri(songUrl.substringAfter(" - "))
        player.setMediaItem(mediaItem)
        player.prepare()

        // This listener will update the song title based on the player's state
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    playButton.isEnabled = false
                    pauseButton.isEnabled = true
                } else {
                    playButton.isEnabled = true
                    pauseButton.isEnabled = false
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_BUFFERING) {
                    val msg = "Buffering: $songTitle"
                    textView.text = msg
                }
                if(state == Player.STATE_READY) {
                }
                if (state == Player.STATE_IDLE) {
                    val msg = "Idle"
                    textView.text = msg
                }
                if (state == Player.STATE_ENDED) {
                    val msg = "Song Ended"
                    textView.text = msg
                }
            }
        })

        // Setup the button listeners
        playButton.setOnClickListener {
            if (!player.isPlaying && player.playbackState == Player.STATE_IDLE) {
                player.prepare()
            }
            player.play()
            val msg = "Playing Now: $songTitle"
            textView.text = msg
        }

        pauseButton.setOnClickListener {
            player.pause()
            val msg = "Paused: $songTitle"
            textView.text = msg
        }

        stopButton.setOnClickListener {
            player.stop()
            val msg = "Stopped: $songTitle"
            textView.text = msg

            // Reset the music
            player.seekTo(0)
        }
    }

}
