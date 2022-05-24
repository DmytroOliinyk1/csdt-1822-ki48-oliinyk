package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.common.model.Pagination;
import com.lpnu.virtual.library.common.service.CacheService;
import com.lpnu.virtual.library.core.asset.model.PagedResult;
import com.lpnu.virtual.library.core.asset.model.SearchMode;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.search.MetadataSearchManager;
import com.lpnu.virtual.library.metadata.field.search.SearchConstructor;
import com.lpnu.virtual.library.metadata.field.search.SearchUtils;
import com.lpnu.virtual.library.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetSearchService {
    private final AssetPreviewService assetPreviewService;
    private final MetadataSearchManager metadataSearchManager;
    private final AssetMetadataService assetMetadataService;
    private final CacheService cacheService;

    public PagedResult startAuthorsSearch(Long id, Pagination pagination) {
        SearchConstructor sc = SearchUtils.buildConstructorForAuthors(
                assetMetadataService.getFieldValueForTabular(id, Fields.PSEUDONYM));
        return startSearch(sc, pagination, PresetCode.PREVIEW_AUTHOR_PAGE);
    }

    public PagedResult startSearch(Pagination pagination) {
        return startSearch(pagination, SearchMode.DEFAULT);
    }

    public PagedResult startSearch(Pagination pagination, SearchMode mode) {
        return startSearch(pagination, mode, null);
    }

    public PagedResult startSearch(Pagination pagination, SearchMode mode, List<FieldDto> fields) {
        return startSearch(defineSearchConstructor(mode, fields), pagination, null);
    }

    private SearchConstructor defineSearchConstructor(SearchMode mode, List<FieldDto> fields) {
        SearchConstructor sc;
        if (SearchMode.MY_PAGE.equals(mode)) {
            sc = SearchUtils.buildConstructorForMyPage();
        } else if (SearchMode.ALL.equals(mode)) {
            sc = SearchUtils.buildConstructorForAllPage();
        } else if (SearchMode.FILTERS.equals(mode)) {
            sc = SearchUtils.buildConstructorWithFilters(fields);
        } else if (SearchMode.SUBSCRIBED.equals(mode)) {
            sc = SearchUtils.buildConstructorForSubscribed();
        } else {
            sc = SearchUtils.buildConstructorForDefault();
        }
        return sc;
    }

    public PagedResult startSearch(SearchConstructor sc, Pagination pagination, PresetCode code) {
        pagination = PaginationUtils.initPagination(pagination);

        List<Long> assetIds;

        if (!cacheService.containsInSearchIds(pagination.getSearchId())) {
            assetIds = metadataSearchManager.getAssetIdByCondition(sc);
            String searchId = String.valueOf(System.nanoTime());
            cacheService.putInSearchIds(searchId, assetIds);
            pagination.setSearchId(searchId);
        }
        assetIds = cacheService.getFromSearchIds(pagination.getSearchId());
        pagination.setSize(assetIds.size());

        return new PagedResult(PaginationUtils.getAssetIdsOnPage(assetIds, pagination)
                .stream()
                .map(id -> assetPreviewService.getAssetDetails(id, code != null ? code :PresetCode.PREVIEW_PAGE))
                .collect(Collectors.toList()), pagination);
    }

}
