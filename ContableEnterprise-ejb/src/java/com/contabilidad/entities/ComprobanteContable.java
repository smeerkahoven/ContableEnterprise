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

})
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
    private int idLibro;
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
    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    public static String getEstadoNombre(String estado) {
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

    public ComprobanteContable() {
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

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
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
