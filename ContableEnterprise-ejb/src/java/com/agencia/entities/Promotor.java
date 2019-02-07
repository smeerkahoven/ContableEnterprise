/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_promotor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotor.findAll", query = "SELECT p FROM Promotor p"), 
    @NamedQuery(name = "Promotor.comboAllCounter" , query = "SELECT p FROM Promotor p WHERE p.tipo='C' and p.estado='A'")
})
public class Promotor extends Entidad  {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_promotor")
    private Integer idPromotor;
    @Column(name = "id_empresa")
    private Integer idEmpresa ;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "ci")
    private String ci;
    @Size(max = 128)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 16)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 16)
    @Column(name = "celular")
    private String celular;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "comision_nac")
    private BigDecimal comisionNac;
    @Column(name = "comision_int")
    private BigDecimal comisionInt;
    @Size(max = 1)
    @Column(name = "comision_nac_tipo")
    private String comisionNacTipo;
    @Size(max = 1)
    @Column(name = "comision_int_tipo")
    private String comisionIntTipo;
    @Column(name = "reporta_total_ventas")
    private Boolean reportaTotalVentas;
    @Size(max = 45)
    @Column(name = "observaciones")
    private String observaciones;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;
    @Size(max = 1)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    public Promotor() {
    }

    public Promotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Promotor(Integer idPromotor, String nombre, String apellido, String ci) {
        this.idPromotor = idPromotor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public BigDecimal getComisionNac() {
        return comisionNac;
    }

    public void setComisionNac(BigDecimal comisionNac) {
        this.comisionNac = comisionNac;
    }

    public BigDecimal getComisionInt() {
        return comisionInt;
    }

    public void setComisionInt(BigDecimal comisionInt) {
        this.comisionInt = comisionInt;
    }

    public String getComisionNacTipo() {
        return comisionNacTipo;
    }

    public void setComisionNacTipo(String comisionNacTipo) {
        this.comisionNacTipo = comisionNacTipo;
    }

    public String getComisionIntTipo() {
        return comisionIntTipo;
    }

    public void setComisionIntTipo(String comisionIntTipo) {
        this.comisionIntTipo = comisionIntTipo;
    }

    public Boolean getReportaTotalVentas() {
        return reportaTotalVentas;
    }

    public void setReportaTotalVentas(Boolean reportaTotalVentas) {
        this.reportaTotalVentas = reportaTotalVentas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromotor != null ? idPromotor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotor)) {
            return false;
        }
        Promotor other = (Promotor) object;
        if ((this.idPromotor == null && other.idPromotor != null) || (this.idPromotor != null && !this.idPromotor.equals(other.idPromotor))) {
            return false;
        }
        return true;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    
    @Override
    public String toString() {
        return "com.agencia.entities.Promotor[ idPromotor=" + idPromotor + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        
        return this.getIdPromotor();
    }
    
    

}
