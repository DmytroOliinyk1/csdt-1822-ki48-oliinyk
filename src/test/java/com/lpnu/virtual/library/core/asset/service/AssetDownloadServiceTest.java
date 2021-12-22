package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.content.service.AssetContentService;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetDownloadServiceTest {
    @Mock
    private AssetContentService assetContentService;

    @InjectMocks
    private AssetDownloadService assetDownloadService;

    private static final Long DUMMY_ASSET_ID = 1L;

    @Test
    public void test() {
    }

    @Test
    public void testGetStreamFromContent() throws IOException {
        when(assetContentService.getAssetContentPath(DUMMY_ASSET_ID)).thenReturn("dummy.txt");
        Map<String, Object> result = assetDownloadService.getStreamFromContent(DUMMY_ASSET_ID);

        verify(assetContentService).getAssetContentPath(DUMMY_ASSET_ID);
        verifyNoMoreInteractions(assetContentService);

        assertTrue(result.keySet().containsAll(Arrays.asList("mimetype", "content")));
        assertTrue(result.containsValue(new File("dummy.txt")));
        assertTrue(result.containsValue("text/plain"));
    }
}
