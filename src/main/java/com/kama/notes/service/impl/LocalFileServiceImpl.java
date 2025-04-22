package com.kama.notes.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kama.notes.service.FileService;

@Service
public class LocalFileServiceImpl implements FileService {

    /**
     * 基础上传路径（本地存储的绝对或相对路径）
     */
    @Value("${upload.path}")
    private String uploadBasePath;

    /**
     * 返回给前端的地址前缀 (可配合CDN/Nginx等)
     */
    @Value("${upload.url-prefix}")
    private String urlPrefix;

    /**
     * 允许上传的图片后缀名（小写形式）
     */
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS
            = Arrays.asList(".jpg", ".jpeg", ".png", ".webp");

    /**
     * 单个图片最大尺寸 (10MB)
     */
    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;

    @Override
    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传的图片文件为空");
        }

        // 校验文件大小
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("图片大小不能超过 10MB");
        }

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("图片文件名无效");
        }

        // 校验后缀（小写判断）
        String lowerCaseExtension = originalFilename
                .substring(originalFilename.lastIndexOf("."))
                .toLowerCase();

        if (!ALLOWED_IMAGE_EXTENSIONS.contains(lowerCaseExtension)) {
            throw new IllegalArgumentException(
                    "只支持 " + ALLOWED_IMAGE_EXTENSIONS + " 等格式图片");
        }
        return doUpload(file);
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }
        return doUpload(file);
    }

    /**
     * 实际执行文件上传的公共方法
     *
     * @param file MultipartFile
     * @return 上传后可访问的URL
     */
    private String doUpload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("文件名不合法");
        }

        // 统一生成新文件名
        String fileExtension = originalFilename
                .substring(originalFilename.lastIndexOf("."))
                .toLowerCase();
        String newFileName = UUID.randomUUID() + fileExtension;

        // 确保目录存在
        File uploadDir = new File(uploadBasePath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IllegalStateException("无法创建上传目录: " + uploadBasePath);
        }

        // 保存文件
        File destFile = new File(uploadDir, newFileName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new IllegalStateException("文件保存失败: " + e.getMessage(), e);
        }
        // 返回访问URL
        return urlPrefix + "/" + newFileName;
    }
}
