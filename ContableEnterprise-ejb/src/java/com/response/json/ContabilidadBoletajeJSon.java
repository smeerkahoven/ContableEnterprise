/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.configuracion.entities.ContabilidadBoletaje;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.Contabilidad;

/**
 *
 * @author xeio
 */
public class ContabilidadBoletajeJSon {

    private Integer idEmpresa;
    private ComboSelect idTotalBoletoBs;
    private ComboSelect idTotalBoletoUs;
    private ComboSelect idCuentaFee;
    private ComboSelect idDescuentos;
    private String emisionBolivianos;
    private String emisionDolares;
    // Plan cuentas
    private ComboSelect cuentaEfectivoDebeBs;
    private ComboSelect cuentaEfectivoHaberBs;
    private ComboSelect cuentaEfectivoDebeUsd;
    private ComboSelect cuentaEfectivoHaberUsd;
    //
    private ComboSelect depositoBancoHaberBs;
    private ComboSelect depositoBancoDebeBs;
    private ComboSelect depositoBancoDebeUsd;
    private ComboSelect depositoBancoHaberUsd;
    //
    private ComboSelect cuentaEfectivoNoBspDebeBs;
    private ComboSelect cuentaEfectivoNoBspDebeUsd;
    private ComboSelect tarjetaCreditoBspDebeBs;
    private ComboSelect tarjetaCreditoBspDebeUsd;
    private ComboSelect cuentaEfectivoNoBspHaberBs;
    private ComboSelect cuentaEfectivoNoBspHaberUsd;
    private ComboSelect tarjetaCreditoBspHaberBs;
    private ComboSelect tarjetaCreditoBspHaberUsd;
    private ComboSelect otrosCargosClienteCobrarDebeBs;
    private ComboSelect otrosCargosClienteCobrarDebeUsd;
    //
    private ComboSelect notaCreditoHaberBs;
    private ComboSelect notaCreditoHaberUsd;
    //
    private ComboSelect depositoClienteAnticipadoBs;
    private ComboSelect depositoClienteAnticipadoUsd;
    //
    private ComboSelect acreditacionDepositoAnticipadoDebeBs;
    private ComboSelect acreditacionDepositoAnticipadoHaberBs;
    private ComboSelect acreditacionDepositoAnticipadoDebeUsd;
    private ComboSelect acreditacionDepositoAnticipadoHaberUsd;
    private ComboSelect devolucionDepositoAnticipadoHaberBs;
    private ComboSelect devolucionDepositoAnticipadoDebeBs;
    private ComboSelect devolucionDepositoAnticipadoHaberUsd;
    private ComboSelect devolucionDepositoAnticipadoDebeUsd;

