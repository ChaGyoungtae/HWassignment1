package com.smutify_cgt.smutify.music;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "song_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String singer;
    private String title;
    private String genre;

    @OneToMany(mappedBy = "song")
    private List<Playlist> playlists;
}
