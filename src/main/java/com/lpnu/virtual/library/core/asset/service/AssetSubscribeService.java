package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.search.MetadataSearchManager;
import com.lpnu.virtual.library.metadata.field.search.SearchConstructor;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AssetSubscribeService {
    private final FieldManagerService fieldManagerService;

    public void subscribe(Long id) {
        if (!isSubscribed(id)) {
            fieldManagerService.saveMetadata(id, Fields.SUBSCRIBER, UserUtils.getUserLogin());
        }
    }

    public void unsubscribe(Long id) {
        fieldManagerService.deleteMetadataValue(id, Fields.SUBSCRIBER, UserUtils.getUserLogin());
    }

    public Boolean isSubscribed(Long id) {
        return fieldManagerService.getTabularValues(id, Fields.SUBSCRIBER).contains(id.toString());
    }
}
