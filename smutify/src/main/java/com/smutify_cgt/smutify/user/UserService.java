package com.smutify_cgt.smutify.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String createUser(String username) {

        Optional<User> findUser = userRepository.findByUsername(username);
        if(!findUser.isPresent()) {
            User user = new User(username);
            userRepository.save(user);
            return "회원가입 성공";
        } else {
            return "중복회원입니다.";
        }
    }

    public String logInUser(String username, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession(false) == null) {

            Optional<User> findUser = userRepository.findByUsername(username);
            if(!findUser.isPresent()) {
                return "존재하지 않는 회원입니다.";
            } else {
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("username", username);
                session.setMaxInactiveInterval(1800);
                return "로그인 완료";
            }
        } else {
            return "이미 로그인 되어있습니다.";
        }
    }

}