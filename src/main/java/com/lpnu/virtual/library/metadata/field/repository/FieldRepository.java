package com.lpnu.virtual.library.metadata.field.repository;

import com.lpnu.virtual.library.metadata.field.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    Field findByFieldId(String fieldId);

    @Query("select f.tableName from Field f where f.fieldId = ?1")
    String findTableNameByFieldId(String fieldId);
}
