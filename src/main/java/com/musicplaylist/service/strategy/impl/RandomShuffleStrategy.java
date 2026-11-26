package com.musicplaylist.service.strategy.impl;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.strategy.ShuffleStrategy;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("randomShuffle")
public class RandomShuffleStrategy implements ShuffleStrategy {

    @Override
    public List<Song> shuffle(List<Song> songs) {
        List<Song> result = new ArrayList<>(songs);
        Collections.shuffle(result);
        return result;
    }
}
