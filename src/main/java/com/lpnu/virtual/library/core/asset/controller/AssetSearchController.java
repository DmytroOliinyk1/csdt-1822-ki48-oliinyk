package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.model.SearchMode;
import com.lpnu.virtual.library.core.asset.service.AssetMetadataService;
import com.lpnu.virtual.library.core.asset.service.AssetSearchService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/asset/search")
@RequiredArgsConstructor
public class AssetSearchController {

    private final AssetSearchService assetSearchService;
    private final AssetMetadataService assetMetadataService;

    @GetMapping("/my")
    public String getMyAssets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.MY_PAGE));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "my-asset";
    }

    @GetMapping("/all")
    public String getAllAssets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.ALL));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "all-asset";
    }

    @GetMapping("/subscribed")
    public String getMyCollection(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.SUBSCRIBED));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "subscribed";
    }

    @GetMapping("/apply/filters")
    public String getFilters(Model model) {
        model.addAttribute("metadata",
                new AssetMetadataDto(assetMetadataService.getFieldsByPreset(PresetCode.FILTERS)));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "apply-filters";
    }

    @PostMapping("/apply/filters")
    public String applyFilters(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            @ModelAttribute AssetMetadataDto metadata,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.FILTERS,
                        metadata.getFields()));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "filtered-asset";
    }

    @GetMapping("/filter")
    public String getFilteredAssets(
            @RequestParam() Integer page,
            @RequestParam() String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId)));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "filtered-asset";
    }

    @GetMapping("/authors")
    public String getAuthorsOfBook(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            @RequestParam() Long id,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startAuthorsSearch(id, PaginationUtils.createPagination(page, searchId)));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "authors-paged";
    }
}
