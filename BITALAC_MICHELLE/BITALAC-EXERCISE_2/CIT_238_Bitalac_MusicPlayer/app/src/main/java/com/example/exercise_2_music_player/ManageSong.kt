package com.example.exercise_2_music_player

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class ManageSong : AppCompatActivity() {

    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var songTitleTextView: TextView
    private lateinit var statusTextView: TextView

    private var songUrl: String = ""
    private var player: ExoPlayer? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.manage_song)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songTitleTextView = findViewById(R.id.songTitleTextView)
        statusTextView = findViewById(R.id.statusTextView)

        songUrl = intent.getStringExtra("SONG_URL") ?: ""

        val songTitle = songUrl.substringAfterLast("/").substringBefore(".")
        songTitleTextView.text = songTitle

        // PLAY button
        playButton.setOnClickListener {
            player?.play()
            isPlaying = true
            statusTextView.text = "Playing"
        }

        // PAUSE button
        pauseButton.setOnClickListener {
            player?.pause()
            isPlaying = false
            statusTextView.text = "Paused"
        }

        // STOP button → Stop + Go Back
        stopButton.setOnClickListener {
            player?.stop()
            player?.release()
            player = null
            statusTextView.text = "Stopped"
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        player = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri(songUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()

        // 👇 AUTO PLAY when activity opens
        player?.play()
        isPlaying = true
        statusTextView.text = "Playing"

        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    statusTextView.text = "Finished"
                    isPlaying = false
                }
            }
        })
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
