package com.cyan.curioserver.service;

import com.cyan.curioserver.common.PageResult;
import com.cyan.curioserver.vo.DocumentVO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Long upload(MultipartFile file);
    PageResult<DocumentVO> pageQuery(Integer page, Integer pageSize);
}
