package com.cyan.curioserver.service;

import com.cyan.curioserver.dto.LoginDTO;
import com.cyan.curioserver.dto.RegisterDTO;
import com.cyan.curioserver.vo.LoginUserVO;

public interface AuthService {
    Long register(RegisterDTO registerDTO);
    LoginUserVO login(LoginDTO loginDTO);
}
