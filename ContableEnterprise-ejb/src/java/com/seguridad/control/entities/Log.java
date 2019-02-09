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
    public static final String NOTA_DEBITO_PENDIENTE = "Establecio la Nota de Debito <id> como PENDIENTE" ;
    public static final String NOTA_DEBITO_FINALIZAR = "Establecio la Nota de Debito <id> como FINALIZAR" ;
    public static final String NOTA_DEBITO_ANULAR = "Establecio la Nota de Debito <id> como ANULADA. Sus Transacciones, Ingresos a Caja, Comprobantes Contables" ;
    public static final String NOTA_DEBITO_NUEVA_TRANSACCION_INICIO = "Inicio creacion de Transacciones Nota Debito <id>" ;
    public static final String NOTA_DEBITO_NUEVA_TRANSACCION_FIN = "Fin creacion de Transacciones Nota Debito <id>" ;
    public static final String NOTA_DEBITO_CARGO_GUARDAR = "Se Guardo el Cargo Nro <cargo> para la nota de Debito <nota>" ;

    public static final String INGRESO_CAJA_NUEVO = "Creo el Ingreso de Caja <id> " ;
    public static final String INGRESO_CAJA_PENDIENTE = "Establecio el Ingreso de Caja <id> como PENDIENTE" ;
    public static final String INGRESO_CAJA_FINALIZAR = "Establecio el Ingreso de Caja <id> como FINALIZAR" ;
    public static final String INGRESO_CAJA_ANULAR = "Establecio el Ingreso de Caja <id> como ANULADA. Si el Ingreso estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String INGRESO_CAJA_ANULAR_TRANSACCION = "Establecio la Transaccion <id> como ANULADA. Si el Ingreso estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String INGRESO_CAJA_NUEVA_TRANSACION = "Insercion de la transaccion <trx> para el Ingreso de Caja <id>." ;
    public static final String INGRESO_CAJA_UPDATE_TRANSACCION = "Se ingreso el monto <monto> a la transaccion <trx> del ingreso de caja <id>." ;
    
    public static final String NOTA_CREDITO_NUEVO = "Creo la Nota de Crédito<id> " ;
    public static final String NOTA_CREDITO_PENDIENTE = "Establecio la Nota de Crédito <id> como PENDIENTE" ;
    public static final String NOTA_CREDITO_FINALIZAR = "Establecio la Nota de Crédito <id> como FINALIZAR" ;
    public static final String NOTA_CREDITO_ANULAR = "Establecio la Nota de Crédito <id> como ANULADA. Si Nota de Crédito estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String NOTA_CREDITO_ANULAR_TRANSACCION = "Establecio la Transaccion <id> como ANULADA. Si el Nota de Crédito estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String NOTA_CREDITO_NUEVA_TRANSACION = "Insercion de la transaccion <trx> para Nota de Crédito <id>." ;
    public static final String NOTA_CREDITO_UPDATE_TRANSACCION = "Se ingreso el monto <monto> a la transaccion <trx> de Nota de Crédito <id>." ;

    public static final String NOTA_DEBITO_CARGO_EDITAR = "Se edito el Cargo Nro <cargo> para la nota de Debito <nota>" ;
    public static final String NOTA_DEBITO_CARGO_ANULAR = "Se anulo el Cargo Nro<cargo> para la nota de Debito <nota>" ;
    public static final String BOLETO_ASOCIAR_AUTOMATICO = "Asocio el Boleto <boleto> con la Nota <id>" ;
    public static final String BOLETO_ANULAR = "Establecio <boleto> como ANULADO" ;
    public static final String BOLETO_ELIMINAR = "Establecio <boleto> como ELIMINADO" ;
    public static final String BOLETO_SAVE = "Creacion del Boleto <boleto>" ;
    
    public static final String COMPROBANTE_ANULAR = "Anulando comprobante <id>.";
    public static final String COMPROBANTE_SAVE = "Creando Comprobante <id>.";
    public static final String COMPROBANTE_EDIT_TRANSACCION = "Editando Transaccion <tr> del comprobante <id>.";
    public static final String COMPROBANTE_CORRECT_TRANSACCION = "Corrigiendo Transaccion <tr> del comprobante <id>.";
    public static final String COMPROBANTE_UPDATE = "Actualizacion del comprobante <id>.";
    public static final String COMPROBANTE_ADD_CORRECTION = "Adicionando correccion para el libro <id> con el monto <monto>.";
    public static final String COMPROBANTE_ADD_TRANSACCION = "Adicionando transaccion para el libro <id> con el monto <monto>";
    
    
    public static final String PAGO_ANTICIPADO_NUEVO = "Creacion Pago Anticipado <id> " ;
    public static final String PAGO_ANTICIPADO_PENDIENTE = "Establecio el Pago Anticipado <id> como PENDIENTE" ;
    public static final String PAGO_ANTICIPADO_FINALIZAR = "Establecio el Pago Anticipado <id> como FINALIZAR" ;
    public static final String PAGO_ANTICIPADO_ANULAR = "Establecio el Pago Anticipado <id> como ANULADA. Si el Pago Anticipado estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String PAGO_ANTICIPADO_ANULAR_TRANSACCION = "Establecio la Transaccion <id> como ANULADA. Si el el Pago Anticipado estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String PAGO_ANTICIPADO_DEVOLVER_TRANSACCION = "Establecio la Transaccion <id> como DEVUELTA. Si el el Pago Anticipado estaba en Contabilidad, los montos fueron revertidos, asi como sus Comprobantes Contables." ;
    public static final String PAGO_ANTICIPADO_NUEVA_TRANSACION = "Insercion de la transaccion <trx> para el Pago Anticipado <id>." ;
    public static final String PAGO_ANTICIPADO_DEVOLUCION = "Devolucion de <monto> para el Pago Anticipado <id>."; ;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
