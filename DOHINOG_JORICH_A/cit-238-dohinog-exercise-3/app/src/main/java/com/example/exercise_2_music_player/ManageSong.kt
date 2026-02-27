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

    // UI elements
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var songStatusTextView: TextView

    // URL retrieve from the intent
    private var songUrl = ""

    // setup the exoplayer
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

        // Retrieve the song URL from the intent
        // Assuming intent extra "SONG_URL" contains the actual URL string
        songUrl = intent.getStringExtra("SONG_URL") ?: ""
        
        // Setup UI
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songStatusTextView = findViewById(R.id.songStatusTextView)

        // Setup the button listeners
        playButton.setOnClickListener {
            player?.play()
        }

        pauseButton.setOnClickListener {
            player?.pause()
        }

        stopButton.setOnClickListener {
            player?.stop()
            player?.seekTo(0)
            player?.prepare()
        }
    }

    private fun initializePlayer() {
        if (songUrl.isEmpty()) return

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            val mediaItem = MediaItem.fromUri(songUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            
            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    val songName = songUrl.substringAfterLast("/").substringBefore(".mp3")
                    when (state) {
                        Player.STATE_BUFFERING -> songStatusTextView.text = "Buffering: $songName"
                        Player.STATE_READY -> songStatusTextView.text = "Ready: $songName"
                        Player.STATE_IDLE -> songStatusTextView.text = "Idle: $songName"
                        Player.STATE_ENDED -> songStatusTextView.text = "Ended: $songName"
                    }
                }
                
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    val songName = songUrl.substringAfterLast("/").substringBefore(".mp3")
                    if (isPlaying) {
                        songStatusTextView.text = "Playing: $songName"
                    } else if (player?.playbackState != Player.STATE_ENDED) {
                        songStatusTextView.text = "Paused: $songName"
                    }
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }
}
