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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_comprobante_contable")
@TableGenerator(name = "comprobante_tg", initialValue = 0, allocationSize = 1)
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "ComprobanteContable.updateComprobanteContable",
        procedureName = "updateComprobanteContable",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_boleto")
        }
)
@NamedStoredProcedureQuery(
        name = "ComprobanteContable.updateMontosComprobanteContableFromAsientos",
        procedureName = "updateMontosComprobanteContableFromAsientos",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_libro")
        }
)
@NamedStoredProcedureQuery(
        name = "ComprobanteContable.updateComprobanteContableAnularTransaccion",
        procedureName = "updateComprobanteContableAnularTransaccion",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_libro")
        }
)

@NamedQueries({
    @NamedQuery(name = "ComprobanteContable.findAll", query = "SELECT c FROM ComprobanteContable c")
    , @NamedQuery(name = "ComprobanteContable.find", query = "SELECT c FROM ComprobanteContable c WHERE c.idLibro=:idLibro")
    , @NamedQuery(name = "ComprobanteContable.findAllComprobanteByNotaDebito", query = "SELECT c FROM ComprobanteContable c WHERE c.idNotaDebito=:idNotaDebito")
    , @NamedQuery(name = "ComprobanteContable.findAllComprobanteByNotaCredito", query = "SELECT c FROM ComprobanteContable c WHERE c.idNotaCredito=:idNotaCredito")
    , @NamedQuery(name = "ComprobanteContable.findAllComprobanteByIngresoCaja", query = "SELECT c FROM ComprobanteContable c WHERE c.idIngresoCaja=:idIngresoCaja")
    , @NamedQuery(name = "ComprobanteContable.findAllComprobanteByPagoAnticipado", query = "SELECT c FROM ComprobanteContable c WHERE c.idPagoAnticipado=:idPagoAnticipado")

})

@NamedNativeQuery(
        name = "Comprobante.getMayoresNac",
        query = "select ac.id_asiento idAsiento, ac.id_libro idLibro ,cc.fecha, ac.gestion, ac.fecha_movimiento fechaMovimiento ,ac. id_plan_cuenta idPlanCuenta,\n"
        + "coalesce(ac.monto_debe_nac,0)  montoDebe, coalesce(ac.monto_haber_nac,0)  montoHaber,\n"
        + "ac.id_boleto idBoleto, ac.id_cargo idCargo, ac.id_nota_transaccion idNotaTransaccion, ac.id_ingreso_caja_transaccion idIngresoCajaTransaccion,\n"
        + "ac.id_nota_credito_transaccion idNotaCreditoTransaccion, ac.id_pago_anticipado idPagoAnticipado,\n"
        + "ac.id_pago_anticipado_transaccion idPagoAnticipadoTransaccion, ac.id_devolucion idDevolucion, cc.id_cliente idCliente,\n"
        + "cc.id_numero_gestion idNumeroGestion, cc.gestion, cc.concepto, cc.factor_cambiario factorCambiario, cc.tipo\n"
        + ",(select descripcion from cnt_nota_debito_transaccion where id_nota_debito_transaccion = ac.id_nota_transaccion) ndtrxDescripcion \n"
        + ",(select descripcion from cnt_nota_credito_transaccion where id_nota_credito_transaccion = ac.id_nota_credito_transaccion) nctrxDescripcion \n"
        + ",(select descripcion from cnt_ingreso_transaccion where id_transaccion = ac.id_ingreso_caja_transaccion)ictrxDescripcion \n"
        + ",(select concepto from cnt_pago_anticipado where id_pago_anticipado = ac.id_pago_anticipado) paDescripcion \n"
        + ",(select descripcion from cnt_pago_anticipado_transaccion where id_pago_anticipado_transaccion=ac.id_pago_anticipado_transaccion)patrxDescripcion \n"
        + ",(select concepto from cnt_devolucion where id_devolucion = ac.id_devolucion) deDescripcion \n"
        + "from cnt_asiento_contable ac\n"
        + "inner join cnt_comprobante_contable cc on cc.id_libro = ac.id_libro\n"
        + "where cc.id_empresa=?1 and cc.fecha>=?2 and cc.fecha<=?3 and ac.id_plan_cuenta = ?4 and cc.estado='E'",
        resultSetMapping = "Mayores"
)

