package com.musicplaylist.service.impl;

import com.musicplaylist.exception.ResourceNotFoundException;
import com.musicplaylist.model.Playlist;
import com.musicplaylist.model.PlaylistSong;
import com.musicplaylist.model.Song;
import com.musicplaylist.repository.PlaylistRepository;
import com.musicplaylist.repository.PlaylistSongRepository;
import com.musicplaylist.repository.SongRepository;
import com.musicplaylist.service.PlaylistSongService;

import com.musicplaylist.service.ShuffleService;
import com.musicplaylist.service.strategy.enumration.ShuffleType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;

    private final ShuffleService shuffleService;

    public PlaylistSongServiceImpl(
            PlaylistRepository playlistRepository,
            SongRepository songRepository,
            PlaylistSongRepository playlistSongRepository, ShuffleService shuffleService) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.shuffleService = shuffleService;
    }

    @Override
    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Song not found with id: " + songId));

        if (playlistSongRepository.existsByPlaylistIdAndSongId(
                playlistId, songId)) {
            throw new IllegalArgumentException(
                    "This song is already in the playlist");
        }

        int nextPosition = playlistSongRepository
                .findByPlaylistIdOrderByPositionAsc(playlistId)
                .size();

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(nextPosition);

        playlistSongRepository.save(playlistSong);
    }

    @Override
    @Transactional
    public void removeSongFromPlaylist(Long playlistId, Long songId) {

        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException(
                    "Playlist not found with id: " + playlistId);
        }

        if (!playlistSongRepository.existsByPlaylistIdAndSongId(
                playlistId, songId)) {
            throw new ResourceNotFoundException(
                    "Song not found in this playlist");
        }

        playlistSongRepository.deleteByPlaylistIdAndSongId(
                playlistId, songId);
    }


    @Override
    @Transactional
    public void shufflePlaylist(Long playlistId, ShuffleType type) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        List<PlaylistSong> playlistSongs =
                playlistSongRepository.findByPlaylistIdOrderByPositionAsc(
                        playlistId);

        List<Song> songs = playlistSongs.stream()
                .map(PlaylistSong::getSong)
                .toList();

        List<Song> shuffledSongs = shuffleService.shuffle(songs, type);

        for (int i = 0; i < shuffledSongs.size(); i++) {
            Song song = shuffledSongs.get(i);

            PlaylistSong playlistSong = playlistSongs.stream()
                    .filter(ps -> ps.getSong().getId().equals(song.getId()))
                    .findFirst()
                    .orElseThrow();

            playlistSong.setPosition(i);
        }

        playlistSongRepository.saveAll(playlistSongs);
    }

}
