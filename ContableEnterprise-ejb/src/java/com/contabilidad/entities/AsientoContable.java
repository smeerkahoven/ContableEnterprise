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
@Table(name = "cnt_asiento_contable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsientoContable.findAll", query = "SELECT a FROM AsientoContable a")
    ,@NamedQuery(name = "AsientoContable.find", query = "SELECT a FROM AsientoContable a WHERE a.idLibro=:idLibro")
    ,@NamedQuery(name = "AsientoContable.findAllByIdNotaDebitoTransaccion", query = "SELECT a FROM AsientoContable a WHERE a.idNotaTransaccion = :idNotaTransaccion")
    ,@NamedQuery(name = "AsientoContable.findAllByIdIngresoTransaccion", query = "SELECT a FROM AsientoContable a WHERE a.idIngresoCajaTransaccion= :idIngresoCajaTransaccion")
    ,@NamedQuery(name = "AsientoContable.findAllByNotaCreditoTransaccion", query = "SELECT a FROM AsientoContable a WHERE a.idNotaCreditoTransaccion= :idNotaCreditoTransaccion")
    ,@NamedQuery(name = "AsientoContable.findAllByPagoAnticipadoTransaccion", query = "SELECT a FROM AsientoContable a WHERE a.idPagoAnticipadoTransaccion = :idPagoAnticipadoTransaccion")
    ,@NamedQuery(name = "AsientoContable.updateEstadoFromBoleto", query = "UPDATE AsientoContable a SET a.estado=:estado WHERE a.idBoleto= :idBoleto")

}
)
public class AsientoContable extends Entidad {

    public static class Tipo {

        public static final String BOLETO = "B";
        public static final String CARGO = "C";
        //---------------------------------------------
        public static final String PAQUETE = "P";
        public static final String SEGURO = "S";
        public static final String HOTEL = "H";
        public static final String ALQUILER_AUTO = "A";
        public static final String RESERVA = "R";
        //---------------------------------------------
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_asiento", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAsiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion", updatable = false)
    private int gestion;
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

    @Column(name = "id_boleto")
    private Integer idBoleto;
    @Column(name = "id_nota_transaccion")
    private Integer idNotaTransaccion;
    @Column(name = "id_nota_credito_transaccion")
    private Integer idNotaCreditoTransaccion;
    @Column(name = "id_ingreso_caja_transaccion")
    private Integer idIngresoCajaTransaccion;
    @Column(name = "id_pago_anticipado")
    private Integer idPagoAnticipado;
    @Column(name = "id_pago_anticipado_transaccion")
    private Integer idPagoAnticipadoTransaccion;
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "id_usuario_anular")
    private String idUsuarioAnular;

    public AsientoContable() {
    }

    public String getIdUsuarioAnular() {
        return idUsuarioAnular;
    }

    public void setIdUsuarioAnular(String idUsuarioAnular) {
        this.idUsuarioAnular = idUsuarioAnular;
    }

    public Integer getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }

    public void setIdPagoAnticipadoTransaccion(Integer idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public Integer getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(Integer idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(Integer idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public Integer getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(Integer idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
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

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.idAsiento;
        hash = 73 * hash + this.gestion;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AsientoContable other = (AsientoContable) obj;
        if (this.idAsiento != other.idAsiento) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    @Override
    public int getId() throws CRUDException {
        return this.idAsiento;
    }

    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.AsientoContable[ asientoContablePK=" + idAsiento + " ]";
    }

}
