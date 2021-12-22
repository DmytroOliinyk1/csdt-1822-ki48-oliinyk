package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.Asset;
import com.lpnu.virtual.library.core.asset.repository.AssetRepository;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    @Mock
    private AssetRepository repo;

    @InjectMocks
    private AssetService assetService;

    @Test
    public void testGetContentPath() {
        when(repo.getContentPath(1L)).thenReturn("path");

        String result = assetService.getContentPath(1L);

        verify(repo).getContentPath(1L);
        verifyNoMoreInteractions(repo);

        assertEquals(result, "path");
    }

    @Test
    public void testIsContentExists() {
        when(repo.existsByChecksum("md5")).thenReturn(Boolean.TRUE);

        Boolean result = assetService.isContentExists("md5");

        verify(repo).existsByChecksum("md5");
        verifyNoMoreInteractions(repo);

        assertTrue(result);
    }

    @Test
    public void testSave() {
        Asset asset = new Asset();
        asset.setId(1L);

        when(repo.save(asset)).thenReturn(asset);

        Asset result = assetService.save(asset);

        verify(repo).save(asset);
        verifyNoMoreInteractions(repo);

        assertEquals(result, asset);
    }
}
