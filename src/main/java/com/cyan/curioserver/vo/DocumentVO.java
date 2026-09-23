package com.cyan.curioserver.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentVO {
    private Long id;
    private String name;
    private Long size;
    private LocalDateTime createAt;
}
