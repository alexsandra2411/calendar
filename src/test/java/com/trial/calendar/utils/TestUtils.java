package com.trial.calendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static Date getDateFromString(String dateValue) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(dateValue);
        return new java.sql.Date(date.getTime());
    }
}
