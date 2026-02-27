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
    private lateinit var songTitle: TextView

//    URL retrieve from the intent
    private  var songUrl = ""
    private  var songName = ""

    //    setup the exoplayer
    private lateinit var player: ExoPlayer

//    Track if player was playing before going to background
    private var wasPlaying = false

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
        songUrl = intent.getStringExtra("SONG_URL") ?: ""

//        Setup the button functions
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songTitle = findViewById(R.id.songTitle)

//        Setup the Song title
        songName = songUrl.substringBefore(" - ")
        songTitle.text = songName

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

    override fun onStart() {
        super.onStart()

//        Setup the ExoPlayer
        player = ExoPlayer.Builder(this).build()
//        Setup the media item to play using the URL retrieved from the intent
        val actualUrl = songUrl.substringAfter(" - ")
        val mediaItem = MediaItem.fromUri(actualUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

//        This listener will update the song title based on the player's state
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    songTitle.text = "$songName - Playing"
                } else {
                    songTitle.text = "$songName - Paused"
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_BUFFERING) {
                    songTitle.text = "$songName - Buffering..."
                }
                if(state == Player.STATE_READY) {
                    songTitle.text = "$songName - Ready"
                    // Auto-resume playback if it was playing before
                    if (wasPlaying) {
                        player.play()
                        wasPlaying = false // Reset flag after resuming
                    }
                }
                if (state == Player.STATE_IDLE) {
                    songTitle.text = "$songName - Idle"
                }
                if (state == Player.STATE_ENDED) {
                    songTitle.text = "$songName - Ended"
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Auto-resume is now handled in onStart when player reaches STATE_READY
    }

    override fun onPause() {
        super.onPause()
        // Remember if player was playing before pausing
        wasPlaying = player.isPlaying
        player.pause()
        // Pause the music
    }

    override fun onStop() {
        super.onStop()
        // Release the player when activity is no longer visible
        player.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Player is already released in onStop
    }


}
