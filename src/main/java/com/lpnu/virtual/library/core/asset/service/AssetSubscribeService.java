package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetSubscribeService {
    private final FieldManagerService fieldManagerService;

    public Boolean subscribe(Long id) {
        if (!isSubscribed(id)) {
            return fieldManagerService.saveMetadata(id, Fields.SUBSCRIBER, UserUtils.getUserLogin());
        }
        return Boolean.FALSE;
    }

    public Boolean unsubscribe(Long id) {
        return fieldManagerService.deleteMetadataValue(id, Fields.SUBSCRIBER, UserUtils.getUserLogin());
    }

    public Boolean isSubscribed(Long id) {
        return fieldManagerService.getTabularValues(id, Fields.SUBSCRIBER).contains(id.toString());
    }
}
