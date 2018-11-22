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
    public static final String AMADEUS_D_TAG_DATE = "yyMMdd" ;
    public static final String AMADEUS_D_TAG_H_DATE = "ddMMMyyyy" ;
    public static final String SABRE_M3_DATE = "ddMMMyyyy" ;

    public static final String fromFormatToOtherFormat(String date, String fromFormat, String toFormat) {

        try {
            SimpleDateFormat from = new SimpleDateFormat(fromFormat);

            Date fromDate = from.parse(date);
            System.out.println(fromDate);

            return new SimpleDateFormat(toFormat).format(fromDate);
            
        } catch (ParseException ex) {
            Logger.getLogger(DateContable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * Devuelve en formato dd/MM/yyyy HH:mm:ss la fecha actual
     * @return 
     */
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

        if (!op.isPresent()) {
            return null;
        }

        if (date.length() == 0) {
            return null;
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
        Date nota = new Date();
        int gestion = DateContable.getPartitionDateInt(DateContable.getDateFormat(nota, DateContable.LATIN_AMERICA_FORMAT));

        System.out.println(gestion);
        System.out.println(fromFormatToOtherFormat("180427", "yyMMdd", "dd/MM/yyyy"));

    }
}
