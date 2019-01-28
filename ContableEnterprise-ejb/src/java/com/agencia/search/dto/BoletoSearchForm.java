/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.search.dto;

import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class BoletoSearchForm implements Serializable {

    private Long numeroBoleto;

    private Integer aerolinea;

    private String fechaInicio;

    private String fechaFin;

    private Integer idEmpresa;

    private Integer notaDebito;

    private String nombrePasajero;

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public Integer getNotaDebito() {
        return notaDebito;
    }

    public void setNotaDebito(Integer notaDebito) {
        this.notaDebito = notaDebito;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getNumeroBoleto() {
        return numeroBoleto;
    }

    public void setNumeroBoleto(Long numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    public Integer getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Integer aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

}
