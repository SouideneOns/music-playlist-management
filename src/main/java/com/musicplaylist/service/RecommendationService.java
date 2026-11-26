package com.musicplaylist.service;

import com.musicplaylist.model.Song;
import java.util.List;

public interface RecommendationService {

    List<Song> recommendSongs(Long playlistId);
}
