package com.lpnu.virtual.library.core.asset.model;
import com.lpnu.virtual.library.metadata.field.util.FieldUtils;
import lombok.Data;

@Data
public class AssetDto {
    private Long id;
    private String thumbnail;
    private AssetMetadataDto metadata;
    private Boolean isCurrentSubscribed;

    public Boolean isSubscribed() {
        return FieldUtils.isSubscribed(metadata.getFields());
    }
}
