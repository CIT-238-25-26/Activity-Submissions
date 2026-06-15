package com.example.exercise_2_music_player

object SongRepository {

    val songs = mutableListOf(
        Song(
            title = "Song 1",
            artist = "SoundHelix",
            url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        ),
        Song(
            title = "Song 2",
            artist = "SoundHelix",
            url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
        ),
        Song(
            title = "Song 3",
            artist = "SoundHelix",
            url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
        ),
        Song(
            title = "Song 4",
            artist = "SoundHelix",
            url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3"
        ),
        Song(
            title = "Song 5",
            artist = "SoundHelix",
            url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3"
        )
    )

    // Starts empty — songs are added here when the heart is tapped
    val favoriteSongs = mutableListOf<Song>()

    fun toggleFavorite(song: Song) {
        song.isFavorite = !song.isFavorite
        if (song.isFavorite) {
            if (!favoriteSongs.contains(song)) {
                favoriteSongs.add(song)
            }
        } else {
            favoriteSongs.remove(song)
        }
    }
}