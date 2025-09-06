package com.retailease.billingsystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    
     String uploadFile(MultipartFile file);

    boolean deleteFile(String fileName);
}
