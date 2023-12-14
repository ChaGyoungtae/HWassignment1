package com.smutify_cgt.smutify.user;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@NoArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam @Valid String user, BindingResult result, Model model) {


        userService.createUser(user);

        return "/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}

