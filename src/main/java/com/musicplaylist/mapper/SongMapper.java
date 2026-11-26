package com.musicplaylist.mapper;

import com.musicplaylist.dto.SongRequest;
import com.musicplaylist.dto.SongResponse;
import com.musicplaylist.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public Song toEntity(SongRequest request) {
        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setArtist(request.getArtist());
        song.setGenre(request.getGenre());
        song.setDuration(request.getDuration());
        song.setAudioUrl(request.getAudioUrl());
        return song;
    }

    public SongResponse toResponse(Song song) {
        SongResponse response = new SongResponse();
        response.setId(song.getId());
        response.setTitle(song.getTitle());
        response.setArtist(song.getArtist());
        response.setGenre(song.getGenre());
        response.setDuration(song.getDuration());
        response.setAudioUrl(song.getAudioUrl());
        return response;
    }
}
