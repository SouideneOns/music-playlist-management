package com.musicplaylist.service;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.strategy.ShuffleType;
import java.util.List;

public interface ShuffleService {

    List<Song> shuffle(List<Song> songs, ShuffleType type);
}
