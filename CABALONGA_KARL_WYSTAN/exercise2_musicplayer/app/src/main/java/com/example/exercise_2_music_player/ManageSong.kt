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
    private lateinit var songTitleText: TextView

    //    URL retrieve from the intent
    private var songUrl = ""

    //    setup the exoplayer
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

        val fullData = intent.getStringExtra("extra_song_url") ?: ""

        val title = fullData.substringBefore(" - ")
        songUrl = fullData.substringAfter(" - ")

        songTitleText = findViewById(R.id.songTitle)
        songTitleText.text = title


        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)

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
        player = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri(songUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    songTitleText.text = "${songTitleText.text} - Playing"
                } else {
                    songTitleText.text = "${songTitleText.text} - Paused"
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_BUFFERING) {
                    songTitleText.text = "${songTitleText.text} - Buffering"
                }
                if(state == Player.STATE_READY) {
                    songTitleText.text = "${songTitleText.text} - Ready"
                }
                if (state == Player.STATE_IDLE) {
                    songTitleText.text = "${songTitleText.text} - Stopped"
                }
                if (state == Player.STATE_ENDED) {
                    songTitleText.text = "${songTitleText.text} - Ended"
                }
            }
        })

    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}
