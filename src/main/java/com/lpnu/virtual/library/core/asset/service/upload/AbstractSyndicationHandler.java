package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.util.ValuesUtils;

public abstract class AbstractSyndicationHandler implements AssetSyndicationHandler {
    protected AssetSyndicationHandler next;

    @Override
    public AssetSyndicationHandler addNext(AssetSyndicationHandler next) {
        this.next = next;
        return this.next;
    }

    @Override
    public AssetUploadContext perform(AssetUploadContext context) {
        if(!skipStep(context)){
            syndicate(context);
        }
        return ValuesUtils.hasElements(context.getErrors()) || this.next == null ? context : this.next.perform(context);
    }

    public Boolean skipStep(AssetUploadContext context) {
        return Boolean.FALSE;
    };

    protected abstract AssetUploadContext syndicate(AssetUploadContext context);
}
