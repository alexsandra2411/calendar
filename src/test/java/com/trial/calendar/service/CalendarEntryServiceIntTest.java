package com.trial.calendar.service;

import com.trial.calendar.CalendarApplication;
import com.trial.calendar.controller.CalendarEntryController;
import com.trial.calendar.dao.CalendarEntryDaoImpl;
import com.trial.calendar.domain.CalendarEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;
import java.sql.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalendarApplication.class)
public class CalendarEntryServiceIntTest {

    
    private MockMvc mockMvc;
    public DataSource dataSource;
    public JdbcTemplate jdbcTemplate;

    @Mock
    private CalendarEntryDaoImpl calendarEntryDaoImpl;
    @Mock
    private CalendarEntryService calendarEntryServiceImpl;

    @InjectMocks
    private CalendarEntryController calendarEntryController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(calendarEntryController).build();
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/schema.sql","/data.sql")

                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void checkFindCalendarEntryById() throws Exception { //todo rename
        CalendarEntry ce = new CalendarEntry();
        ce.setId(1L);
        ce.setDescription("test");
        ce.setDate(new Date(System.currentTimeMillis()));
        calendarEntryServiceImpl.addCalendarService(ce) ;       
        assertThat(calendarEntryServiceImpl.findCalendarEntryById(1L).isPresent()).isTrue();
       mockMvc.perform(get("/calendar-entries/{id}", 1L)).andExpect(status().isOk());

    }

    @After
    public void destroy(){
        jdbcTemplate.execute("drop table calendar_entry");
    }

}
