package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.common.file.FileService;
import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.service.upload.AssetSyndication;
import com.lpnu.virtual.library.core.asset.service.upload.AssetSyndicationHandler;
import com.lpnu.virtual.library.core.asset.service.upload.ContentSyndicationHandler;
import com.lpnu.virtual.library.core.asset.service.upload.MetadataSyndicationHandler;
import com.lpnu.virtual.library.core.asset.service.upload.ThumbnailSyndicationHandler;
import com.lpnu.virtual.library.core.asset.service.upload.ValidationSyndication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AssetUploadService {
    private final ValidationSyndication validator;
    private final AssetSyndication assetSyndication;
    private final ContentSyndicationHandler contentSyndicationHandler;
    private final MetadataSyndicationHandler metadataSyndicationHandler;
    private final ThumbnailSyndicationHandler thumbnailSyndicationHandler;
    private final FileService fileService;

    public String syndicateAsset(AssetUploadContext context) {
        AssetSyndicationHandler handler = buildHandlerChain(
                validator,
                contentSyndicationHandler,
                assetSyndication,
                metadataSyndicationHandler,
                thumbnailSyndicationHandler
        );

        handler.perform(context);

        if (context.hasErrors()) {
            deleteFile(context);
            return "Failed of syndication: " + String.join(" | ", context.getErrors());
        }

        return "Success syndication";
    }

    private void deleteFile(AssetUploadContext context) {
        fileService.deleteFileIfExists(context.getFileName(), context.getMd5());
    }

    private AssetSyndicationHandler buildHandlerChain(AssetSyndicationHandler identity, AssetSyndicationHandler... chain) {
        Stream.of(chain).reduce(identity, AssetSyndicationHandler::addNext);
        return identity;
    }
}
