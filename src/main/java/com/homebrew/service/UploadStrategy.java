package com.homebrew.service;

import com.homebrew.model.Package;
import com.homebrew.model.PackageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadStrategy {
    String uploadFile(MultipartFile file, PackageRequest pkg) throws IOException;

    String getPreSignedUrl(Package pkg);
}
