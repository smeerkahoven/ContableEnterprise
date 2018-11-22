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
@Table(name = "cnt_cargo_boleto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargoBoleto.findAll", query = "SELECT c FROM CargoBoleto c")
    , @NamedQuery(name = "CargoBoleto.findByUsuarioCreador", query = "SELECT c FROM CargoBoleto c WHERE c.usuarioCreador = :usuarioCreador")
})
public class CargoBoleto extends Entidad {

    public static class Tipo {

        public static final String ALQUILER = "A";
        public static final String PAQUETE = "P";
        public static final String SEGURO = "S";
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta_mayorista")
    private PlanCuentas idCuentaMayorista;
    @Column(name = "comision_mayorista")
    private BigDecimal comisionMayorista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta_agencia")
    private PlanCuentas idCuentaAgencia;
    @Column(name = "comision_agencia")
    private BigDecimal comisionAgencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta_promotor")
    private PlanCuentas idCuentaPromotor;
    @Column(name = "comision_promotor")
    private BigDecimal comisionPromotor;

    @Size(max = 256)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "usuario_creador")
    private String usuarioCreador;

    @Column(name = "id_nota_debito")
    private Integer idNotaDebito;

    @Column(name = "id_nota_debito_transaccion")
    private Integer idNotaDebitoTransaccion;

    @Column(name = "id_libro")
    private Integer idLibro;

    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;

    @Column(name = "id_ingreso_caja_transaccion")
    private Integer idIngresoCajaTransaccion;

    public CargoBoleto() {
    }

    public CargoBoleto(Integer idOtrosCargos) {
        this.idCargo = idOtrosCargos;
    }

    public CargoBoleto(Integer idOtrosCargos, int idEmpresa, Date fechaInsert, String usuarioCreador) {
        this.idCargo = idOtrosCargos;
        this.idEmpresa = idEmpresa;
        this.fechaInsert = fechaInsert;
        this.usuarioCreador = usuarioCreador;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Integer getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(Integer idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getComisionMayorista() {
        return comisionMayorista;
    }

    public void setComisionMayorista(BigDecimal comisionMayorista) {
        this.comisionMayorista = comisionMayorista;
    }

    public BigDecimal getComisionAgencia() {
        return comisionAgencia;
    }

    public void setComisionAgencia(BigDecimal comisionAgencia) {
        this.comisionAgencia = comisionAgencia;
    }

    public BigDecimal getComisionPromotor() {
        return comisionPromotor;
    }

    public void setComisionPromotor(BigDecimal comisionPromotor) {
        this.comisionPromotor = comisionPromotor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public PlanCuentas getIdCuentaMayorista() {
        return idCuentaMayorista;
    }

    public void setIdCuentaMayorista(PlanCuentas idCuentaMayorista) {
        this.idCuentaMayorista = idCuentaMayorista;
    }

    public PlanCuentas getIdCuentaAgencia() {
        return idCuentaAgencia;
    }

    public void setIdCuentaAgencia(PlanCuentas idCuentaAgencia) {
        this.idCuentaAgencia = idCuentaAgencia;
    }

    public PlanCuentas getIdCuentaPromotor() {
        return idCuentaPromotor;
    }

    public void setIdCuentaPromotor(PlanCuentas idCuentaPromotor) {
        this.idCuentaPromotor = idCuentaPromotor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CargoBoleto)) {
            return false;
        }
        CargoBoleto other = (CargoBoleto) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public int getId() throws CRUDException {
        return this.idCargo;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.CargoBoleto[ idCargo=" + idCargo + " ]";
    }

}
