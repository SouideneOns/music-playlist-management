package com.musicplaylist.service.impl;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.ShuffleService;
import com.musicplaylist.service.strategy.ShuffleStrategy;
import com.musicplaylist.service.strategy.enumration.ShuffleType;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ShuffleServiceImpl implements ShuffleService {

    private final Map<String, ShuffleStrategy> strategies;

    public ShuffleServiceImpl(Map<String, ShuffleStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public List<Song> shuffle(List<Song> songs, ShuffleType type) {

        String beanName = switch (type) {
            case RANDOM -> "randomShuffle";
            case SMART -> "smartShuffle";
            case GENRE_BALANCED -> "genreBalancedShuffle";
        };

        ShuffleStrategy strategy = strategies.get(beanName);

        return strategy.shuffle(songs);
    }
}
