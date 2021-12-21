package com.lpnu.virtual.library.core.admin.service;

import com.lpnu.virtual.library.core.admin.model.FieldCreateDto;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import com.lpnu.virtual.library.metadata.field.util.FieldMapper;
import com.lpnu.virtual.library.metadata.field.util.FieldValidator;
import com.lpnu.virtual.library.util.ExecutionTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AdminActionService {
    private final FieldManagerService fieldManagerService;

    public String createField(FieldCreateDto dto) {
        filterDto(dto);
        Field field = FieldMapper.map(dto);
        return FieldValidator.validate(field) ? fieldManagerService.createField(dto) : ExecutionTypeCode.ERROR.toString();
    }

    private void filterDto(FieldCreateDto dto) {
        dto.setFieldId(dto.getFieldId().toUpperCase(Locale.ROOT));
        dto.setTableName(dto.getTableName().toLowerCase(Locale.ROOT));
    }
}
