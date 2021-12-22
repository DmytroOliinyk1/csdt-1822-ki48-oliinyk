package com.lpnu.virtual.library.metadata.field.util;

import com.lpnu.virtual.library.metadata.field.db.Sqls;
import com.lpnu.virtual.library.metadata.field.model.FieldType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlUtils {
    public static String createLinearSql(String tableName, Integer size) {
        return String.format(Sqls.CREATE_JOIN_TABLE_FOR_LINEAR_FIELD, tableName, size, tableName);
    }

    public static String createTabularSql(String tableName, Integer size) {
        return String.format(Sqls.CREATE_JOIN_TABLE_FOR_TABULAR_FIELD, tableName, size, tableName);
    }

    public static String createTableSql(FieldType type, String tableName, Integer size) {
        return FieldUtils.isLinear(type) ? createLinearSql(tableName, size) : createTabularSql(tableName, size);
    }
}
