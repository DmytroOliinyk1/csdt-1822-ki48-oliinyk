package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.service.AssetService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.util.FileUtils;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContentSyndicationHandler extends AbstractSyndicationHandler {
    @Value("${upload.directory}")
    private String uploadDir;

    private final AssetService assetService;

    @Override
    public Boolean skipStep(AssetUploadContext context) {
        return context.isAuthorUpload();
    }

    @Override
    public AssetUploadContext syndicate(AssetUploadContext context) {
        MultipartFile uploadFile = context.getFile();
        try (InputStream input = uploadFile.getInputStream()) {
            String md5 = FileUtils.getMd5Digest(input);

            if (assetService.isContentExists(md5)) {
                if (context.getFileName().contains("SKIP_VALIDATION")) {
                    return uploadLikeTest(context, md5);
                }
                context.addErrors(Collections.singletonList("File is already exists"));
                return context;
            }

            new File(buildMd5Dir(md5)).mkdir();

            File target = new File(createTargetFilePath(context, md5));

            target.createNewFile();

            uploadFile.transferTo(target);
            context.setMd5(md5);
            return context;
        } catch (NullPointerException | IOException e) {
            context.addErrors(Collections.singletonList("Failed to upload file"));
            return context;
        }
    }

    private AssetUploadContext uploadLikeTest(AssetUploadContext context, String md5) {
        context.setMd5(md5);
        return context;
    }

    private String createTargetFilePath(AssetUploadContext context, String md5) {
        return buildMd5Dir(md5) + File.separator + context.getFileName();
    }

    private String buildMd5Dir(String md5) {
        return System.getProperty("user.dir") + File.separator + uploadDir + File.separator + md5;
    }
}
