package com.cyan.curioserver.dto;

import lombok.Data;
import lombok.ToString;

@Data
public class RegisterDTO {
    private String username;
    @ToString.Exclude
    private String password;
}
