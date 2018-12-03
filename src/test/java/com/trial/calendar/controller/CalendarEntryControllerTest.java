package com.trial.calendar.controller;

import com.trial.calendar.CalendarApplication;
import com.trial.calendar.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
public class CalendarEntryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private CalendarEntryController calendarEntryController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(calendarEntryController).build();
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/schema.sql", "/data.sql")
                .build();
    }

    @Test
    public void checkGetMethodOfCalendarControllerByExistingId() throws Exception {
        mockMvc.perform(get("/calendar-entries/" + 1L)).andExpect(status().isOk());
    }

    @Test
    public void checkGetMethodOfCalendarControllerByNotExistingId() throws Exception {
        mockMvc.perform(get("/calendar-entries/" + 221L)).andExpect(status().isNotFound());
    }

    @Test
    public void checkFindCalendarEntryByExistingDate() throws Exception {
        Date date = TestUtils.getDateFromString("2012-01-12");
        mockMvc.perform(get("/calendar-entries/by-date/" + date)).andExpect(status().isOk());
    }

    @Test
    public void checkFindCalendarEntryByExistingDates() throws Exception {
        Date startDate = TestUtils.getDateFromString("2012-01-12");
        Date endDate = TestUtils.getDateFromString("2018-01-12");
        mockMvc.perform(get("/calendar-entries/by-dates/" + startDate + "/" + endDate)).andExpect(status().isOk());
    }

    @Test
    public void checkFindCalendarEntryByNotExistingDates() throws Exception {
        Date startDate = TestUtils.getDateFromString("2006-01-12");
        Date endDate = TestUtils.getDateFromString("2008-01-12");
        mockMvc.perform(get("/calendar-entries/by-dates/" + startDate + "/" + endDate)).andExpect(status().isNotFound());
    }

    @Test
    public void checkGetCalendarEntryByNotExistingDate() throws Exception {
        Date date = TestUtils.getDateFromString("2001-01-12");
        mockMvc.perform(get("/calendar-entries/by-date/" + date)).andExpect(status().isNotFound());
    }

}
