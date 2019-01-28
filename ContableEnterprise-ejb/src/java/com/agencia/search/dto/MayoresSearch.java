/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.search.dto;

import com.seguridad.utils.ComboSelect;


/**
 *
 * @author xeio
 */
public class MayoresSearch {

    private Integer idEmpresa;
    private String fechaInicio;
    private String fechaFin;
    private String moneda;
    private ComboSelect idCuenta;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public ComboSelect getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(ComboSelect idCuenta) {
        this.idCuenta = idCuenta;
    }



}
