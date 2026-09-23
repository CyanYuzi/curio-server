package com.cyan.curioserver.controller;

import com.cyan.curioserver.common.Result;
import com.cyan.curioserver.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping
    public Result<Long> upload(
            @RequestParam(value = "file",required = false) MultipartFile file) {
        Long documentId = documentService.upload(file);
        return Result.success(documentId);
    }
}
