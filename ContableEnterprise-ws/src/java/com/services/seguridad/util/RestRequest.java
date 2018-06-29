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
public class RestRequest {

    private String token;

    private Object content;

    private String formName;

    private String ip;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String form) {
        this.formName = form;
    }

    public String getToken() {
        return token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
