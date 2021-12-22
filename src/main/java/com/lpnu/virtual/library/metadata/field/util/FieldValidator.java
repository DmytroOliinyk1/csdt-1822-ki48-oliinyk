package com.lpnu.virtual.library.metadata.field.util;

import com.lpnu.virtual.library.metadata.field.model.Field;
import org.apache.commons.lang3.StringUtils;

public class FieldValidator {
    public static Boolean validate(Field field) {
        return StringUtils.isNotBlank(field.getFieldId())
                && StringUtils.isNotBlank(field.getTableName())
                && field.getType() != null;
    }
}
