/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.control.entities.Entidad;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_plan_cuentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanCuentas.findAll", query = "SELECT p FROM PlanCuentas p WHERE p.idEmpresa=:idEmpresa ORDER BY p.idPlanCuentas, p.cuenta, p.nivel asc"),
    //@NamedQuery(name = "PlanCuentas.findAll", query = "SELECT p ,(select c.cuenta from PlanCuentas c where p.idPlanCuentas=c.idPlanCuentas ) as idPlanCuentaPadreNombre FROM PlanCuentas p ORDER BY p.idPlanCuentas, p.cuenta, p.nivel asc"),
    @NamedQuery(name = "PlanCuentas.forCombo", query = "SELECT p.idPlanCuentas,p.cuenta FROM PlanCuentas p WHERE p.nivel = 5 AND p.idEmpresa=:idEmpresa  Order by p.cuenta"),
    @NamedQuery(name = "PlanCuentas.forComboPlan", query = "SELECT p FROM PlanCuentas p WHERE p.idEmpresa=:idEmpresa and p.idPlanCuentaPadre= (SELECT q.idPlanCuentas FROM PlanCuentas q WHERE q.cuenta like :cuenta ) ORDER BY p.idPlanCuentas, p.cuenta, p.nivel asc"),
    @NamedQuery(name = "PlanCuentas.forSearch", query = "SELECT p.idPlanCuentas,p.cuenta FROM PlanCuentas p WHERE p.nivel < 5 Order by p.cuenta"),
    
})
public class PlanCuentas extends Entidad {

    private static final long serialVersionUID = 1L;
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @Id
    @Basic(optional = false)
    @Column(name = "id_plan_cuentas")
    private BigInteger idPlanCuentas;

    @Column(name = "id_plan_cuenta_padre")
    private BigInteger idPlanCuentaPadre;

    @Column(name = "cuenta", length = 40)
    private String cuenta;

    @Column(name = "aplica_movimiento")
    private String aplicaMovimiento;

    @Column(name = "moneda", length = 2)
    private String moneda;

    @Column(name = "marco", length = 2)
    private String marco;

    @Column(name = "cta_itb", length = 24)
    private BigInteger ctaItb;

    @Column(name = "nivel")
    private Integer nivel;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "mantenimiento_valor")
    private String mantenimientoValor;

    @Transient
    private String ctaItbNombre;

    @Transient
    private String idPlanCuentaPadreNombre;

    public String getCtaItbNombre() {
        return ctaItbNombre;
    }

    public void setCtaItbNombre(String ctaItbNombre) {
        this.ctaItbNombre = ctaItbNombre;
    }

    
    public String getIdPlanCuentaPadreNombre() {
        return idPlanCuentaPadreNombre;
    }

    public void setIdPlanCuentaPadreNombre(String idPlanCuentaPadreNombre) {
        this.idPlanCuentaPadreNombre = idPlanCuentaPadreNombre;
    }

    public String getMantenimientoValor() {
        return mantenimientoValor;
    }

    public void setMantenimientoValor(String mantenimientoValor) {
        this.mantenimientoValor = mantenimientoValor;
    }

    public PlanCuentas() {
    }

    public PlanCuentas(BigInteger idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigInteger getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(BigInteger idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public BigInteger getIdPlanCuentaPadre() {
        return idPlanCuentaPadre;
    }

    public void setIdPlanCuentaPadre(BigInteger idPlanCuentaPadre) {
        this.idPlanCuentaPadre = idPlanCuentaPadre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getAplicaMovimiento() {
        return aplicaMovimiento;
    }

    public void setAplicaMovimiento(String aplica) {
        this.aplicaMovimiento = aplica;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMarco() {
        return marco;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public BigInteger getCtaItb() {
        return ctaItb;
    }

    public void setCtaItb(BigInteger ctaItb) {
        this.ctaItb = ctaItb;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanCuentas != null ? idPlanCuentas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanCuentas)) {
            return false;
        }
        PlanCuentas other = (PlanCuentas) object;
        if ((this.idPlanCuentas == null && other.idPlanCuentas != null) || (this.idPlanCuentas != null && !this.idPlanCuentas.equals(other.idPlanCuentas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.PlanCuentas[ idPlanCuentas=" + idPlanCuentas + " ]";
    }

}
