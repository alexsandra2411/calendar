package com.trial.calendar.dao;

import com.trial.calendar.domain.CalendarEntry;
import com.trial.calendar.mapper.CalendarEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CalendarEntryDaoImpl extends AbstractDao implements CalendarEntryDao {

    public static final String FIND_CALENDAR_ENTRY_BY_ID = "SELECT * FROM CALENDAR_ENTRY  where id = ?";
    public static final String FIND_CALENDAR_ENTRY_BY_DATE = "SELECT * FROM CALENDAR_ENTRY  where date = ?";
    public static final String FIND_CALENDAR_ENTRY_BY_DATE_INTERVAL = "SELECT * FROM CALENDAR_ENTRY  where date BETWEEN ? AND ?";
    public static final String INSERT_CALENDAR_ENTRY = "INSERT INTO calendar_entry(id, description, date) VALUES  (?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CalendarEntryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<CalendarEntry> findCalendarEntryById(Long id) {
        CalendarEntry calendarEntry = queryForNullableObject(jdbcTemplate, FIND_CALENDAR_ENTRY_BY_ID,
                new Object[]{id}, new CalendarEntryMapper());
        return calendarEntry != null ? Optional.of(calendarEntry) : Optional.empty();
    }

    @Override
    public List<CalendarEntry> findCalendarEntriesByDate(Date date) {
        List<CalendarEntry> calendarEntries = jdbcTemplate.query(FIND_CALENDAR_ENTRY_BY_DATE,
                new Object[]{date}, new CalendarEntryMapper());
        return calendarEntries;
    }

    public List<CalendarEntry> findCalendarEntriesBetweenDates(Date startDate, Date endDate) {
        List<CalendarEntry> calendarEntries = jdbcTemplate.query(FIND_CALENDAR_ENTRY_BY_DATE_INTERVAL,
                new Object[]{startDate, endDate}, new CalendarEntryMapper());
        return calendarEntries;
    }

    @Override
    public void insertCalendarEntity(CalendarEntry calendarEntry) {
        jdbcTemplate.update(INSERT_CALENDAR_ENTRY, new Object[]{calendarEntry.getId(), calendarEntry.getDescription(),
                calendarEntry.getDate()});
    }
}

