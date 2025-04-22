package com.kama.notes.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传文件，并返回文件的访问路径或存储位置
     *
     * @param file 上传的文件
     * @return 存储后的文件URL或路径
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传图片，并返回图片的访问路径或存储位置
     *
     * @param file 上传的图片文件
     * @return 存储后的图片URL或路径
     */
    String uploadImage(MultipartFile file);
}
