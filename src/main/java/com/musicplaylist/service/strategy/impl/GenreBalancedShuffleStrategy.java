package com.musicplaylist.service.strategy.impl;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.strategy.ShuffleStrategy;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component("genreBalancedShuffle")
public class GenreBalancedShuffleStrategy implements ShuffleStrategy {

    @Override
    public List<Song> shuffle(List<Song> songs) {

        Map<String, List<Song>> songsByGenre = new LinkedHashMap<>();

        for (Song song : songs) {
            String genre = song.getGenre() == null
                    ? "Unknown"
                    : song.getGenre();

            songsByGenre.computeIfAbsent(
                    genre.toLowerCase(),
                    key -> new ArrayList<>()
            ).add(song);
        }

        List<List<Song>> genreGroups =
                new ArrayList<>(songsByGenre.values());

        genreGroups.forEach(Collections::shuffle);

        List<Song> result = new ArrayList<>();

        boolean hasSongs = true;

        while (hasSongs) {
            hasSongs = false;

            for (List<Song> group : genreGroups) {
                if (!group.isEmpty()) {
                    result.add(group.remove(0));
                    hasSongs = true;
                }
            }
        }

        return result;
    }
}
