package com.qzh.notes.service.impl;

import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.model.vo.upload.ImageVO;
import com.qzh.notes.service.FileService;
import com.qzh.notes.service.UploadService;
import com.qzh.notes.utils.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    FileService fileService;

    @Override
    public ApiResponse<ImageVO> uploadImage(MultipartFile file) {
        String url = fileService.uploadImage(file);
        ImageVO imageVO = new ImageVO();
        imageVO.setUrl(url);
        return ApiResponseUtil.success("上传成功", imageVO);
    }
}