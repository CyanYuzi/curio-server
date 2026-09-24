package com.cyan.curioserver.service.impl;

import com.cyan.curioserver.dto.RegisterDTO;
import com.cyan.curioserver.entity.User;
import com.cyan.curioserver.mapper.UserMapper;
import com.cyan.curioserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Long register(RegisterDTO registerDTO) {
        if(registerDTO == null){
            throw new IllegalArgumentException("注册信息不能为空");
        }
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        if(username == null || !username.matches("[a-zA-Z0-9_]{3,30}")){
            throw new IllegalArgumentException("用户名须为3到30位英文字母、数字或下划线");
        }
        if(password == null || password.isBlank()
                || password.codePointCount(0, password.length())<15
                || password.getBytes(StandardCharsets.UTF_8).length>72){
            throw new IllegalArgumentException(
                    "密码至少15个字符，且UTF-8编码不超过72字节");
        }
        if(userMapper.findByUsername(username) != null){
            throw new IllegalArgumentException("用户名被占用");
        }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        try{
            int rows = userMapper.insert(user);
            if(rows != 1){
                throw new IllegalStateException("注册失败");
            }
        } catch (DuplicateKeyException exception) {
            throw new IllegalArgumentException("用户名已被使用");
        }
        return user.getId();
    }
}
