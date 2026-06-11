package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.exercise_2_music_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    MusicListFragment.OnSongSelectedListener,
    FavoritesFragment.OnSongSelectedListener,
    MusicPlayerFragment.OnPlayerControlListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: MusicViewModel by viewModels()
    
    private var currentSongIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup Drawer Navigation
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_my_music, R.id.nav_now_playing, R.id.nav_favorites, R.id.nav_profile),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        // Setup Bottom Navigation
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSongSelected(index: Int) {
        currentSongIndex = index
        playCurrentSong()
    }

    override fun onNextRequested() {
        val songs = viewModel.songs.value ?: emptyList()
        if (songs.isNotEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % songs.size
            playCurrentSong()
        }
    }

    override fun onPreviousRequested() {
        val songs = viewModel.songs.value ?: emptyList()
        if (songs.isNotEmpty()) {
            currentSongIndex = if (currentSongIndex <= 0) songs.size - 1 else currentSongIndex - 1
            playCurrentSong()
        }
    }

    private fun playCurrentSong() {
        val songs = viewModel.songs.value ?: emptyList()
        if (currentSongIndex in songs.indices) {
            val playerFragment = supportFragmentManager.findFragmentById(R.id.playerFragmentContainer) as? MusicPlayerFragment
            playerFragment?.playSong(songs[currentSongIndex].url)
        }
    }
}
