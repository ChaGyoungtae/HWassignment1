package com.smutify_cgt.smutify.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Playlist_SongTableRepository extends JpaRepository<Playlist_SongTable,Long> {

    List<Playlist_SongTable> findAllByPlaylistid(Long playlistId);

    @Query("select ps from Playlist_SongTable ps " +
            "where ps.playlistid = :list_id and ps.songid = :song_id")
    List<Playlist_SongTable> findPlaylist(@Param("song_id") Long song_id,
                                          @Param("list_id") Long list_id);
}
