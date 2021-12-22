package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.core.asset.service.AssetPreviewService;
import com.lpnu.virtual.library.core.feed.service.FeedService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/asset/preview")
@RequiredArgsConstructor
public class AssetPreviewController {

    private final AssetPreviewService assetPreviewService;
    private final FeedService feedService;

    @GetMapping
    public String getPreview(@RequestParam Long id, @RequestParam(required = false) PresetCode code,
            Model model) {
        model.addAttribute("asset", assetPreviewService.getAssetDetails(id,
                PresetCode.PREVIEW_AUTHOR.equals(code) ? PresetCode.PREVIEW_AUTHOR : PresetCode.PREVIEW));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        if (!PresetCode.PREVIEW_AUTHOR.equals(code) && UserUtils.isAuthorized()) {
            feedService.logSearch(id);
        }
        return PresetCode.PREVIEW_AUTHOR.equals(code) ? "author-preview" : "asset-preview";
    }

}
