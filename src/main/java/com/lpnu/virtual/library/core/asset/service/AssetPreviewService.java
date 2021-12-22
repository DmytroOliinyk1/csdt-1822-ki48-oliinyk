package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.AssetDto;
import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.thumbnail.service.ThumbnailService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetPreviewService {

    private final AssetMetadataService assetMetadataService;
    private final ThumbnailService thumbnailService;

    public AssetDto getAssetDetails(Long id, PresetCode code) {
        AssetDto asset = new AssetDto();

        AssetMetadataDto metadata = assetMetadataService.getAssetMetadata(id, code);
        String thumbnailEncode = thumbnailService.getThumbnailEncode(id);

        asset.setId(id);
        asset.setMetadata(metadata);
        asset.setThumbnail("data:image/gif;base64," + thumbnailEncode);
        return asset;
    }
}
