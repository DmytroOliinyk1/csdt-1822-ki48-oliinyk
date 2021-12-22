package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;

public interface AssetSyndicationHandler {
    AssetSyndicationHandler addNext(AssetSyndicationHandler handler);

    AssetUploadContext perform(AssetUploadContext context);
}
