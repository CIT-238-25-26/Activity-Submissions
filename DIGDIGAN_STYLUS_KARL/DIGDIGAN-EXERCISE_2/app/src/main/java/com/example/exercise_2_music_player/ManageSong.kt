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
    private lateinit var songStatusTextView: TextView

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

        // 2a. Retrieve the song URL from the intent
        // The full string is e.g. "Song 1 - https://..."
        val songData = intent.getStringExtra("SONG_URL") ?: ""

        // 2c. Get the song name using substringBefore (everything before " - ")
        val songTitle = songData.substringBefore(" - ")

        // Get the actual URL (everything after " - ")
        songUrl = songData.substringAfter(" - ")

        // 2b. Setup the button references
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        songTitleTextView = findViewById(R.id.songTitle)
        songStatusTextView = findViewById(R.id.songStatus)

        // 2c. Display the song title
        songTitleTextView.text = songTitle

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
            // Reset the music back to the beginning
            player.seekTo(0)
        }
    }

    // 3a. Move ExoPlayer initialization to onStart()
    override fun onStart() {
        super.onStart()

        // Initialize ExoPlayer
        player = ExoPlayer.Builder(this).build()

        // 2d. Put the song URL to the media item
        val mediaItem = MediaItem.fromUri(songUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

        // 2e. Add listener to update the status TextView based on player state
        player.addListener(object : Player.Listener {

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    songStatusTextView.text = "Status: Playing"
                } else {
                    // Only show Paused if the player is not in IDLE or ENDED state
                    if (player.playbackState != Player.STATE_IDLE &&
                        player.playbackState != Player.STATE_ENDED) {
                        songStatusTextView.text = "Status: Paused"
                    }
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> songStatusTextView.text = "Status: Buffering..."
                    Player.STATE_READY     -> songStatusTextView.text = "Status: Ready"
                    Player.STATE_IDLE      -> songStatusTextView.text = "Status: Idle"
                    Player.STATE_ENDED     -> songStatusTextView.text = "Status: Ended"
                }
            }
        })
    }

    // 3b. Pause the music when the activity is paused
    override fun onPause() {
        super.onPause()
        if (::player.isInitialized) {
            player.pause()
        }
    }

    // 3c. Resume/play the music when the activity resumes
    override fun onResume() {
        super.onResume()
        if (::player.isInitialized) {
            player.play()
        }
    }

    // 3d. Release the player when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        if (::player.isInitialized) {
            player.release()
        }
    }
}