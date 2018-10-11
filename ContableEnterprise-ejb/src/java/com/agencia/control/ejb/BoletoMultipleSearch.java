/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import java.io.Serializable;

/**
 * Busqueda del Boleto Multiple para poblar la tabla de multiples
 * @author xeio
 */
public class BoletoMultipleSearch implements Serializable {
    
    private Integer idBoleto;
    private Integer idBoletoPadre ;

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdBoletoPadre() {
        return idBoletoPadre;
    }

    public void setIdBoletoPadre(Integer idBoletoPadre) {
        this.idBoletoPadre = idBoletoPadre;
    }
    
    
    
}
