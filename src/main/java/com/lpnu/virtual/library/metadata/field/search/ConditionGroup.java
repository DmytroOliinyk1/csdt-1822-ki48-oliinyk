package com.lpnu.virtual.library.metadata.field.search;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConditionGroup {
    private List<MetadataCondition> metadataConditions;
    private List<Operator> innerGroupOperators;
}
