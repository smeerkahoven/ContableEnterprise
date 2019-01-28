/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_pago_anticipado")
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "PagoAnticipado.updateMontosPagoAnticipado",
        procedureName = "updateMontosPagoAnticipado",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_pago_anticipado")
        }
)
@NamedQueries({
    @NamedQuery(name = "PagoAnticipado.findAll", query = "SELECT p FROM PagoAnticipado p")
    ,@NamedQuery(name="PagoAnticipado.updateToPendiente", 
            query="UPDATE PagoAnticipado p SET p.concepto=:concepto, p.estado=:estado, p.factorCambiario=:factorCambiario, p.fechaEmision=:fechaEmision, p.formaPago=:formaPago, p.idBanco=:idBanco, p.idCliente=:idCliente, p.idCuentaDeposito=:idCuentaDeposito, p.idTarjetaCredito=:idTarjetaCredito, p.moneda=:moneda, p.nroCheque=:nroCheque, p.nroDeposito=:nroDeposito, p.nroTarjeta=:nroTarjeta WHERE p.idPagoAnticipado=:idPagoAnticipado")    
})
public class PagoAnticipado extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_pago_anticipado")
    private Integer idPagoAnticipado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;

    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "monto_anticipado")
    private BigDecimal montoAnticipado;
    @Column(name = "monto_total_acreditado")
    private BigDecimal montoTotalAcreditado;
    @Size(max = 256)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 45)
    @Column(name = "forma_pago")
    private String formaPago;
    @Size(max = 45)
    @Column(name = "nro_cheque")
    private String nroCheque;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Column(name = "id_cuenta_deposito")
    private Integer idCuentaDeposito;

    @Column(name = "nro_deposito")
    private String nroDeposito;
    @Column(name = "id_tarjeta_credito")
    private Integer idTarjetaCredito;
    @Size(max = 16)
    @Column(name = "nro_tarjeta")
    private String nroTarjeta;
    @Size(max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idPagoAnticipado")
    private Collection<PagoAnticipadoTransaccion> pagoAnticipadoTransaccionCollection;

    public PagoAnticipado() {
    }

    public PagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public Integer getIdCuentaDeposito() {
        return idCuentaDeposito;
    }

    public void setIdCuentaDeposito(Integer idCuentaDeposito) {
        this.idCuentaDeposito = idCuentaDeposito;
    }

    public Integer getIdTarjetaCredito() {
        return idTarjetaCredito;
    }

    public void setIdTarjetaCredito(Integer idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }
    
    

    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMontoAnticipado() {
        return montoAnticipado;
    }

    public void setMontoAnticipado(BigDecimal montoAnticipado) {
        this.montoAnticipado = montoAnticipado;
    }

    public BigDecimal getMontoTotalAcreditado() {
        return montoTotalAcreditado;
    }

    public void setMontoTotalAcreditado(BigDecimal montoTotalAcreditado) {
        this.montoTotalAcreditado = montoTotalAcreditado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(String nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
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

    @XmlTransient
    public Collection<PagoAnticipadoTransaccion> getPagoAnticipadoTransaccionCollection() {
        return pagoAnticipadoTransaccionCollection;
    }

    public void setPagoAnticipadoTransaccionCollection(Collection<PagoAnticipadoTransaccion> pagoAnticipadoTransaccionCollection) {
        this.pagoAnticipadoTransaccionCollection = pagoAnticipadoTransaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagoAnticipado != null ? idPagoAnticipado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoAnticipado)) {
            return false;
        }
        PagoAnticipado other = (PagoAnticipado) object;
        if ((this.idPagoAnticipado == null && other.idPagoAnticipado != null) || (this.idPagoAnticipado != null && !this.idPagoAnticipado.equals(other.idPagoAnticipado))) {
            return false;
        }
        return true;
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

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdPagoAnticipado();
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.PagoAnticipado[ idPagoAnticipado=" + idPagoAnticipado + " ]";
    }

}
