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
import javax.persistence.CascadeType;
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
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
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
@NamedStoredProcedureQuery(
        name = "NotaDebito.updateNotaDebito",
        procedureName = "updateNotaDebito",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
        }
)

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "IngresoCaja.updateMontosIngresoCajaFromFinalizar",
            procedureName = "updateMontosIngresoCajaFromFinalizar",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_ingreso")
            }
    )
    ,
    @NamedStoredProcedureQuery(
            name = "IngresoCaja.updateMontosIngresoCaja",
            procedureName = "updateMontosIngresoCaja",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_ingreso_caja")
            }
    )

})

@NamedQueries({
    @NamedQuery(name = "IngresoCaja.findAll", query = "SELECT i FROM IngresoCaja i WHERE i.idEmpresa=:idEmpresa ORDER By i.idIngresoCaja ")
    ,@NamedQuery(name = "IngresoCaja.findByIdCliente", query = "SELECT i FROM IngresoCaja i WHERE i.idCliente=:idCliente and i.idEmpresa=:idEmpresa ORDER by i.idIngresoCaja")
    ,@NamedQuery(name = "IngresoCaja.updateToPendiente",
            query = "UPDATE IngresoCaja i SET i.idCliente=:idCliente, "
            + "i.fechaEmision=:fechaEmision,"
            + "i.formaPago=:formaPago,"
            + "i.idBanco=:idBanco,"
            + "i.nroCheque=:nroCheque,"
            + "i.idTarjetaCredito=:idTarjetaCredito,"
            + "i.nroTarjeta=:nroTarjeta,"
            + "i.nroDeposito=:nroDeposito,"
            + "i.idCuentaDeposito=:idCuentaDeposito,"
            + "i.estado=:estado "
            + "WHERE i.idIngresoCaja=:idIngresoCaja")

})
public class IngresoCaja extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @Basic(optional = false)
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIngresoCaja", fetch = FetchType.EAGER)
    private Collection<IngresoTransaccion> ingresoTransaccionCollection;

    @Column(name = "estado")
    private String estado;

    public IngresoCaja() {
    }

    public IngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public IngresoCaja(Integer idIngresoCaja, String idUsuario, Date fechaEmision, Date fechaInsert, String moneda) {
        this.idIngresoCaja = idIngresoCaja;
        this.idUsuario = idUsuario;
        this.fechaEmision = fechaEmision;
        this.fechaInsert = fechaInsert;
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

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
