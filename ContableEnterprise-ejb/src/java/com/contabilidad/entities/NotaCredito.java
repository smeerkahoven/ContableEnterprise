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
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_nota_credito")
@XmlRootElement
@NamedStoredProcedureQuery(
        name = "NotaCredito.updateMontosNotaCredito",
        procedureName = "updateMontosNotaCredito",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_nota_credito")
        }
)

@NamedQueries({
    @NamedQuery(name = "NotaCredito.findAll", query = "SELECT n FROM NotaCredito n")
    , @NamedQuery(name = "NotaCredito.findByIdNotaCredito", query = "SELECT n FROM NotaCredito n WHERE n.idNotaCredito = :idNotaCredito")
    , @NamedQuery(name = "NotaCredito.findByIdUsuario", query = "SELECT n FROM NotaCredito n WHERE n.idUsuario = :idUsuario")
    , @NamedQuery(name = "NotaCredito.findByFechaEmision", query = "SELECT n FROM NotaCredito n WHERE n.fechaEmision = :fechaEmision")
    , @NamedQuery(name = "NotaCredito.findByFechaInsert", query = "SELECT n FROM NotaCredito n WHERE n.fechaInsert = :fechaInsert")
    , @NamedQuery(name = "NotaCredito.findByMontoAbonadoBs", query = "SELECT n FROM NotaCredito n WHERE n.montoAbonadoBs = :montoAbonadoBs")
    , @NamedQuery(name = "NotaCredito.findByMontoAbonadoUsd", query = "SELECT n FROM NotaCredito n WHERE n.montoAbonadoUsd = :montoAbonadoUsd")
    , @NamedQuery(name = "NotaCredito.findByFactorCambiario", query = "SELECT n FROM NotaCredito n WHERE n.factorCambiario = :factorCambiario")
    , @NamedQuery(name = "NotaCredito.findByConcepto", query = "SELECT n FROM NotaCredito n WHERE n.concepto = :concepto")
    , @NamedQuery(name = "NotaCredito.findByEstado", query = "SELECT n FROM NotaCredito n WHERE n.estado = :estado")
    , @NamedQuery(name="NotaCredito.updateToPendiente", 
            query="UPDATE NotaCredito i SET i.idCliente=:idCliente, "
                    + "i.fechaEmision=:fechaEmision,"
                    + "i.estado=:estado, "
                    + "i.concepto=:concepto "
                    + "WHERE i.idNotaCredito=:idNotaCredito")
})
public class NotaCredito extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_nota_credito")
    private Integer idNotaCredito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_abonado_bs")
    private BigDecimal montoAbonadoBs;
    @Column(name = "monto_abonado_usd")
    private BigDecimal montoAbonadoUsd;
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Size(max = 128)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    
    @OneToMany(mappedBy = "idNotaCredito")
    private List<NotaCreditoTransaccion> notaCreditoTransaccionList;

    public NotaCredito() {
    }

    public NotaCredito(Integer idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public NotaCredito(Integer idNotaCredito, String idUsuario, Date fechaInsert) {
        this.idNotaCredito = idNotaCredito;
        this.idUsuario = idUsuario;
        this.fechaInsert = fechaInsert;
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

    public Integer getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(Integer idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public BigDecimal getMontoAbonadoBs() {
        return montoAbonadoBs;
    }

    public void setMontoAbonadoBs(BigDecimal montoAbonadoBs) {
        this.montoAbonadoBs = montoAbonadoBs;
    }

    public BigDecimal getMontoAbonadoUsd() {
        return montoAbonadoUsd;
    }

    public void setMontoAbonadoUsd(BigDecimal montoAbonadoUsd) {
        this.montoAbonadoUsd = montoAbonadoUsd;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
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

    @XmlTransient
    public List<NotaCreditoTransaccion> getNotaCreditoTransaccionList() {
        return notaCreditoTransaccionList;
    }

    public void setNotaCreditoTransaccionList(List<NotaCreditoTransaccion> notaCreditoTransaccionList) {
        this.notaCreditoTransaccionList = notaCreditoTransaccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotaCredito != null ? idNotaCredito.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaCredito)) {
            return false;
        }
        NotaCredito other = (NotaCredito) object;
        if ((this.idNotaCredito == null && other.idNotaCredito != null) || (this.idNotaCredito != null && !this.idNotaCredito.equals(other.idNotaCredito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.NotaCredito[ idNotaCredito=" + idNotaCredito + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idNotaCredito;
    }

}
