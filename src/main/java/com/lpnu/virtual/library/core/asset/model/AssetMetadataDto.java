package com.lpnu.virtual.library.core.asset.model;

import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import lombok.Data;

import java.util.List;

@Data
public class AssetMetadataDto {
    private List<FieldDto> fields;
    private String srcPath;

    public AssetMetadataDto(List<FieldDto> fields) {
        this.fields = fields;
    }
}
