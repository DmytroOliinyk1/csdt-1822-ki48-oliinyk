package com.lpnu.virtual.library.metadata.field.db;

public interface Sqls {
    String ASSET_ID = "asset_id";

    String FIELD_VALUE = "field_value";

    String STRING_FORMAT = "%s";

    String INT_FORMAT = "%d";

    String LINEAR_PK_CONSTRAINT = String.format("primary key (`%s`)", ASSET_ID);

    String TABULAR_PK_CONSTRAINT = String.format("primary key (`%s`, `%s`)", ASSET_ID, FIELD_VALUE);

    String INNO_DB = "ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    String ASSET_ID_REFERENCE_CONSTRAINT = String.format("foreign key (`%s`) "
                    + "references `%s`(`%s`) "
                    + "on delete cascade "
                    + "on update cascade",
            ASSET_ID, Tables.ASSET, ASSET_ID);

    String CREATE_JOIN_TABLE_FOR_LINEAR_FIELD = String.format(
            "create table if not exists `%s`"
                    + "("
                    + "`%s` bigint not null,"
                    + "`%s` varchar(%s) not null,"
                    + "%s, "
                    + "%s"
                    + ") "
                    + "%s",
            STRING_FORMAT, ASSET_ID, FIELD_VALUE, INT_FORMAT, LINEAR_PK_CONSTRAINT, ASSET_ID_REFERENCE_CONSTRAINT, INNO_DB);

    String CREATE_JOIN_TABLE_FOR_TABULAR_FIELD = String.format(
            "create table if not exists `%s`"
                    + "("
                    + "`%s` bigint not null,"
                    + "`%s` varchar(%s) not null,"
                    + "%s, "
                    + "%s"
                    + ") "
                    + "%s",
            STRING_FORMAT, ASSET_ID, FIELD_VALUE, INT_FORMAT, TABULAR_PK_CONSTRAINT, ASSET_ID_REFERENCE_CONSTRAINT,
            INNO_DB);

    String GET_LINEAR_VALUE = String.format("select %s from %s where %s = ? limit 1", FIELD_VALUE, STRING_FORMAT, ASSET_ID);

    String GET_TABULAR_VALUE = String.format("select %s from %s where %s = ?", FIELD_VALUE, STRING_FORMAT, ASSET_ID);

    String SEARCH_CONSTRUCTOR_START = String.format("select %s.%s from %s ", Tables.ASSET, ASSET_ID, Tables.ASSET);

    String TABLE_LEFT_JOIN = "left join %s ";

    String JOIN_ON_CONDITION = String.format("on %s.%s = %s.%s ", Tables.ASSET, ASSET_ID, STRING_FORMAT, ASSET_ID);

    String WHERE_CONDITION = String.format("%s.%s ", STRING_FORMAT, FIELD_VALUE);

    String INSERT_METADATA_VALUE = String.format("insert into %s (%s, %s) values (%s, '%s')",
            STRING_FORMAT, ASSET_ID, FIELD_VALUE, STRING_FORMAT, STRING_FORMAT);

    String DELETE_METADATA_VALUE = String.format("delete from %s where %s = %s and %s = '%s'",
            STRING_FORMAT, ASSET_ID, STRING_FORMAT, FIELD_VALUE, STRING_FORMAT);
}
