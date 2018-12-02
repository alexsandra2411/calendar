package com.trial.calendar.service;

import com.trial.calendar.dao.CalendarEntryDao;
import com.trial.calendar.domain.CalendarEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CalendarEntryServiceImpl implements CalendarEntryService {


    @Autowired
    public CalendarEntryDao calendarEntryDao;

    @Override
    public Optional<CalendarEntry> findCalendarEntryById(Long id) {
        return calendarEntryDao.findCalendarEntryById(id);
    }

    @Override
    public List<CalendarEntry> findCalendarEntriesByDate(Date date) {
        return calendarEntryDao.findCalendarEntriesByDate(date);
    }

    @Override
    public List<CalendarEntry> findCalendarEntriesBetweenDates(Date startDate, Date endDate) {
        return calendarEntryDao.findCalendarEntriesBetweenDates(startDate, endDate);
    }
    @Override
    public void addCalendarService(CalendarEntry ce) {
        calendarEntryDao.insertCalendarEntity(ce);
    }
}
