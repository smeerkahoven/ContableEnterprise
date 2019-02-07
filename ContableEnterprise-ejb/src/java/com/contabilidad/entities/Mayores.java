/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author xeio
 */
public class Mayores implements Serializable {

    private Integer idAsiento;
    private Integer idLibro;
    private Date fecha;
    private Date fechaMovimiento;
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
    private Integer idDevolucion;
    private Integer idCliente;
    private Integer idNumeroGestion;
    private Integer gestion;
    private String concepto;
    private BigDecimal factorCambiario;
    private String tipo;
    private Double saldoDebe;
    private Double saldoHaber;

    private String ndtrxDescripcion;
    private String nctrxDescripcion;
    private String ictrxDescripcion;
    private String paDescripcion;
    private String patrxDescripcion;
    private String deDescripcion;

    public Mayores(Integer idAsiento, Integer idLibro,
            Date fecha, Date fechaMovimiento, Integer idPlanCuenta,
            BigDecimal montoDebe, BigDecimal montoHaber, Integer idBoleto,
            Integer idCargo, Integer idNotaTransaccion,
            Integer idIngresoCajaTransaccion, Integer idNotaCreditoTransaccion,
            Integer idPagoAnticipado, Integer idPagoAnticipadoTransaccion,
            Integer idDevolucion,
            Integer idCliente, Integer idNumeroGestion, Integer gestion,
            String concepto, BigDecimal factorCambiario, String tipo,
            String ndtrxDescripcion, String nctrxDescripcion,
            String ictrxDescripcion, String paDescripcion,
            String patrxDescripcion, String deDescripcion
    ) {
        this.idAsiento = idAsiento;
        this.idLibro = idLibro;
        this.fecha = fecha;
        this.fechaMovimiento = fechaMovimiento;
        this.idPlanCuenta = idPlanCuenta;
        this.montoDebe = montoDebe;
        this.montoHaber = montoHaber;
        this.idBoleto = idBoleto;
        this.idCargo = idCargo;
        this.idNotaTransaccion = idNotaTransaccion;
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
        this.idPagoAnticipado = idPagoAnticipado;
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
        this.idDevolucion = idDevolucion;
        this.idCliente = idCliente;
        this.idNumeroGestion = idNumeroGestion;
        this.gestion = gestion;
        this.concepto = concepto;
        this.factorCambiario = factorCambiario;
        this.tipo = tipo;

        this.ndtrxDescripcion = ndtrxDescripcion;
        this.nctrxDescripcion = nctrxDescripcion;
        this.ictrxDescripcion = ictrxDescripcion;
        this.paDescripcion = paDescripcion;
        this.patrxDescripcion = patrxDescripcion;
        this.deDescripcion = deDescripcion;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public String getNdtrxDescripcion() {
        return ndtrxDescripcion;
    }

    public void setNdtrxDescripcion(String ndtrxDescripcion) {
        this.ndtrxDescripcion = ndtrxDescripcion;
    }

    public String getNctrxDescripcion() {
        return nctrxDescripcion;
    }

    public void setNctrxDescripcion(String nctrxDescripcion) {
        this.nctrxDescripcion = nctrxDescripcion;
    }

    public String getIctrxDescripcion() {
        return ictrxDescripcion;
    }

    public void setIctrxDescripcion(String ictrxDescripcion) {
        this.ictrxDescripcion = ictrxDescripcion;
    }

    public String getPaDescripcion() {
        return paDescripcion;
    }

    public void setPaDescripcion(String paDescripcion) {
        this.paDescripcion = paDescripcion;
    }

    public String getPatrxDescripcion() {
        return patrxDescripcion;
    }

    public void setPatrxDescripcion(String patrxDescripcion) {
        this.patrxDescripcion = patrxDescripcion;
    }

    public String getDeDescripcion() {
        return deDescripcion;
    }

    public void setDeDescripcion(String deDescripcion) {
        this.deDescripcion = deDescripcion;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
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

}
