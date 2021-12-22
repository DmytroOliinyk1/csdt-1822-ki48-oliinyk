package com.lpnu.virtual.library.core.admin.model;

import lombok.Data;

@Data
public class FieldCreateDto {
    private String fieldId;
    private String tableName;
    private Integer size;
    private String type;
}
