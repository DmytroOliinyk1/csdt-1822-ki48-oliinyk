package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.thumbnail.service.ThumbnailService;
import com.lpnu.virtual.library.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ThumbnailSyndicationHandler extends AbstractSyndicationHandler {

    private final ThumbnailService thumbnailService;

    @Value("${thumbnail.directory}")
    private String thumbnailDir;

    @Override
    public AssetUploadContext syndicate(AssetUploadContext context) {
        MultipartFile thumbnail = context.getThumbnail();
        if (!thumbnailService.uploadThumbnail(thumbnail, context.getAssetId())) {
            context.addErrors(Collections.singletonList("Failed to upload thumbnail"));
        }
        return context;
    }

    private String createTargetFilePath(AssetUploadContext context) {
        return buildDir() + File.separator + context.getAssetId() + ".jpg";
    }

    private String buildDir() {
        return System.getProperty("user.dir") + File.separator + thumbnailDir;
    }
}
