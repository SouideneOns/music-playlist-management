package com.musicplaylist.controller;

import com.musicplaylist.dto.PlaylistRequest;
import com.musicplaylist.dto.PlaylistResponse;
import com.musicplaylist.dto.SongResponse;
import com.musicplaylist.mapper.PlaylistMapper;
import com.musicplaylist.mapper.SongMapper;
import com.musicplaylist.model.Playlist;
import com.musicplaylist.service.ExportService;
import com.musicplaylist.service.PlaylistService;

import com.musicplaylist.service.PlaylistSongService;
import com.musicplaylist.service.RecommendationService;
import com.musicplaylist.service.strategy.ShuffleType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final PlaylistSongService playlistSongService;
    private final ExportService exportService;
    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;

    private final RecommendationService recommendationService;

    public PlaylistController(
            PlaylistService playlistService,
            PlaylistMapper playlistMapper,
            PlaylistSongService playlistSongService,
            RecommendationService recommendationService, ExportService exportService,
            SongMapper songMapper) {

        this.playlistService = playlistService;
        this.playlistMapper = playlistMapper;
        this.playlistSongService = playlistSongService;
        this.recommendationService = recommendationService;
        this.exportService = exportService;
        this.songMapper = songMapper;
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(
            @Valid @RequestBody PlaylistRequest request) {

        Playlist playlist = playlistMapper.toEntity(request);
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(playlistMapper.toResponse(createdPlaylist));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> getAllPlaylists() {

        List<PlaylistResponse> playlists = playlistService.getAllPlaylists()
                .stream()
                .map(playlistMapper::toResponse)
                .toList();

        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylistById(
            @PathVariable Long id) {

        Playlist playlist = playlistService.getPlaylistById(id);

        return ResponseEntity.ok(playlistMapper.toResponse(playlist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistRequest request) {

        Playlist playlist = playlistMapper.toEntity(request);
        Playlist updatedPlaylist = playlistService.updatePlaylist(id, playlist);

        return ResponseEntity.ok(playlistMapper.toResponse(updatedPlaylist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {

        playlistService.deletePlaylist(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        playlistSongService.addSongToPlaylist(playlistId, songId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {

        playlistSongService.removeSongFromPlaylist(playlistId, songId);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{playlistId}/shuffle")
    public ResponseEntity<PlaylistResponse> shufflePlaylist(
            @PathVariable Long playlistId,
            @RequestParam ShuffleType type) {

        playlistSongService.shufflePlaylist(playlistId, type);

        Playlist playlist = playlistService.getPlaylistById(playlistId);

        return ResponseEntity.ok(playlistMapper.toResponse(playlist));
    }


    @GetMapping("/{playlistId}/recommendations")
    public ResponseEntity<List<SongResponse>> recommendSongs(
            @PathVariable Long playlistId) {

        List<SongResponse> recommendations =
                recommendationService.recommendSongs(playlistId)
                        .stream()
                        .map(songMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(recommendations);
    }


    @GetMapping("/{playlistId}/export")
    public ResponseEntity<String> exportPlaylist(
            @PathVariable Long playlistId,
            @RequestParam String format) {

        String content = exportService.exportPlaylist(playlistId, format);

        return ResponseEntity.ok(content);
    }

}
