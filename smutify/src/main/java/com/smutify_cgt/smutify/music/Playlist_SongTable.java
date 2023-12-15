package com.smutify_cgt.smutify.music;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist_SongTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="song_id")
    private Long songid;

    @Column(name="playlist_id")
    private Long playlistid;

    public Playlist_SongTable(Long songid, Long playlistid) {
        this.songid = songid;
        this.playlistid = playlistid;
    }
}