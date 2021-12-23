package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.core.asset.service.AssetDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/asset/download")
@RequiredArgsConstructor
public class AssetDownloadController {

    private final AssetDownloadService assetDownloadService;

    @GetMapping()
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam Long id) throws IOException {
        Map<String, Object> resource = assetDownloadService.getStreamFromContent(id);

        File fileContent = (File) resource.get("content");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + URLEncoder.encode(fileContent.getName(), "UTF-8"))
                .contentType(MediaType.valueOf(resource.get("mimetype").toString() + "; charset=UTF-8"))
                .contentLength(fileContent.length())
                .body(new InputStreamResource(
                        new FileInputStream(fileContent)));
    }
}
