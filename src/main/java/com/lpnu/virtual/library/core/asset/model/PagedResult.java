package com.lpnu.virtual.library.core.asset.model;

import com.lpnu.virtual.library.common.model.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResult {
    private List<AssetDto> assets;
    private Pagination pagination;
}
