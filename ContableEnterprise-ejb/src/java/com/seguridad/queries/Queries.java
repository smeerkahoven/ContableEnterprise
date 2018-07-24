/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.queries;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cheyo
 */
public class Queries {

    static final long serialVersionUID = 42L;

    public final static String GET_MENU = "GET_MENU";
    public final static String GET_EMPRESA_PRINCIPAL = "GET_EMPRESA_PRINCIPAL";
    public final static String GET_SUCURSALES = "GET_SUCURSALES";
    public final static String GET_EMPLEADOS="GET_EMPLEADOS";
    public final static String GET_EMPLEADOS_COMBO_USUARIO = "GET_EMPLEADOS_COMBO_USUARIO";
    public final static String GET_MODULO_EDIT="GET_MODULO_EDIT";
    public final static String GET_MODULO_NUEVO="GET_MODULO_NUEVO";
    public final static String GET_BANCO_CUENTA_LINK="GET_BANCO_CUENTA_LINK";
    
    //removes
    public final static String DELETE_IMPUESTO_AEROLINEA="DELETE_IMPUESTO_AEROLINEA";

    private static Queries queries;
    
    private Properties prop = new Properties();
    
    private Queries() {
        try {
            InputStream realPath = this.getClass().
                    getResourceAsStream("Queries.properties");
            prop.load(realPath);
        } catch (IOException ex) {
            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static Queries getQueries(){
        if(queries == null){
            queries = new Queries();
        }
        
        return queries ;
    }
    
    public String getPropertie(String token){
        return prop.getProperty(token);
    }

}
