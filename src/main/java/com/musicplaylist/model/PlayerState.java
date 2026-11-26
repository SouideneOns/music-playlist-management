
package com.musicplaylist.model;

public class PlayerState {

    private Long playlistId;
    private Long currentSongId;
    private boolean playing;

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public Long getCurrentSongId() {
        return currentSongId;
    }

    public void setCurrentSongId(Long currentSongId) {
        this.currentSongId = currentSongId;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}