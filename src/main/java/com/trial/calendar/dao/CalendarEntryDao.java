package com.trial.calendar.dao;


import com.trial.calendar.domain.CalendarEntry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CalendarEntryDao {

    Optional<CalendarEntry> findCalendarEntryById(Long id);

    List<CalendarEntry> findCalendarEntriesByDate(Date date);

    List<CalendarEntry> findCalendarEntriesBetweenDates(Date startDate, Date endDate);

    void insertCalendarEntity(CalendarEntry calendarEntry);
}
