package com.musicplaylist.service.impl;

import com.musicplaylist.exception.ResourceNotFoundException;
import com.musicplaylist.model.Playlist;
import com.musicplaylist.repository.PlaylistRepository;
import com.musicplaylist.service.ExportService;
import com.musicplaylist.service.export.ExportStrategy;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ExportServiceImpl implements ExportService {

    private final PlaylistRepository playlistRepository;
    private final Map<String, ExportStrategy> strategies;

    public ExportServiceImpl(
            PlaylistRepository playlistRepository,
            Map<String, ExportStrategy> strategies) {
        this.playlistRepository = playlistRepository;
        this.strategies = strategies;
    }

    @Override
    public String exportPlaylist(Long playlistId, String format) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Playlist not found with id: " + playlistId));

        String beanName = switch (format.toUpperCase()) {
            case "JSON" -> "jsonExport";
            case "M3U" -> "m3uExport";
            default -> throw new IllegalArgumentException(
                    "Unsupported export format: " + format);
        };

        return strategies.get(beanName).export(playlist);
    }
}