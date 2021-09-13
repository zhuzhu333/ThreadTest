package com.czg.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarTemplate {
    public static String getCurrentWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekDay == 0) {
            weekDay = 7;
        }
        return "星期" + weekDay;
    }
}
