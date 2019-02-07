/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.boletaje;

import com.seguridad.utils.ComboSelect;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class VentaBoletosSearchJson implements Serializable {

    private String fechaInicio;
    private String fechaFin;
    private ComboSelect idAerolinea;
    private ComboSelect idCliente;
    private Integer idEmpresa;
    private String tipoCupon;

    public String getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

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

    public ComboSelect getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(ComboSelect idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public ComboSelect getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }

}
