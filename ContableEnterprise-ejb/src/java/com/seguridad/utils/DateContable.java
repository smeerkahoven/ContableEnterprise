/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Cheyo
 */
public class DateContable {

    public static String getCurrentDateStr() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
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

    public static String getDateFormat(Date date, String format) {
        if (date == null) {
            return "";
        }
        return  new SimpleDateFormat(format).format(date);
    }
}
