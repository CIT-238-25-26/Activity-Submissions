package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), 
    MusicListFragment.OnSongSelectedListener, 
    MusicPlayerFragment.OnPlayerControlListener {

    private val songs = listOf(
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )
    
    private var currentSongIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }

    override fun onSongSelected(index: Int) {
        currentSongIndex = index
        playCurrentSong()
    }

    override fun onNextRequested() {
        if (songs.isNotEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % songs.size
            playCurrentSong()
        }
    }

    override fun onPreviousRequested() {
        if (songs.isNotEmpty()) {
            currentSongIndex = if (currentSongIndex <= 0) songs.size - 1 else currentSongIndex - 1
            playCurrentSong()
        }
    }

    private fun playCurrentSong() {
        if (currentSongIndex in songs.indices) {
            val playerFragment = supportFragmentManager.findFragmentById(R.id.playerFragmentContainer) as? MusicPlayerFragment
            playerFragment?.playSong(songs[currentSongIndex])
        }
    }
}
