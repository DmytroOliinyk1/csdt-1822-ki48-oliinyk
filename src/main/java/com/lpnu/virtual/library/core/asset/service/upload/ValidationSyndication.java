package com.lpnu.virtual.library.core.asset.service.upload;

import com.lpnu.virtual.library.core.asset.model.AssetUploadContext;
import com.lpnu.virtual.library.core.asset.service.AssetSearchService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.search.MetadataSearchManager;
import com.lpnu.virtual.library.metadata.field.search.SearchUtils;
import com.lpnu.virtual.library.metadata.field.util.FieldUtils;
import com.lpnu.virtual.library.util.FileUtils;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationSyndication extends AbstractSyndicationHandler {

    private final MetadataSearchManager metadataSearchManager;

    @Override
    protected AssetUploadContext syndicate(AssetUploadContext context) {
        validateFile(context);
        validateMetadata(context);
        validateUser(context);
        validateAuthor(context);
        return context;
    }

    private void validateAuthor(AssetUploadContext context) {
        if (context.isAuthorUpload()) {
            String pseudonym = FieldUtils.getFieldValue(context.getFields(), Fields.PSEUDONYM);
            if (ValuesUtils.hasElements(metadataSearchManager.getAssetIdByCondition(
                    SearchUtils.buildConstructorForSearchAuthorByPseudonym(pseudonym)))) {
                context.addErrors(Collections.singletonList("Author is already exists"));
            }

        }
    }

    private void validateFile(AssetUploadContext context) {
        MultipartFile file = context.getFile();
        if ((file == null || file.isEmpty() || !FileUtils.validFileExtinction(context.getFileName()))
                && !context.isAuthorUpload()) {
            context.addErrors(Collections.singletonList("Invalid file"));
        }
    }

    private void validateMetadata(AssetUploadContext context) {
        List<FieldDto> metadata = context.getFields();
        if (!ValuesUtils.hasElements(metadata)) {
            context.addErrors(Collections.singletonList("Empty metadata"));
        }
    }

    private void validateUser(AssetUploadContext context) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || !user.isEnabled() || !ValuesUtils.hasElements(user.getAuthorities())) {
            context.addErrors(Collections.singletonList("Invalid user"));
        }
    }
}
