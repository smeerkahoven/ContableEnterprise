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
    public static final String VALOR_DOLAR_NO_ESTABLECIDO = "VALOR_DOLAR_NO_ESTABLECIDO";

    public static final String RESTFUL_ERROR = "RESTFUL_ERROR";
    public static final String RESTFUL_PARAMETERS_SENT = "RESTFUL_PARAMETERS_SENT";
    public static final String RESTFUL_RECORD_EXISTS = "RESTFUL_RECORD_EXISTS";
    public static final String RESTFUL_SUCCESS = "RESTFUL_SUCCESS";
    public static final String RESTFUL_UPDATE_SUCCESS = "RESTFUL_UPDATE_SUCCESS";
    public static final String RESTFUL_UPDATE_ERROR = "RESTFUL_UPDATE_ERROR";
    public static final String RESTFUL_WARNING = "RESTFUL_WARNING";
    public static final String RESTFUL_TOKEN_MANDATORY = "RESTFUL_TOKEN_MANDATORY";
    public static final String RESTFUL_TOKEN_EXPIRED = "RESTFUL_TOKEN_EXPIRED";
    public static final String RESTFUL_VALUE_NOT_FOUND = "RESTFUL_VALUE_NOT_FOUND";
    public static final String RESTFUL_NUMERO_COMPROBANTE = "RESTFUL_NUMERO_COMPROBANTE";

    public static final String RESTFUL_SUCCES_MAIL_SENT = "RESTFUL_SUCCES_MAIL_SENT";
    public static final String RESTFUL_ERROR_MAIL_SENT = "RESTFUL_ERROR_MAIL_SENT";
    public static final String RESTFUL_INVALID_MAIL = "RESTFUL_INVALID_MAIL";
    
    public static final String RESTFUL_COMPROBANTE_ANULADO_SUCCESS="RESTFUL_COMPROBANTE_ANULADO_SUCCESS";
    public static final String RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS="RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS";
    public static final String RESTFUL_COMPROBANTE_GENERADO_SUCCESS="RESTFUL_COMPROBANTE_GENERADO_SUCCESS";
    
    //boleto
    public static final String RESTFUL_NUMERO_BOLETO_INSERTADO="RESTFUL_NUMERO_BOLETO_INSERTADO";
    public static final String RESTFUL_NUMERO_BOLETO_VALIDO="RESTFUL_NUMERO_BOLETO_VALIDO";
    public static final String RESTFUL_NUMERO_BOLETO_NO_EXISTE="RESTFUL_NUMERO_BOLETO_NO_EXISTE";
    public static final String RESTFUL_BOLETO_NO_CREADO="RESTFUL_BOLETO_NO_CREADO";
    public static final String RESTFUL_BOLETO_INSERTADO="RESTFUL_BOLETO_INSERTADO";
    public static final String RESTFUL_BOLETO_ASOCIADO="RESTFUL_BOLETO_ASOCIADO";
    public static final String RESTFUL_BOLETO_VOID_INSERTADO="RESTFUL_BOLETO_VOID_INSERTADO";

    //NOTA Debito
    public static final String RESTFUL_NO_RECORDS_FOUND="RESTFUL_NO_RECORDS_FOUND";
    public static final String RESTFUL_NO_TICKETS_FOUND="RESTFUL_NO_TICKETS_FOUND";
    public static final String RESTFUL_CANT_CREATE_NOTA_DEBITO="RESTFUL_CANT_CREATE_NOTA_DEBITO";
    public static final String RESTFUL_LISTA_VACIA="RESTFUL_LISTA_VACIA";
    public static final String RESTFUL_BUSQUEDA_VACIA="RESTFUL_BUSQUEDA_VACIA";
    public static final String RESTFUL_NO_EXISTE_NOTADEBITO="RESTFUL_NO_EXISTE_NOTADEBITO";
    public static final String RESTFUL_NOTA_DEBITO_ANULADA_OK="RESTFUL_NOTA_DEBITO_ANULADA_OK";
    public static final String RESTFUL_NOTA_DEBITO_TRANSACCION_ANULADA_OK="RESTFUL_NOTA_DEBITO_TRANSACCION_ANULADA_OK";
    public static final String RESTFUL_NOTA_DEBITO_ANULADA_NOK="RESTFUL_NOTA_DEBITO_ANULADA_NOK";
    
    private int code;

    private Object content;

    private Object entidad;

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }

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
