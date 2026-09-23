package com.cyan.curioserver.controller;

import com.cyan.curioserver.common.PageResult;
import com.cyan.curioserver.common.Result;
import com.cyan.curioserver.service.DocumentService;
import com.cyan.curioserver.vo.DocumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public Result<PageResult<DocumentVO>> pageQuery(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) {
        PageResult<DocumentVO> result = documentService.pageQuery(page, pageSize);
        return Result.success(result);
    }
}
