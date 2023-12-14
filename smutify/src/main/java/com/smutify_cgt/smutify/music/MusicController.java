package com.smutify_cgt.smutify.music;

import com.smutify_cgt.smutify.user.UserRepository;
import com.smutify_cgt.smutify.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicController {

    private final SongTableRepository songTableRepository;
    private final PlaylistRepository playlistRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/search")
    public String showAll(Model model){
        List<SongTable> songs = songTableRepository.findAll();

        model.addAttribute("songs",songs);
        return "search";

    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Playlist> playlists = playlistRepository.findAll();
        model.addAttribute("playlists", playlists);

        return "/main";
    }

    @PostMapping("/create-playlist")
    public String createPlaylist(HttpSession session) {
        String username = session.getAttribute("username").toString();

        return "미완성";
    }
}
