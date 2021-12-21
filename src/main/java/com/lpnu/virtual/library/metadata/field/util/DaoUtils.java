package com.lpnu.virtual.library.metadata.field.util;

import com.lpnu.virtual.library.metadata.field.db.FieldManagerDao;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class DaoUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DaoUtils.class);

    public Boolean executeInTransaction(Connection con, String sql) {
        try {
            con.setAutoCommit(Boolean.FALSE);
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();
            con.commit();
            return Boolean.TRUE;
        } catch (SQLException e) {
            try {
                con.rollback();
                LOG.error("Failed to execute transaction: " + e.getMessage(), e);
                return Boolean.FALSE;
            } catch (SQLException ex) {
                LOG.error("Failed to rollback transaction: " + ex.getMessage(), ex);
                return Boolean.FALSE;
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Failed to close connection: " + e.getMessage(), e);
            }
        }
    }

    public List<Object> select(Connection con, String sql, Integer columnNumber, String... params) {
        try {
            PreparedStatement st = con.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                st.setString(i + 1, params[i]);
            }
            ResultSet rs = st.executeQuery();
            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                for (int i = 0; i < columnNumber; i++) {
                    result.add(rs.getObject(i + 1));
                }
            }
            return result;
        } catch (SQLException e) {
            LOG.error("Failed to select: " + e.getMessage(), e);
            return Collections.emptyList();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Failed to close connection: " + e.getMessage(), e);
            }
        }
    }

    public Boolean execute(Connection con, String sql) {
        try {
            PreparedStatement st = con.prepareStatement(sql);
            return st.execute();
        } catch (SQLException e) {
            LOG.error("Failed to execute statement: " + e.getMessage(), e);
            return Boolean.FALSE;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Failed to close connection: " + e.getMessage(), e);
            }
        }
    }
}
