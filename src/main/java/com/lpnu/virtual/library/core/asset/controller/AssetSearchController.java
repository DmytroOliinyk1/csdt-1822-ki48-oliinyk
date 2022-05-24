package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.common.model.Pagination;
import com.lpnu.virtual.library.common.service.CacheService;
import com.lpnu.virtual.library.common.utils.SessionUtils;
import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.model.PagedResult;
import com.lpnu.virtual.library.core.asset.model.SearchMode;
import com.lpnu.virtual.library.core.asset.service.AssetMetadataService;
import com.lpnu.virtual.library.core.asset.service.AssetSearchService;
import com.lpnu.virtual.library.core.feed.service.FeedService;
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

import java.util.List;

@Controller
@RequestMapping("/asset/search")
@RequiredArgsConstructor
public class AssetSearchController {

    private final AssetSearchService assetSearchService;
    private final AssetMetadataService assetMetadataService;
    private final FeedService feedService;
    private final CacheService cacheService;

    @GetMapping("/my")
    public String getMyAssets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.MY_PAGE));
        SessionUtils.setContextForModel(model);
        return "my-asset";
    }

    @GetMapping("/all")
    public String getAllAssets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.ALL));
        SessionUtils.setContextForModel(model);
        return "all-asset";
    }

    @GetMapping("/subscribed")
    public String getMyCollection(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.SUBSCRIBED));
        SessionUtils.setContextForModel(model);
        return "subscribed";
    }

    @GetMapping("/apply/filters")
    public String getFilters(Model model) {
        model.addAttribute("metadata",
                new AssetMetadataDto(assetMetadataService.getFieldsByPreset(PresetCode.FILTERS)));
        SessionUtils.setContextForModel(model);

        return "apply-filters";
    }

    @PostMapping("/apply/filters")
    public String applyFilters(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            @ModelAttribute AssetMetadataDto metadata,
            Model model) {
        PagedResult result = assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId), SearchMode.FILTERS,
                metadata.getFields());
        model.addAttribute("result", result);
        SessionUtils.setContextForModel(model);
        logSearch(result.getPagination(), cacheService.getFromSearchIds(result.getPagination().getSearchId()));
        return "filtered-asset";
    }

    @GetMapping("/filter")
    public String getFilteredAssets(
            @RequestParam() Integer page,
            @RequestParam() String searchId,
            Model model) {
        model.addAttribute("result",
                assetSearchService.startSearch(PaginationUtils.createPagination(page, searchId)));
        SessionUtils.setContextForModel(model);
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
        SessionUtils.setContextForModel(model);
        return "authors-paged";
    }

    private void logSearch(Pagination pagination, List<Long> assetIds) {
        if (UserUtils.isAuthorized() && pagination.getPage() == 1) {
            feedService.logSearch(assetIds);
        }
    }
}
