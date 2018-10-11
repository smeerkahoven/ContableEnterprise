/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;

/**
 *
 * @author xeio
 */
public abstract class Moneda implements Serializable {

    public static final String NACIONAL = "B";

    public static final String NACIONAL_FULL = "Bolivianos";

    public static final String NACIONAL_BOB = "BOB";

    public static final String EXTRANJERA = "U";

    public static final String EXTRANJERA_FULL = "USD";

    public static final String AMBAS = "A";
    
    public static final String AMBAS_FULL = "Ambas";

    protected String codigo;
    protected String abreviado;
    protected String literal;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

}
