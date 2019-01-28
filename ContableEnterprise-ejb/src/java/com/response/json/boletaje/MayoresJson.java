/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.boletaje;

import com.contabilidad.entities.Mayores;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class MayoresJson {

    private Integer idAsiento;
    private Integer idLibro;
    private String fecha;
    private String fechaMovimiento;
    private Integer idPlanCuenta;
    private BigDecimal montoDebe;
    private BigDecimal montoHaber;
    private Integer idBoleto;
    private Integer idCargo;
    private Integer idNotaTransaccion;
    private Integer idIngresoCajaTransaccion;
    private Integer idNotaCreditoTransaccion;
    private Integer idPagoAnticipado;
    private Integer idPagoAnticipadoTransaccion;
    private Integer idCliente;
    private Integer idNumeroGestion;
    private Integer gestion;
    private String concepto;
    private BigDecimal factorCambiario;
    private String tipo;
    private Double saldoDebe;
    private Double saldoHaber;
    
    public static MayoresJson toMayoresJson(Mayores data){
        MayoresJson json = new MayoresJson();
        
        json.setConcepto(data.getConcepto());
        json.setFactorCambiario(data.getFactorCambiario());
        json.setFecha(DateContable.getDateFormat(data.getFecha(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaMovimiento(DateContable.getDateFormat(data.getFechaMovimiento(), DateContable.LATIN_AMERICA_FORMAT));
        json.setGestion(data.getGestion());
        json.setIdAsiento(data.getIdAsiento());
        json.setIdBoleto(data.getIdBoleto());
        json.setIdCargo(data.getIdCargo());
        json.setIdCliente(data.getIdCliente());
        json.setIdIngresoCajaTransaccion(data.getIdIngresoCajaTransaccion());
        json.setIdLibro(data.getIdLibro());
        json.setIdNotaCreditoTransaccion(data.getIdNotaCreditoTransaccion());
        json.setIdNotaTransaccion(data.getIdNotaTransaccion());
        json.setIdNumeroGestion(data.getIdNumeroGestion());
        json.setIdPagoAnticipado(data.getIdPagoAnticipado());
        json.setIdPlanCuenta(data.getIdPlanCuenta());
        json.setMontoDebe(data.getMontoDebe());
        json.setMontoHaber(data.getMontoHaber());
        json.setSaldoDebe(data.getSaldoDebe());
        json.setSaldoHaber(data.getSaldoHaber());
        json.setTipo(data.getTipo());
        
        return json ;
    }

    public Integer getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Integer idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Integer getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(Integer idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public BigDecimal getMontoDebe() {
        return montoDebe;
    }

    public void setMontoDebe(BigDecimal montoDebe) {
        this.montoDebe = montoDebe;
    }

    public BigDecimal getMontoHaber() {
        return montoHaber;
    }

    public void setMontoHaber(BigDecimal montoHaber) {
        this.montoHaber = montoHaber;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(Integer idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public Integer getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(Integer idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public Integer getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(Integer idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public Integer getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }

    public void setIdPagoAnticipadoTransaccion(Integer idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdNumeroGestion() {
        return idNumeroGestion;
    }

    public void setIdNumeroGestion(Integer idNumeroGestion) {
        this.idNumeroGestion = idNumeroGestion;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldoDebe() {
        return saldoDebe;
    }

    public void setSaldoDebe(Double saldoDebe) {
        this.saldoDebe = saldoDebe;
    }

    public Double getSaldoHaber() {
        return saldoHaber;
    }

    public void setSaldoHaber(Double saldoHaber) {
        this.saldoHaber = saldoHaber;
    }

}
