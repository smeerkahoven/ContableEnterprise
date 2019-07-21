/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.configuracion;

import com.configuracion.entities.Parametros;

/**
 *
 * @author xeio
 */
public class ParametrosJson {
    private String idParametro;
    private String valor ;
    private String descripcion ;

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    public static Parametros toParametro(ParametrosJson json){
        Parametros data = new Parametros();
        data.setDescripcion(json.getDescripcion());
        data.setIdParametro(json.getIdParametro());
        data.setValor(json.getValor());
        return data ;
    }
}
