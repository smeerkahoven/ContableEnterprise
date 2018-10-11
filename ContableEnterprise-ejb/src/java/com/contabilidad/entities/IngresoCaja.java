/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_ingreso_caja")
@NamedQueries({
    @NamedQuery(name = "IngresoCaja.findAll", query = "SELECT i FROM IngresoCaja i")})
public class IngresoCaja extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_nota_debito")
    private int idNotaDebito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_counter")
    private Integer idCounter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "moneda")
    private String moneda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_abonado_bs")
    private BigDecimal montoAbonadoBs;
    @Column(name = "monto_abonado_usd")
    private BigDecimal montoAbonadoUsd;
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Size(max = 1)
    @Column(name = "forma_pago")
    private String formaPago;
    @Size(max = 1)
    @Column(name = "tipo_contado")
    private String tipoContado;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Size(max = 45)
    @Column(name = "nro_cheque")
    private String nroCheque;
    @Column(name = "id_tarjeta_credito")
    private Integer idTarjetaCredito;
    @Size(max = 45)
    @Column(name = "nro_tarjeta")
    private String nroTarjeta;
    @Size(max = 45)
    @Column(name = "nro_deposito")
    private String nroDeposito;

    @Column(name = "combinado_contado")
    private Short combinadoContado;

    @Column(name = "combinado_tarjeta")
    private Short combinadoTarjeta;

    @Column(name = "id_cuenta_deposito")
    private Integer idCuentaDeposito;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIngresoCaja")
    private Collection<IngresoTransaccion> ingresoTransaccionCollection;

    public IngresoCaja() {
    }

    public IngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public IngresoCaja(Integer idIngresoCaja, int idNotaDebito, String idUsuario, Date fechaEmision, Date fechaInsert, String moneda) {
        this.idIngresoCaja = idIngresoCaja;
        this.idNotaDebito = idNotaDebito;
        this.idUsuario = idUsuario;
        this.fechaEmision = fechaEmision;
        this.fechaInsert = fechaInsert;
        this.moneda = moneda;
    }

    public Integer getIdCuentaDeposito() {
        return idCuentaDeposito;
    }

    public void setIdCuentaDeposito(Integer idCuentaDeposito) {
        this.idCuentaDeposito = idCuentaDeposito;
    }

    public String getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(String nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(Integer idCounter) {
        this.idCounter = idCounter;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public int getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(int idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoAbonadoBs() {
        return montoAbonadoBs;
    }

    public void setMontoAbonadoBs(BigDecimal montoAbonadoBs) {
        this.montoAbonadoBs = montoAbonadoBs;
    }

    public BigDecimal getMontoAbonadoUsd() {
        return montoAbonadoUsd;
    }

    public void setMontoAbonadoUsd(BigDecimal montoAbonadoUsd) {
        this.montoAbonadoUsd = montoAbonadoUsd;
    }

    

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getTipoContado() {
        return tipoContado;
    }

    public void setTipoContado(String tipoContado) {
        this.tipoContado = tipoContado;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public Collection<IngresoTransaccion> getIngresoTransaccionCollection() {
        return ingresoTransaccionCollection;
    }

    public void setIngresoTransaccionCollection(Collection<IngresoTransaccion> ingresoTransaccionCollection) {
        this.ingresoTransaccionCollection = ingresoTransaccionCollection;
    }

    public Short getCombinadoContado() {
        return combinadoContado;
    }

    public void setCombinadoContado(Short combinadoContado) {
        this.combinadoContado = combinadoContado;
    }

    public Short getCombinadoTarjeta() {
        return combinadoTarjeta;
    }

    public void setCombinadoTarjeta(Short combinadoTarjeta) {
        this.combinadoTarjeta = combinadoTarjeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngresoCaja != null ? idIngresoCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoCaja)) {
            return false;
        }
        IngresoCaja other = (IngresoCaja) object;
        if ((this.idIngresoCaja == null && other.idIngresoCaja != null) || (this.idIngresoCaja != null && !this.idIngresoCaja.equals(other.idIngresoCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.IngresoCaja[ idIngresoCaja=" + idIngresoCaja + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return idIngresoCaja;
    }

}
