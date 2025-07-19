package com.homebrew.homebrew.service.impl;

import com.homebrew.homebrew.exception.S3UploadFailException;
import com.homebrew.homebrew.model.Package;
import com.homebrew.homebrew.model.PackageRequest;
import com.homebrew.homebrew.service.UploadStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service("awsS3Upload")
@Slf4j
public class AWSS3Upload implements UploadStrategy {
    @Value("${aws.s3.storage.bucket-name}")
    private String bucketName;
    @Value("${aws.s3.download.url}")
    private String s3DownloadUrl;
    @Autowired
    private S3Client s3Client;


    @Override
    public String uploadFile(MultipartFile file, PackageRequest pkg) throws IOException {
        try {
            String key = pkg.getName() + "-" + pkg.getVersion() + ".zip";
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return String.format(s3DownloadUrl, bucketName, key);
        } catch (Exception e) {
            log.error("Exception occurred while uploadFile :{}", e.getMessage());
            throw new S3UploadFailException("Failed to upload file to S3 bucket "+pkg.getName());
        }
    }
}
