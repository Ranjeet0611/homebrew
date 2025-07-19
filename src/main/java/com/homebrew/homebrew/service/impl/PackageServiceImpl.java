package com.homebrew.homebrew.service.impl;

import com.homebrew.homebrew.exception.NoSuchPackageFoundException;
import com.homebrew.homebrew.model.Package;
import com.homebrew.homebrew.model.PackageRequest;
import com.homebrew.homebrew.repository.PackageRepository;
import com.homebrew.homebrew.service.PackageService;
import com.homebrew.homebrew.service.UploadStrategy;
import com.homebrew.homebrew.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    @Qualifier("awsS3Upload")
    private UploadStrategy awsS3Upload;

    @Override
    public Package getLatestVersion(String name) {
        try {
            log.info("Start getLatestVersion :{}", name);
            List<Package> packages = packageRepository.getPackageByName(name);
            if (CollectionUtils.isEmpty(packages)) {
                throw new NoSuchPackageFoundException("Package with name " + name + " doesn't exists");
            }
            return packages.get(0);
        } catch (Exception e) {
            log.error("Exception occurred while getLatestVersion :{}", e.getMessage(),e);
            throw e;
        }
    }
    @Override
    public String savePackage(MultipartFile file, PackageRequest pkg) throws NoSuchAlgorithmException, IOException {
        try{
            log.info("Start savePackage :{}",pkg);
            List<Package> packages = getExistingPackages(file, pkg);
            if(!CollectionUtils.isEmpty(packages)){
                return packages.get(0).getDownloadUrl();
            }
            UploadStrategyImpl uploadStrategy = new UploadStrategyImpl(awsS3Upload);
            String s3Url = uploadStrategy.uploadFile(file, pkg);
            save(s3Url,pkg);
            return s3Url;
        }
        catch (Exception e){
            log.error("Exception occurred while savePackage :{}",e.getMessage(),e);
            throw e;
        }
    }

    private void save(String s3Url,PackageRequest packageRequest) {
        Package pkg = new Package();
        pkg.setId(UUID.randomUUID().toString());
        pkg.setName(packageRequest.getName());
        pkg.setDescription(packageRequest.getDescription());
        pkg.setVersion(packageRequest.getVersion());
        pkg.setDownloadUrl(s3Url);
        pkg.setContentHash(packageRequest.getContentHash());
        pkg.setCreatedAt(LocalDateTime.now());
        packageRepository.save(pkg);
    }

    private List<Package> getExistingPackages(MultipartFile file,PackageRequest pkg) throws NoSuchAlgorithmException, IOException {
        try{
            String contentHash = HashUtil.getSHA265Hash(file.getInputStream());
            pkg.setContentHash(contentHash);
            List<Package> packages = packageRepository.getPackageByNameAndContentHash(pkg.getName(), contentHash);
            if(CollectionUtils.isEmpty(packages)){
                return  null;
            }
            return packages;
        }
        catch (Exception e){
            log.error("Exception occurred while getExistingPackages :{}",e.getMessage(),e);
            throw e;
        }
    }
}
