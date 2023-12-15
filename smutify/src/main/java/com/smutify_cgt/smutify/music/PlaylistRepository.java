package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findAll();

    List<Playlist> findPlaylistByPlaylistName(String playlistname);

    List<Playlist> findPlaylistByPlaylistNameAndUser(String playlistname, User user);

    List<Playlist> findAllById(Long playlistId);
}
