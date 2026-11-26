package com.musicplaylist.service;

import com.musicplaylist.service.strategy.enumration.ShuffleType;

public interface PlaylistSongService {

    void addSongToPlaylist(Long playlistId, Long songId);

    void removeSongFromPlaylist(Long playlistId, Long songId);

    void shufflePlaylist(Long playlistId, ShuffleType type);
}
