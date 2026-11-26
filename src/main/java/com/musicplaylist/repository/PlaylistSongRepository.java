package com.musicplaylist.repository;

import com.musicplaylist.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistSongRepository
        extends JpaRepository<PlaylistSong, Long> {

    List<PlaylistSong> findByPlaylistIdOrderByPositionAsc(Long playlistId);

    boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);

    void deleteByPlaylistIdAndSongId(Long playlistId, Long songId);
}
