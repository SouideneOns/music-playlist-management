package com.musicplaylist.service;

import com.musicplaylist.model.Song;
import java.util.List;

public interface SongService {

    Song createSong(Song song);

    List<Song> getAllSongs();

    Song getSongById(Long id);

    Song updateSong(Long id, Song song);

    void deleteSong(Long id);

    List<Song> getSongsByArtist(String artist);

    List<Song> getSongsByGenre(String genre);
}

