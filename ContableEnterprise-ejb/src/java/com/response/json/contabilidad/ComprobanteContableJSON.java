/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.ComprobanteContable;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class ComprobanteContableJSON implements Serializable{

    private Integer idLibro;
    private Integer idNumeroGestion;
    private String idNumeroGestionColumna;
    private Integer gestion;
    private String idUsuarioCreador;
    private String idUsuarioAnulado;
    private String fecha;
    private String nombre;
    private String estado;
    private String estadoNombre;
    private BigDecimal factorCambiario;
    private String tipo;
    private String tipoNombre;
    private Integer idEmpresa;
    private String concepto;
    private String fechaInsert;
    private BigDecimal totalDebeMonNac;
    private BigDecimal totalDebeMonExt;
    private BigDecimal totalHaberMonNac;
    private BigDecimal totalHaberMonExt;
    private String conErrores;
    private ComboSelect idCliente;

    private List<AsientoContableJSON> transacciones = new LinkedList<AsientoContableJSON>();

    public static ComprobanteContable toComprobanteContable(ComprobanteContableJSON c) {
        ComprobanteContable newc = new ComprobanteContable();
        newc.setIdLibro(c.getIdLibro());
        newc.setGestion(c.getGestion());
        newc.setConcepto(c.getConcepto());
        newc.setEstado(c.getEstado());
        newc.setFactorCambiario(c.getFactorCambiario());
        newc.setFecha(DateContable.toLatinAmericaDateFormat(c.getFecha()));

        newc.setIdNumeroGestion(c.getIdNumeroGestion());
        newc.setIdUsuarioAnulado(c.getIdUsuarioAnulado());
        newc.setIdUsuarioCreador(c.getIdUsuarioCreador());

        if (c.getIdCliente() != null) {
            newc.setIdCliente(new Cliente(((Double) c.getIdCliente().getId()).intValue()));
        }

        newc.setIdEmpresa(c.getIdEmpresa());
        newc.setTipo(c.getTipo());
        newc.setTotalDebeExt(c.getTotalDebeMonExt());
        newc.setTotalDebeNac(c.getTotalDebeMonNac());
        newc.setTotalHaberExt(c.getTotalHaberMonExt());
        newc.setTotalHaberNac(c.getTotalHaberMonNac());
        newc.setFechaInsert(DateContable.getCurrentDate());
        newc.setConErrores(c.getConErrores());
        newc.setNombre(c.getNombre());

        return newc;
    }

    public static ComprobanteContableJSON toComprobanteContableJSON(ComprobanteContable c) {
        ComprobanteContableJSON json = new ComprobanteContableJSON();
        json.setIdLibro(c.getIdLibro());
        json.setGestion(c.getGestion());
        json.setIdNumeroGestionColumna(c.getGestion() + "-" + c.getIdNumeroGestion());

        json.setConErrores((c.getConErrores()));
        json.setConcepto(c.getConcepto());
        json.setEstado(c.getEstado());
        json.setFactorCambiario(c.getFactorCambiario());
        json.setFecha(DateContable.getDateFormat(c.getFecha(), DateContable.LATIN_AMERICA_FORMAT));
        json.setFechaInsert(DateContable.getDateFormat(c.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setIdEmpresa(c.getIdEmpresa());
        json.setIdNumeroGestion(c.getIdNumeroGestion());
        json.setIdUsuarioAnulado(c.getIdUsuarioAnulado());
        json.setIdUsuarioCreador(c.getIdUsuarioCreador());
        if (c.getIdCliente() != null) {
            json.setIdCliente(new ComboSelect(c.getIdCliente().getIdCliente(), c.getIdCliente().getNombre()));
        }
        json.setTipo(c.getTipo());
        json.setTotalDebeMonExt(c.getTotalDebeExt());
        json.setTotalDebeMonNac(c.getTotalDebeNac());
        json.setTotalHaberMonExt(c.getTotalHaberExt());
        json.setTotalHaberMonNac(c.getTotalHaberNac());
        json.setEstadoNombre(ComprobanteContable.getEstadoNombre(c.getEstado()));
        json.setNombre(c.getNombre());

        return json;
    }

    public ComboSelect getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ComboSelect idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdNumeroGestionColumna() {
        return idNumeroGestionColumna;
    }

    public void setIdNumeroGestionColumna(String idNumeroGestionColumna) {
        this.idNumeroGestionColumna = idNumeroGestionColumna;
    }

    public String getTipoNombre() {
        return tipoNombre;
    }

    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public List<AsientoContableJSON> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<AsientoContableJSON> transacciones) {
        this.transacciones = transacciones;
    }

    public String getConErrores() {
        return conErrores;
    }

    public void setConErrores(String conErrores) {
        this.conErrores = conErrores;
    }

    public Integer getIdNumeroGestion() {
        return idNumeroGestion;
    }

    public void setIdNumeroGestion(Integer idNumeroGestion) {
        this.idNumeroGestion = idNumeroGestion;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getIdUsuarioAnulado() {
        return idUsuarioAnulado;
    }

    public void setIdUsuarioAnulado(String idUsuarioAnulado) {
        this.idUsuarioAnulado = idUsuarioAnulado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(String fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public BigDecimal getTotalDebeMonNac() {
        return totalDebeMonNac;
    }

    public void setTotalDebeMonNac(BigDecimal totalDebeMonNac) {
        this.totalDebeMonNac = totalDebeMonNac;
    }

    public BigDecimal getTotalDebeMonExt() {
        return totalDebeMonExt;
    }

    public void setTotalDebeMonExt(BigDecimal totalDebeMonExt) {
        this.totalDebeMonExt = totalDebeMonExt;
    }

    public BigDecimal getTotalHaberMonNac() {
        return totalHaberMonNac;
    }

    public void setTotalHaberMonNac(BigDecimal totalHaberMonNac) {
        this.totalHaberMonNac = totalHaberMonNac;
    }

    public BigDecimal getTotalHaberMonExt() {
        return totalHaberMonExt;
    }

    public void setTotalHaberMonExt(BigDecimal totalHaberMonExt) {
        this.totalHaberMonExt = totalHaberMonExt;
    }

}
