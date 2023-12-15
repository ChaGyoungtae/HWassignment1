package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import com.smutify_cgt.smutify.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final Playlist_SongTableRepository playlist_songTableRepository;

    @Transactional
    public String createPlaylist(User user, String inputplaylistname){

        System.out.println("[PlayListService] : createPlaylist()");

        System.out.println(inputplaylistname);
        List<Playlist> playlists = playlistRepository.findPlaylistByPlaylistName(inputplaylistname);

        if(playlists.isEmpty()){
            if(inputplaylistname.isEmpty()){
                return "생성 실패";
            } else {
                Playlist newplaylist = new Playlist(inputplaylistname, user);
                playlistRepository.save(newplaylist);

                user.addPlaylist(newplaylist);
                return "생성 성공";
            }
        } else {
            System.out.println(playlists);
            return "생성 실패";
        }




    }

    public List<Playlist> showPlaylist(Long userid){

        List<Playlist> playlists = userRepository.findById(userid).get().getPlaylists();

        return playlists;
    }

    public void createNowSong(Long songId, Long playlistId){

        Playlist_SongTable playlist_songTable = new Playlist_SongTable(songId,playlistId);
        playlist_songTableRepository.save(playlist_songTable);
    }

}