@NamedNativeQuery(
        name = "Comprobante.getMayoresExt",
        query = "select ac.id_asiento idAsiento, ac.id_libro idLibro ,cc.fecha, ac.gestion, ac.fecha_movimiento fechaMovimiento ,ac. id_plan_cuenta idPlanCuenta,\n"
        + "coalesce(ac.monto_debe_ext,0)  montoDebe, coalesce(ac.monto_haber_ext)  montoHaber,\n"
        + "ac.id_boleto idBoleto, ac.id_cargo idCargo, ac.id_nota_transaccion idNotaTransaccion, ac.id_ingreso_caja_transaccion idIngresoTransaccion,\n"
        + "ac.id_nota_credito_transaccion idNotaCreditoTransaccion, ac.id_pago_anticipado idPagoAnticipado, \n"
        + "ac.id_pago_anticipado_transaccion idPagoAnticipadoTransaccion, ac.id_devolucion idDevolucion, cc.id_cliente idCliente,\n"
        + "cc.id_numero_gestion idNumeroGestion, cc.gestion, cc.concepto, cc.factor_cambiario factorCambiario, cc.tipo\n"
        + ",(select descripcion from cnt_nota_debito_transaccion where id_nota_debito_transaccion = ac.id_nota_transaccion) ndtrxDescripcion \n"
        + ",(select descripcion from cnt_nota_credito_transaccion where id_nota_credito_transaccion = ac.id_nota_credito_transaccion) nctrxDescripcion \n"
        + ",(select descripcion from cnt_ingreso_transaccion where id_transaccion = ac.id_ingreso_caja_transaccion)ictrxDescripcion \n"
        + ",(select concepto from cnt_pago_anticipado where id_pago_anticipado = ac.id_pago_anticipado) paDescripcion \n"
        + ",(select descripcion from cnt_pago_anticipado_transaccion where id_pago_anticipado_transaccion=ac.id_pago_anticipado_transaccion)patrxDescripcion \n"
        + ",(select concepto from cnt_devolucion where id_devolucion = ac.id_devolucion) deDescripcion \n"
        + "from cnt_asiento_contable ac\n"
        + "inner join cnt_comprobante_contable cc on cc.id_libro = ac.id_libro\n"
        + "where cc.id_empresa=?1 and cc.fecha>=?2 and cc.fecha<=?3 and ac.id_plan_cuenta = ?4 and cc.estado='E'",
        resultSetMapping = "Mayores"
)

@NamedNativeQuery(
        name = "Comprobante.getAcumuladosNac",
        query = "select sum(ac.monto_debe_nac)montoAcumuladoDebe, sum(ac.monto_haber_nac)montoAcumuladoHaber\n"
        + "from cnt_asiento_contable ac\n"
        + "inner join cnt_comprobante_contable cc on cc.id_libro = ac.id_libro\n"
        + "where cc.id_empresa=?1 and cc.fecha<?2  and ac.id_plan_cuenta = ?3 and cc.estado='E'",
        resultSetMapping = "MayoresAcumulados"
)

@NamedNativeQuery(
        name = "Comprobante.getAcumuladosExt",
        query = "select sum(ac.monto_debe_ext)montoAcumuladoDebe, sum(ac.monto_haber_ext)montoAcumuladoHaber\n"
        + "from cnt_asiento_contable ac\n"
        + "inner join cnt_comprobante_contable cc on cc.id_libro = ac.id_libro\n"
        + "where cc.id_empresa=?1 and cc.fecha<?2  and ac.id_plan_cuenta = ?3 and cc.estado='E'",
        resultSetMapping = "MayoresAcumulados"
)

@SqlResultSetMapping(
        name = "MayoresAcumulados",
        classes = @ConstructorResult(
                targetClass = MayoresAcumulados.class,
                columns = {
                    @ColumnResult(name = "montoAcumuladoDebe", type = BigDecimal.class)
                    ,@ColumnResult(name = "montoAcumuladoHaber", type = BigDecimal.class)
                }
        )
)

@SqlResultSetMapping(
        name = "Mayores",
        classes = @ConstructorResult(
                targetClass = Mayores.class,
                columns = {
                    @ColumnResult(name = "idAsiento", type = Integer.class)
                    ,@ColumnResult(name = "idLibro", type = Integer.class)
                    ,@ColumnResult(name = "fecha", type = Date.class)
                    ,@ColumnResult(name = "fechaMovimiento", type = Date.class)
                    ,@ColumnResult(name = "idPlanCuenta", type = Integer.class)
                    ,@ColumnResult(name = "montoDebe", type = BigDecimal.class)
                    ,@ColumnResult(name = "montoHaber", type = BigDecimal.class)
                    ,@ColumnResult(name = "idBoleto", type = Integer.class)
                    ,@ColumnResult(name = "idCargo", type = Integer.class)
                    ,@ColumnResult(name = "idNotaTransaccion", type = Integer.class)
                    ,@ColumnResult(name = "idIngresoCajaTransaccion", type = Integer.class)
                    ,@ColumnResult(name = "idNotaCreditoTransaccion", type = Integer.class)
                    ,@ColumnResult(name = "idPagoAnticipado", type = Integer.class)
                    ,@ColumnResult(name = "idPagoAnticipadoTransaccion", type = Integer.class)
                    ,@ColumnResult(name = "idDevolucion", type = Integer.class)
                    ,@ColumnResult(name = "idCliente", type = Integer.class)
                    ,@ColumnResult(name = "idNumeroGestion", type = Integer.class)
                    ,@ColumnResult(name = "gestion", type = Integer.class)
                    ,@ColumnResult(name = "concepto", type = String.class)
                    ,@ColumnResult(name = "factorCambiario", type = BigDecimal.class)
                    ,@ColumnResult(name = "tipo", type = String.class)
                    ,@ColumnResult(name = "ndtrxDescripcion", type = String.class)
                    ,@ColumnResult(name = "nctrxDescripcion", type = String.class)
                    ,@ColumnResult(name = "ictrxDescripcion", type = String.class)
                    ,@ColumnResult(name = "paDescripcion", type = String.class)
                    ,@ColumnResult(name = "patrxDescripcion", type = String.class)
                    ,@ColumnResult(name = "deDescripcion", type = String.class)
                }
        )
)

