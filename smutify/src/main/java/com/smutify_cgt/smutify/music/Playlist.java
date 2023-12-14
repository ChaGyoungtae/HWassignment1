package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playlistNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_table_id")
    private SongTable song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public void addPlaylist(User user, Long playlistNumber) {

    }
}
