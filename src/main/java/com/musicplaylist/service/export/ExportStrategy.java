package com.musicplaylist.service.export;

import com.musicplaylist.model.Playlist;

public interface ExportStrategy {

    String export(Playlist playlist);
}
