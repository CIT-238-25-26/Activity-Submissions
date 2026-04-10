package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.exercise_2_music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnSongSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    val songs = listOf(
        Song("Song 1", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        Song("Song 2", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
        Song("Song 3", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"),
        Song("Song 4", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"),
        Song("Song 5", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3"),
        Song("Song 6", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3"),
        Song("Song 7", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3"),
        Song("Song 8", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3"),
        Song("Song 9", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"),
        Song("Song 10", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_my_music, R.id.nav_now_playing, R.id.nav_favorites),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSongSelected(index: Int) {
        val bundle = Bundle().apply {
            putInt("songIndex", index)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.playerFragment, bundle)
    }

    override fun onPreviousSong() {}

    override fun onNextSong() {}
}
