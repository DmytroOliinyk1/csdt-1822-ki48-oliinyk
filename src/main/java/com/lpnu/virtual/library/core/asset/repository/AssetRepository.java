package com.lpnu.virtual.library.core.asset.repository;

import com.lpnu.virtual.library.core.asset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("select a.contentPath from Asset a where a.id = ?1")
    String getContentPath(Long assetId);

    Boolean existsByChecksum(String checksum);
}
