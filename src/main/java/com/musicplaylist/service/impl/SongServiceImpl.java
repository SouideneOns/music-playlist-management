package com.musicplaylist.service.impl;

import com.musicplaylist.model.Song;
import com.musicplaylist.repository.SongRepository;
import com.musicplaylist.service.SongService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Song not found with id: " + id));
    }

    @Override
    public Song updateSong(Long id, Song song) {
        Song existingSong = getSongById(id);

        existingSong.setTitle(song.getTitle());
        existingSong.setArtist(song.getArtist());
        existingSong.setGenre(song.getGenre());
        existingSong.setDuration(song.getDuration());
        existingSong.setAudioUrl(song.getAudioUrl());

        return songRepository.save(existingSong);
    }

    @Override
    public void deleteSong(Long id) {
        Song song = getSongById(id);
        songRepository.delete(song);
    }

    @Override
    public List<Song> getSongsByArtist(String artist) {
        return songRepository.findByArtistIgnoreCase(artist);
    }

    @Override
    public List<Song> getSongsByGenre(String genre) {
        return songRepository.findByGenreIgnoreCase(genre);
    }
}
