package com.example.drugstore.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Utils {
    public static boolean todayOrNextDay(Date date) {
        Date currentDate = new Date();
        return DateUtils.isSameDay(date, currentDate) ||
                DateUtils.isSameDay(date, DateUtils.addDays(currentDate,1));
    }
}
