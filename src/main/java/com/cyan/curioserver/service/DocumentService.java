package com.cyan.curioserver.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Long upload(MultipartFile file);
}
