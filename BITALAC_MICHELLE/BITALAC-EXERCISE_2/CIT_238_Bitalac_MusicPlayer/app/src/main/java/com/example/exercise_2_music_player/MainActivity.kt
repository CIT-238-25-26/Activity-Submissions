package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var songsListView: ListView

    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
        "Song 4 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3",
        "Song 5 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsListView = findViewById(R.id.songsListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
        songsListView.adapter = adapter

        songsListView.setOnItemClickListener { _, _, position, _ ->
            val songUrl = songs[position].substringAfter(" - ")

            val intent = Intent(this, ManageSong::class.java)
            intent.putExtra("SONG_URL", songUrl)
            intent.putExtra("AUTO_PLAY", true)
            startActivity(intent)
        }
    }
}
