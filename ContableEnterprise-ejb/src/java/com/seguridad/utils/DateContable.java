/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cheyo
 */
public class DateContable {

    public static final String PARTITION_FORMAT = "yyyyMM";

    public static final String LATIN_AMERICA_FORMAT = "dd/MM/yyyy";
    public static final String LATIN_AMERICA_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static String getCurrentDateStr() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getCurrentDateStr(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static Date toLatinAmericaDateFormat(String date) {
        
        Optional op = Optional.ofNullable(date);
        
        if (!op.isPresent()){
            return null ;
        }
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(LATIN_AMERICA_FORMAT);
            Date parseDAte = formatter.parse(date);

            return parseDAte;
        } catch (ParseException ex) {
            Logger.getLogger(DateContable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String getPartitionDate(String strDate) {
        //for strdate = 2017 July 25

        DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(LATIN_AMERICA_FORMAT)
                .toFormatter();

        LocalDate parsedDate = LocalDate.parse(strDate, f);
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern(PARTITION_FORMAT);

        String newDate = parsedDate.format(f2);

        return newDate;
    }

    
    public static Integer getPartitionDateInt(String strDate) {
        //for strdate = 2017 July 25

        DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern(LATIN_AMERICA_FORMAT)
                .toFormatter();

        LocalDate parsedDate = LocalDate.parse(strDate, f);
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern(PARTITION_FORMAT);

        String newDate = parsedDate.format(f2);

        return Integer.parseInt(newDate);
    }
    /**
     *
     * @return
     */
    public static Date getCurrentDate() {
        long m = System.currentTimeMillis();
        Date d = new Date(m);
        return d;
    }

    public DateContable() {
    }

   public static String getDateFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static void main(String args[]) {
            System.out.println(DateContable.getPartitionDate(DateContable.getCurrentDateStr(LATIN_AMERICA_FORMAT)));
            Date d = new Date("2018-08-21T04:00:00Z[UTC]");
            
            SimpleDateFormat simple = new SimpleDateFormat(LATIN_AMERICA_FORMAT);
            simple.format(d);
            
            
    }
}
