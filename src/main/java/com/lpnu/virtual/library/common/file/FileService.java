package com.lpnu.virtual.library.common.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${upload.directory}")
    private String uploadDir;

    public void deleteFileIfExists(String fileName, String md5) {
        if(StringUtils.isBlank(fileName) || StringUtils.isBlank(md5)) {
            return;
        }
        try {
            Path file = Paths.get(System.getProperty("user.dir"), uploadDir, md5, fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
