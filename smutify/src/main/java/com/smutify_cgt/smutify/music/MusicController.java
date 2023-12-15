package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.User;
import com.smutify_cgt.smutify.user.UserRepository;
import com.smutify_cgt.smutify.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final SongTableRepository songTableRepository;
    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PlaylistService playlistService;
    private final SongTableService songTableService;
    private final Playlist_SongTableRepository playlist_SongTableRepository;

    @GetMapping("/search")
    public String showAll(Model model, HttpSession session){
        List<SongTable> songs = songTableRepository.findAll();

        session.setAttribute("songs",songs);
        model.addAttribute("songs",songs);
        return "search";

    }

    @PostMapping("/add_new_song")
    public String addNewSong(Model model, HttpSession session, @RequestParam Long playlistId){
        Long songId = (Long) session.getAttribute("songId");
        playlistService.createNowSong(songId,playlistId);
        List<SongTable> songs = songTableRepository.findAll();
        model.addAttribute("songs",songs);
        return "search";
    }

    @PostMapping("/search-keyword")
    public String searchKeyword(@RequestParam String searchkeyword, @RequestParam String searchcategory, Model model, HttpSession session){

        System.out.println(searchcategory+","+searchkeyword);
        List<SongTable> songs;

        if("singer".equals(searchcategory)){
            songs = songTableRepository.findBySingerLike("%"+searchkeyword+"%");
        } else if ("title".equals(searchcategory)) {
            songs = songTableRepository.findByTitleLike("%"+searchkeyword+"%");
        } else if ("genre".equals(searchcategory)){
            if(searchkeyword.isEmpty()){
                songs  = songTableRepository.findAll();
            }
            else{
                songs = songTableRepository.findByGenreLike("%"+searchkeyword+"%");
            }
        } else {
            songs  = songTableRepository.findAll();
        }

        session.setAttribute("songs",songs);
        model.addAttribute("songs",songs);

        return "search";
    }

    @PostMapping("/align-songtable")
    public String alignSongtable(Model model, @RequestParam String sortField, HttpSession session){

        List<SongTable> alignSongTable = (List<SongTable>) session.getAttribute("songs");

        System.out.println(alignSongTable);
        if(sortField.equals("singer")){
            alignSongTable.sort(Comparator.comparing(SongTable::getSinger));
        } else if (sortField.equals("title")) {
            alignSongTable.sort(Comparator.comparing(SongTable::getTitle));
        } else{
            alignSongTable.sort(Comparator.comparing(SongTable::getGenre));
        }


        model.addAttribute("songs",alignSongTable);

        System.out.println(sortField);


        return "search";
    }

    @PostMapping("/mod-genre")
    @Transactional
    public String modGenre(Model model, @RequestParam String newgenre, @RequestParam String title){

        songTableRepository.updateGenreByTitle(title,newgenre);
        List<SongTable> updateSongTable = songTableRepository.findAll();
        model.addAttribute("songs",updateSongTable);
        return "search";
    }

    @GetMapping("/main")
    public String mainPage(Model model, HttpSession session) {

        System.out.println("[mainPage]");

        User user = (User) session.getAttribute("user");
        Long userid = user.getId();
        System.out.println(user.getUsername());

        model.addAttribute("playlists",playlistService.showPlaylist(userid));

        return "main";
    }

    @PostMapping("/create-playlist")
    public String createPlaylist(HttpSession session, @RequestParam String inputplaylistname, Model model) {

        User user = (User) session.getAttribute("user");
        String result = playlistService.createPlaylist(user,inputplaylistname);

        if ("생성 성공".equals(result)){
            model.addAttribute("playlists",playlistService.showPlaylist(user.getId()));
            return "main";
        } else{
            return "create_playlist_ng";
        }
    }

    @PostMapping("/show-list")
    public String showPlaylist(@RequestParam Long userplaylistId, HttpSession session, Model model){

        User user = (User) session.getAttribute("user");
        List<SongTable> result = songTableService.findList(userplaylistId);

        model.addAttribute("list_id", userplaylistId);
        model.addAttribute("mysongs",result);
        return "show_list";
    }


    @GetMapping("delete_song")
    public String deleteSong(@RequestParam("song_id") Long song_id,
                             @RequestParam("list_id") Long list_id,
                             Model model) {
        songTableService.deleteSong(song_id,list_id);

        List<SongTable> result = songTableService.findList(list_id);
        model.addAttribute("mysongs",result);
        return "show_list";
    }
    @PostMapping("/add-song")
    public String addSong(@RequestParam Long songId, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        session.setAttribute("songId",songId);

        model.addAttribute("playlists",playlistService.showPlaylist(user.getId()));

        return "add_song";
    }
}