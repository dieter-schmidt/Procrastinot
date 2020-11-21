package com.example.myapplication;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.*;

public class CalendarConverter {
//    @TypeConverter
//    public static Calendar toCalendar(Long l) {
//        //current date & time
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(l);
////        c.get(Calendar.YEAR)
//        return c;
//    }
//
//    @TypeConverter
//    public static Long fromCalendar(Calendar c){
//        return c == null ? null : c.getTime().getTime();
//    }

    @TypeConverter
    public static Calendar toCalendar(String s) {
        //current date & time
        Calendar c = Calendar.getInstance();
        String[] dateFields = s.split("-");
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateFields[0]));
        c.set(Calendar.MONTH, Integer.parseInt(dateFields[1]));
        c.set(Calendar.YEAR, Integer.parseInt(dateFields[2]));
        return c;
    }

    @TypeConverter
    public static String fromCalendar(Calendar c){
        if (c == null){
            return null;
        }
        return new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
    }


}
