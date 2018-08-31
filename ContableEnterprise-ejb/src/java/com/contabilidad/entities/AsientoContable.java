/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_asiento_contable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsientoContable.findAll", query = "SELECT a FROM AsientoContable a")
    ,
@NamedQuery(name = "AsientoContable.find", query = "SELECT a FROM AsientoContable a WHERE a.idLibro=:idLibro")
}
)
public class AsientoContable extends Entidad {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsientoContablePK asientoContablePK;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_movimiento", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;
    @Column(name = "id_libro", updatable = false)
    private Integer idLibro;
    @Column(name = "id_plan_cuenta")
    private Integer idPlanCuenta;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "monto_debe_ext")
    private BigDecimal montoDebeExt;
    @Column(name = "monto_debe_nac")
    private BigDecimal montoDebeNac;
    @Column(name = "monto_haber_ext")
    private BigDecimal montoHaberExt;
    @Column(name = "monto_haber_nac")
    private BigDecimal montoHaberNac;

    public AsientoContable() {
    }

    public AsientoContable(AsientoContablePK asientoContablePK) {
        this.asientoContablePK = asientoContablePK;
    }

    public AsientoContable(int idAsiento, int gestion) {
        this.asientoContablePK = new AsientoContablePK(idAsiento, gestion);
    }

    public AsientoContablePK getAsientoContablePK() {
        return asientoContablePK;
    }

    public void setAsientoContablePK(AsientoContablePK asientoContablePK) {
        this.asientoContablePK = asientoContablePK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(Integer idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoDebeExt() {
        return montoDebeExt;
    }

    public void setMontoDebeExt(BigDecimal montoDebeExt) {
        this.montoDebeExt = montoDebeExt;
    }

    public BigDecimal getMontoDebeNac() {
        return montoDebeNac;
    }

    public void setMontoDebeNac(BigDecimal montoDebeNac) {
        this.montoDebeNac = montoDebeNac;
    }

    public BigDecimal getMontoHaberExt() {
        return montoHaberExt;
    }

    public void setMontoHaberExt(BigDecimal montoHaberExt) {
        this.montoHaberExt = montoHaberExt;
    }

    public BigDecimal getMontoHaberNac() {
        return montoHaberNac;
    }

    public void setMontoHaberNac(BigDecimal montoHaberNac) {
        this.montoHaberNac = montoHaberNac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asientoContablePK != null ? asientoContablePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsientoContable)) {
            return false;
        }
        AsientoContable other = (AsientoContable) object;
        if ((this.asientoContablePK == null && other.asientoContablePK != null) || (this.asientoContablePK != null && !this.asientoContablePK.equals(other.asientoContablePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.AsientoContable[ asientoContablePK=" + asientoContablePK + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getAsientoContablePK().getIdAsiento();
    }

}