public class ComprobanteContable extends Entidad {

    @Override
    public int getId() throws CRUDException {
        return this.idLibro;
    }

    public static class Tipo {

        public static final String ASIENTO_DIARIO = "AD";
        public static final String COMPROBANTE_INGRESO = "CI";
        public static final String COMPROBANTE_TRASPASO = "CT";
        public static final String COMPROBANTE_EGRESO = "CE";
        public static final String ASIENTO_AJUSTE = "AJ";

    }
    public static final String EMITIDO = "E";
    public static final String PENDIENTE = "P";
    public static final String ANULADO = "A";
    public static final String RECUPERADO = "R";

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id_libro")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idLibro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gestion")
    private Integer gestion;

    @Basic(optional = false)
    @Column(name = "id_numero_gestion")
    private int idNumeroGestion;
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;
    @Size(max = 16)
    @Column(name = "id_usuario_anulado")
    private String idUsuarioAnulado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Size(max = 128)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Size(max = 2)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Column(name = "id_nota_debito")
    private Integer idNotaDebito;
    @Column(name = "id_nota_credito")
    private Integer idNotaCredito;
    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;
    @Column(name = "id_pago_anticipado")
    private Integer idPagoAnticipado;
    @Column(name = "id_devolucion")
    private Integer idDevolucion;
    @Column(name = "total_debe_nac")
    private BigDecimal totalDebeNac;
    @Column(name = "total_haber_nac")
    private BigDecimal totalHaberNac;
    @Column(name = "total_debe_ext")
    private BigDecimal totalDebeExt;
    @Column(name = "total_haber_ext")
    private BigDecimal totalHaberExt;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Column(name = "con_errores")
    private String conErrores;

    @Column(name = "nombre", length = 64)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    public static String getEstadoNombre(String estado) {
        if (estado == null){
            return "-" ;
        }
        switch (estado) {
            case ComprobanteContable.ANULADO:
                return "Anulado";
            case ComprobanteContable.PENDIENTE:
                return "Pendiente";
            case ComprobanteContable.RECUPERADO:
                return "Recuperado";
            case ComprobanteContable.EMITIDO:
                return "Emitido";
            default:
                return "Sin Estado";
        }
    }

    @Transient
    private List<AsientoContable> transacciones = new LinkedList<AsientoContable>();

    public ComprobanteContable(Integer idLibro){
        this.idLibro = idLibro ;
    }
    
    public ComprobanteContable() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public Integer getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(Integer idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public Integer getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(Integer idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public List<AsientoContable> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<AsientoContable> transacciones) {
        this.transacciones = transacciones;
    }

    public ComprobanteContable(int idNumeroGestion, String idUsuarioCreador, Date fecha) {
        this.idNumeroGestion = idNumeroGestion;
        this.idUsuarioCreador = idUsuarioCreador;
        this.fecha = fecha;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public int getIdNumeroGestion() {
        return idNumeroGestion;
    }

    public void setIdNumeroGestion(int idNumeroGestion) {
        this.idNumeroGestion = idNumeroGestion;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public String getIdUsuarioAnulado() {
        return idUsuarioAnulado;
    }

    public void setIdUsuarioAnulado(String idUsuarioAnulado) {
        this.idUsuarioAnulado = idUsuarioAnulado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public BigDecimal getTotalDebeNac() {
        return totalDebeNac;
    }

    public void setTotalDebeNac(BigDecimal totalDebeNac) {
        this.totalDebeNac = totalDebeNac;
    }

    public BigDecimal getTotalHaberNac() {
        return totalHaberNac;
    }

    public void setTotalHaberNac(BigDecimal totalHaberNac) {
        this.totalHaberNac = totalHaberNac;
    }

    public BigDecimal getTotalDebeExt() {
        return totalDebeExt;
    }

    public void setTotalDebeExt(BigDecimal totalDebeExt) {
        this.totalDebeExt = totalDebeExt;
    }

    public BigDecimal getTotalHaberExt() {
        return totalHaberExt;
    }

    public void setTotalHaberExt(BigDecimal totalHaberExt) {
        this.totalHaberExt = totalHaberExt;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getConErrores() {
        return conErrores;
    }

    public void setConErrores(String conErrores) {
        this.conErrores = conErrores;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.idLibro;
        hash = 11 * hash + this.gestion;
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
        final ComprobanteContable other = (ComprobanteContable) obj;
        if (this.idLibro != other.idLibro) {
            return false;
        }
        if (this.gestion != other.gestion) {
            return false;
        }
        return true;
    }

}
