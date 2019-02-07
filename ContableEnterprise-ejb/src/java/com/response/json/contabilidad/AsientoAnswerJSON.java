/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.response.json.agencia.BoletoJSON;

/**
 *
 * @author xeio
 */
public class AsientoAnswerJSON {

    private NotaDebitoTransaccionJson notaDebito;
    private BoletoJSON boleto;
    private CargoBoletoJSON cargo;
    private IngresoTransaccionJson ingreso;
    private NotaCreditoTransaccionJson notaCredito;

    public NotaDebitoTransaccionJson getNotaDebito() {
        return notaDebito;
    }

    public void setNotaDebito(NotaDebitoTransaccionJson notaDebito) {
        this.notaDebito = notaDebito;
    }

    public NotaCreditoTransaccionJson getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCreditoTransaccionJson notaCredito) {
        this.notaCredito = notaCredito;
    }

    public BoletoJSON getBoleto() {
        return boleto;
    }

    public void setBoleto(BoletoJSON boleto) {
        this.boleto = boleto;
    }

    public CargoBoletoJSON getCargo() {
        return cargo;
    }

    public void setCargo(CargoBoletoJSON cargo) {
        this.cargo = cargo;
    }

    public IngresoTransaccionJson getIngreso() {
        return ingreso;
    }

    public void setIngreso(IngresoTransaccionJson ingreso) {
        this.ingreso = ingreso;
    }

}
