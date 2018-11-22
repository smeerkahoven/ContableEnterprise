/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.utils;

import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class ComboSelect implements Serializable {

    private Object id;

    private String name;

    private String comodin;

    public ComboSelect() {

    }

    public ComboSelect(Object id) {
        this.id = id;
    }

    public ComboSelect(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    public ComboSelect(Object id, String name, String comodin) {
        this.id = id;
        this.name = name;
        this.comodin = comodin ;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComodin() {
        return comodin;
    }

    public void setComodin(String comodin) {
        this.comodin = comodin;
    }

}
