package com.homebrew.controller;

import com.homebrew.model.Package;
import com.homebrew.model.PackageMetaData;
import com.homebrew.model.PackageRequest;
import com.homebrew.model.Response;
import com.homebrew.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/v1")
public class HomebrewController {

    @Autowired
    private PackageService packageService;

    @PostMapping(value = "/uploadPackage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<String>> uploadPackage(@RequestParam String name,
                                                          @RequestParam("file") MultipartFile file,
                                                          @RequestParam String version,
                                                          @RequestParam String description) throws NoSuchAlgorithmException, IOException {
        PackageRequest packageRequest = new PackageRequest(name, description, version);
        String s3Url = packageService.savePackage(file, packageRequest);
        Response<String> response = new Response.ResponseBuilder<String>()
                .setData(s3Url)
                .setTimeStamp()
                .setStatus(HttpStatus.OK.toString()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getPackage")
    public ResponseEntity<Response<PackageMetaData>> getPackageByName(@RequestParam String packageName){
        Package latestVersion = packageService.getLatestVersion(packageName);
        PackageMetaData packageMetaData = new PackageMetaData(latestVersion.getName(),latestVersion.getVersion(),latestVersion.getDownloadUrl());
        Response<PackageMetaData> response = new Response.ResponseBuilder<PackageMetaData>()
                .setData(packageMetaData)
                .setTimeStamp()
                .setStatus(HttpStatus.OK.toString()).build();
        return ResponseEntity.ok(response);
    }
}
