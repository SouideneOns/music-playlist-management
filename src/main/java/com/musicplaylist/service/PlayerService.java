
package com.musicplaylist.service;

import com.musicplaylist.model.PlayerState;

public interface PlayerService {

    PlayerState play(Long playlistId);

    PlayerState pause();

    PlayerState resume();

    PlayerState getState();
}