package com.lpnu.virtual.library.metadata.field.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldDto {
    private String fieldId;
    private String title;
    private String displayValue;
    private Boolean visibleOnUi;
    private Boolean hyperLink;

    public FieldDto(String fieldId) {
        this.fieldId = fieldId;
    }

    public FieldDto(String fieldId, String displayValue) {
        this.fieldId = fieldId;
        this.displayValue = displayValue;
    }

    public Boolean isVisibleOnUi() {
        return this.visibleOnUi;
    }

    public Boolean isHyperLink() {
        return this.hyperLink;
    }
}
