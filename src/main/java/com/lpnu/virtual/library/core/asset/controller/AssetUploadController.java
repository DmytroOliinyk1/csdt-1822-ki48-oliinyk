package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.service.AssetMetadataService;
import com.lpnu.virtual.library.core.asset.service.AssetUploadService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.user.model.CustomUserDetails;
import com.lpnu.virtual.library.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Column;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@RequestMapping("/asset/upload")
@RequiredArgsConstructor
public class AssetUploadController {
    private final AssetMetadataService assetMetadataService;
    private final AssetUploadService assetUploadService;

    @GetMapping()
    public String uploadAsset(@RequestParam(value = "code", required = false) PresetCode code, Model model) {
        model.addAttribute("metadata",
                new AssetMetadataDto(assetMetadataService.getFieldsByPreset(code != null ? code : PresetCode.BOOK_UPLOAD)));
        return PresetCode.AUTHOR_UPLOAD.equals(code) ? "author-create" : "asset-upload";
    }

    @PostMapping()
    public String uploadAsset(@RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @ModelAttribute AssetMetadataDto metadata,
            @RequestParam(value = "code", required = false) PresetCode code, Model model) {
        String result = assetUploadService.syndicateAsset(
                new AssetUploadContext(metadata.getFields(), file, thumbnail, code));
        model.addAttribute("msg", result);
        return "index";
    }
}
