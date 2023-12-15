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

    @GetMapping("/search")
    public String showAll(Model model, HttpSession session){
        List<SongTable> songs = songTableRepository.findAll();

        session.setAttribute("songs",songs);
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

        return "/search";
    }

    @PostMapping("/align-songtable")
    public String alignSongtable(Model model, @RequestParam String sortField, HttpSession session){

        List<SongTable> alignSongTable = (List<SongTable>) session.getAttribute("songs");

        System.out.println(alignSongTable);

        alignSongTable.sort(Comparator.comparing(SongTable::getSinger));

        model.addAttribute("songs",alignSongTable);

        System.out.println(sortField);


        return "/search";
    }

    @PostMapping("/mod-genre")
    @Transactional
    public String modGenre(Model model, @RequestParam String newgenre, @RequestParam String title){

        songTableRepository.updateGenreByTitle(title,newgenre);
        List<SongTable> updateSongTable = songTableRepository.findAll();
        model.addAttribute("songs",updateSongTable);
        return "/search";
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
    public String showPlaylist(@RequestParam String userplaylist, HttpSession session, Model model){

        User user = (User) session.getAttribute("user");
        List<Playlist> userplaylists = playlistRepository.findPlaylistByPlaylistNameAndUser(userplaylist,user);

        model.addAttribute("userlists",userplaylists);
        return "show_list";
    }


}
