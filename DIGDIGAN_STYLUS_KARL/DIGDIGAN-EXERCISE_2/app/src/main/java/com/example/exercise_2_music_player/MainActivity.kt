package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //    List of songs
    private lateinit var songsListView: ListView
    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
        "Song 4 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 5 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 6 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
        "Song 7 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 8 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 9 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }

//        Setup the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

//        Put a click listener on the ListView to open the ManageSong activity when a song is clicked
        songsListView.setOnItemClickListener { parent, view, position, id ->
            // Get the selected song string (e.g. "Song 1 - https://...")
            val selectedSong = songs[position]

            // Create an intent to navigate to ManageSong activity
            val intent = Intent(this, ManageSong::class.java)

            // Pass the selected song string to the ManageSong activity
            intent.putExtra("SONG_URL", selectedSong)

            startActivity(intent)
        }
    }
}