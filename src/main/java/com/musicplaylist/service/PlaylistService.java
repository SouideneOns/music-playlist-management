
package com.musicplaylist.service;

import com.musicplaylist.model.Playlist;
import java.util.List;

public interface PlaylistService {

    Playlist createPlaylist(Playlist playlist);

    List<Playlist> getAllPlaylists();

    Playlist getPlaylistById(Long id);

    Playlist updatePlaylist(Long id, Playlist playlist);

    void deletePlaylist(Long id);
}

