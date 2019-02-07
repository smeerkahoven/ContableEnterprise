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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_ingreso_transaccion")
@NamedQueries({
    @NamedQuery(name = "IngresoTransaccion.findAll", query = "SELECT i FROM IngresoTransaccion i")
    ,@NamedQuery(name = "IngresoTransaccion.findByIdNotaTransaccion", query = "SELECT i from IngresoTransaccion i WHERE i.idNotaTransaccion=:idNotaTransaccion")
    ,@NamedQuery(name = "IngresoTransaccion.findByIdIngresoCaja", query = "SELECT i from IngresoTransaccion i WHERE i.idIngresoCaja=:idIngresoCaja")
    ,@NamedQuery(name = "IngresoTransaccion.findByIdNotaDebito", query = "SELECT i from IngresoTransaccion i WHERE i.idNotaTransaccion.idNotaDebito = :idNotaDebito")
})
public class IngresoTransaccion extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;
    @Size(max = 64)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "monto_bs")
    private BigDecimal montoBs;
    @Column(name = "monto_usd")
    private BigDecimal montoUsd;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;

    @JoinColumn(name = "id_ingreso_caja", referencedColumnName = "id_ingreso_caja")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private IngresoCaja idIngresoCaja;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_transaccion")
    private NotaDebitoTransaccion idNotaTransaccion;

    @Column(name = "estado")
    private String estado;

    public IngresoTransaccion() {
    }

    public IngresoTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoBs() {
        return montoBs;
    }

    public void setMontoBs(BigDecimal montoBs) {
        this.montoBs = montoBs;
    }

    public BigDecimal getMontoUsd() {
        return montoUsd;
    }

    public void setMontoUsd(BigDecimal montoUsd) {
        this.montoUsd = montoUsd;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public IngresoCaja getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(IngresoCaja idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public NotaDebitoTransaccion getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(NotaDebitoTransaccion idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoTransaccion)) {
            return false;
        }
        IngresoTransaccion other = (IngresoTransaccion) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.IngresoTransaccion[ idTransaccion=" + idTransaccion + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdTransaccion();
    }

}
