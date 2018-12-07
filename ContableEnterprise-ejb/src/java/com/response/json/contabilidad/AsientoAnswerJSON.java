/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Boleto;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;

/**
 *
 * @author xeio
 */
public class AsientoAnswerJSON {
    
    private NotaDebitoTransaccion transaccion ;
    private Boleto boleto ;
    private CargoBoleto cargo ;
    private IngresoTransaccion ingreso ;

    public IngresoTransaccion getIngreso() {
        return ingreso;
    }

    public void setIngreso(IngresoTransaccion ingreso) {
        this.ingreso = ingreso;
    }
    
    public NotaDebitoTransaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(NotaDebitoTransaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public CargoBoleto getCargo() {
        return cargo;
    }

    public void setCargo(CargoBoleto cargo) {
        this.cargo = cargo;
    }

    
    
    
}
