package com.musicplaylist.dto;

import java.util.List;

public class PlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private List<SongResponse> songs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SongResponse> getSongs() {
        return songs;
    }

    public void setSongs(List<SongResponse> songs) {
        this.songs = songs;
    }
}
