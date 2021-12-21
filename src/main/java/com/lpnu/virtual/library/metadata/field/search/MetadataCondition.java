package com.lpnu.virtual.library.metadata.field.search;

import java.util.List;

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

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
