package com.cyan.curioserver.service;

import com.cyan.curioserver.common.PageResult;
import com.cyan.curioserver.dto.DownloadFile;
import com.cyan.curioserver.vo.DocumentVO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Long upload(MultipartFile file,Long userId);

    PageResult<DocumentVO> pageQuery(Integer page, Integer pageSize,Long userId);

    DownloadFile prepareDownload(Long id ,Long userId);

    void delete(Long id , Long userId);
}
