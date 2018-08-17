/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad.util;

/**
 *
 * @author xeio
 */
public class RestResponse {
    
    public static final String BANCO_CUENTA_RELACION = "BANCO_CUENTA_RELACION";
    public static final String VALOR_DOLAR_NO_ESTABLECIDO = "VALOR_DOLAR_NO_ESTABLECIDO" ; 
    
    public static final String RESTFUL_ERROR = "RESTFUL_ERROR";
    public static final String RESTFUL_PARAMETERS_SENT = "RESTFUL_PARAMETERS_SENT";
    public static final String RESTFUL_RECORD_EXISTS = "RESTFUL_RECORD_EXISTS";
    public static final String RESTFUL_SUCCESS = "RESTFUL_SUCCESS";
    public static final String RESTFUL_WARNING = "RESTFUL_WARNING";
    public static final String RESTFUL_TOKEN_MANDATORY = "RESTFUL_TOKEN_MANDATORY";
    public static final String RESTFUL_TOKEN_EXPIRED = "RESTFUL_TOKEN_EXPIRED";
    public static final String RESTFUL_VALUE_NOT_FOUND = "RESTFUL_VALUE_NOT_FOUND";
    public static final String RESTFUL_NUMERO_COMPROBANTE="RESTFUL_NUMERO_COMPROBANTE";
    
    public static final String RESTFUL_SUCCES_MAIL_SENT = "RESTFUL_SUCCES_MAIL_SENT" ;
    public static final String RESTFUL_ERROR_MAIL_SENT = "RESTFUL_ERROR_MAIL_SENT" ;
     public static final String RESTFUL_INVALID_MAIL = "RESTFUL_INVALID_MAIL";


    private int code;

    private Object content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
