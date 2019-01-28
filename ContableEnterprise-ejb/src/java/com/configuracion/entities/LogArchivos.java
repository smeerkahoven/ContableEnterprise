/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigInteger;
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
@Table(name = "cnt_log_archivos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogArchivos.findAll", query = "SELECT l FROM LogArchivos l")
    , @NamedQuery(name = "LogArchivos.findByIdLog", query = "SELECT l FROM LogArchivos l WHERE l.idLog = :idLog")
    , @NamedQuery(name = "LogArchivos.findByFecha", query = "SELECT l FROM LogArchivos l WHERE l.fecha = :fecha")
    , @NamedQuery(name = "LogArchivos.findByNombreArchivo", query = "SELECT l FROM LogArchivos l WHERE l.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "LogArchivos.findByTipoArchivo", query = "SELECT l FROM LogArchivos l WHERE l.tipoArchivo = :tipoArchivo")
    , @NamedQuery(name = "LogArchivos.findByMensaje", query = "SELECT l FROM LogArchivos l WHERE l.mensaje = :mensaje")
    , @NamedQuery(name = "LogArchivos.findByUsuario", query = "SELECT l FROM LogArchivos l WHERE l.usuario = :usuario")
    , @NamedQuery(name = "LogArchivos.findByNumeroBoleto", query = "SELECT l FROM LogArchivos l WHERE l.numeroBoleto = :numeroBoleto")})
public class LogArchivos extends Entidad {

    public static class Tipo {
        public static final String INFO = "INFO";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_log")
    private Integer idLog;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo_archivo")
    private String tipoArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "mensaje")
    private String mensaje;
    @Size(max = 16)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "numero_boleto")
    private BigInteger numeroBoleto;

    public LogArchivos() {
    }

    public LogArchivos(Integer idLog) {
        this.idLog = idLog;
    }

    public LogArchivos(Integer idLog, String nombreArchivo, String tipoArchivo, String mensaje) {
        this.idLog = idLog;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.mensaje = mensaje;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigInteger getNumeroBoleto() {
        return numeroBoleto;
    }

    public void setNumeroBoleto(BigInteger numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogArchivos)) {
            return false;
        }
        LogArchivos other = (LogArchivos) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.configuracion.entities.LogArchivos[ idLog=" + idLog + " ]";
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int getId() throws CRUDException {
        return this.idLog;
    }

}
