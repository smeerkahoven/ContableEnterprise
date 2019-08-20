/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.dto;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 *
 * @author xeio
 */
public class ReporteEstadoClienteMainDto {

    private Integer idCliente;
    private String nombre;
    private BigDecimal totalSaldoNac;
    private BigDecimal totalSaldoExt;
    private LinkedList<ReporteEstadoClienteDto> entities = new LinkedList<>();

    public ReporteEstadoClienteMainDto(Integer idCliente, String nombre, BigDecimal totalSaldoNac, BigDecimal totalSaldoExt) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.totalSaldoNac = totalSaldoNac;
        this.totalSaldoExt = totalSaldoExt;
    }

    public ReporteEstadoClienteMainDto() {
    }

    public LinkedList<ReporteEstadoClienteDto> getEntities() {
        return entities;
    }

    public void setEntities(LinkedList<ReporteEstadoClienteDto> entities) {
        this.entities = entities;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getTotalSaldoNac() {
        return totalSaldoNac;
    }

    public void setTotalSaldoNac(BigDecimal totalSaldoNac) {
        this.totalSaldoNac = totalSaldoNac;
    }

    public BigDecimal getTotalSaldoExt() {
        return totalSaldoExt;
    }

    public void setTotalSaldoExt(BigDecimal totalSaldoExt) {
        this.totalSaldoExt = totalSaldoExt;
    }

}
