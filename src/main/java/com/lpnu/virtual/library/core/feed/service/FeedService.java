package com.lpnu.virtual.library.core.feed.service;

import com.lpnu.virtual.library.common.model.Pagination;
import com.lpnu.virtual.library.common.service.CacheService;
import com.lpnu.virtual.library.core.asset.model.AssetDto;
import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.model.PagedResult;
import com.lpnu.virtual.library.core.asset.service.AssetMetadataService;
import com.lpnu.virtual.library.core.asset.service.AssetPreviewService;
import com.lpnu.virtual.library.core.asset.utils.AssetUtils;
import com.lpnu.virtual.library.core.feed.mapper.SavedSearchMapper;
import com.lpnu.virtual.library.core.feed.model.SavedSearch;
import com.lpnu.virtual.library.core.feed.repository.FeedRepository;
import com.lpnu.virtual.library.core.feed.repository.FeedRepositoryExtended;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.model.Values;
import com.lpnu.virtual.library.metadata.field.search.MetadataSearchManager;
import com.lpnu.virtual.library.metadata.field.search.SearchConstructor;
import com.lpnu.virtual.library.metadata.field.search.SearchUtils;
import com.lpnu.virtual.library.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepo;
    private final FeedRepositoryExtended feedRepoExt;
    private final AssetMetadataService assetMetadataService;
    private final CacheService cacheService;
    private final MetadataSearchManager metadataSearchManager;
    private final AssetPreviewService assetPreviewService;

    public List<Long> getAssetIds() {
        return feedRepoExt.getAssetIdsByUserLoginDesc(UserUtils.getUserLogin());
    }

    public SavedSearch logSearch(Long id) {
        return feedRepo.save(SavedSearchMapper.map(id));
    }

    public List<SavedSearch> logSearch(List<Long> ids) {
        return ids.stream()
                .map(SavedSearchMapper::map)
                .peek(feedRepo::save)
                .collect(Collectors.toList());
    }

    public PagedResult startSearch(Pagination pagination) {
        Set<Long> forFeedIds = new HashSet<>(getAssetIds());
        List<AssetMetadataDto> assets = forFeedIds.stream()
                .map(id -> assetMetadataService.getAssetMetadata(id, PresetCode.FEED_FILTERS))
                .collect(Collectors.toList());

        SearchConstructor sc = SearchUtils.buildConstructorForFeed(assets);
        return startSearch(sc, pagination, new ArrayList<>(forFeedIds));
    }

    public PagedResult startSearch(SearchConstructor sc, Pagination pagination, List<Long> forFeedIds) {
        pagination = PaginationUtils.initPagination(pagination);

        List<Long> assetIds;

        if (!cacheService.containsInSearchIds(pagination.getSearchId())) {
            assetIds = new ArrayList<>(getWithFilters(sc, forFeedIds));

            String searchId = String.valueOf(System.nanoTime());
            cacheService.putInSearchIds(searchId, assetIds);
            pagination.setSearchId(searchId);
        }
        assetIds = cacheService.getFromSearchIds(pagination.getSearchId());
        pagination.setSize(assetIds.size());

        List<AssetDto> assetDtos = PaginationUtils.getAssetIdsOnPage(assetIds, pagination)
                .stream()
                .map(id -> assetPreviewService.getAssetDetails(id, PresetCode.PREVIEW_PAGE))
                .filter(a -> Values.BOOK_ASSET_TYPE.equals(
                        AssetUtils.getFieldValueById(a.getMetadata(), Fields.ASSET_TYPE)))
                .collect(Collectors.toList());

        pagination.setSize(assetDtos.size());

        return new PagedResult(assetDtos, pagination);
    }

    private Set<Long> getWithFilters(SearchConstructor sc, List<Long> forFeedIds) {
        return new HashSet<>(metadataSearchManager.getAssetIdByCondition(sc));
    }

    public void cleanUpForAllUser() {
        List<String> userLogins = feedRepo.getUniqueUser();
        for (String userLogin : userLogins) {
            if (feedRepo.getCountByUserLogin(userLogin) >= 100L) {
                feedRepo.deleteAllById(feedRepoExt.getOldIdsByUserLogin(userLogin));
            }
        }
    }
}
