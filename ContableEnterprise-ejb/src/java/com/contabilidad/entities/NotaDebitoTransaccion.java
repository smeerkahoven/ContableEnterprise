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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_nota_debito_transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotaDebitoTransaccion.findAll", query = "SELECT n FROM NotaDebitoTransaccion n")
    ,@NamedQuery(name = "NotaDebitoTransaccion.updateBoletoEstado", query = "UPDATE NotaDebitoTransaccion n SET n.estado=:estado WHERE n.idBoleto=:idBoleto")
    ,@NamedQuery(name = "NotaDebitoTransaccion.findAllByIdNotaDebito", query = "SELECT n FROM NotaDebitoTransaccion n WHERE n.idNotaDebito=:idNotaDebito")
})
public class NotaDebitoTransaccion extends Entidad {

    public static class Tipo {

        public static final String BOLETO = "B";
        public static final String PAQUETE = "P";
        public static final String SEGURO = "S";
        public static final String HOTEL = "H";
        public static final String ALQUILER_AUTO = "A";
        public static final String RESERVA = "R";
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_nota_debito_transaccion")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idNotaDebitoTransaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private Integer gestion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_nota_debito")
    private Integer idNotaDebito;
    @Size(max = 64)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_bs")
    private BigDecimal montoBs;
    @Column(name = "monto_usd")
    private BigDecimal montoUsd;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Column(name = "id_boleto")
    private Integer idBoleto;
    @Column(name = "id_plan_cuentas")
    private Integer idPlanCuentas;
    @Column(name = "estado")
    private String estado;
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "tipo")
    private String tipo;

    public NotaDebitoTransaccion() {
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.NotaDebitoTransaccion[ notaDebitoTransaccionPK=" + idNotaDebito + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return getIdNotaDebitoTransaccion();
    }

}
