package com.example.exercise_2_music_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), SelectedSongListener {

    private var currentIndex = 0
    private val songs = listOf(
        "Aetheric" to "https://drive.google.com/file/d/1xI_s545a63DbuxyirdQ9oXSeBPKOG_-T/view?usp=sharing",
        "Dagored" to "https://drive.google.com/file/d/1CwnBO6MVkRjr9pP2uaK4n8Mn930PsJfJ/view?usp=sharing",
        "Lukrembo" to "https://drive.google.com/file/d/1-4Qa6uZ-x0YeZKj2chSiKYLIedYBr_ml/view?usp=sharing",
        "Moavii" to "https://drive.google.com/file/d/11O4o-tpn8x4phnL92UZbFnJwaUBb30ya/view?usp=sharing",
    )

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_my_music, R.id.nav_favorites, R.id.nav_now_playing, R.id.nav_profile),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = findViewById<NavigationView>(R.id.navView)
        navView.setupWithNavController(navController)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNav.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { item ->
            val handled = androidx.navigation.ui.NavigationUI.onNavDestinationSelected(item, navController)
            drawerLayout.closeDrawers()
            handled
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSongSelected(index: Int) {
        currentIndex = index
        updatePlayerFragment()
    }

    override fun onNextSong() {
        currentIndex = (currentIndex + 1) % songs.size
        updatePlayerFragment()
    }

    override fun onPreviousSong() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else songs.size - 1
        updatePlayerFragment()
    }


    private fun updatePlayerFragment() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentDestinationFragment = navHostFragment
            ?.childFragmentManager
            ?.primaryNavigationFragment

        val playerFrag = currentDestinationFragment
            ?.childFragmentManager
            ?.findFragmentById(R.id.player_container) as? ManageSong

        val (songTitle, songUrl) = songs[currentIndex]
        playerFrag?.loadNewSong(songTitle, songUrl)
    }
}
