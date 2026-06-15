package com.example.exercise_2_music_player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusicViewModel : ViewModel() {
    private val _songs = MutableLiveData<List<Song>>(
        listOf(
            Song("Song 1", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
            Song("Song 2", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"),
            Song("Song 3", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3")
        )
    )
    val songs: LiveData<List<Song>> get() = _songs

    fun toggleFavorite(song: Song) {
        val currentList = _songs.value ?: return
        val updatedList = currentList.map {
            if (it.url == song.url) {
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        _songs.value = updatedList
    }
}
