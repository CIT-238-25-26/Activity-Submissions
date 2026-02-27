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
            insets
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

        // Finish the songListView’s setOnItemClickListener [cite: 7]
        songsListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ManageSong::class.java)
            // Adding the selected song item to the intent [cite: 7]
            intent.putExtra("SONG_DATA", songs[position])
            startActivity(intent)
        }
    }
}