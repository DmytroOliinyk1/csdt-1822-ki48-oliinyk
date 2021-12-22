package com.lpnu.virtual.library.core.asset.content.service;

import com.lpnu.virtual.library.core.asset.service.AssetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetContentServiceTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetContentService assetContentService;

    @Test
    public void testGetAssetContentPath() {
        when(assetService.getContentPath(1L)).thenReturn("dummy.txt");

        String result = assetContentService.getAssetContentPath(1L);

        verify(assetService).getContentPath(1L);
        verifyNoMoreInteractions(assetService);

        assertEquals(result, System.getProperty("user.dir") + File.separator + "null\\dummy.txt");
    }

}
