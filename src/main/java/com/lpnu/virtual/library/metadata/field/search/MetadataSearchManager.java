package com.lpnu.virtual.library.metadata.field.search;

import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetadataSearchManager {
    private final SearchManagerDao searchManagerDao;
    private final FieldManagerService fieldManager;

    public List<Long> getAssetIdByCondition(SearchConstructor sc) {
        return searchManagerDao.getAssetIds(sc.construct(fieldManager.getAllFieldsConfig()))
                .stream()
                .map(id -> Long.valueOf(id.toString()))
                .collect(Collectors.toList());
    }
}
