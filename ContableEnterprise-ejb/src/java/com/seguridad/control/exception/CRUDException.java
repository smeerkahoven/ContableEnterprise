/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.exception;

import com.seguridad.control.LoggerContable;
import java.util.logging.Level;

/**
 *
 * @author Cheyo
 */
public class CRUDException extends Exception {

    public CRUDException() {

    }

    public CRUDException(String message) {
        super(message);
        
    }

}
