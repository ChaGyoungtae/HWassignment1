package com.smutify_cgt.smutify.music;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SongTableService {

    private final SongTableRepository songTableRepository;
    private final Playlist_SongTableRepository playlist_songTableRepository;

    public List<SongTable> findList(Long userplaylistId) {
        List<Playlist_SongTable> result = playlist_songTableRepository.findAllByPlaylistid(userplaylistId);
        List<SongTable> songs = new ArrayList<>();

        for (Playlist_SongTable playlist_songTable : result) {
            Long song_id = playlist_songTable.getSongid();
            System.out.println(song_id);
            songs.add(songTableRepository.findById(song_id).get());
        }

        return songs;
    }

    public void deleteSong(Long song_id, Long list_id) {
         Playlist_SongTable result = playlist_songTableRepository.findPlaylist(song_id, list_id).get(0);
         playlist_songTableRepository.delete(result);
    }
}
