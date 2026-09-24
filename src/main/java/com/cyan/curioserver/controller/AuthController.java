package com.cyan.curioserver.controller;

import com.cyan.curioserver.common.Result;
import com.cyan.curioserver.dto.LoginDTO;
import com.cyan.curioserver.dto.RegisterDTO;
import com.cyan.curioserver.entity.User;
import com.cyan.curioserver.exception.NotLoggedInException;
import com.cyan.curioserver.service.AuthService;
import com.cyan.curioserver.vo.LoginUserVO;
import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginUserVO> login(@RequestBody LoginDTO loginDTO,
                                     HttpServletRequest request){
        LoginUserVO loginUser = authService.login(loginDTO);

        HttpSession oldSession = request.getSession(false);
        if(oldSession!=null){
            oldSession.invalidate();
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser",loginUser);
        session.setMaxInactiveInterval(30*60);
        return Result.success(loginUser);
    }
    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterDTO registerDTO){
        Long userId = authService.register(registerDTO);
        return Result.success(userId);
    }
    @GetMapping("/me")
    public Result<LoginUserVO> me (HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            throw new NotLoggedInException();
        }
        LoginUserVO loginUser = (LoginUserVO) session.getAttribute("loginUser");
        if(loginUser == null){
            throw new NotLoggedInException();
        }
        return Result.success(loginUser);
    }
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return Result.success();
    }
}
