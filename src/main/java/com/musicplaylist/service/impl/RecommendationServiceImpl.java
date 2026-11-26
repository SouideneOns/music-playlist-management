package com.musicplaylist.service.impl;

import com.musicplaylist.exception.ResourceNotFoundException;
import com.musicplaylist.model.PlaylistSong;
import com.musicplaylist.model.Song;
import com.musicplaylist.repository.PlaylistRepository;
import com.musicplaylist.repository.PlaylistSongRepository;
import com.musicplaylist.repository.SongRepository;
import com.musicplaylist.service.RecommendationService;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;

    public RecommendationServiceImpl(
            PlaylistRepository playlistRepository,
            PlaylistSongRepository playlistSongRepository,
            SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> recommendSongs(Long playlistId) {

        if (!playlistRepository.existsById(playlistId)) {
            throw new ResourceNotFoundException(
                    "Playlist not found with id: " + playlistId);
        }

        List<PlaylistSong> playlistSongs =
                playlistSongRepository.findByPlaylistIdOrderByPositionAsc(
                        playlistId);

        Set<Long> existingSongIds = playlistSongs.stream()
                .map(ps -> ps.getSong().getId())
                .collect(Collectors.toSet());

        Set<String> genres = playlistSongs.stream()
                .map(ps -> ps.getSong().getGenre())
                .filter(genre -> genre != null)
                .collect(Collectors.toSet());

        return genres.stream()
                .flatMap(genre -> songRepository
                        .findByGenreIgnoreCase(genre).stream())
                .filter(song -> !existingSongIds.contains(song.getId()))
                .distinct()
                .limit(10)
                .toList();
    }
}
