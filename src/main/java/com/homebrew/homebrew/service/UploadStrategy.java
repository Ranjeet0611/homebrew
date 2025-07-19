package com.homebrew.homebrew.service;

import com.homebrew.homebrew.model.Package;
import com.homebrew.homebrew.model.PackageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadStrategy {
    String uploadFile(MultipartFile file, PackageRequest pkg) throws IOException;
}
