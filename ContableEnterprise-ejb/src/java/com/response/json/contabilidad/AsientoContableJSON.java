/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.AsientoContablePK;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class AsientoContableJSON {

    private int idAsiento;
    private int gestion;
    private String estado;
    private String fechaMovimiento;
    private Integer idLibro;
    private ComboSelect idPlanCuenta;
    private String moneda;
    private BigDecimal debeMonExt;
    private BigDecimal debeMonNac;
    private BigDecimal haberMonExt;
    private BigDecimal haberMonNac;
    private String action;

    public static AsientoContable toAsientoContable(AsientoContableJSON a) {
        System.out.println(a.getIdPlanCuenta().getId());
        Number idPlanCuenta = a.getIdPlanCuenta().getId() instanceof Double ? (Double) a.getIdPlanCuenta().getId() : (BigDecimal) a.getIdPlanCuenta().getId();
        AsientoContable anew = new AsientoContable();
        anew.setEstado(a.getEstado());
        anew.setFechaMovimiento(DateContable.getCurrentDate());
        /*anew.setGestion(a.getGestion());
        anew.setIdAsiento(a.getIdAsiento());*/
        anew.setIdAsiento(a.getIdAsiento());
        anew.setGestion(a.getGestion());
        
        anew.setIdLibro(a.getIdLibro());
        anew.setIdPlanCuenta(idPlanCuenta.intValue());
        anew.setMoneda(a.getMoneda());
        anew.setMontoDebeExt(a.getDebeMonExt());
        anew.setMontoDebeNac(a.getDebeMonNac());
        anew.setMontoHaberExt(a.getHaberMonExt());
        anew.setMontoHaberNac(a.getHaberMonNac());

        return anew;
    }

    public static List<AsientoContableJSON> toAsientoContableJSON(List<AsientoContable> l) {
        List<AsientoContableJSON> json = new LinkedList<>();
        for (AsientoContable a : l) {
            json.add(toAsientoContableJSON(a));
        }

        return json;
    }

    public static AsientoContableJSON toAsientoContableJSON(AsientoContable a) {
        AsientoContableJSON json = new AsientoContableJSON();
        json.setIdAsiento(a.getIdAsiento());
        json.setGestion(a.getGestion());

        json.setDebeMonExt(a.getMontoDebeExt());
        json.setDebeMonNac(a.getMontoDebeNac());
        json.setEstado(a.getEstado());
        json.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setHaberMonExt(a.getMontoHaberExt());
        json.setHaberMonNac(a.getMontoHaberNac());
        json.setIdLibro(a.getIdLibro());
        ComboSelect s = new ComboSelect();
        s.setId(a.getIdPlanCuenta());
        json.setIdPlanCuenta(s);
        json.setMoneda(a.getMoneda());

        return json;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public ComboSelect getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(ComboSelect idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getDebeMonExt() {
        return debeMonExt;
    }

    public void setDebeMonExt(BigDecimal debeMonExt) {
        this.debeMonExt = debeMonExt;
    }

    public BigDecimal getDebeMonNac() {
        return debeMonNac;
    }

    public void setDebeMonNac(BigDecimal debeMonNac) {
        this.debeMonNac = debeMonNac;
    }

    public BigDecimal getHaberMonExt() {
        return haberMonExt;
    }

    public void setHaberMonExt(BigDecimal haberMonExt) {
        this.haberMonExt = haberMonExt;
    }

    public BigDecimal getHaberMonNac() {
        return haberMonNac;
    }

    public void setHaberMonNac(BigDecimal haberMonNac) {
        this.haberMonNac = haberMonNac;
    }

}
