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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_pago_anticipado_transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAnticipadoTransaccion.findAll", query = "SELECT p FROM PagoAnticipadoTransaccion p")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByIdPagoAnticipado", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.idPagoAnticipado = :idPagoAnticipado")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByDescripcion", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByMonto", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.monto = :monto")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByFechaInsert", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.fechaInsert = :fechaInsert")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByIdUsuarioCreador", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.idUsuarioCreador = :idUsuarioCreador")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByEstado", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.estado = :estado")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByNotadebitoTransaccion", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.idNotaTransaccion = :idNotaTransaccion")
    , @NamedQuery(name = "PagoAnticipadoTransaccion.findByNotaDebito", query = "SELECT p FROM PagoAnticipadoTransaccion p WHERE p.idNotaTransaccion.idNotaDebito = :idNotaDebito")})
public class PagoAnticipadoTransaccion extends Entidad {

    public static class Tipo {

        public final static String ACREDITACION = "AC";
        public final static String DEVOLUCION = "DE";
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_pago_anticipado_transaccion")
    private Integer idPagoAnticipadoTransaccion;
    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Size(max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "id_pago_anticipado", referencedColumnName = "id_pago_anticipado")
    @ManyToOne
    private PagoAnticipado idPagoAnticipado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_debito_transaccion")
    private NotaDebitoTransaccion idNotaTransaccion;

    public PagoAnticipadoTransaccion() {
    }

    public PagoAnticipadoTransaccion(Integer idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }

    public void setIdPagoAnticipadoTransaccion(Integer idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PagoAnticipado getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(PagoAnticipado idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public NotaDebitoTransaccion getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(NotaDebitoTransaccion idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagoAnticipadoTransaccion != null ? idPagoAnticipadoTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAnticipadoTransaccion)) {
            return false;
        }
        PagoAnticipadoTransaccion other = (PagoAnticipadoTransaccion) object;
        if ((this.idPagoAnticipadoTransaccion == null && other.idPagoAnticipadoTransaccion != null) || (this.idPagoAnticipadoTransaccion != null && !this.idPagoAnticipadoTransaccion.equals(other.idPagoAnticipadoTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdPagoAnticipadoTransaccion();
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.PagoAnticipadoTransaccion[ idPagoAnticipadoTransaccion=" + idPagoAnticipadoTransaccion + " ]";
    }

}
