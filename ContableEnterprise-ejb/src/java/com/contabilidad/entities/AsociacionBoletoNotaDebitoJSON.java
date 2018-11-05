/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author xeio
 */
public class AsociacionBoletoNotaDebitoJSON implements Serializable {

    private ArrayList<Integer> boletos = new ArrayList<>();

    private Integer idNotaDebito;

    private Integer idCliente;

    private BigDecimal factor;

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }


    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public ArrayList<Integer> getBoletos() {
        return boletos;
    }

    public void setBoletos(ArrayList<Integer> boletos) {
        this.boletos = boletos;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

}
