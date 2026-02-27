package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var songsListView: ListView
    private val songs = listOf(
        "Ambient Dreams|Relaxing Music - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Electric Vibes|Electronic Mix - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Jazz Cafe|Smooth Jazz - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
        "Summer Breeze|Chill Beats - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
        "Night Drive|Lo-Fi Hip Hop - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
        "Ocean Waves|Nature Sounds - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3"
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
        val adapter = SongAdapter(songs)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

//        For navigating ManageSong activity chuchunes
        songsListView.setOnItemClickListener { parent, view, position, id ->
            val songData = songs[position]
            // Extract song name and URL for the intent
            val songName = songData.substringBefore("|")
            val songUrl = songData.substringAfter(" - ")
            val songItem = "$songName - $songUrl"

            val intent = android.content.Intent(this, ManageSong::class.java)
            intent.putExtra("songItem", songItem)
            startActivity(intent)
        }
    }

    // Custom adapter for song items
    inner class SongAdapter(private val songList: List<String>) : BaseAdapter() {

        override fun getCount(): Int = songList.size

        override fun getItem(position: Int): Any = songList[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.item_song, parent, false)

            val songData = songList[position]
            val songTitle = songData.substringBefore("|")
            val artistName = songData.substringAfter("|").substringBefore(" - ")

            view.findViewById<TextView>(R.id.songTitle).text = songTitle
            view.findViewById<TextView>(R.id.songArtist).text = artistName

            return view
        }
    }
}
