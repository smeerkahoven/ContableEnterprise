/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cheyo
 */
public class LoggerContable {
    
    public static void log(String message, Object o, Level level) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        
        Logger logger = Logger.getLogger(c.toString()) ;
        logger.log(Level.SEVERE, "[{0}]:[{1}]:[{2}]", new Object[]{f.format(c.getTime()) , o.getClass().getCanonicalName(), message});        
    }
}
