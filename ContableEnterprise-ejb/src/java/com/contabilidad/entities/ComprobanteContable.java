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
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
@NamedQueries({
    @NamedQuery(name = "ComprobanteContable.findAll", query = "SELECT c FROM ComprobanteContable c")
    ,
      @NamedQuery(name = "ComprobanteContable.find", query = "SELECT c FROM ComprobanteContable c WHERE c.comprobanteContablePK.idLibro=:idLibro")

})
public class ComprobanteContable extends Entidad {

    public static class Tipo {

        public static final String ASIENTO_DIARIO = "AD";
        public static final String COMPROBANTE_INGRESO = "CI";
        public static final String COMPROBANTE_TRASPASO = "CT";
        public static final String COMPROBANTE_EGRESO = "CE";
        public static final String ASIENTO_AJUSTE = "AJ";

    }
    public static final String APROBADO = "A";
    public static final String PENDIENTE = "P";
    public static final String ANULADO = "N";
    public static final String RECUPERADO = "R";

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComprobanteContablePK comprobanteContablePK;
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
    @Column(name = "nombre")
    private String nombre;
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
    @Column(name = "totalDebeNac")
    private BigDecimal totalDebeNac;
    @Column(name = "totalHaberNac")
    private BigDecimal totalHaberNac;
    @Column(name = "totalDebeExt")
    private BigDecimal totalDebeExt;
    @Column(name = "totalHaberExt")
    private BigDecimal totalHaberExt;
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Column(name = "con_errores")
    private String conErrores;

    public static String getEstadoNombre(String estado) {
        switch (estado) {
            case ComprobanteContable.ANULADO:
                return "Anulado";
            case ComprobanteContable.PENDIENTE:
                return "Pendiente";
            case ComprobanteContable.RECUPERADO:
                return "Recuperado";

            case ComprobanteContable.APROBADO:
                return "Aprobado";
            default:
                return "Sin Estado";
        }
    }

    @Transient
    private List<AsientoContable> transacciones = new LinkedList<AsientoContable>();

    public ComprobanteContable() {
    }

    public List<AsientoContable> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<AsientoContable> transacciones) {
        this.transacciones = transacciones;
    }

    public ComprobanteContable(ComprobanteContablePK comprobanteContablePK) {
        this.comprobanteContablePK = comprobanteContablePK;
    }

    public ComprobanteContable(ComprobanteContablePK comprobanteContablePK, int idNumeroGestion, String idUsuarioCreador, Date fecha) {
        this.comprobanteContablePK = comprobanteContablePK;
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

    public ComprobanteContable(int idLibro, int gestion) {
        this.comprobanteContablePK = new ComprobanteContablePK(idLibro, gestion);
    }

    public ComprobanteContablePK getComprobanteContablePK() {
        return comprobanteContablePK;
    }

    public void setComprobanteContablePK(ComprobanteContablePK comprobanteContablePK) {
        this.comprobanteContablePK = comprobanteContablePK;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comprobanteContablePK != null ? comprobanteContablePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprobanteContable)) {
            return false;
        }
        ComprobanteContable other = (ComprobanteContable) object;
        if ((this.comprobanteContablePK == null && other.comprobanteContablePK != null) || (this.comprobanteContablePK != null && !this.comprobanteContablePK.equals(other.comprobanteContablePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.ComprobanteContable[ comprobanteContablePK=" + comprobanteContablePK + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getComprobanteContablePK().getIdLibro();
    }

}
