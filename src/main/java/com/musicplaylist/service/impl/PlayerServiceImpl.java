
package com.musicplaylist.service.impl;

import com.musicplaylist.exception.ResourceNotFoundException;
import com.musicplaylist.model.PlaylistSong;
import com.musicplaylist.model.PlayerState;
import com.musicplaylist.repository.PlaylistRepository;
import com.musicplaylist.repository.PlaylistSongRepository;
import com.musicplaylist.service.PlayerService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;

    private final PlayerState state = new PlayerState();

    public PlayerServiceImpl(
            PlaylistRepository playlistRepository,
            PlaylistSongRepository playlistSongRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
    }

    @Override
    public synchronized PlayerState play(Long playlistId) {

        playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        List<PlaylistSong> songs =
                playlistSongRepository.findByPlaylistIdOrderByPositionAsc(
                        playlistId);

        if (songs.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot play an empty playlist");
        }

        state.setPlaylistId(playlistId);
        state.setCurrentSongId(songs.get(0).getSong().getId());
        state.setPlaying(true);

        return state;
    }

    @Override
    public synchronized PlayerState pause() {
        state.setPlaying(false);
        return state;
    }

    @Override
    public synchronized PlayerState resume() {
        if (state.getCurrentSongId() == null) {
            throw new IllegalStateException(
                    "No song is currently selected");
        }

        state.setPlaying(true);
        return state;
    }

    @Override
    public synchronized PlayerState getState() {
        return state;
    }
}