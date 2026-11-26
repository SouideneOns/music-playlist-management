package com.musicplaylist.controller;

import com.musicplaylist.dto.SongRequest;
import com.musicplaylist.dto.SongResponse;
import com.musicplaylist.mapper.SongMapper;
import com.musicplaylist.model.Song;
import com.musicplaylist.service.SongService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;
    private final SongMapper songMapper;

    public SongController(SongService songService, SongMapper songMapper) {
        this.songService = songService;
        this.songMapper = songMapper;
    }

    @PostMapping
    public ResponseEntity<SongResponse> createSong(
            @Valid @RequestBody SongRequest request) {

        Song song = songMapper.toEntity(request);
        Song createdSong = songService.createSong(song);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(songMapper.toResponse(createdSong));
    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> getAllSongs() {

        List<SongResponse> songs = songService.getAllSongs()
                .stream()
                .map(songMapper::toResponse)
                .toList();

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {

        Song song = songService.getSongById(id);

        return ResponseEntity.ok(songMapper.toResponse(song));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(
            @PathVariable Long id,
            @Valid @RequestBody SongRequest request) {

        Song song = songMapper.toEntity(request);
        Song updatedSong = songService.updateSong(id, song);

        return ResponseEntity.ok(songMapper.toResponse(updatedSong));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {

        songService.deleteSong(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<SongResponse>> getSongsByArtist(
            @PathVariable String artist) {

        List<SongResponse> songs = songService.getSongsByArtist(artist)
                .stream()
                .map(songMapper::toResponse)
                .toList();

        return ResponseEntity.ok(songs);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<SongResponse>> getSongsByGenre(
            @PathVariable String genre) {

        List<SongResponse> songs = songService.getSongsByGenre(genre)
                .stream()
                .map(songMapper::toResponse)
                .toList();

        return ResponseEntity.ok(songs);
    }
}
