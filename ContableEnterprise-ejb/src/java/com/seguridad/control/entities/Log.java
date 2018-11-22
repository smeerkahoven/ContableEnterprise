/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cheyo
 */
@Entity
@Table(name = "tb_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Log.findAll", query = "SELECT l FROM Log l")})
public class Log implements Serializable {
    
    public static final String NOTA_DEBITO_NUEVO = "Creo la Nota de Debito <id> " ;
    public static final String NOTA_DEBITO_PENDIENTE = "Establecio <id> como PENDIENTE" ;
    public static final String NOTA_DEBITO_FINALIZAR = "Establecio <id> como FINALIZAR" ;
    public static final String NOTA_DEBITO_ANULAR = "Establecio <id> como ANULAR" ;
    public static final String NOTA_DEBITO_NUEVA_TRANSACCION_INICIO = "Inicio creacion de Transacciones Nota Debito <id>" ;
    public static final String NOTA_DEBITO_NUEVA_TRANSACCION_FIN = "Fin creacion de Transacciones Nota Debito <id>" ;
    public static final String NOTA_DEBITO_CARGO_GUARDAR = "Se Guardo el Cargo Nro <cargo> para la nota de Debito <nota>" ;
    public static final String NOTA_DEBITO_CARGO_EDITAR = "Se edito el Cargo Nro <cargo> para la nota de Debito <nota>" ;
    public static final String NOTA_DEBITO_CARGO_ANULAR = "Se anulo el Cargo Nro<cargo> para la nota de Debito <nota>" ;
    public static final String BOLETO_ASOCIAR_AUTOMATICO = "Asocio el Boleto <boleto> con la Nota <id>" ;
    public static final String BOLETO_ANULAR = "Establecio <boleto> como ANULADO" ;
    public static final String BOLETO_SAVE = "Creacion del Boleto <boleto>" ;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;
    @Column(name = "fecha_log")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLog;
    @Column(name = "accion", length = 16)
    private String accion;
    @Column(name = "usuario", length = 16)
    private String usuario;
    @Column(name = "formulario", length = 64)
    private String formulario;
    @Column(name = "ip", length = 32)
    private String ip;
    @Column(name = "comentario", length = 256)
    private String comentario;

    public Log() {
    }

    public Log(Integer idLog) {
        this.idLog = idLog;
    }

    public Log(Integer idLog, Date fechaLog) {
        this.idLog = idLog;
        this.fechaLog = fechaLog;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Date getFechaLog() {
        return fechaLog;
    }

    public void setFechaLog(Date fechaLog) {
        this.fechaLog = fechaLog;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        return !((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog)));
    }

    @Override
    public String toString() {
        return "com.seguridad.control.entities.Log[ idLog=" + idLog + " ]";
    }

}
