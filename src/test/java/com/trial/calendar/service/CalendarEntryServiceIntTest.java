package com.trial.calendar.service;

import com.trial.calendar.CalendarApplication;
import com.trial.calendar.controller.CalendarEntryController;
import com.trial.calendar.domain.CalendarEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
public class CalendarEntryServiceIntTest {


    private MockMvc mockMvc;
    @Autowired
    public DataSource dataSource;

    @Autowired
    private CalendarEntryService calendarEntryServiceImpl;

    @InjectMocks
    private CalendarEntryController calendarEntryController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // this.mockMvc = MockMvcBuilders.standaloneSetup(calendarEntryController).build();
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/schema.sql", "/data.sql")
                .build();
    }

    @Test
    public void checkFindCalendarEntryByExistingId() throws Exception {
        Optional<CalendarEntry> calendarEntryById = calendarEntryServiceImpl.findCalendarEntryById(1L);
        assertThat(calendarEntryById.isPresent()).isTrue();
        //Check that description = test BD party (Alice)
        assertThat(calendarEntryById.get().getDescription()).isEqualTo("test BD party (Alice)");
        //check that dat = 2012-09-17
        System.out.println(calendarEntryById.get());
        assertThat(calendarEntryById.get().getDate().toString()).isEqualTo("2012-09-17");
    }

    @Test
    public void checkFindCalendarEntryByNotExistingId() throws Exception {
        Optional<CalendarEntry> calendarEntryById = calendarEntryServiceImpl.findCalendarEntryById(111L);
        assertThat(calendarEntryById.isPresent()).isFalse();
    }

    @Test
    public void checkFindCalendarEntryByExistingDate() throws Exception {
        java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2012-01-12");
        List<CalendarEntry> calendarEntriesByDate = calendarEntryServiceImpl.findCalendarEntriesByDate(new java.sql.Date(date.getTime()));
        assertThat(calendarEntriesByDate.isEmpty()).isFalse();
    }

    @Test
    public void checkFindCalendarEntryByExistingDates() throws Exception {
        java.util.Date startDate = new SimpleDateFormat("yyyy-mm-dd").parse("2012-01-12");
        java.util.Date endDate = new SimpleDateFormat("yyyy-mm-dd").parse("2018-01-12");
        List<CalendarEntry> calendarEntriesByDates = calendarEntryServiceImpl.findCalendarEntriesBetweenDates(new java.sql.Date(startDate.getTime()),
                new java.sql.Date(endDate.getTime()));
        System.out.println(calendarEntriesByDates);
        assertThat(calendarEntriesByDates.isEmpty()).isFalse();
        assertThat(calendarEntriesByDates.size()==3).isTrue();
    }

    @Test
    public void checkFindCalendarEntryByNotExistingDates() throws Exception {
        java.util.Date startDate = new SimpleDateFormat("yyyy-mm-dd").parse("2006-01-12");
        java.util.Date endDate = new SimpleDateFormat("yyyy-mm-dd").parse("2008-01-12");
        List<CalendarEntry> calendarEntriesByDates = calendarEntryServiceImpl.findCalendarEntriesBetweenDates(new java.sql.Date(startDate.getTime()),
                new java.sql.Date(endDate.getTime()));
        System.out.println(calendarEntriesByDates);
        assertThat(calendarEntriesByDates.isEmpty()).isTrue();
    }

    @Test
    public void checkFindCalendarEntryByNotExistingDate() throws Exception {
        java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2013-07-12");
        List<CalendarEntry> calendarEntriesByDate = calendarEntryServiceImpl.findCalendarEntriesByDate(new java.sql.Date(date.getTime()));
        System.out.println(calendarEntriesByDate);
        assertThat(calendarEntriesByDate.isEmpty()).isTrue();
    }


}
