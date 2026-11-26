package com.musicplaylist;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.strategy.impl.RandomShuffleStrategy;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomShuffleStrategyTest {

    @Test
    void shouldKeepAllSongsAfterShuffle() {

        Song song1 = new Song();
        song1.setId(1L);

        Song song2 = new Song();
        song2.setId(2L);

        Song song3 = new Song();
        song3.setId(3L);

        List<Song> songs = List.of(song1, song2, song3);

        RandomShuffleStrategy strategy = new RandomShuffleStrategy();

        List<Song> result = strategy.shuffle(songs);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(songs));
        assertNotSame(songs, result);
    }
}