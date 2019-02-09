/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.json;

import com.seguridad.utils.ComboSelect;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class KardexClienteSearchJson implements Serializable {
    
    private String tipoBusqueda ;
    // Busqueda tipo Cliente C
    private ComboSelect idCliente ;
    // Busqueda tipo Nota de debito ; N
    private Integer idNotaDebito ;
    // Busqueda tipo Boleto B
    private Long nroBoleto ;
    // Busqueda tipo Pasajero P
    private String nombrePasajero ;
    //
    private String fechaInicio ;
    private String fechaFin ;
    //

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public ComboSelect getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Long getNroBoleto() {
        return nroBoleto;
    }

    public void setNroBoleto(Long nroBoleto) {
        this.nroBoleto = nroBoleto;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
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
