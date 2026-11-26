package com.musicplaylist.mapper;

import com.musicplaylist.dto.PlaylistRequest;
import com.musicplaylist.dto.PlaylistResponse;
import com.musicplaylist.model.Playlist;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class PlaylistMapper {

    private final SongMapper songMapper;

    public PlaylistMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public Playlist toEntity(PlaylistRequest request) {
        Playlist playlist = new Playlist();
        playlist.setName(request.getName());
        playlist.setDescription(request.getDescription());
        return playlist;
    }

    public PlaylistResponse toResponse(Playlist playlist) {
        PlaylistResponse response = new PlaylistResponse();
        response.setId(playlist.getId());
        response.setName(playlist.getName());
        response.setDescription(playlist.getDescription());

        response.setSongs(
                playlist.getPlaylistSongs() == null
                        ? Collections.emptyList()
                        : playlist.getPlaylistSongs().stream()
                        .sorted((a, b) -> Integer.compare(a.getPosition(), b.getPosition()))
                        .map(playlistSong -> songMapper.toResponse(playlistSong.getSong()))
                        .collect(Collectors.toList())
        );

        return response;
    }
}
