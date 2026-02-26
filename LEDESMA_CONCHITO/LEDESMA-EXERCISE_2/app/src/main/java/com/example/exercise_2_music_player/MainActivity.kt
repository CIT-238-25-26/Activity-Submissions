package com.example.exercise_2_music_player

import android.content.Intent // Added import for Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // List of songs
    private lateinit var songsListView: ListView
    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
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

        // Setup the ListView
        // You need an ArrayAdapter to connect the list of songs to the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

        // Put a click listener on the ListView to open the ManageSong activity when a song is clicked
        songsListView.setOnItemClickListener { parent, view, position, id ->
            // 1. Get the specific song string from the list based on the position clicked
            val selectedSong = songs[position]

            // 2. Create the Intent to move to ManageSong activity
            val intent = Intent(this, ManageSong::class.java)

            // 3. Pass the song data to the next activity
            // We use a key "SONG_DATA" to retrieve it later
            intent.putExtra("SONG_DATA", selectedSong)

            // 4. Launch the activity
            startActivity(intent)
        }
    }
}