package com.trial.calendar.controller;

import com.trial.calendar.domain.CalendarEntry;
import com.trial.calendar.service.CalendarEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/calendar-entries")
public class CalendarEntryController {

    public CalendarEntryService calendarEntryService;

    public CalendarEntryController(CalendarEntryService calendarEntryService) {
        this.calendarEntryService = calendarEntryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEntry> getCalendarEntryById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<CalendarEntry> calendarEntryById = calendarEntryService.findCalendarEntryById(id);
        return calendarEntryById.isPresent() ? new ResponseEntity<>(calendarEntryById.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<CalendarEntry>> getCalendarEntriesByDate(@PathVariable("date") Date date) {
        if (date == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CalendarEntry> calendarEntriesByDate = calendarEntryService.findCalendarEntriesByDate(date);
        return calendarEntriesByDate != null && !calendarEntriesByDate.isEmpty() ?
                new ResponseEntity<>(calendarEntriesByDate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("by-dates/{start-date}/{end-date}")
    public ResponseEntity<List<CalendarEntry>> getCalendarEntriesByDates(@PathVariable("start-date") Date startDate,
                                                                         @PathVariable("end-date") Date endDate) {
        if (startDate == null || endDate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CalendarEntry> calendarEntriesBetweenDates = calendarEntryService.findCalendarEntriesBetweenDates(startDate, endDate);
        return calendarEntriesBetweenDates != null && !calendarEntriesBetweenDates.isEmpty() ?
                new ResponseEntity<>(calendarEntriesBetweenDates, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
