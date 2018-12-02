package com.trial.calendar.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class AbstractDao {

    public <T> T queryForNullableObject(JdbcTemplate template,
                                        String sql, Object[] params, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = template.query(sql,params, rowMapper);

        if (results == null || results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        } else {
            return results.iterator().next();
        }
    }

}
