package com.lpnu.virtual.library.util;

import com.lpnu.virtual.library.metadata.field.db.FieldManagerDao;
import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static final List<String> ALLOWED_FILE_EXTINCTIONS = Collections.unmodifiableList(
            Arrays.asList("pdf", "doc", "docx", "txt"));

    public static void copyToDirectory(InputStream source, Path target) throws IOException {
        Files.copy(source, target);
    }

    public static String getMd5Digest(InputStream file) {
        try {
            return DigestUtils.md5Hex(file);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }

    public static Boolean validFileExtinction(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return ALLOWED_FILE_EXTINCTIONS.contains(extension);
    }
}
