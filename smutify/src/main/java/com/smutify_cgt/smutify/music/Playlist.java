package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playlistName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_table_id")
    private SongTable song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Playlist(String title, User user) {
        this.playlistName = title;
        this.user = user;
    }
}
