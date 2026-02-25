package com.example.tabares_exercise_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var songsListView: ListView
    private val songsList = listOf(
        Song("Love Me Not", "Ravyn Lenae", "https://uneven-apricot-mwxdnaxvop.edgeone.app/Ravyn%20Lenae%20-%20Love%20Me%20Not.mp3"),
        Song("2002", "Anne-Marie", "https://modern-pink-3czicetwms.edgeone.app/Anne-Marie%20-%202002%20(Mix%20Lyrics)%20Ellie%20Goulding,%20Meghan%20Trainor,%20Seafret%20[rvnkD_J_4lE].mp3"),
        Song("Byahe", "John Roa", "https://modern-pink-3czicetwms.edgeone.app/Byahe%20-%20Jroa%20(Lyrics).mp3"),
        Song("Angel Numbers/Ten Toes", "Chris Brown", "https://modern-pink-3czicetwms.edgeone.app/Chris%20Brown%20-%20Angel%20Numbers%20%20Ten%20Toes%20(Lyrics).mp3"),
        Song("To The Bone", "Pamungkas", "https://modern-pink-3czicetwms.edgeone.app/Pamungkas%20-%20To%20The%20Bone%20(Official%20Music%20Video).mp3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        getSharedPreferences("MusicPlayerPrefs", Context.MODE_PRIVATE).edit().clear().apply()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        songsListView = findViewById(R.id.songsListView)
        songsListView.adapter = SongAdapter(this, songsList)
        songsListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ManageSong::class.java)
            intent.putExtra("SONG_DATA", songsList[position].toString())
            startActivity(intent)
        }
    }

}
