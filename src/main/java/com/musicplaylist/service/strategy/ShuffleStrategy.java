
package com.musicplaylist.service.strategy;

import com.musicplaylist.model.Song;
import java.util.List;

public interface ShuffleStrategy {

    List<Song> shuffle(List<Song> songs);
}