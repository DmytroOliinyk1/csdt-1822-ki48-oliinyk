package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.content.service.AssetContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AssetDownloadService {

    private final AssetContentService assetContentService;

    public Map<String, Object> getStreamFromContent(Long id) throws IOException {
        Map<String, Object> result = new HashMap<>();

        String contentPath = assetContentService.getAssetContentPath(id);

        File content = new File(contentPath);

        result.put("mimetype", getMimetypeOfAsset(contentPath));
        result.put("content", content);

        return result;

    }

    public String getMimetypeOfAsset(String path) throws IOException {
        return Files.probeContentType(new File(path)
                .toPath());
    }
}
