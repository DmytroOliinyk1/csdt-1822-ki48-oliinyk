package com.lpnu.virtual.library.core.asset.service;

import com.lpnu.virtual.library.core.asset.model.AssetMetadataDto;
import com.lpnu.virtual.library.core.preset.model.Preset;
import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.core.preset.model.PresetField;
import com.lpnu.virtual.library.core.preset.service.PresetService;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.metadata.field.model.Fields;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssetMetadataServiceTest {

    @Mock
    private PresetService presetService;

    @Mock
    private FieldManagerService fieldManagerService;

    @InjectMocks
    private AssetMetadataService assetMetadataService;

    @Test
    public void testGetFieldsByPreset() {

        FieldDto dto = new FieldDto();
        dto.setFieldId("FIELD.TEST");
        dto.setHyperLink(Boolean.FALSE);
        dto.setVisibleOnUi(Boolean.TRUE);

        PresetField presetField = new PresetField();
        presetField.setId("FIELD.TEST");

        Preset preset = new Preset();
        preset.setFields(Collections.singletonList(presetField));

        when(presetService.getPreset(any())).thenReturn(preset);

        List<FieldDto> dtos = assetMetadataService.getFieldsByPreset(PresetCode.PREVIEW);

        verify(presetService).getPreset(any());
        verifyNoMoreInteractions(presetService);

        assertEquals(dto, dtos.stream()
                .findFirst()
                .orElse(null));

    }

    @Test
    public void testGetAssetMetadata() {

        FieldDto dto = new FieldDto();
        dto.setFieldId("FIELD.TEST");
        dto.setHyperLink(Boolean.FALSE);
        dto.setVisibleOnUi(Boolean.TRUE);
        dto.setDisplayValue("test");

        PresetField presetField = new PresetField();
        presetField.setId("FIELD.TEST");

        Preset preset = new Preset();
        preset.setFields(Collections.singletonList(presetField));

        AssetMetadataDto assetMetadataDto = new AssetMetadataDto(Collections.singletonList(dto));

        Long dummyId = 1L;

        when(presetService.getPreset(any())).thenReturn(preset);
        when(fieldManagerService.getDisplayedValue(dummyId, "FIELD.TEST")).thenReturn("test");

        AssetMetadataDto result = assetMetadataService.getAssetMetadata(dummyId, PresetCode.PREVIEW);

        assertEquals(result, assetMetadataDto);

    }
}