    public static ContabilidadBoletajeJSon toJSon(ContabilidadBoletaje data) {
        ContabilidadBoletajeJSon json = new ContabilidadBoletajeJSon();

        json.setAcreditacionDepositoAnticipadoDebeBs(new ComboSelect(data.getAcreditacionDepositoAnticipadoDebeBs()));
        json.setAcreditacionDepositoAnticipadoDebeUsd(new ComboSelect(data.getAcreditacionDepositoAnticipadoDebeUsd()));
        json.setAcreditacionDepositoAnticipadoHaberBs(new ComboSelect(data.getAcreditacionDepositoAnticipadoHaberBs()));
        json.setAcreditacionDepositoAnticipadoHaberUsd(new ComboSelect(data.getAcreditacionDepositoAnticipadoHaberUsd()));

        json.setCuentaEfectivoDebeBs(new ComboSelect(data.getCuentaEfectivoDebeBs()));
        json.setCuentaEfectivoDebeUsd(new ComboSelect(data.getCuentaEfectivoDebeUsd()));
        json.setCuentaEfectivoHaberBs(new ComboSelect(data.getCuentaEfectivoHaberBs()));
        json.setCuentaEfectivoHaberUsd(new ComboSelect(data.getCuentaEfectivoHaberUsd()));

        json.setCuentaEfectivoNoBspDebeBs(new ComboSelect(data.getCuentaEfectivoNoBspDebeBs()));
        json.setCuentaEfectivoNoBspDebeUsd(new ComboSelect(data.getCuentaEfectivoNoBspDebeUsd()));
        json.setCuentaEfectivoNoBspHaberBs(new ComboSelect(data.getCuentaEfectivoNoBspHaberBs()));
        json.setCuentaEfectivoNoBspHaberUsd(new ComboSelect(data.getCuentaEfectivoNoBspHaberUsd()));

        json.setDepositoBancoDebeBs(new ComboSelect(data.getDepositoBancoDebeBs()));
        json.setDepositoBancoDebeUsd(new ComboSelect(data.getDepositoBancoDebeUsd()));
        json.setDepositoBancoHaberBs(new ComboSelect(data.getDepositoBancoHaberBs()));
        json.setDepositoBancoHaberUsd(new ComboSelect(data.getDepositoBancoHaberUsd()));

        json.setDepositoClienteAnticipadoBs(new ComboSelect(data.getDepositoClienteAnticipadoBs()));
        json.setDepositoClienteAnticipadoUsd(new ComboSelect(data.getDepositoClienteAnticipadoUsd()));

        json.setDevolucionDepositoAnticipadoDebeBs(new ComboSelect(data.getDevolucionDepositoAnticipadoDebeBs()));
        json.setDevolucionDepositoAnticipadoDebeUsd(new ComboSelect(data.getDevolucionDepositoAnticipadoDebeUsd()));
        json.setDevolucionDepositoAnticipadoHaberBs(new ComboSelect(data.getDevolucionDepositoAnticipadoHaberBs()));
        json.setDevolucionDepositoAnticipadoHaberUsd(new ComboSelect(data.getDevolucionDepositoAnticipadoHaberUsd()));

        json.setEmisionBolivianos(data.getEmisionBolivianos());
        json.setEmisionDolares(data.getEmisionDolares());

        json.setIdCuentaFee(new ComboSelect(data.getIdCuentaFee()));
        json.setIdDescuentos(new ComboSelect(data.getIdDescuentos()));
        json.setIdEmpresa(data.getIdEmpresa());

        json.setIdTotalBoletoBs(new ComboSelect(data.getIdTotalBoletoBs()));
        json.setIdTotalBoletoUs(new ComboSelect(data.getIdTotalBoletoUs()));

        json.setNotaCreditoHaberBs(new ComboSelect(data.getNotaCreditoHaberBs()));
        json.setNotaCreditoHaberUsd(new ComboSelect(data.getNotaCreditoHaberUsd()));

        json.setOtrosCargosClienteCobrarDebeBs(new ComboSelect(data.getOtrosCargosClienteCobrarDebeBs()));
        json.setOtrosCargosClienteCobrarDebeUsd(new ComboSelect(data.getOtrosCargosClienteCobrarDebeUsd()));

        json.setTarjetaCreditoBspDebeBs(new ComboSelect(data.getTarjetaCreditoBspDebeBs()));
        json.setTarjetaCreditoBspDebeUsd(new ComboSelect(data.getTarjetaCreditoBspDebeUsd()));
        json.setTarjetaCreditoBspHaberBs(new ComboSelect(data.getTarjetaCreditoBspHaberBs()));
        json.setTarjetaCreditoBspHaberUsd(new ComboSelect(data.getTarjetaCreditoBspHaberUsd()));

        return json;
    }

