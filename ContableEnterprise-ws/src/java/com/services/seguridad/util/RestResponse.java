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

    public static final String RESTFUL_ERROR = "RESTFUL_ERROR";
    public static final String RESTFUL_SUCCESS = "RESTFUL_SUCCESS";
    public static final String RESTFUL_WARNING = "RESTFUL_WARNING";
    public static final String RESTFUL_TOKEN_MANDATORY = "RESTFUL_TOKEN_MANDATORY";
    public static final String RESTFUL_TOKEN_EXPIRED = "RESTFUL_TOKEN_EXPIRED";

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
