package com.smutify_cgt.smutify.user;

import com.smutify_cgt.smutify.music.Playlist;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Playlist> playlists = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public User(Long userid, String username){
        this.id = userid;
        this.username = username;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}
