package com.qzh.notes.service;

import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.model.vo.upload.ImageVO;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 上传图片
     */
    ApiResponse<ImageVO> uploadImage(MultipartFile file);
}