package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
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
            return@setOnApplyWindowInsetsListener insets
        }

        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = SongAdapter()

        songsListView.setOnItemClickListener { _, _, position, _ ->
            val selected = songs[position]
            val intent = Intent(this, ManageSong::class.java)
            intent.putExtra("songUrl", selected)
            startActivity(intent)
        }
    }

    inner class SongAdapter : BaseAdapter() {
        override fun getCount(): Int = songs.size
        override fun getItem(position: Int): Any = songs[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.song_list_item, parent, false)

            val songTitle = view.findViewById<TextView>(R.id.songItemTitle)
            songTitle.text = songs[position].substringBefore(" - ")

            return view
        }
    }
}
