package com.lpnu.virtual.library.metadata.field.util;

import com.lpnu.virtual.library.core.admin.model.FieldCreateDto;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldType;

public class FieldMapper {
    public static Field map(FieldCreateDto dto) {
        Field field = new Field();
        field.setFieldId(dto.getFieldId());
        field.setTableName(dto.getTableName());
        field.setType(FieldType.valueOf(dto.getType()));
        return field;
    }
}
