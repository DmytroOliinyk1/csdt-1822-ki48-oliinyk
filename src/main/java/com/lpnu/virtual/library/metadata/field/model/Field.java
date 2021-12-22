package com.lpnu.virtual.library.metadata.field.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "field_config")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Field {
    @Id
    @Column(name = "field_id")
    private String fieldId;

    @Enumerated(EnumType.STRING)
    @Column(name ="field_type")
    private FieldType type;

    @Column(name = "table_name")
    private String tableName;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Field field = (Field) o;
        return fieldId != null && Objects.equals(fieldId, field.fieldId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
