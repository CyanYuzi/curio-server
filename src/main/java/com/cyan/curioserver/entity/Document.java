package com.cyan.curioserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private Long id;
    private Long userId;
    private String name;
    private Long size;
    private String contentType;
    private String storagePath;
    private String fileHash;
    private Integer deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
