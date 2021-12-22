package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.AssetDto;
import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.asset.thumbnail.service.ThumbnailService;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetPreviewServiceTest {

    @Mock
    private AssetMetadataService assetMetadataService;

    @Mock
    private ThumbnailService thumbnailService;

    @InjectMocks
    private AssetPreviewService assetPreviewService;

    @Test
    public void test() {
    }

    @Test
    public void testGetAssetDetails() {
        FieldDto dto = new FieldDto();
        dto.setFieldId("FIELD.TEST");
        dto.setHyperLink(Boolean.FALSE);
        dto.setVisibleOnUi(Boolean.TRUE);
        dto.setDisplayValue("test");

        AssetMetadataDto assetMetadataDto = new AssetMetadataDto(Collections.singletonList(dto));
        AssetDto assetDto = new AssetDto();
        assetDto.setId(1L);
        assetDto.setMetadata(assetMetadataDto);
        assetDto.setThumbnail("data:image/gif;base64,encoded");

        when(assetMetadataService.getAssetMetadata(1L, PresetCode.PREVIEW)).thenReturn(assetMetadataDto);
        when(thumbnailService.getThumbnailEncode(1L)).thenReturn("encoded");
        AssetDto result = assetPreviewService.getAssetDetails(1L, PresetCode.PREVIEW);

        verify(assetMetadataService).getAssetMetadata(1L, PresetCode.PREVIEW);
        verify(thumbnailService).getThumbnailEncode(1L);
        verifyNoMoreInteractions(assetMetadataService);
        verifyNoMoreInteractions(thumbnailService);

        assertEquals(result, assetDto);
    }
}
