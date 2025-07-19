package com.homebrew.homebrew.service;

import com.homebrew.homebrew.model.Package;
import com.homebrew.homebrew.model.PackageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface PackageService {
    Package getLatestVersion(String name);
    String savePackage(MultipartFile file, PackageRequest pkg) throws NoSuchAlgorithmException, IOException;
}
