package com.example.exercise_2_music_player

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

//    List of songs
    private lateinit var songsListView: ListView
    private val songsList = listOf(
        Song("SoundHelix Song 1", "SoundHelix", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        Song("SoundHelix Song 2", "SoundHelix", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
        Song("SoundHelix Song 3", "SoundHelix", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"),
        Song("Love Me Not", "Ravyn Lenae", "https://uneven-apricot-mwxdnaxvop.edgeone.app/Ravyn%20Lenae%20-%20Love%20Me%20Not.mp3"),
        Song("Angleyes", "ABBA", "https://uneven-apricot-mwxdnaxvop.edgeone.app/ABBA%20-%20Angeleyes%20(Lyrics).mp3"),
        Song("Tensionado", "Soapdish", "https://uneven-apricot-mwxdnaxvop.edgeone.app/Tensionado%20(Lyrics)%20-%20Soapdish.mp3"),
        Song("I love you so", "The Walters", "https://uneven-apricot-mwxdnaxvop.edgeone.app/The%20Walters%20-%20I%20Love%20You%20So%20(Lyrics).mp3"),
        Song("Ang Pag-ibig ay Kanibalismo", "fitterkarma", "https://uneven-apricot-mwxdnaxvop.edgeone.app/Pag%20ibig%20ay%20Kanibalismo%20II%20-%20fitterkarma%20(Lyrics).mp3")


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
        val adapter = SongAdapter(this, songsList)
        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = adapter

//        Put a click listener on the ListView to open the ManageSong activity when a song is clicked
        songsListView.setOnItemClickListener { parent, view, position, id ->
            val selectedSong = songsList[position]
            val intent = Intent(this, ManageSong::class.java)
            intent.putExtra("SONG_DATA", selectedSong.toString())
            startActivity(intent)
        }
    }

}
