package com.musicplaylist.service;

public interface ExportService {
    String exportPlaylist(Long playlistId, String format);
}
