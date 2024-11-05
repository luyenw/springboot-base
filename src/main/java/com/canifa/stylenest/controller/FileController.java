package com.canifa.stylenest.controller;

import com.canifa.stylenest.entity.dto.response.ApiResponse;
import com.canifa.stylenest.exception.CommonException;
import com.canifa.stylenest.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                storageService.save(file);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .message("Uploaded the file successfully: ")
                            .success(true)
                            .build()
            );
        } catch (Exception e) {
            String message = "Could not upload the file: " + ". Error: " + e.getMessage();
            throw new CommonException(message, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
