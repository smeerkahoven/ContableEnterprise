/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

/**
 *
 * @author xeio
 */
public enum FormasDePago {

    CREDITO("C"),
    CONTADO("E"),
    TARJETA("T"),
    COMBINADO("P");

    private final String value;

    private FormasDePago(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
