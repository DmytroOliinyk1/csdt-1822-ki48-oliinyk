package com.lpnu.virtual.library.core.feed.mapper;

import com.lpnu.virtual.library.core.feed.model.SavedSearch;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SavedSearchMapper {
    public static SavedSearch map(Long assetId) {
        SavedSearch savedSearch = new SavedSearch();
        savedSearch.setAssetId(assetId);
        savedSearch.setUserLogin(UserUtils.getUserLogin());
        savedSearch.setCreatedDate(System.currentTimeMillis());
        return savedSearch;
    }

    public static List<SavedSearch> map(List<Long> assetIds) {
        return assetIds.stream()
                .map(SavedSearchMapper::map)
                .collect(Collectors.toList());
    }
}
