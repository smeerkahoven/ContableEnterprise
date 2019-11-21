/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
import com.cobranzas.dto.KardexClienteDto;
import com.cobranzas.dto.ReporteEstadoClienteDto;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_nota_debito")
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "NotaDebito.asociarBoletoNotaDebito",
        procedureName = "asociarBoletoNotaDebito",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_debito")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_cliente")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_counter")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = BigDecimal.class, name = "in_factor")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "in_usuario_creador")
            ,@StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class, name = "out_id_transacion")
        }
)

@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "NotaDebito.updateBoletoNotaDebito",
            procedureName = "updateBoletoNotaDebito",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_transaccion")
            }
    )
    ,
    @NamedStoredProcedureQuery(
            name = "NotaDebito.updateMontosNotaDebitoEnPendiente",
            procedureName = "updateMontosNotaDebitoEnPendiente",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_debito")
            }
    )

    ,
    
    @NamedStoredProcedureQuery(
            name = "NotaDebito.updateMontosNotaDebitoEmitidos",
            procedureName = "updateMontosNotaDebitoEmitidos",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_debito")
            }
    )
    ,
    
    
@NamedStoredProcedureQuery(
            name = "NotaDebito.updateNotaDebito",
            procedureName = "updateNotaDebito",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
            }
    )

    ,

@NamedStoredProcedureQuery(
            name = "NotaDebito.updateMontosAdeudadosNotaDebito",
            procedureName = "updateMontosAdeudadosNotaDebito",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_debito")
            }
    )
    ,


@NamedStoredProcedureQuery(
            name = "NotaDebito.updateNotaDebitoAnularTransaccion",
            procedureName = "updateNotaDebitoAnularTransaccion",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_debito")
            }
    )
    ,

@NamedStoredProcedureQuery(
            name = "NotaDebito.generarKardexCliente",
            procedureName = "generarKardexCliente",
            resultSetMappings = "KardexClienteDto",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_cliente")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_selected")
            }
    )
    ,
@NamedStoredProcedureQuery(
            name = "NotaDebito.historicoCliente",
            procedureName = "historicoCliente",
            resultSetMappings = "KardexClienteDto",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_cliente")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_start_date")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_end_date")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_selected")
            }
    )
    ,

@NamedStoredProcedureQuery(
            name = "NotaDebito.generarReporteClienteEstados",
            procedureName = "generarReporteClienteEstados",
            resultSetMappings = "ReporteEstadoClienteDto",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_empresa")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_start_date")
                ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_end_date")
            }
    )

})

@SqlResultSetMapping(
        name = "KardexClienteDto",
        classes = @ConstructorResult(
                targetClass = KardexClienteDto.class,
                columns = {
                    @ColumnResult(name = "v_id_documento", type = Integer.class)
                    ,@ColumnResult(name = "v_glosa", type = String.class)
                    ,@ColumnResult(name = "v_fecha", type = String.class)
                    ,@ColumnResult(name = "v_vencimiento", type = String.class)
                    ,@ColumnResult(name = "v_forma_pago", type = String.class)
                    ,@ColumnResult(name = "v_monto_debe_nac", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_monto_haber_nac", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_saldo_nac", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_monto_debe_ext", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_monto_haber_ext", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_saldo_ext", type = BigDecimal.class)
                    ,@ColumnResult(name = "v_tipo_documento", type = String.class)
                    ,@ColumnResult(name = "v_estado", type = String.class)
                    ,@ColumnResult(name = "v_row", type = String.class)
                    ,@ColumnResult(name = "v_selected", type = String.class)
                }
        )
)
@SqlResultSetMappings(
        @SqlResultSetMapping(
                name = "ReporteEstadoClienteDto",
                classes = @ConstructorResult(
                        targetClass = ReporteEstadoClienteDto.class,
                        columns = {
                            @ColumnResult(name = "v_id_documento", type = Integer.class)
                            ,@ColumnResult(name = "v_glosa", type = String.class)
                            ,@ColumnResult(name = "v_fecha", type = String.class)
                            ,@ColumnResult(name = "v_vencimiento", type = String.class)
                            ,@ColumnResult(name = "v_forma_pago", type = String.class)
                            ,@ColumnResult(name = "v_monto_debe_nac", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_monto_haber_nac", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_saldo_nac", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_monto_debe_ext", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_monto_haber_ext", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_saldo_ext", type = BigDecimal.class)
                            ,@ColumnResult(name = "v_tipo_documento", type = String.class)
                            ,@ColumnResult(name = "v_estado", type = String.class)
                            ,@ColumnResult(name = "v_row", type = String.class)
                            ,@ColumnResult(name = "v_id_cliente", type = Integer.class)
                            ,@ColumnResult(name = "v_nombre_cliente", type = String.class)
                            ,@ColumnResult(name = "v_telefono_fijo", type = String.class)
                            ,@ColumnResult(name = "v_telefono_celular", type = String.class)
                            ,@ColumnResult(name = "v_email", type = String.class)
                        }
                )
        )
)

@NamedQueries({
    @NamedQuery(name = "NotaDebito.findAll", query = "SELECT n FROM NotaDebito n")
    ,@NamedQuery(name = "NotaDebito.getAllNotaDebitoWhereVencimientoIsYesterday", query = "SELECT n FROM NotaDebito n WHERE n.formaPago='C' AND n.estado='E' AND n.creditoVencimiento<= :yesterday AND (n.montoAdeudadoBs > 0 OR n.montoAdeudadoUsd >0) ")
    ,@NamedQuery(name = "NotaDebito.getAllNotaDebitoEnMora", query = "SELECT n FROM NotaDebito n WHERE n.estado = 'M'")
    ,@NamedQuery(name = "NotaDebito.getNotaDebitoEnMora", query = "SELECT n FROM NotaDebito n WHERE n.estado = 'M' and n.idEmpresa=:idEmpresa")
    ,@NamedQuery(name = "NotaDebito.getNotaDebitoEnPendiente", query = "SELECT n FROM NotaDebito n WHERE n.estado = 'P' and n.idEmpresa=:idEmpresa")
})
public class NotaDebito extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_nota_debito")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idNotaDebito;
    @Basic(optional = false)
//    @NotNull
    @Column(name = "gestion", nullable = false)
    private Integer gestion;
    @Basic(optional = false)
//    @NotNull
    @Column(name = "id_empresa", nullable = false)
    private Integer idEmpresa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_counter")
    private Promotor idCounter;
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
//    @NotNull
    @Column(name = "fecha_insert", nullable = false)
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
    private Integer creditoDias;
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

    public NotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    /*
    public String getFechaEmision() {
        String date = DateContable.getDateFormat(fechaEmision, DateContable.LATIN_AMERICA_FORMAT);
        
        return date;
    }*/
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

    public Integer getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(Integer creditoDias) {
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

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
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
