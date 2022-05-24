package com.lpnu.virtual.library.core.asset.utils;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@UtilityClass
public class AssetUtils {
    public static String getFieldValueById(AssetMetadataDto assetMetadataDto, String fieldId) {
        FieldDto fieldDto = assetMetadataDto.getFields().stream()
                .filter(f -> f.getFieldId().equals(fieldId))
                .findFirst()
                .orElse(null);
        return Objects.nonNull(fieldDto) ? fieldDto.getDisplayValue() : StringUtils.EMPTY;
    }
}