    public static ContabilidadBoletaje toContabilidadBoletaje(ContabilidadBoletajeJSon json) {
        ContabilidadBoletaje data = new ContabilidadBoletaje();

        data.setAcreditacionDepositoAnticipadoDebeBs(((Double) json.getAcreditacionDepositoAnticipadoDebeBs().getId()).intValue());
        data.setAcreditacionDepositoAnticipadoDebeUsd(((Double) json.getAcreditacionDepositoAnticipadoDebeUsd().getId()).intValue());
        data.setAcreditacionDepositoAnticipadoHaberBs(((Double) json.getAcreditacionDepositoAnticipadoHaberBs().getId()).intValue());
        data.setAcreditacionDepositoAnticipadoHaberUsd(((Double) json.getAcreditacionDepositoAnticipadoHaberUsd().getId()).intValue());
        
        data.setCuentaEfectivoDebeBs(((Double) json.getCuentaEfectivoDebeBs().getId()).intValue());
        data.setCuentaEfectivoDebeUsd(((Double) json.getCuentaEfectivoDebeUsd().getId()).intValue());
        data.setCuentaEfectivoHaberBs(((Double) json.getCuentaEfectivoHaberBs().getId()).intValue());
        data.setCuentaEfectivoHaberUsd(((Double) json.getCuentaEfectivoHaberUsd().getId()).intValue());
        
        data.setCuentaEfectivoNoBspDebeBs(((Double) json.getCuentaEfectivoNoBspDebeBs().getId()).intValue());
        data.setCuentaEfectivoNoBspDebeUsd(((Double) json.getCuentaEfectivoNoBspDebeUsd().getId()).intValue());
        data.setCuentaEfectivoNoBspHaberBs(((Double) json.getCuentaEfectivoNoBspHaberBs().getId()).intValue());
        data.setCuentaEfectivoNoBspHaberUsd(((Double) json.getCuentaEfectivoNoBspHaberUsd().getId()).intValue());
        
        data.setDepositoBancoDebeBs(((Double) json.getDepositoBancoDebeBs().getId()).intValue());
        data.setDepositoBancoDebeUsd(((Double) json.getDepositoBancoDebeUsd().getId()).intValue());
        data.setDepositoBancoHaberBs(((Double) json.getDepositoBancoHaberBs().getId()).intValue());
        data.setDepositoBancoHaberUsd(((Double) json.getDepositoBancoHaberUsd().getId()).intValue());
        
        data.setDepositoClienteAnticipadoBs(((Double) json.getDepositoClienteAnticipadoBs().getId()).intValue());
        data.setDepositoClienteAnticipadoUsd(((Double) json.getDepositoClienteAnticipadoUsd().getId()).intValue());
        
        data.setDevolucionDepositoAnticipadoDebeBs(((Double) json.getDevolucionDepositoAnticipadoDebeBs().getId()).intValue());
        data.setDevolucionDepositoAnticipadoDebeUsd(((Double) json.getDevolucionDepositoAnticipadoDebeUsd().getId()).intValue());
        data.setDevolucionDepositoAnticipadoHaberBs(((Double) json.getDevolucionDepositoAnticipadoHaberBs().getId()).intValue());
        data.setDevolucionDepositoAnticipadoHaberUsd(((Double) json.getDevolucionDepositoAnticipadoHaberUsd().getId()).intValue());
        
        data.setEmisionBolivianos(json.getEmisionBolivianos());
        data.setEmisionDolares(json.getEmisionDolares());
        data.setIdCuentaFee(((Double) json.getIdCuentaFee().getId()).intValue());
        data.setIdDescuentos(((Double) json.getIdDescuentos().getId()).intValue());
        data.setIdEmpresa(json.getIdEmpresa());
        data.setIdTotalBoletoBs(((Double) json.getIdTotalBoletoBs().getId()).intValue());
        data.setIdTotalBoletoUs(((Double) json.getIdTotalBoletoUs().getId()).intValue());
        
        data.setNotaCreditoHaberBs(((Double) json.getNotaCreditoHaberBs().getId()).intValue());
        data.setNotaCreditoHaberUsd(((Double) json.getNotaCreditoHaberUsd().getId()).intValue());
        
        data.setOtrosCargosClienteCobrarDebeBs(((Double) json.getOtrosCargosClienteCobrarDebeBs().getId()).intValue());
        data.setOtrosCargosClienteCobrarDebeUsd(((Double) json.getOtrosCargosClienteCobrarDebeUsd().getId()).intValue());
        
        data.setTarjetaCreditoBspDebeBs(((Double) json.getTarjetaCreditoBspDebeBs().getId()).intValue());
        data.setTarjetaCreditoBspDebeUsd(((Double) json.getTarjetaCreditoBspDebeUsd().getId()).intValue());
        data.setTarjetaCreditoBspHaberBs(((Double) json.getTarjetaCreditoBspHaberBs().getId()).intValue());
        data.setTarjetaCreditoBspHaberUsd(((Double) json.getTarjetaCreditoBspHaberUsd().getId()).intValue());
        
        return data;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public ComboSelect getIdTotalBoletoBs() {
        return idTotalBoletoBs;
    }

    public void setIdTotalBoletoBs(ComboSelect idTotalBoletoBs) {
        this.idTotalBoletoBs = idTotalBoletoBs;
    }

    public ComboSelect getIdTotalBoletoUs() {
        return idTotalBoletoUs;
    }

    public void setIdTotalBoletoUs(ComboSelect idTotalBoletoUs) {
        this.idTotalBoletoUs = idTotalBoletoUs;
    }

    public ComboSelect getIdCuentaFee() {
        return idCuentaFee;
    }

    public void setIdCuentaFee(ComboSelect idCuentaFee) {
        this.idCuentaFee = idCuentaFee;
    }

    public ComboSelect getIdDescuentos() {
        return idDescuentos;
    }

    public void setIdDescuentos(ComboSelect idDescuentos) {
        this.idDescuentos = idDescuentos;
    }

    public String getEmisionBolivianos() {
        return emisionBolivianos;
    }

    public void setEmisionBolivianos(String emisionBolivianos) {
        this.emisionBolivianos = emisionBolivianos;
    }

    public String getEmisionDolares() {
        return emisionDolares;
    }

    public void setEmisionDolares(String emisionDolares) {
        this.emisionDolares = emisionDolares;
    }

    public ComboSelect getCuentaEfectivoDebeBs() {
        return cuentaEfectivoDebeBs;
    }

    public void setCuentaEfectivoDebeBs(ComboSelect cuentaEfectivoDebeBs) {
        this.cuentaEfectivoDebeBs = cuentaEfectivoDebeBs;
    }

    public ComboSelect getCuentaEfectivoHaberBs() {
        return cuentaEfectivoHaberBs;
    }

    public void setCuentaEfectivoHaberBs(ComboSelect cuentaEfectivoHaberBs) {
        this.cuentaEfectivoHaberBs = cuentaEfectivoHaberBs;
    }

    public ComboSelect getCuentaEfectivoDebeUsd() {
        return cuentaEfectivoDebeUsd;
    }

    public void setCuentaEfectivoDebeUsd(ComboSelect cuentaEfectivoDebeUsd) {
        this.cuentaEfectivoDebeUsd = cuentaEfectivoDebeUsd;
    }

    public ComboSelect getCuentaEfectivoHaberUsd() {
        return cuentaEfectivoHaberUsd;
    }

    public void setCuentaEfectivoHaberUsd(ComboSelect cuentaEfectivoHaberUsd) {
        this.cuentaEfectivoHaberUsd = cuentaEfectivoHaberUsd;
    }

    public ComboSelect getDepositoBancoHaberBs() {
        return depositoBancoHaberBs;
    }

    public void setDepositoBancoHaberBs(ComboSelect depositoBancoHaberBs) {
        this.depositoBancoHaberBs = depositoBancoHaberBs;
    }

    public ComboSelect getDepositoBancoDebeBs() {
        return depositoBancoDebeBs;
    }

    public void setDepositoBancoDebeBs(ComboSelect depositoBancoDebeBs) {
        this.depositoBancoDebeBs = depositoBancoDebeBs;
    }

    public ComboSelect getDepositoBancoDebeUsd() {
        return depositoBancoDebeUsd;
    }

    public void setDepositoBancoDebeUsd(ComboSelect depositoBancoDebeUsd) {
        this.depositoBancoDebeUsd = depositoBancoDebeUsd;
    }

    public ComboSelect getDepositoBancoHaberUsd() {
        return depositoBancoHaberUsd;
    }

    public void setDepositoBancoHaberUsd(ComboSelect depositoBancoHaberUsd) {
        this.depositoBancoHaberUsd = depositoBancoHaberUsd;
    }

    public ComboSelect getCuentaEfectivoNoBspDebeBs() {
        return cuentaEfectivoNoBspDebeBs;
    }

    public void setCuentaEfectivoNoBspDebeBs(ComboSelect cuentaEfectivoNoBspDebeBs) {
        this.cuentaEfectivoNoBspDebeBs = cuentaEfectivoNoBspDebeBs;
    }

    public ComboSelect getCuentaEfectivoNoBspDebeUsd() {
        return cuentaEfectivoNoBspDebeUsd;
    }

    public void setCuentaEfectivoNoBspDebeUsd(ComboSelect cuentaEfectivoNoBspDebeUsd) {
        this.cuentaEfectivoNoBspDebeUsd = cuentaEfectivoNoBspDebeUsd;
    }

    public ComboSelect getTarjetaCreditoBspDebeBs() {
        return tarjetaCreditoBspDebeBs;
    }

    public void setTarjetaCreditoBspDebeBs(ComboSelect tarjetaCreditoBspDebeBs) {
        this.tarjetaCreditoBspDebeBs = tarjetaCreditoBspDebeBs;
    }

    public ComboSelect getTarjetaCreditoBspDebeUsd() {
        return tarjetaCreditoBspDebeUsd;
    }

    public void setTarjetaCreditoBspDebeUsd(ComboSelect tarjetaCreditoBspDebeUsd) {
        this.tarjetaCreditoBspDebeUsd = tarjetaCreditoBspDebeUsd;
    }

    public ComboSelect getCuentaEfectivoNoBspHaberBs() {
        return cuentaEfectivoNoBspHaberBs;
    }

    public void setCuentaEfectivoNoBspHaberBs(ComboSelect cuentaEfectivoNoBspHaberBs) {
        this.cuentaEfectivoNoBspHaberBs = cuentaEfectivoNoBspHaberBs;
    }

    public ComboSelect getCuentaEfectivoNoBspHaberUsd() {
        return cuentaEfectivoNoBspHaberUsd;
    }

    public void setCuentaEfectivoNoBspHaberUsd(ComboSelect cuentaEfectivoNoBspHaberUsd) {
        this.cuentaEfectivoNoBspHaberUsd = cuentaEfectivoNoBspHaberUsd;
    }

    public ComboSelect getTarjetaCreditoBspHaberBs() {
        return tarjetaCreditoBspHaberBs;
    }

    public void setTarjetaCreditoBspHaberBs(ComboSelect tarjetaCreditoBspHaberBs) {
        this.tarjetaCreditoBspHaberBs = tarjetaCreditoBspHaberBs;
    }

    public ComboSelect getTarjetaCreditoBspHaberUsd() {
        return tarjetaCreditoBspHaberUsd;
    }

    public void setTarjetaCreditoBspHaberUsd(ComboSelect tarjetaCreditoBspHaberUsd) {
        this.tarjetaCreditoBspHaberUsd = tarjetaCreditoBspHaberUsd;
    }

    public ComboSelect getOtrosCargosClienteCobrarDebeBs() {
        return otrosCargosClienteCobrarDebeBs;
    }

    public void setOtrosCargosClienteCobrarDebeBs(ComboSelect otroCargosClienteCobrarDebeBs) {
        this.otrosCargosClienteCobrarDebeBs = otroCargosClienteCobrarDebeBs;
    }

    public ComboSelect getOtrosCargosClienteCobrarDebeUsd() {
        return otrosCargosClienteCobrarDebeUsd;
    }

    public void setOtrosCargosClienteCobrarDebeUsd(ComboSelect otrosCargosClienteCobrarDebeUsd) {
        this.otrosCargosClienteCobrarDebeUsd = otrosCargosClienteCobrarDebeUsd;
    }

    public ComboSelect getNotaCreditoHaberBs() {
        return notaCreditoHaberBs;
    }

    public void setNotaCreditoHaberBs(ComboSelect notaCreditoHaberBs) {
        this.notaCreditoHaberBs = notaCreditoHaberBs;
    }

    public ComboSelect getNotaCreditoHaberUsd() {
        return notaCreditoHaberUsd;
    }

    public void setNotaCreditoHaberUsd(ComboSelect notaCreditoHaberUsd) {
        this.notaCreditoHaberUsd = notaCreditoHaberUsd;
    }

    public ComboSelect getDepositoClienteAnticipadoBs() {
        return depositoClienteAnticipadoBs;
    }

    public void setDepositoClienteAnticipadoBs(ComboSelect depositoClienteAnticipadoBs) {
        this.depositoClienteAnticipadoBs = depositoClienteAnticipadoBs;
    }

    public ComboSelect getDepositoClienteAnticipadoUsd() {
        return depositoClienteAnticipadoUsd;
    }

    public void setDepositoClienteAnticipadoUsd(ComboSelect depositoClienteAnticipadoUsd) {
        this.depositoClienteAnticipadoUsd = depositoClienteAnticipadoUsd;
    }

    public ComboSelect getAcreditacionDepositoAnticipadoDebeBs() {
        return acreditacionDepositoAnticipadoDebeBs;
    }

    public void setAcreditacionDepositoAnticipadoDebeBs(ComboSelect acreditacionDepositoAnticipadoDebeBs) {
        this.acreditacionDepositoAnticipadoDebeBs = acreditacionDepositoAnticipadoDebeBs;
    }

    public ComboSelect getAcreditacionDepositoAnticipadoHaberBs() {
        return acreditacionDepositoAnticipadoHaberBs;
    }

    public void setAcreditacionDepositoAnticipadoHaberBs(ComboSelect acreditacionDepositoAnticipadoHaberBs) {
        this.acreditacionDepositoAnticipadoHaberBs = acreditacionDepositoAnticipadoHaberBs;
    }

    public ComboSelect getAcreditacionDepositoAnticipadoDebeUsd() {
        return acreditacionDepositoAnticipadoDebeUsd;
    }

    public void setAcreditacionDepositoAnticipadoDebeUsd(ComboSelect acreditacionDepositoAnticipadoDebeUsd) {
        this.acreditacionDepositoAnticipadoDebeUsd = acreditacionDepositoAnticipadoDebeUsd;
    }

    public ComboSelect getAcreditacionDepositoAnticipadoHaberUsd() {
        return acreditacionDepositoAnticipadoHaberUsd;
    }

    public void setAcreditacionDepositoAnticipadoHaberUsd(ComboSelect acreditacionDepositoAnticipadoHaberUsd) {
        this.acreditacionDepositoAnticipadoHaberUsd = acreditacionDepositoAnticipadoHaberUsd;
    }

    public ComboSelect getDevolucionDepositoAnticipadoHaberBs() {
        return devolucionDepositoAnticipadoHaberBs;
    }

    public void setDevolucionDepositoAnticipadoHaberBs(ComboSelect devolucionDepositoAnticipadoHaberBs) {
        this.devolucionDepositoAnticipadoHaberBs = devolucionDepositoAnticipadoHaberBs;
    }

    public ComboSelect getDevolucionDepositoAnticipadoDebeBs() {
        return devolucionDepositoAnticipadoDebeBs;
    }

    public void setDevolucionDepositoAnticipadoDebeBs(ComboSelect devolucionDepositoAnticipadoDebeBs) {
        this.devolucionDepositoAnticipadoDebeBs = devolucionDepositoAnticipadoDebeBs;
    }

    public ComboSelect getDevolucionDepositoAnticipadoHaberUsd() {
        return devolucionDepositoAnticipadoHaberUsd;
    }

    public void setDevolucionDepositoAnticipadoHaberUsd(ComboSelect devolucionDepositoAnticipadoHaberUsd) {
        this.devolucionDepositoAnticipadoHaberUsd = devolucionDepositoAnticipadoHaberUsd;
    }

    public ComboSelect getDevolucionDepositoAnticipadoDebeUsd() {
        return devolucionDepositoAnticipadoDebeUsd;
    }

    public void setDevolucionDepositoAnticipadoDebeUsd(ComboSelect devolucionDepositoAnticipadoDebeUsd) {
        this.devolucionDepositoAnticipadoDebeUsd = devolucionDepositoAnticipadoDebeUsd;
    }

}
