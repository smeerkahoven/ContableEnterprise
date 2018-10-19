/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
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
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_nota_debito")
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "NotaDebito.updateNotaDebito",
        procedureName = "updateNotaDebito",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
        }
)
@NamedQueries({
    @NamedQuery(name = "NotaDebito.findAll", query = "SELECT n FROM NotaDebito n")})
public class NotaDebito extends Entidad {

    public static final String EMITIDO = "E";
    public static final String PENDIENTE = "P";
    public static final String ANULADO = "A";
    public static final String CREADO = "C" ;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_nota_debito")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idNotaDebito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private int gestion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private int idEmpresa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_counter")
    private Promotor idCounter;
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_total_bs")
    private BigDecimal montoTotalBs;
    @Column(name = "monto_total_usd")
    private BigDecimal montoTotalUsd;
    @Column(name = "monto_adeudado_bs")
    private BigDecimal montoAdeudadoBs;
    @Column(name = "monto_adeudado_usd")
    private BigDecimal montoAdeudadoUsd;
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;

    @Column(name = "forma_pago")
    private String formaPago;

    @Column(name = "tipo_contado")
    private String tipoContado;

    @Column(name = "id_banco")
    private Integer idBanco;

    @Column(name = "nro_cheque")
    private String nroCheque;

    @Column(name = "id_tarjeta_credito")
    private Integer idTarjetaCredito;

    @Column(name = "nro_tarjeta")
    private String nroTarjeta;

    @Column(name = "nro_deposito")
    private String nroDeposito;

    @Column(name = "id_cuenta_deposito")
    private Integer idCuentaDeposito;

    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;

    @Column(name = "credito_dias")
    private int creditoDias;

    @Column(name = "credito_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creditoVencimiento;

    @Column(name = "combinado_contado")
    private Short combinadoContado;
    @Column(name = "combinado_tarjeta")
    private Short combinadoTarjeta;
    @Column(name = "combinado_credito")
    private Short combinadoCredito;
    @Column(name = "combinado_contado_tipo")
    private String combinadoContadoTipo;

    @Column(name = "estado")
    private String estado;

    public NotaDebito() {
    }

    public NotaDebito(Integer idNotaDebito, Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Promotor getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(Promotor idCounter) {
        this.idCounter = idCounter;
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

    public BigDecimal getMontoTotalBs() {
        return montoTotalBs;
    }

    public void setMontoTotalBs(BigDecimal montoTotalBs) {
        this.montoTotalBs = montoTotalBs;
    }

    public BigDecimal getMontoTotalUsd() {
        return montoTotalUsd;
    }

    public void setMontoTotalUsd(BigDecimal montoTotalUsd) {
        this.montoTotalUsd = montoTotalUsd;
    }

    public BigDecimal getMontoAdeudadoBs() {
        return montoAdeudadoBs;
    }

    public void setMontoAdeudadoBs(BigDecimal montoAdeudadoBs) {
        this.montoAdeudadoBs = montoAdeudadoBs;
    }

    public BigDecimal getMontoAdeudadoUsd() {
        return montoAdeudadoUsd;
    }

    public void setMontoAdeudadoUsd(BigDecimal montoAdeudadoUsd) {
        this.montoAdeudadoUsd = montoAdeudadoUsd;
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

    public String getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(String nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public Integer getIdCuentaDeposito() {
        return idCuentaDeposito;
    }

    public void setIdCuentaDeposito(Integer idCuentaDeposito) {
        this.idCuentaDeposito = idCuentaDeposito;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public int getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(int creditoDias) {
        this.creditoDias = creditoDias;
    }

    public Date getCreditoVencimiento() {
        return creditoVencimiento;
    }

    public void setCreditoVencimiento(Date creditoVencimiento) {
        this.creditoVencimiento = creditoVencimiento;
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

    public Short getCombinadoCredito() {
        return combinadoCredito;
    }

    public void setCombinadoCredito(Short combinadoCredito) {
        this.combinadoCredito = combinadoCredito;
    }

    public String getCombinadoContadoTipo() {
        return combinadoContadoTipo;
    }

    public void setCombinadoContadoTipo(String combinadoContadoTipo) {
        this.combinadoContadoTipo = combinadoContadoTipo;
    }

    public int getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(int idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    @Override
    public String toString() {
        return "com.contabilidad.entities.NotaDebito[ notaDebito=" + idNotaDebito + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idNotaDebito;
    }

}
