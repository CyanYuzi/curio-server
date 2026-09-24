package com.cyan.curioserver.controller;

import com.cyan.curioserver.common.PageResult;
import com.cyan.curioserver.common.Result;
import com.cyan.curioserver.dto.DownloadFile;
import com.cyan.curioserver.service.DocumentService;
import com.cyan.curioserver.vo.DocumentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cyan.curioserver.dto.DownloadFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.nio.charset.StandardCharsets;
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
            @RequestAttribute("currentUserId") Long userId,
            @RequestParam(value = "file",required = false) MultipartFile file) {
        Long documentId = documentService.upload(file,userId);
        return Result.success(documentId);
    }

    @GetMapping
    public Result<PageResult<DocumentVO>> pageQuery(
            @RequestAttribute("currentUserId") Long userId,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize) {
        PageResult<DocumentVO> result = documentService.pageQuery(page, pageSize,userId);
        return Result.success(result);
    }
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id,@RequestAttribute("currentUserId") Long userId){
        DownloadFile downloadFile = documentService.prepareDownload(id, userId);

        Resource resource = new FileSystemResource(downloadFile.getPath());

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(downloadFile.getName(), StandardCharsets.UTF_8).build();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,disposition.toString())
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id,@RequestAttribute("currentUserId") Long userId){
        documentService.delete(id , userId);
        return Result.success();
    }
}