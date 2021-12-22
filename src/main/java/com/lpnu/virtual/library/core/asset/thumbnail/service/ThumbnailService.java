package com.lpnu.virtual.library.core.asset.thumbnail.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Service
public class ThumbnailService {

    @Value("${thumbnail.directory}")
    private String thumbnailDir;

    public Boolean uploadThumbnail(MultipartFile thumbnail, Long id) {
        try {
            if (thumbnail != null && !thumbnail.isEmpty()) {
                File target = new File(createTargetFilePath(id));
                target.createNewFile();
                thumbnail.transferTo(target);
            }
            return Boolean.TRUE;
        } catch (NullPointerException | IOException e) {
            return Boolean.FALSE;
        }
    }

    private String createTargetFilePath(Long id) {
        return buildDir() + File.separator + id + ".jpg";
    }

    private String buildDir() {
        return System.getProperty("user.dir") + File.separator + thumbnailDir;
    }

    public String getThumbnailEncode(Long id) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(createTargetFilePath(id)));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            return StringUtils.EMPTY;
        }

    }
}
