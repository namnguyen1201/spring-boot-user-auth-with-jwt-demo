package com.example.namnh.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTimeUtils {
    private static final String SIMPLE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat simpleDateFormat;

    public static Date toSimpleDateTime(Date date) throws ParseException {
        simpleDateFormat = new SimpleDateFormat(SIMPLE_DATE_TIME_FORMAT);
        String strDate = simpleDateFormat.format(date);
        System.out.println("date: "+date);
        System.out.println("strDate: "+strDate);
        return simpleDateFormat.parse(strDate);
    }
}
