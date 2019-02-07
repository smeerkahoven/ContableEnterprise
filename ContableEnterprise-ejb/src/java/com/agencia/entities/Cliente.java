/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_cliente", updatable = false)
    private Integer idCliente;

    @Column(name = "id_cliente_grupo")
    private Integer idClienteGrupo;

    @Column(name = "id_promotor")
    private Integer idPromotor;

    @Column(name = "id_cliente_corporativo")
    private Integer idClienteCorporativo;

    @Size(max = 128)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 12)
    @Column(name = "ci")
    private String ci;

    @Size(max = 12)
    @Column(name = "nit")
    private String nit;

    @Size(max = 128)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 32)
    @Column(name = "telefono_fijo")
    private String telefonoFijo;

    @Size(max = 32)
    @Column(name = "telefono_celular")
    private String telefonoCelular;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "limite_credito")
    private BigDecimal limiteCredito;

    @Column(name = "moneda_credito")
    private String monedaCredito;

    @Column(name = "plazo_maximo")
    private Integer plazoMaximo;

    @Column(name = "interes_mora")
    private BigDecimal interesMora;
    @Column(name = "calc_automatico_interes")
    private Boolean calcAutomaticoInteres;
    @Size(max = 1)
    @Column(name = "interes_desde")
    private String interesDesde;
    @Size(max = 64)
    @Column(name = "representante")
    private String representante;
    @Size(max = 64)
    @Column(name = "representante_direccion")
    private String representanteDireccion;
    @Size(max = 32)
    @Column(name = "representante_telefono")
    private String representanteTelefono;
    @Size(max = 32)
    @Column(name = "representante_celular")
    private String representanteCelular;
    @Size(max = 12)
    @Column(name = "representante_ci")
    private String representanteCi;
    @Column(name = "fecha_alta", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    public Cliente() {
    }

    public String getMonedaCredito() {
        return monedaCredito;
    }

    public void setMonedaCredito(String monedaCredito) {
        this.monedaCredito = monedaCredito;
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Integer getIdClienteGrupo() {
        return idClienteGrupo;
    }

    public void setIdClienteGrupo(Integer idClienteGrupo) {
        this.idClienteGrupo = idClienteGrupo;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdClienteCorporativo() {
        return idClienteCorporativo;
    }

    public void setIdClienteCorporativo(Integer idClienteCorporativo) {
        this.idClienteCorporativo = idClienteCorporativo;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Integer getPlazoMaximo() {
        return plazoMaximo;
    }

    public void setPlazoMaximo(Integer plazoMaximo) {
        this.plazoMaximo = plazoMaximo;
    }

    public BigDecimal getInteresMora() {
        return interesMora;
    }

    public void setInteresMora(BigDecimal interesMora) {
        this.interesMora = interesMora;
    }

    public Boolean getCalcAutomaticoInteres() {
        return calcAutomaticoInteres;
    }

    public void setCalcAutomaticoInteres(Boolean calcAutomaticoInteres) {
        this.calcAutomaticoInteres = calcAutomaticoInteres;
    }

    public String getInteresDesde() {
        return interesDesde;
    }

    public void setInteresDesde(String interesDesde) {
        this.interesDesde = interesDesde;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getRepresentanteDireccion() {
        return representanteDireccion;
    }

    public void setRepresentanteDireccion(String representanteDireccion) {
        this.representanteDireccion = representanteDireccion;
    }

    public String getRepresentanteTelefono() {
        return representanteTelefono;
    }

    public void setRepresentanteTelefono(String representanteTelefono) {
        this.representanteTelefono = representanteTelefono;
    }

    public String getRepresentanteCelular() {
        return representanteCelular;
    }

    public void setRepresentanteCelular(String representanteCelular) {
        this.representanteCelular = representanteCelular;
    }

    public String getRepresentanteCi() {
        return representanteCi;
    }

    public void setRepresentanteCi(String representanteCi) {
        this.representanteCi = representanteCi;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.Cliente[ idCliente=" + idCliente + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.idCliente;
    }

}
