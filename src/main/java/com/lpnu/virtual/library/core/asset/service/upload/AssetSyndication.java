package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.Asset;
import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AssetSyndication extends AbstractSyndicationHandler {

    @Value("${upload.directory}")
    private String uploadDir;

    private final AssetService assetService;

    @Override
    protected AssetUploadContext syndicate(AssetUploadContext context) {
        Asset asset = new Asset();
        asset.setChecksum(context.getMd5());
        asset.setContentPath(context.getMd5() + File.separator + context.getFileName());
        try {
            asset = assetService.save(asset);
        } catch (RuntimeException e) {
            context.addErrors(Collections.singletonList("Failed to save asset"));
            return context;
        }
        context.setAssetId(asset.getId());
        return context;
    }
}
