package com.smutify_cgt.smutify.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(String username) {

        String result = userService.createUser(username);
        if (result == "회원가입 성공"){
            return "/login";
        } else {
            return "/signup_ng";
        }


    }

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String logIn(String username, HttpServletRequest httpServletRequest) {

        String result = userService.logInUser(username, httpServletRequest);

        if (result == "로그인 완료") {
            return "redirect:/main";
        } else if(result == "존재하지 않는 회원입니다.") {
            return "/login_ng";
        } else {

            return "/login_already";
        }

    }

    @PostMapping("/logout")
    public String logOut(HttpServletRequest httpServletRequest){


        HttpSession session = httpServletRequest.getSession(false);

        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}

