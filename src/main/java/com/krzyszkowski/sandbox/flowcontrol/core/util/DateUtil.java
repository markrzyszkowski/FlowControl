package com.krzyszkowski.sandbox.flowcontrol.core.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    public static long getWorkingDays(Date from, Date to) {
        var start = LocalDate.ofInstant(from.toInstant(), ZoneId.systemDefault());
        var end = LocalDate.ofInstant(to.toInstant(), ZoneId.systemDefault());

        var workingDays = start.datesUntil(end)
                               .filter(day -> !isWeekend(day.getDayOfWeek()))
                               .count();

        if (!isWeekend(end.getDayOfWeek())) {
            workingDays++;
        }

        return workingDays;
    }

    public static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
