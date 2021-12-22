package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.model.Values;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataSyndicationHandler extends AbstractSyndicationHandler {

    private final FieldManagerService fieldManagerService;

    @Override
    protected AssetUploadContext syndicate(AssetUploadContext context) {
        setUploader(context);
        setAssetType(context);
        context.getFields().forEach(f -> {
            try {
                fieldManagerService.saveMetadata(context.getAssetId(), f.getFieldId(), f.getDisplayValue());
            } catch (RuntimeException e) {

            }
        });
        return context;
    }

    private void setAssetType(AssetUploadContext context) {
        FieldDto assetType = new FieldDto(Fields.ASSET_TYPE);
        assetType.setDisplayValue(
                context.isAuthorUpload() ? Values.AUTHOR_ASSET_TYPE : Values.BOOK_ASSET_TYPE);
        context.getFields().add(assetType);
    }

    private void setUploader(AssetUploadContext context) {
        FieldDto uploader = new FieldDto(Fields.UPLOADER);
        uploader.setDisplayValue(UserUtils.getUserLogin());
        context.getFields().add(uploader);
    }
}
