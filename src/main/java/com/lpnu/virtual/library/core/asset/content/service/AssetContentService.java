package com.lpnu.virtual.library.core.asset.content.service;

import com.lpnu.virtual.library.core.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class AssetContentService {

    @Value("${upload.directory}")
    private String uploadDir;

    private final AssetService assetService;

    public String getAssetContentPath(Long id) {
        return buildDir() + File.separator + assetService.getContentPath(id);
    }

    private String buildDir() {
        return System.getProperty("user.dir") + File.separator + uploadDir;
    }
}
