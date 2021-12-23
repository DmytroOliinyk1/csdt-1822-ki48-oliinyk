package com.lpnu.virtual.library.metadata.field.search;

public enum Operator {
    IS("="),
    LIKE("like"),
    IS_NOT("!="),
    IS_EMPTY("is null"),
    IS_NOT_EMPTY("is not null"),
    IS_ANY_OF("in"),
    WHERE("where"),
    AND("and"),
    OR("or");
    private final String sql;

    Operator(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
