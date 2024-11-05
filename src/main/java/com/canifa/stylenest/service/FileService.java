package com.canifa.stylenest.service;

import com.canifa.stylenest.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File save(MultipartFile multipartFile);
}
