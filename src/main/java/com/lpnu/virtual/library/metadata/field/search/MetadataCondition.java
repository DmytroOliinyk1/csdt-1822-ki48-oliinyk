package com.lpnu.virtual.library.metadata.field.search;

import lombok.Data;

import java.util.List;

@Data
public class MetadataCondition {
    private String fieldId;
    private String value;
    private List<String> values;
    private Operator operator;

    public MetadataCondition(String fieldId, String value, Operator operator) {
        this.fieldId = fieldId;
        this.value = value;
        this.operator = operator;
    }

    public MetadataCondition(String fieldId, List<String> values,
            Operator operator) {
        this.fieldId = fieldId;
        this.values = values;
        this.operator = operator;
    }

    public MetadataCondition(String fieldId, Operator operator) {
        this.fieldId = fieldId;
        this.operator = operator;
    }
}
