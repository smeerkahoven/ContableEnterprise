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
import javax.persistence.NamedNativeQuery;
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
@Table(name = "cnt_nota_credito_transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotaCreditoTransaccion.findAll", query = "SELECT n FROM NotaCreditoTransaccion n")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByIdNotaCreditoTransaccion", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.idNotaCreditoTransaccion = :idNotaCreditoTransaccion")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByDescripcion", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.descripcion = :descripcion")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByMontoBs", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.montoBs = :montoBs")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByMontoUsd", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.montoUsd = :montoUsd")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByMoneda", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.moneda = :moneda")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByFechaInsert", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.fechaInsert = :fechaInsert")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByEstado", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.estado = :estado")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByNotaCredito", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.idNotaCredito = :idNotaCredito")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByNotaDebito", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.idNotaTransaccion.idNotaDebito = :idNotaDebito")
    , @NamedQuery(name = "NotaCreditoTransaccion.findByNotadebitoTransaccion", query = "SELECT n FROM NotaCreditoTransaccion n WHERE n.idNotaTransaccion = :idNotaTransaccion")
})

public class NotaCreditoTransaccion extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_nota_credito_transaccion")
    private Integer idNotaCreditoTransaccion;
    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_bs")
    private BigDecimal montoBs;
    @Column(name = "monto_usd")
    private BigDecimal montoUsd;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_nota_credito", referencedColumnName = "id_nota_credito")
    @ManyToOne
    private NotaCredito idNotaCredito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_transaccion")
    private NotaDebitoTransaccion idNotaTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plan_cuenta")
    private PlanCuentas idPlanCuenta;

    @Size(max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;

    public NotaCreditoTransaccion() {
    }

    public NotaCreditoTransaccion(Integer idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public NotaDebitoTransaccion getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(NotaDebitoTransaccion idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public PlanCuentas getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(PlanCuentas idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public Integer getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(Integer idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public NotaCredito getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(NotaCredito idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotaCreditoTransaccion != null ? idNotaCreditoTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaCreditoTransaccion)) {
            return false;
        }
        NotaCreditoTransaccion other = (NotaCreditoTransaccion) object;
        if ((this.idNotaCreditoTransaccion == null && other.idNotaCreditoTransaccion != null) || (this.idNotaCreditoTransaccion != null && !this.idNotaCreditoTransaccion.equals(other.idNotaCreditoTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.NotaCreditoTransaccion[ idNotaCreditoTransaccion=" + idNotaCreditoTransaccion + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idNotaCreditoTransaccion;
    }

}
