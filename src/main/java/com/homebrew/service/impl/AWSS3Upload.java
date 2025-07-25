package com.homebrew.service.impl;

import com.homebrew.exception.FailedToGeneratePreSignedUrlException;
import com.homebrew.exception.S3UploadFailException;
import com.homebrew.model.Package;
import com.homebrew.model.PackageRequest;
import com.homebrew.service.UploadStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service("awsS3Upload")
@Slf4j
public class AWSS3Upload implements UploadStrategy {
    @Value("${aws.s3.storage.bucket-name}")
    private String bucketName;
    @Value("${aws.s3.download.url}")
    private String s3DownloadUrl;
    @Autowired
    private S3Client s3Client;
    @Autowired
    private S3Presigner s3Presigner;
    @Value("${aws.s3.storage.preSigned-url-ttl}")
    private int urlTTL;

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
            throw new S3UploadFailException("Failed to upload file to S3 bucket " + pkg.getName());
        }
    }

    @Override
    public String getPreSignedUrl(Package pkg) {
        try {
            String key = pkg.getName() + "-" + pkg.getVersion() + ".zip";
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            GetObjectPresignRequest preSignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(urlTTL))
                    .getObjectRequest(getObjectRequest)
                    .build();
            PresignedGetObjectRequest preSignedRequest = s3Presigner.presignGetObject(preSignRequest);
            return preSignedRequest.url().toString();
        } catch (Exception e) {
            log.error("Exception occurred while getting preSigned url :{}", e.getMessage(), e);
            throw new FailedToGeneratePreSignedUrlException("Failed to generate preSigned Url");
        }
    }
}
