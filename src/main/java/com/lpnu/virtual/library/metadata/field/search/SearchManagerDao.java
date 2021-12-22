package com.lpnu.virtual.library.metadata.field.search;

import com.lpnu.virtual.library.metadata.field.db.FieldManagerDao;
import com.lpnu.virtual.library.metadata.field.db.Sqls;
import com.lpnu.virtual.library.metadata.field.util.DaoUtils;
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

@Repository
@RequiredArgsConstructor
public class SearchManagerDao {
    private static final Logger LOG = LoggerFactory.getLogger(SearchManagerDao.class);
    private final DataSource dataSource;

    public List<Object> getAssetIds(String sql) {
        try {
            Connection connection = dataSource.getConnection();
            return DaoUtils.select(connection, sql, 1);
        } catch (SQLException e) {
            LOG.error("Failed to get asset ids by search sql: " + sql, e);
            return Collections.emptyList();
        }
    }
}
