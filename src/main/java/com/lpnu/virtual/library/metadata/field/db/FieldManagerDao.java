package com.lpnu.virtual.library.metadata.field.db;

import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.util.DaoUtils;
import com.lpnu.virtual.library.metadata.field.util.SqlUtils;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.lpnu.virtual.library.metadata.field.db.Sqls.INSERT_METADATA_VALUE;

@Repository
@RequiredArgsConstructor
public class FieldManagerDao {
    private static final Logger LOG = LoggerFactory.getLogger(FieldManagerDao.class);

    private final DataSource dataSource;

    public Boolean createJoinTable(Field field, Integer size) {
        try {
            Connection connection = dataSource.getConnection();
            String createStatement = SqlUtils.createTableSql(field.getType(), field.getTableName(), size);
            return DaoUtils.executeInTransaction(connection, createStatement);
        } catch (SQLException e) {
            LOG.error("Failed to create join table for field: " + field, e);
            return Boolean.FALSE;
        }
    }

    public List<Object> getTabularValues(String id, String tableName) {
        try {
            Connection connection = dataSource.getConnection();
            String select = String.format(Sqls.GET_TABULAR_VALUE, tableName);
            return DaoUtils.select(connection, select, 1, id);
        } catch (SQLException e) {
            LOG.error("Failed to get values for item: " + id + ", " + tableName, e);
            return Collections.emptyList();
        }
    }

    public Object getLinearValue(String id, String tableName) {
        try {
            Connection connection = dataSource.getConnection();
            String select = String.format(Sqls.GET_LINEAR_VALUE, tableName);
            List<Object> result = DaoUtils.select(connection, select, 1, id);
            return ValuesUtils.hasElements(result) ? result.stream()
                    .findFirst()
                    .orElse(ValuesUtils.emptyValue()) : ValuesUtils.emptyValue();
        } catch (SQLException e) {
            LOG.error("Failed to get value for item: " + id + ", " + tableName, e);
            return ValuesUtils.emptyValue();
        }
    }

    public Boolean saveMetadata(String assetId, String tableName, String value) {
        try {
            Connection connection = dataSource.getConnection();
            String insert = String.format(Sqls.INSERT_METADATA_VALUE, tableName, assetId, value);
            return DaoUtils.execute(connection, insert);
        } catch (SQLException e) {
            LOG.error("Failed to save metadata for item : " + assetId + ", " + tableName + ", value: " + value, e);
            return Boolean.FALSE;
        }
    }

    public Boolean deleteMetadata(String assetId, String tableName, String value) {
        try {
            Connection connection = dataSource.getConnection();
            String delete = String.format(Sqls.DELETE_METADATA_VALUE, tableName, assetId, value);
            return DaoUtils.execute(connection, delete);
        } catch (SQLException e) {
            LOG.error("Failed to delete metadata for item : " + assetId + ", " + tableName + ", value: " + value, e);
            return Boolean.FALSE;
        }
    }
}
