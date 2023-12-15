package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Transactional
    public String createPlaylist(User user, String inputplaylistname){

        Playlist newplaylist = new Playlist(inputplaylistname, user);

        playlistRepository.save(newplaylist);

        return "";
    }
}
