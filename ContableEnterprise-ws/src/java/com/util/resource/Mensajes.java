/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cheyo
 */
public class Mensajes {

    
    static final long serialVersionUID = 42L;

    private static Mensajes mensaje;

    private Properties prop = new Properties();

    private Mensajes() {

        try {
            InputStream realPath = this.getClass().getResourceAsStream("Mensajes.properties");
            prop.load(realPath);
        } catch (IOException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Mensajes getMensajes() {
        if (mensaje == null) {
            mensaje = new Mensajes();
        }
        return mensaje;
    }

    public String getProperty(String token) {
        String msg = prop.getProperty(token);
        if (msg == null || msg.isEmpty()) {
            msg = "<Configurar Mensaje> " + token;
        }
        return msg;
    }
}
