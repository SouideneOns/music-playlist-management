package com.musicplaylist.service.export.impl;

import com.musicplaylist.model.Playlist;
import com.musicplaylist.model.PlaylistSong;
import com.musicplaylist.service.export.ExportStrategy;

import org.springframework.stereotype.Component;

@Component("m3uExport")
public class M3uExportStrategy implements ExportStrategy {

    @Override
    public String export(Playlist playlist) {

        StringBuilder result = new StringBuilder("#EXTM3U\n");

        playlist.getPlaylistSongs().stream()
                .sorted((a, b) -> Integer.compare(
                        a.getPosition(), b.getPosition()))
                .map(PlaylistSong::getSong)
                .forEach(song -> {
                    result.append("#EXTINF:")
                            .append(song.getDuration())
                            .append(",")
                            .append(song.getArtist())
                            .append(" - ")
                            .append(song.getTitle())
                            .append("\n");

                    result.append(song.getAudioUrl() == null
                            ? ""
                            : song.getAudioUrl());

                    result.append("\n");
                });

        return result.toString();
    }
}