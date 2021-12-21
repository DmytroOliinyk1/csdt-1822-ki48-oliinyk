package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.preset.model.Preset;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.preset.service.PresetService;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import com.lpnu.virtual.library.util.RightsUtils;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetMetadataService {

    private final PresetService presetService;
    private final FieldManagerService fieldManagerService;

    public List<FieldDto> getFieldsByPreset(PresetCode presetCode) {
        return presetService.getPreset(presetCode)
                .getFields().stream()
                .filter(f -> !ValuesUtils.hasElements(f.getAuthority())
                        || ValuesUtils.containsAny(UserUtils.getAuthoritiesAsString(), f.getAuthority()))
                .map(f -> {
                    FieldDto fieldDto = new FieldDto();
                    fieldDto.setFieldId(f.getId());
                    fieldDto.setTitle(f.getTitle());
                    fieldDto.setVisibleOnUi(f.getVisible() != null ? f.getVisible() : Boolean.TRUE);
                    fieldDto.setHyperLink(f.getHyperLink() != null ? f.getHyperLink() : Boolean.FALSE);
                    return fieldDto;
                })
                .collect(Collectors.toList());
    }

    public AssetMetadataDto getAssetMetadata(Long assetId, PresetCode presetCode) {
        return new AssetMetadataDto(getFieldsByPreset(presetCode).stream()
                .peek(f -> {
                    f.setDisplayValue(fieldManagerService.getDisplayedValue(assetId, f.getFieldId()));
                })
                .filter(f -> StringUtils.isNotBlank(f.getDisplayValue()))
                .collect(Collectors.toList()));
    }

    public String getFieldValue(Long assetId, String fieldId) {
        return fieldManagerService.getDisplayedValue(assetId, fieldId);
    }

    public List<String> getFieldValueForTabular(Long assetId, String fieldId) {
        return fieldManagerService.getTabularValues(assetId, fieldId);
    }

}
