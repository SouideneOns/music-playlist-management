
package com.musicplaylist.controller;

import com.musicplaylist.model.PlayerState;
import com.musicplaylist.service.PlayerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/play/{playlistId}")
    public ResponseEntity<PlayerState> play(
            @PathVariable Long playlistId) {
        return ResponseEntity.ok(playerService.play(playlistId));
    }

    @PostMapping("/pause")
    public ResponseEntity<PlayerState> pause() {
        return ResponseEntity.ok(playerService.pause());
    }

    @PostMapping("/resume")
    public ResponseEntity<PlayerState> resume() {
        return ResponseEntity.ok(playerService.resume());
    }

    @GetMapping("/state")
    public ResponseEntity<PlayerState> getState() {
        return ResponseEntity.ok(playerService.getState());
    }
}