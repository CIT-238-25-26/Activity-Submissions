package com.example.exercise_2_music_player

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.net.Uri

class MainActivity : AppCompatActivity() {

    //    List of songs
    private lateinit var songsListView: ListView

    //  For the song urls, I used a method called 'Direct Download API links' for Google Drive. (I downloaded the songs, trimmed it to lower the size, and uploaded it to Gdrive.)
    private val songs = listOf(
        "Clarity - https://drive.google.com/uc?export=download&id=14j6dm05QQ91zp5vyClDgBbCmnOOezaTX", // Clarity - Zedd
        "Konsensya - https://drive.google.com/uc?export=download&id=1rQLvFQM-9J274cR0n5nsRsd89vpT5-ev", // Konsensya - IV of Spades
        "Aura - https://drive.google.com/uc?export=download&id=1HkI9vgKPnMXussNjyKnO1NdcJNojlhlz" // Aura - IV of Spades
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
//        You need an ArrayAdapter to connect the list of songs to the ListView
//        Welcome to Android
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

//        Put a click listener on the ListView to open the ManageSong activity when a song is clicked
        songsListView.setOnItemClickListener { parent, view, position, id ->
            // Get the full string from your list using the clicked position
            val selectedSong = songs[position]

            // Extract the Title (everything before the " - ")
            val songTitle = selectedSong.substringBefore(" - ")

            // Extract the URL (everything after the " - ")
            val songUrl = selectedSong.substringAfter(" - ")

            // Create an Intent to open YOUR ManageSong activity
            val intent = Intent(this, ManageSong::class.java).apply {
                // Pass the Title and URL to the next screen
                putExtra("SONG_TITLE", songTitle)
                putExtra("SONG_URL", songUrl)
            }
            startActivity(intent)
        }

    }
}
