/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "cnt_archivo_boleto")
@NamedQueries({
    @NamedQuery(name = "ArchivoBoleto.findAll", query = "SELECT a FROM ArchivoBoleto a")
    , @NamedQuery(name = "ArchivoBoleto.findById", query = "SELECT a FROM ArchivoBoleto a WHERE a.idArchivoBoleto = :idArchivoBoleto")
    , @NamedQuery(name = "ArchivoBoleto.findByNombreArchivo", query = "SELECT a FROM ArchivoBoleto a WHERE a.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "ArchivoBoleto.findByEstadoCreado", query = "SELECT a FROM ArchivoBoleto a WHERE a.estado = :estado ORDER BY a.idArchivoBoleto")
    , @NamedQuery(name = "ArchivoBoleto.updateEstado", query = "UPDATE ArchivoBoleto a SET a.estado=:estado WHERE a.idArchivoBoleto=:idArchivoBoleto")
    , @NamedQuery(name = "ArchivoBoleto.findByTipoArchivo", query = "SELECT a FROM ArchivoBoleto a WHERE a.tipoArchivo = :tipoArchivo")})
public class ArchivoBoleto extends Entidad {

    public class TipoArchivo {

        public static final String AMADEUS = "A";
        public static final String SABRE = "S";
    }
    
    public class  Estado {
        public static final String CREADO="C" ;
        public static final String PROCESADO = "P";
        public static final String ERROR = "E";
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_archivo_boleto")
    private Integer idArchivoBoleto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_archivo")
    private String tipoArchivo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;

    public ArchivoBoleto() {
    }

    public ArchivoBoleto(Integer id) {
        this.idArchivoBoleto = id;
    }

    public ArchivoBoleto(Integer id, String nombreArchivo, String tipoArchivo, String contenido) {
        this.idArchivoBoleto = id;
        this.nombreArchivo = nombreArchivo;
        this.tipoArchivo = tipoArchivo;
        this.contenido = contenido;
    }

    public Integer getIdArchivoBoleto() {
        return idArchivoBoleto;
    }

    public void setIdArchivoBoleto(Integer idArchivoBoleto) {
        this.idArchivoBoleto = idArchivoBoleto;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivoBoleto != null ? idArchivoBoleto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoBoleto)) {
            return false;
        }
        ArchivoBoleto other = (ArchivoBoleto) object;
        if ((this.idArchivoBoleto == null && other.idArchivoBoleto != null) || (this.idArchivoBoleto != null && !this.idArchivoBoleto.equals(other.idArchivoBoleto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.configuracion.entities.ArchivoBoleto[ id=" + idArchivoBoleto + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdArchivoBoleto();
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
    
    

}
