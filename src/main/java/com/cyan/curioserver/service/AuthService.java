package com.cyan.curioserver.service;

import com.cyan.curioserver.dto.RegisterDTO;

public interface AuthService {
    Long register(RegisterDTO registerDTO);
}
