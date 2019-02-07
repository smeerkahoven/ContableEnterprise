/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author xeio
 */
public class BoletoPlanillaBsp implements Serializable {

    private Integer idBoleto;
    private Integer idAerolinea;
    private String iata;
    private String numero;
    private Boolean ivaItComision;
    private Integer idNotaDebito;
    private String tipoBoleto;
    private String tipoCupon;
    private Long ticketNumber;
    private String fechaEmision;
    private String fechaViaje;
    private BigDecimal importeNeto;
    private BigDecimal impuestos;
    private BigDecimal totalBoleto;
    private BigDecimal comision;
    private BigDecimal montoComision;
    private BigDecimal totalMontoCobrar;
    private String estado;
    // reporte ventas
    private String ruta;
    private String nombrePasajero ;
    private BigDecimal montoPagarLineaAerea ;
    // reporte comision cliente
    private BigDecimal fee;
    private BigDecimal descuento;

    public BoletoPlanillaBsp(Integer idBoleto, Integer idAerolinea, String iata,
            String numero, Boolean ivaItComision, Integer idNotaDebito, String tipoBoleto,
            String tipoCupon, Long ticketNumber, String fechaEmision, String fechaViaje,
            BigDecimal importeNeto, BigDecimal impuestos, BigDecimal totalBoleto, BigDecimal comision,
            BigDecimal montoComision, BigDecimal totalMontoCobrar, String estado) {
        this.idBoleto = idBoleto;
        this.idAerolinea = idAerolinea;
        this.iata = iata;
        this.numero = numero;
        this.ivaItComision = ivaItComision;
        this.idNotaDebito = idNotaDebito;
        this.tipoBoleto = tipoBoleto;
        this.tipoCupon = tipoCupon;
        this.ticketNumber = ticketNumber;
        this.fechaEmision = fechaEmision;
        this.fechaViaje = fechaViaje;
        this.importeNeto = importeNeto;
        this.impuestos = impuestos;
        this.totalBoleto = totalBoleto;
        this.comision = comision;
        this.montoComision = montoComision;
        this.totalMontoCobrar = totalMontoCobrar;
        this.estado = estado;

    }

    public BoletoPlanillaBsp(Integer idBoleto, Integer idAerolinea, String iata, String numero,
            Boolean ivaItComision, Integer idNotaDebito, String tipoBoleto, String tipoCupon, 
            Long ticketNumber, String fechaEmision, String fechaViaje, BigDecimal importeNeto,
            BigDecimal impuestos, BigDecimal totalBoleto, BigDecimal comision, 
            BigDecimal montoComision, BigDecimal montoPagarLineaAerea, String estado, 
            String ruta, String nombrePasajero) {
        this.idBoleto = idBoleto;
        this.idAerolinea = idAerolinea;
        this.iata = iata;
        this.numero = numero;
        this.ivaItComision = ivaItComision;
        this.idNotaDebito = idNotaDebito;
        this.tipoBoleto = tipoBoleto;
        this.tipoCupon = tipoCupon;
        this.ticketNumber = ticketNumber;
        this.fechaEmision = fechaEmision;
        this.fechaViaje = fechaViaje;
        this.importeNeto = importeNeto;
        this.impuestos = impuestos;
        this.totalBoleto = totalBoleto;
        this.comision = comision;
        this.montoComision = montoComision;
        this.montoPagarLineaAerea = montoPagarLineaAerea;
        this.estado = estado;
        this.ruta = ruta;
        this.nombrePasajero = nombrePasajero;
    }

    public BoletoPlanillaBsp(Integer idBoleto, Integer idAerolinea, String iata, String numero,
            Integer idNotaDebito, String tipoBoleto, String tipoCupon,
            Long ticketNumber, String fechaEmision, String fechaViaje, 
            BigDecimal importeNeto, BigDecimal impuestos, 
            BigDecimal totalBoleto, String ruta, String nombrePasajero, 
            BigDecimal fee, BigDecimal descuento) {
        this.idBoleto = idBoleto;
        this.idAerolinea = idAerolinea;
        this.iata = iata;
        this.numero = numero;
        this.idNotaDebito = idNotaDebito;
        this.tipoBoleto = tipoBoleto;
        this.tipoCupon = tipoCupon;
        this.ticketNumber = ticketNumber;
        this.fechaEmision = fechaEmision;
        this.fechaViaje = fechaViaje;
        this.importeNeto = importeNeto;
        this.impuestos = impuestos;
        this.totalBoleto = totalBoleto;
        this.ruta = ruta;
        this.nombrePasajero = nombrePasajero;
        this.fee = fee;
        this.descuento = descuento;
    }

    public BigDecimal getMontoPagarLineaAerea() {
        return montoPagarLineaAerea;
    }

    public void setMontoPagarLineaAerea(BigDecimal montoPagarLineaAerea) {
        this.montoPagarLineaAerea = montoPagarLineaAerea;
    }

    
    
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }
    
    

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }
    
    

    public BigDecimal getTotalMontoCobrar() {
        return totalMontoCobrar;
    }

    public void setTotalMontoCobrar(BigDecimal totalMontoCobrar) {
        this.totalMontoCobrar = totalMontoCobrar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getIvaItComision() {
        return ivaItComision;
    }

    public void setIvaItComision(Boolean ivaItComision) {
        this.ivaItComision = ivaItComision;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public String getTipoBoleto() {
        return tipoBoleto;
    }

    public void setTipoBoleto(String tipoBoleto) {
        this.tipoBoleto = tipoBoleto;
    }

    public String getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

    public Long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getTotalBoleto() {
        return totalBoleto;
    }

    public void setTotalBoleto(BigDecimal totalBoleto) {
        this.totalBoleto = totalBoleto;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

}
