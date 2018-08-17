/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;
 
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cnt_comprobante_contable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprobanteContable.findAll", query = "SELECT c FROM ComprobanteContable c")})
public class ComprobanteContable extends Entidad {
    
    public static final String INICIAL = "I" ;
    public static final String ANULADO ="A" ;
    public static final String PENDIENTE = "P" ;
    public static final String RECUPERADO="R";

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComprobanteContablePK comprobanteContablePK;
    @Column(name = "id_usuario_creador", length = 16)
    private String idUsuarioCreador;
    @Column(name = "id_usuario_anulado", length = 16)
    private String idUsuarioAnulado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "nombre", length = 64)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "estado" , length = 2)
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Basic(optional = false)
    @Column(name = "tipo", length = 2)
    private String tipo;
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private int idEmpresa;
    @Column(name = "concepto", length = 128)
    private String concepto;

    public ComprobanteContable() {
    }

    public ComprobanteContable(ComprobanteContablePK comprobanteContablePK) {
        this.comprobanteContablePK = comprobanteContablePK;
    }

    public ComprobanteContable(ComprobanteContablePK comprobanteContablePK, Date fecha, String estado, BigDecimal factorCambiario, String tipo, int idEmpresa) {
        this.comprobanteContablePK = comprobanteContablePK;
        this.fecha = fecha;
        this.estado = estado;
        this.factorCambiario = factorCambiario;
        this.tipo = tipo;
        this.idEmpresa = idEmpresa;
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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
        return this.comprobanteContablePK.getIdLibro();
    }
    
}
