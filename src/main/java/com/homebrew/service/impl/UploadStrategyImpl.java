package com.homebrew.service.impl;

import com.homebrew.model.PackageRequest;
import com.homebrew.service.UploadStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UploadStrategyImpl {
    private final UploadStrategy uploadStrategy;

    public UploadStrategyImpl(UploadStrategy uploadStrategy) {
        this.uploadStrategy = uploadStrategy;
    }

    public String uploadFile(MultipartFile file, PackageRequest pkg) throws IOException {
        return uploadStrategy.uploadFile(file,pkg);
    }
}
