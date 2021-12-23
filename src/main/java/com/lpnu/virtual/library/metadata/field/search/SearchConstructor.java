package com.lpnu.virtual.library.metadata.field.search;

import com.lpnu.virtual.library.metadata.field.db.Sqls;
import com.lpnu.virtual.library.metadata.field.model.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SearchConstructor {

    private List<MetadataCondition> conditions;
    private List<ConditionGroup> groupConditions;

    public SearchConstructor() {
        conditions = new ArrayList<>();
        groupConditions = new ArrayList<>();
    }

    public String construct(List<Field> fieldConfig) {
        StringBuilder searchSql = new StringBuilder(Sqls.SEARCH_CONSTRUCTOR_START);
        applyTableJoins(searchSql, fieldConfig);
        applyConditions(searchSql, fieldConfig);
        return searchSql.toString();
    }

    private List<String> getFieldIds() {
        Set<String> fieldsIds = conditions.stream()
                .map(MetadataCondition::getFieldId)
                .collect(Collectors.toSet());

        fieldsIds.addAll(
                groupConditions.stream()
                        .map(ConditionGroup::getMetadataConditions)
                        .flatMap(Collection::stream)
                        .map(MetadataCondition::getFieldId)
                        .collect(Collectors.toSet())
        );

        return new ArrayList<>(fieldsIds);
    }

    private void applyConditions(StringBuilder sql, List<Field> fieldConfig) {
        sql.append(Operator.WHERE.getSql()).append(" ");

        groupConditions.forEach(applyConditionGroupConsumer(sql, fieldConfig));
        if (!groupConditions.isEmpty()) {
            sql.replace(sql.length() - 4, sql.length() - 1, StringUtils.EMPTY);
        }

        conditions.forEach(applyConditionsConsumer(sql, fieldConfig));
        if (!conditions.isEmpty()) {
            sql.replace(sql.length() - 4, sql.length() - 1, StringUtils.EMPTY);
        }
    }

    private Consumer<ConditionGroup> applyConditionGroupConsumer(StringBuilder sql, List<Field> fieldConfig) {
        return gc -> {
            List<MetadataCondition> mcs = gc.getMetadataConditions();
            List<Operator> os = gc.getInnerGroupOperators();
            sql.append("(");
            for (int i = 0; i < mcs.size(); i++) {
                MetadataCondition c = mcs.get(i);
                sql.append(String.format(Sqls.WHERE_CONDITION, getTableName(fieldConfig, c.getFieldId())))
                        .append(" ")
                        .append(getValueCondition(c))
                        .append(" ");

                if (i < mcs.size() - 1) {
                    sql.append(os.get(i).getSql())
                            .append(" ");
                }
            }
            sql.append(")")
                    .append(" ")
                    .append(Operator.OR.getSql())
                    .append(" ");
        };
    }

    private String getTableName(List<Field> fieldConfig, String fieldId) {
        return fieldConfig.stream()
                .filter(fc -> fc.getFieldId().equals(fieldId))
                .map(Field::getTableName)
                .findFirst()
                .get();
    }

    private Consumer<MetadataCondition> applyConditionsConsumer(StringBuilder sql, List<Field> fieldConfig) {
        return c -> {
            fieldConfig.stream()
                    .filter(f -> c.getFieldId().equals(f.getFieldId()))
                    .findFirst().ifPresent(field ->
                            sql.append(String.format(Sqls.WHERE_CONDITION, field.getTableName()))
                                    .append(" ")
                                    .append(getValueCondition(c))
                                    .append(" ")
                                    .append(Operator.OR.getSql())
                                    .append(" "));

        };
    }

    private String getValueCondition(MetadataCondition condition) {
        if (Operator.LIKE.equals(condition.getOperator())) {
            return condition.getOperator().getSql()
                    + " '%"
                    + condition.getValue()
                    + "'";
        } else if (Operator.IS_ANY_OF.equals(condition.getOperator())) {
            return condition.getOperator().getSql()
                    + " ('"
                    + StringUtils.join(condition.getValues(), "','")
                    + "')";
        } else if (Operator.IS_EMPTY.equals(condition.getOperator()) || Operator.IS_NOT_EMPTY.equals(
                condition.getOperator())) {
            return condition.getOperator().getSql();
        } else {
            return condition.getOperator().getSql()
                    + " '"
                    + condition.getValue()
                    + "'";
        }
    }

    private void applyTableJoins(StringBuilder sql, List<Field> fieldConfig) {
        List<String> fieldIds = getFieldIds();

        fieldConfig.stream()
                .map(Field::getFieldId)
                .filter(fieldIds::contains)
                .map(f -> getFieldConsumer(fieldConfig, f))
                .collect(Collectors.toList())
                .forEach(field ->
                        joinTableConsumer(sql, field));
    }

    private StringBuilder joinTableConsumer(StringBuilder sql, Field field) {
        return sql.append(String.format(Sqls.TABLE_LEFT_JOIN, field.getTableName()))
                .append(String.format(Sqls.JOIN_ON_CONDITION, field.getTableName()));
    }

    private Field getFieldConsumer
            (List<Field> fieldConfig, String f) {
        return fieldConfig.stream().filter(fc -> fc.getFieldId().equals(f)).findFirst().get();
    }

    public SearchConstructor is(String fieldId, String value) {
        this.addMetadataCondition(fieldId, value, Operator.IS);
        return this;
    }

    public SearchConstructor isNot(String fieldId, String value) {
        this.addMetadataCondition(fieldId, value, Operator.IS_NOT);
        return this;
    }

    public SearchConstructor like(String fieldId, String value) {
        this.addMetadataCondition(fieldId, value, Operator.LIKE);
        return this;
    }

    public SearchConstructor isEmpty(String fieldId) {
        this.addMetadataCondition(fieldId, Operator.IS_EMPTY);
        return this;
    }

    public SearchConstructor isNotEmpty(String fieldId) {
        this.addMetadataCondition(fieldId, Operator.IS_NOT_EMPTY);
        return this;
    }

    public SearchConstructor isAnyOf(String fieldId, List<String> values) {
        this.addMetadataCondition(fieldId, values, Operator.IS_ANY_OF);
        return this;
    }

    public MetadataCondition isMeta(String fieldId, String value) {
        return new MetadataCondition(fieldId, value, Operator.IS);
    }

    public MetadataCondition isNotMeta(String fieldId, String value) {
        return new MetadataCondition(fieldId, value, Operator.IS_NOT);
    }

    public MetadataCondition isAnyOfMeta(String fieldId, List<String> values) {
        return new MetadataCondition(fieldId, values, Operator.IS_ANY_OF);
    }

    public MetadataCondition likeMeta(String fieldId, String value) {
        return new MetadataCondition(fieldId, value, Operator.LIKE);
    }

    public SearchConstructor addGroup(List<MetadataCondition> groupConditions, List<Operator> innerGroupOperators) {
        this.addGroupCondition(groupConditions, innerGroupOperators);
        return this;
    }

    public void addGroupCondition(List<MetadataCondition> groupConditions, List<Operator> innerGroupOperators) {
        this.groupConditions.add(new ConditionGroup(groupConditions, innerGroupOperators));
    }

    public void addMetadataCondition(String fieldId, List<String> values, Operator operator) {
        this.conditions.add(new MetadataCondition(fieldId, values, operator));
    }

    public void addMetadataCondition(String fieldId, String value, Operator operator) {
        this.conditions.add(new MetadataCondition(fieldId, value, operator));
    }

    public void addMetadataCondition(String fieldId, Operator operator) {
        this.conditions.add(new MetadataCondition(fieldId, operator));
    }

}
