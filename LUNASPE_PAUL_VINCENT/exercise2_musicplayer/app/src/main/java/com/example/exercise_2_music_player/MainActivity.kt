package com.example.exercise_2_music_player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //    List of songs
    private lateinit var songsListView: ListView
    private val songs = listOf(
        "Song 1 - https://dn710000.ca.archive.org/0/items/01-avicii-the-nights-audio/01%20-%20Avicii%20-%20The%20Nights%20%28Audio%29.mp3",
        "Song 2 - https://dn721602.ca.archive.org/0/items/Tiktok-Mixtapes/TikTok%20Chillout%20Mix.mp3",
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

//        Setup the ListView with custom adapter
        val adapter = SongAdapter(this, songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

//        Put a click listener on the ListView to open the ManageSong activity when a song is clicked
        songsListView.setOnItemClickListener { parent, view, position, id ->
            val selected = songs[position]
            val intent = Intent(this, ManageSong::class.java)
            intent.putExtra("songUrl", selected)
            startActivity(intent)
        }
    }

    // Custom adapter for professional song list
    private class SongAdapter(context: Context, private val songs: List<String>) :
        ArrayAdapter<String>(context, R.layout.song_list_item, songs) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.song_list_item, parent, false)

            val songData = songs[position]
            val songName = songData.substringBefore(" - ")

            view.findViewById<TextView>(android.R.id.text1).text = songName
            view.findViewById<TextView>(android.R.id.text2).text = "Streaming • Online"

            return view
        }
    }
}
