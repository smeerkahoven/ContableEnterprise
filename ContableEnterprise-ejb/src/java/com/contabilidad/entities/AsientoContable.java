/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.agencia.entities.Boleto;
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
        public static final String NOTA_DEBITO_TRANSACCION = "N";
        public static final String NOTA_CREDITO_TRANSACCION = "E";
        public static final String INGRESO_CAJA_TRANSACCION = "I";
        public static final String PAGO_ANTICIPADO = "G";
        public static final String PAGO_ANTICIPADO_TRANSACCION = "O";
        public static final String DEVOLUCION = "D";
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_asiento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idAsiento;
    @Basic(optional = false)
    @NotNull
    
    @Column(name = "gestion", updatable = false)
    private Integer gestion;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_movimiento", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMovimiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_libro")
    private ComprobanteContable idLibro;

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

    // Relaciones con los Objetos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_boleto")
    private Boleto idBoleto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_transaccion")
    private NotaDebitoTransaccion idNotaTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nota_credito_transaccion")
    private NotaCreditoTransaccion idNotaCreditoTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ingreso_caja_transaccion")
    private IngresoTransaccion idIngresoCajaTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pago_anticipado")
    private PagoAnticipado idPagoAnticipado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pago_anticipado_transaccion")
    private PagoAnticipadoTransaccion idPagoAnticipadoTransaccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_devolucion")
    private Devolucion idDevolucion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cargo")
    private CargoBoleto idCargo;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CargoBoleto getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(CargoBoleto idCargo) {
        this.idCargo = idCargo;
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

    public ComprobanteContable getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(ComprobanteContable idLibro) {
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

    public Boleto getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Boleto idBoleto) {
        this.idBoleto = idBoleto;
    }

    public NotaDebitoTransaccion getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(NotaDebitoTransaccion idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public NotaCreditoTransaccion getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(NotaCreditoTransaccion idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public IngresoTransaccion getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(IngresoTransaccion idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
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

    public Integer getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Integer idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    @Override
    public int getId() throws CRUDException {
        return this.idAsiento;
    }

    public PagoAnticipado getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(PagoAnticipado idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public PagoAnticipadoTransaccion getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }

    public void setIdPagoAnticipadoTransaccion(PagoAnticipadoTransaccion idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public Devolucion getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Devolucion idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.AsientoContable[ asientoContablePK=" + idAsiento + " ]";
    }

}
