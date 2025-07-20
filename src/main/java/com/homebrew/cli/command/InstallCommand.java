package com.homebrew.cli.command;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebrew.cli.model.PackageMetaData;
import com.homebrew.cli.model.Response;
import com.homebrew.cli.util.PackageManagerUtil;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@CommandLine.Command(name = "install", description = "Install a package")
public class InstallCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = "Package name to install")
    private String packageName;


    @Override
    public void run() {
        try {
            PackageManagerUtil.ensureSetup();
            log.info("Installing package :{}", packageName);
            String appUrl = PackageManagerUtil.getConfig("spring.app.url");
            URL url = new URL(appUrl + packageName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.err.println("Package not found on server");
                return;
            }
            Response<PackageMetaData> metadata = new ObjectMapper().readValue(conn.getInputStream(), new TypeReference<Response<PackageMetaData>>() {
            });
            URL s3Url = new URL(metadata.getData().getS3Url());
            try (InputStream in = s3Url.openStream()) {
                Path outputDir = PackageManagerUtil.installDir.resolve(packageName);
                PackageManagerUtil.extractZip(in, outputDir);
            }

            List<String> installed = PackageManagerUtil.readInstalled();
            if (!installed.contains(packageName)) installed.add(packageName);
            PackageManagerUtil.writeInstalled(installed);

            log.info("Package Installed :{}", packageName);
        } catch (Exception e) {
            log.error("Exception occurred while install package :{}", e.getMessage(), e);
        }
    }
}
