package com.homebrew.homebrew.cli.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
@Slf4j
public class PackageManagerUtil {
    public static Path installDir = Paths.get(System.getProperty("user.home"), ".custombrew", "packages");
    public static Path installedJson = Paths.get(System.getProperty("user.home"), ".custompaaji", "installed.json");
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void ensureSetup() throws IOException {
        if (!Files.exists(installDir)) Files.createDirectories(installDir);
        if (!Files.exists(installedJson)) Files.write(installedJson, "[]".getBytes());
    }

    public static void extractZip(InputStream zipStream, Path outputDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path newPath = outputDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(newPath);
                } else {
                    Files.createDirectories(newPath.getParent());
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static List<String> readInstalled() throws IOException {
        return objectMapper.readValue(installedJson.toFile(), new TypeReference<List<String>>() {});
    }

    public static void writeInstalled(List<String> packages) throws IOException {
        objectMapper.writeValue(installedJson.toFile(), packages);
    }
}
