package com.trial.calendar.mapper;


import com.trial.calendar.domain.CalendarEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CalendarEntryMapper implements RowMapper<CalendarEntry> {

    @Override
    public CalendarEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getRow() > 0) {
            CalendarEntry calendarEntry = new CalendarEntry();
            calendarEntry.setId(rs.getLong("id"));
            Date date = rs.getDate("date");
            calendarEntry.setDate(date);
            calendarEntry.setDescription(rs.getString("description"));

            return calendarEntry;
        }
        return null;

    }
}
