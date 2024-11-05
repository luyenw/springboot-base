package com.canifa.stylenest.service.impl;

import com.canifa.stylenest.entity.File;
import com.canifa.stylenest.repository.FileRepository;
import com.canifa.stylenest.service.FileService;
import com.canifa.stylenest.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Override
    public File save(MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = uuid + fileType;
        storageService.save(multipartFile, newFileName);
        File file = new File();
        file.setName(newFileName);
        return fileRepository.save(file);
    }
}
