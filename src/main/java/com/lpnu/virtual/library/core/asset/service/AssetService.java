package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.Asset;
import com.lpnu.virtual.library.core.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository repo;

    public String getContentPath(Long assetId) {
        return repo.getContentPath(assetId);
    }

    public Boolean isContentExists(String md5) {
        return repo.existsByChecksum(md5);
    }

    public Asset save(Asset asset) {
        return repo.save(asset);
    }

    public Asset get(Long id) {
        return repo.getById(id);
    }
}
