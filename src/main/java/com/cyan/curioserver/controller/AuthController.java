package com.cyan.curioserver.controller;

import com.cyan.curioserver.common.Result;
import com.cyan.curioserver.dto.RegisterDTO;
import com.cyan.curioserver.entity.User;
import com.cyan.curioserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterDTO registerDTO){
        Long userId = authService.register(registerDTO);
        return Result.success(userId);
    }
}
