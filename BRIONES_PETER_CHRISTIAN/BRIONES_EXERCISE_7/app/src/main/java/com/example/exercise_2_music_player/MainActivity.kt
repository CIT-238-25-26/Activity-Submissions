package com.example.exercise_2_music_player

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity(), MusicPlayerInterface {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var playerContainer: View

    private val songs = listOf(
        "Song 1 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        "Song 2 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        "Song 3 - https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )
    private var currentIndex = -1
    private val favoriteSongs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        bottomNav = findViewById(R.id.bottom_navigation)
        playerContainer = findViewById(R.id.player_container)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        bottomNav.setupWithNavController(navController)

        toolbar.setNavigationIcon(R.drawable.menu)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label

            if (destination.id == R.id.profileFragment) {
                bottomNav.visibility = View.GONE
                playerContainer.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
                playerContainer.visibility = View.VISIBLE
            }
        }

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    if (!navController.popBackStack()) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                }
            }
        })
    }

    override fun onSongSelected(songData: String) {
        currentIndex = songs.indexOf(songData)
        updatePlayer(songData)
    }

    override fun onNextRequested() {
        if (songs.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % songs.size
            updatePlayer(songs[currentIndex])
        }
    }

    override fun onPreviousRequested() {
        if (songs.isNotEmpty()) {
            currentIndex = if (currentIndex <= 0) songs.size - 1 else currentIndex - 1
            updatePlayer(songs[currentIndex])
        }
    }

    override fun onToggleFavorite(songData: String) {
        if (favoriteSongs.contains(songData)) {
            favoriteSongs.remove(songData)
        } else {
            favoriteSongs.add(songData)
        }
        
        // Notify FavoritesFragment if it's currently visible
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()
        if (currentFragment is FavoritesFragment) {
            currentFragment.refreshFavorites()
        }
    }

    override fun isFavorite(songData: String): Boolean {
        return favoriteSongs.contains(songData)
    }

    fun getFavorites(): List<String> = favoriteSongs

    private fun updatePlayer(songData: String) {
        val playerFragment = supportFragmentManager.findFragmentById(R.id.player_container) as? MusicPlayerFragment
        playerFragment?.playSong(songData)
    }
}
