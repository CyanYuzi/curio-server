package com.cyan.curioserver.service.impl;

import com.cyan.curioserver.common.PageResult;
import com.cyan.curioserver.dto.DownloadFile;
import com.cyan.curioserver.entity.Document;
import com.cyan.curioserver.entity.User;
import com.cyan.curioserver.exception.DocumentNotFoundException;
import com.cyan.curioserver.mapper.DocumentMapper;
import com.cyan.curioserver.mapper.UserMapper;
import com.cyan.curioserver.service.DocumentService;
import com.cyan.curioserver.vo.DocumentVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DocumentMapper documentMapper;

    @Value("${curio.storage.upload-dir}")
    private String uploadDir;
    @Override
    public Long upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("请选择非空文件");
        }

        User user = userMapper.findById(1L);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        // 到这里，说明文件非空，而且用户存在。
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("文件名不能为空");
        }
        //判断文件是否存在
        String fileHash = calculateFileHash(file);
        Long duplicateId = documentMapper.findDuplicateId(user.getId(),originalFilename,fileHash);
        if (duplicateId != null) {
            throw new IllegalArgumentException("该资料已存在");
        }
        //处理重复名字加入后缀
        String documentName = resolveAvailableName(user.getId(),originalFilename);
        // 后面继续实现查重、保存文件、插入资料记录。
        Document document = new Document();
        document.setUserId(user.getId());
        document.setName(documentName);
        document.setSize(file.getSize());
        document.setContentType(file.getContentType());
        document.setFileHash(fileHash);

        String storagePath = saveFile(file);
        document.setStoragePath(storagePath);

        try {
            int rows = documentMapper.insert(document);
            if (rows != 1) {
                throw new IllegalStateException("资料保存失败");
            }
        }catch (RuntimeException exception){
            try {
                Files.deleteIfExists(resolveStoragePath(storagePath));
            }catch (IOException ioException){
                exception.addSuppressed(ioException);
            }
            throw exception;
        }
        return document.getId();
    }

    @Override
    public PageResult<DocumentVO> pageQuery(Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            throw new IllegalArgumentException("页码必须大于等于1");
        }
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException("每页条数必须在1到100之间");
        }
        // 当前阶段由后端固定测试用户
        Long userId = 1L;

        try {
            PageHelper.startPage(page, pageSize);
            Page<DocumentVO> result = documentMapper.pageQuery(userId);

            return new PageResult<>(result.getTotal(), result.getResult());
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public DownloadFile prepareDownload(Long id) {
        if(id == null || id <= 0 ){
            throw new IllegalArgumentException("资料id错误");
        }
        Document document = documentMapper.findAvailableById(id , 1L);
        if(document == null){
            throw new DocumentNotFoundException();
        }
        Path path = resolveStoragePath(document.getStoragePath());

        if(!Files.isRegularFile(path) || !Files.isReadable(path)){
            throw new IllegalStateException("资料文件暂不可用");
        }

        return new DownloadFile(document.getName(),path);
    }

    private String calculateFileHash(MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = file.getInputStream()){
                byte[] buffer = new byte[8192];
                int length;

                while ((length = input.read(buffer))!=-1){
                    digest.update(buffer,0,length);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (IOException exception) {
            throw new IllegalStateException("读取文件失败", exception);
        }catch (NoSuchAlgorithmException exception){
            throw new IllegalStateException("无法使用SHA-256算法", exception);
        }
    }
    private String resolveAvailableName(Long userId , String originalFilename) {
        String baseName = originalFilename;
        String extension = "";

        //分离文件后缀
        int dotIndex = originalFilename.lastIndexOf('.');
        if(dotIndex > 0){
            baseName = originalFilename.substring(0, dotIndex);
            extension = originalFilename.substring(dotIndex);
        }
        String candidateName = originalFilename;
        int number = 1;

        while (documentMapper.findIdByName(userId,candidateName)!= null){
            candidateName = baseName + "(" + number + ")" + extension;
            number++;
        }
        return candidateName;
    }
    private String saveFile(MultipartFile file) {
        Path directory = Path.of(uploadDir).toAbsolutePath().normalize();

        try{
            //目录不存在就创建
            Files.createDirectories(directory);
            //由系统创建不重名的新名字
            Path target = Files.createTempFile(directory,"document-",".bin");

            try(InputStream input = file.getInputStream()){
                Files.copy(input,target, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException | RuntimeException exception){
                //保存失败时，清理
                try {
                    Files.deleteIfExists(target);
                }catch (IOException cleanupException){
                    exception.addSuppressed(cleanupException);
                }
                throw exception;
            }

            return target.getFileName().toString();
        }catch (IOException exception){
            throw new IllegalStateException("保存文件失败", exception);
        }
    }
    private Path resolveStoragePath(String storagePath){
        Path root = Path.of(uploadDir).toAbsolutePath().normalize();
        Path stored = Path.of(storagePath);

        //兼容已有的
        Path resolved = stored.isAbsolute() ? stored.normalize() : root.resolve(stored).normalize();
        if (!resolved.startsWith(root)||resolved.equals(root)) {
            throw new IllegalStateException("资料存储地址无效");
        }
        return resolved;
    }
}
