package com.lpnu.virtual.library.metadata.field.util;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.FieldType;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FieldUtils {
    public static Boolean isLinear(Field field) {
        return FieldType.LINEAR.equals(field.getType());
    }


    public static Boolean isLinear(FieldType type) {
        return FieldType.LINEAR.equals(type);
    }


    public static FieldDto getField(List<FieldDto> fields, String fieldId) {
        return fields.stream().filter(f -> fieldId.equals(f.getFieldId())).findFirst().orElse(null);
    }

    public static String getFieldValue(List<FieldDto> fields, String fieldId) {
        FieldDto field = getField(fields, fieldId);
        return field != null ? field.getDisplayValue() : StringUtils.EMPTY;
    }

    public static Boolean isSubscribed(List<FieldDto> fields) {
        FieldDto subscription = getField(fields, Fields.SUBSCRIBER);
        return subscription != null ? subscription.getDisplayValue().contains(UserUtils.getUserLogin()) : Boolean.FALSE;
    }

    public static List<String> getFieldValues(List<AssetMetadataDto> assets, String fieldId) {
        return assets.stream()
                .map(AssetMetadataDto::getFields)
                .flatMap(Collection::stream)
                .map(FieldDto::getDisplayValue)
                .filter(fieldId::equals)
                .collect(Collectors.toList());
    }
}
