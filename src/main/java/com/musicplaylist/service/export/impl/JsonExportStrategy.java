
package com.musicplaylist.service.export.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicplaylist.model.Playlist;
import com.musicplaylist.service.export.ExportStrategy;

import org.springframework.stereotype.Component;

@Component("jsonExport")
public class JsonExportStrategy implements ExportStrategy {

    private final ObjectMapper objectMapper;

    public JsonExportStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String export(Playlist playlist) {
        try {
            return objectMapper.writeValueAsString(playlist);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON export failed", e);
        }
    }
}