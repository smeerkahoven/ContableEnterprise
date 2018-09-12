/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.contabilidad.entities.PlanCuentas;
import com.seguridad.utils.MarcoCuenta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class PlanCuentasJSON {

    private int idEmpresa;
    private Integer idPlanCuentas;
    private String nombreCuenta;
    private Long nroPlanCuenta;
    private Long nroPlanCuentaPadre;
    private String nroPlanCuentaPadreNombre;
    private int nivel;
    private String marco;
    private String marcoNombre;
    private String aplicaMovimiento;
    private String aplicaMovimientoNombre;
    private String mantenimientoValor;
    private String moneda;
    private Integer ctaItb;

    //
    /*private int aplica;
    private String moneda;
    private int marco ;
    private int cuentaItb;
    private double fondos;
    private String tipo;
    private double secuencia;
    private double presupuesto;
    private int tPresupuesto ;
    private int tPasivo;
    private String rubro ;
    private int tGiro;
    private double saldo ;*/
 /**/
    private List<PlanCuentasJSON> children = null;

    public PlanCuentasJSON() {
    }

    private static String getMarcoNombreFromMarco(String marco) {
        switch (marco) {
            case "1":
                return MarcoCuenta.ACTIVOS.name();
            case "2":
                return MarcoCuenta.PASIVOS.name();
            case "3":
                return MarcoCuenta.INGRESOS.name();
            case "4":
                return MarcoCuenta.EGRESOS.name();

        }
        return "";
    }

    private static String getAplicaMovimientoNombreValor(String v) {
        switch (v) {
            case "T":
                return "Transaccion";
            case "M":
                return "Mayorizacion";

        }
        return "";
    }

    public static PlanCuentasJSON createJson(PlanCuentas p) {
        PlanCuentasJSON json = new PlanCuentasJSON();
        json.setIdEmpresa(p.getIdEmpresa());
        json.setIdPlanCuentas(p.getIdPlanCuentas());
        json.setNivel(p.getNivel());
        json.setNroPlanCuenta(p.getNroPlanCuenta());
        json.setNroPlanCuentaPadre(p.getNroPlanCuentaPadre());
        json.setNombreCuenta(p.getCuenta());
        json.setMarco(p.getMarco());
        json.setMarcoNombre(PlanCuentasJSON.getMarcoNombreFromMarco(p.getMarco()));
        json.setMoneda(p.getMoneda());
        json.setAplicaMovimiento(p.getAplicaMovimiento());
        json.setAplicaMovimientoNombre(getAplicaMovimientoNombreValor(p.getAplicaMovimiento()));
        json.setMantenimientoValor(p.getMantenimientoValor());
        json.setCtaItb(p.getCtaItb());
        return json;
    }

    public static PlanCuentas toPlanCuentas(PlanCuentasJSON json) {
        PlanCuentas pc = new PlanCuentas();
        pc.setIdEmpresa(json.getIdEmpresa());
        pc.setIdPlanCuentas(json.getIdPlanCuentas());
        pc.setCuenta(json.getNombreCuenta());
        pc.setNivel(json.getNivel());
        pc.setNroPlanCuenta(json.getNroPlanCuenta());
        pc.setNroPlanCuentaPadre(json.getNroPlanCuentaPadre());
        pc.setMarco(json.getMarco());
        pc.setMoneda(json.getMoneda());
        pc.setMantenimientoValor(json.getMantenimientoValor());
        pc.setCtaItb(json.getCtaItb());
        pc.setAplicaMovimiento(json.getAplicaMovimiento());

        return pc;
    }

    public static PlanCuentasJSON addCuenta(PlanCuentas p, PlanCuentasJSON json) {
        if (json.getChildren() == null) {
            json.children = new ArrayList<>();
        }
        json.getChildren().add(createJson(p));

        return json;
    }

    public String getAplicaMovimientoNombre() {
        return aplicaMovimientoNombre;
    }

    public void setAplicaMovimientoNombre(String aplicaMovimientoNombre) {
        this.aplicaMovimientoNombre = aplicaMovimientoNombre;
    }

    public Integer getCtaItb() {
        return ctaItb;
    }

    public void setCtaItb(Integer ctaItb) {
        this.ctaItb = ctaItb;
    }

    public String getAplicaMovimiento() {
        return aplicaMovimiento;
    }

    public void setAplicaMovimiento(String aplicaMovimiento) {
        this.aplicaMovimiento = aplicaMovimiento;
    }

    public String getMantenimientoValor() {
        return mantenimientoValor;
    }

    public void setMantenimientoValor(String mantenimientoValor) {
        this.mantenimientoValor = mantenimientoValor;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMarcoNombre() {
        return marcoNombre;
    }

    public void setMarcoNombre(String marcoNombre) {
        this.marcoNombre = marcoNombre;
    }

    public String getMarco() {
        return marco;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<PlanCuentasJSON> getChildren() {
        return children;
    }

    public void setChildren(List<PlanCuentasJSON> cuentas) {
        this.children = cuentas;
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public Long getNroPlanCuenta() {
        return nroPlanCuenta;
    }

    public void setNroPlanCuenta(Long nroPlanCuenta) {
        this.nroPlanCuenta = nroPlanCuenta;
    }

    public Long getNroPlanCuentaPadre() {
        return nroPlanCuentaPadre;
    }

    public void setNroPlanCuentaPadre(Long nroPlanCuentaPadre) {
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
    }

    public String getNroPlanCuentaPadreNombre() {
        return nroPlanCuentaPadreNombre;
    }

    public void setNroPlanCuentaPadreNombre(String nroPlanCuentaPadreNombre) {
        this.nroPlanCuentaPadreNombre = nroPlanCuentaPadreNombre;
    }

}
