package com.trial.calendar.service;

import com.trial.calendar.domain.CalendarEntry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CalendarEntryService {


    Optional<CalendarEntry> findCalendarEntryById(Long id);

    List<CalendarEntry> findCalendarEntriesByDate(Date date);

    List<CalendarEntry> findCalendarEntriesBetweenDates(Date startDate, Date endDate);

    void addCalendarService(CalendarEntry ce);
}
