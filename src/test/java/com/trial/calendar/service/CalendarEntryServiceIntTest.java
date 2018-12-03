package com.trial.calendar.service;

import com.trial.calendar.CalendarApplication;
import com.trial.calendar.controller.CalendarEntryController;
import com.trial.calendar.domain.CalendarEntry;
import com.trial.calendar.utils.TestUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
public class CalendarEntryServiceIntTest {

    @Autowired
    public DataSource dataSource;

    @Autowired
    private CalendarEntryService calendarEntryServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/schema.sql", "/data.sql")
                .build();
    }

    @Test
    public void checkFindCalendarEntryByExistingId() {
        Optional<CalendarEntry> calendarEntryById = calendarEntryServiceImpl.findCalendarEntryById(1L);

        assertThat(calendarEntryById.isPresent()).isTrue();
        //Check that description = test BD party (Alice)
        assertThat(calendarEntryById.get().getDescription()).isEqualTo("test BD party (Alice)");
        //check that dat = 2012-09-17
        assertThat(calendarEntryById.get().getDate().toString()).isEqualTo("2012-09-17");
    }

    @Test
    public void checkFindCalendarEntryByNotExistingId() {
        Optional<CalendarEntry> calendarEntryById = calendarEntryServiceImpl.findCalendarEntryById(111L);

        assertThat(calendarEntryById.isPresent()).isFalse();
    }

    @Test
    public void checkFindCalendarEntryByExistingDate() throws Exception {
        Date date = TestUtils.getDateFromString("2012-01-12");
        List<CalendarEntry> calendarEntriesByDate = calendarEntryServiceImpl.findCalendarEntriesByDate(date);

        assertThat(calendarEntriesByDate.isEmpty()).isFalse();
    }

    @Test
    public void checkFindCalendarEntryByExistingDates() throws Exception {
        Date startDate = TestUtils.getDateFromString("2012-01-12");
        Date endDate = TestUtils.getDateFromString("2018-01-12");
        List<CalendarEntry> calendarEntriesByDates = calendarEntryServiceImpl.findCalendarEntriesBetweenDates(startDate, endDate);

        assertThat(calendarEntriesByDates.isEmpty()).isFalse();
        assertThat(calendarEntriesByDates.size() == 3).isTrue();
    }

    @Test
    public void checkFindCalendarEntryByNotExistingDates() throws Exception {
        Date startDate = TestUtils.getDateFromString("2006-01-12");
        Date endDate = TestUtils.getDateFromString("2008-01-12");

        List<CalendarEntry> calendarEntriesByDates = calendarEntryServiceImpl.findCalendarEntriesBetweenDates(startDate, endDate);
        System.out.println(calendarEntriesByDates);
        assertThat(calendarEntriesByDates.isEmpty()).isTrue();
    }

    @Test
    public void checkFindCalendarEntryByNotExistingDate() throws Exception {
        Date date = TestUtils.getDateFromString("2013-07-12");
        List<CalendarEntry> calendarEntriesByDate = calendarEntryServiceImpl.findCalendarEntriesByDate(date);

        assertThat(calendarEntriesByDate.isEmpty()).isTrue();
    }

}
