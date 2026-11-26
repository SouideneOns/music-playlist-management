
package com.musicplaylist.service.impl;

import com.musicplaylist.model.Playlist;
import com.musicplaylist.repository.PlaylistRepository;
import com.musicplaylist.service.PlaylistService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Playlist not found with id: " + id));
    }

    @Override
    public Playlist updatePlaylist(Long id, Playlist playlist) {
        Playlist existingPlaylist = getPlaylistById(id);

        existingPlaylist.setName(playlist.getName());
        existingPlaylist.setDescription(playlist.getDescription());

        return playlistRepository.save(existingPlaylist);
    }

    @Override
    public void deletePlaylist(Long id) {
        Playlist playlist = getPlaylistById(id);
        playlistRepository.delete(playlist);
    }
}
