package com.lpnu.virtual.library.metadata.field.service;

import com.lpnu.virtual.library.core.admin.model.FieldCreateDto;
import com.lpnu.virtual.library.metadata.field.db.FieldManagerDao;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldType;
import com.lpnu.virtual.library.metadata.field.repository.FieldRepository;
import com.lpnu.virtual.library.metadata.field.util.DaoUtils;
import com.lpnu.virtual.library.metadata.field.util.FieldMapper;
import com.lpnu.virtual.library.metadata.field.util.FieldUtils;
import com.lpnu.virtual.library.metadata.field.util.SqlUtils;
import com.lpnu.virtual.library.util.ExecutionTypeCode;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldManagerService {

    private List<Field> allFieldsConfig;

    private final FieldManagerDao fieldManagerDao;
    private final FieldRepository fieldRepo;

    @PostConstruct
    private void init() {
        allFieldsConfig = fieldRepo.findAll();
    }

    public String createField(FieldCreateDto dto) {
        Field saved = createJoinTable(FieldMapper.map(dto), dto.getSize());
        return saved == null ? ExecutionTypeCode.ERROR.toString() : ExecutionTypeCode.SUCCESS.toString();
    }

    public Field createJoinTable(Field field, Integer size) {
        if (fieldManagerDao.createJoinTable(field, size)) {
            Field saved = fieldRepo.save(field);
            reloadAllFieldsConfig();
            return saved;
        }
        return null;
    }

    public Field loadField(String fieldId) {
        return fieldRepo.findByFieldId(fieldId);
    }

    public List<String> getTabularValues(Long assetId, String fieldId) {
        return fieldManagerDao.getTabularValues(assetId.toString(), fieldRepo.findTableNameByFieldId(fieldId))
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public String getTabularDisplayed(Long assetId, String fieldId) {
        List<String> values = getTabularValues(assetId, fieldId);
        return ValuesUtils.hasElements(values) ? String.join(", ", values) : StringUtils.EMPTY;

    }

    public String getLinearValue(Long assetId, String fieldId) {
        Object value = fieldManagerDao.getLinearValue(assetId.toString(), fieldRepo.findTableNameByFieldId(fieldId));
        return value != null ? value.toString() : null;
    }

    public List<Field> getAllFieldsConfig() {
        return allFieldsConfig;
    }

    public void reloadAllFieldsConfig() {
        allFieldsConfig = fieldRepo.findAll();
    }

    public String getDisplayedValue(Long assetId, String fieldId) {
        return FieldUtils.isLinear(fieldRepo.getById(fieldId)) ? getLinearValue(assetId, fieldId)
                : getTabularDisplayed(assetId, fieldId);
    }

    public Field getFieldById(String fieldId) {
        return getAllFieldsConfig()
                .stream()
                .filter(f -> f.getFieldId().equals(fieldId))
                .findFirst()
                .orElse(null);
    }

    public Boolean deleteMetadataValue(Long assetId, String fieldId, String value) {
        Field field = getFieldById(fieldId);
        return fieldManagerDao.deleteMetadata(assetId.toString(), field.getTableName(), value);
    }

    public Boolean saveMetadata(Long assetId, String fieldId, String value) {
        Field field = getFieldById(fieldId);
        List<String> values;
        if (value == null || !StringUtils.isNotBlank(value)) {
            values = Collections.emptyList();
        } else if (FieldType.TABULAR.equals(field.getType())) {
            values = value.contains(",") ? Arrays.asList(value.split(",")) : Collections.singletonList(value);
        } else {
            values = Collections.singletonList(value);
        }
        values.forEach(v -> {
            saveMetadataValue(assetId, field.getTableName(), v.trim());
        });
        return Boolean.TRUE;
    }

    private Boolean saveMetadataValue(Long assetId, String tableName, String value) {
        return fieldManagerDao.saveMetadata(assetId.toString(), tableName, value);
    }

}
