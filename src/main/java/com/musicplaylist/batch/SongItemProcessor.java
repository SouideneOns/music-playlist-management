package com.musicplaylist.batch;

import com.musicplaylist.model.Song;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SongItemProcessor implements ItemProcessor<Song, Song> {

    @Override
    public Song process(Song song) {

        if (song.getTitle() == null || song.getTitle().isBlank()) {
            throw new IllegalArgumentException("Song title is required");
        }

        if (song.getArtist() == null || song.getArtist().isBlank()) {
            throw new IllegalArgumentException("Artist is required");
        }

        if (song.getDuration() <= 0) {
            throw new IllegalArgumentException("Invalid song duration");
        }

        return song;
    }
}
