/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_aerolinea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aerolinea.findAll", query = "SELECT a FROM Aerolinea a")
    ,@NamedQuery(name = "Aerolinea.findForCombo", query = "SELECT a.idAerolinea,a.numero, a.nombre, a.moneda FROM Aerolinea a WHERE a.moneda=:moneda")
    ,@NamedQuery(name = "Aerolinea.find", query = "SELECT a FROM Aerolinea a WHERE a.idAerolinea=:idAerolinea")
    ,@NamedQuery(name = "Aerolinea.findByIata", query = "SELECT a FROM Aerolinea a WHERE a.iata=:iata")
    ,@NamedQuery(name = "Aerolinea.findByNumero", query = "SELECT a FROM Aerolinea a WHERE a.numero=:numero")
    ,@NamedQuery(name = "Aerolinea.allCombo", query = "SELECT a.idAerolinea, a.numero, a.nombre FROM Aerolinea a  ORDER by a.numero")
})
public class Aerolinea extends Entidad {
    
    public static final class Comision {
        public static final String TOTAL = "T" ;
        public static final String NETO = "N" ;
        
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_aerolinea")
    private Integer idAerolinea;

    @Column(name = "numero", length = 2)
    private String numero;

    @Column(name = "iata", length = 4)
    private String iata;

    @Column(name = "nit", length = 15)
    private String nit;

    @Column(name = "emitir_factura_a", length = 64)
    private String emitirFacturaA;

    @Column(name = "nombre", length = 64)
    private String nombre;

    @Column(name = "representante", length = 64)
    private String representante;

    @Column(name = "direccion", length = 256)
    private String direccion;

    @Column(name = "telefono", length = 32)
    private String telefono;

    @Column(name = "celular", length = 32)
    private String celular;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "persona_contacto", length = 64)
    private String personaContacto;

    @Column(name = "bsp")
    private Boolean bsp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "comision_prom_int")
    private BigDecimal comisionPromInt;
    @Size(max = 1)
    @Column(name = "comision_prom_int_tipo")
    private String comisionPromIntTipo;
    @Column(name = "comision_prom_nac")
    private BigDecimal comisionPromNac;
    @Size(max = 1)
    @Column(name = "comision_prom_nac_tipo")
    private String comisionPromNacTipo;

    @Column(name = "round_comision_bob")
    private Boolean roundComisionBob;

    @Column(name = "round_comision_usd")
    private Boolean roundComisionUsd;

    @Column(name = "iva_it_comision")
    private Boolean ivaItComision;

    /*@Column(name = "cta_ventas_mon_nac")
    private BigInteger ctaVentasMonNac;
    @Column(name = "cta_ventas_mon_ext")
    private BigInteger ctaVentasMonExt;
    @Column(name = "cta_comision_mon_nac")
    private BigInteger ctaComisionMonNac;
    @Column(name = "cta_comision_mon_ext")
    private BigInteger ctaComisionMonExt;
    @Column(name = "cta_devolucion_mon_nac")
    private BigInteger ctaDevolucionMonNac;
    @Column(name = "cta_devolucion_mon_ext")
    private BigInteger ctaDevolucionMonExt;
     */
    @Column(name = "boletos_mon_nac")
    private Boolean boletosMonNac;
    @Column(name = "boletos_mon_ext")
    private Boolean boletosMonExt;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "impuesto_valor_neto")
    private Boolean impuestoValorNeto;
    @Column(name = "impuesto_qm")
    private Boolean impuestoQm;
    @Column(name = "cargo_no_fiscal")
    private Boolean cargoNoFiscal;
    @Column(name = "modalidad_boleto")
    private Boolean modalidadBoleto;
    @Column(name = "registra_pnr")
    private Boolean registraPnr;
    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aerolinea", updatable = false)
    private List<AerolineaImpuesto> aerolineaImpuestoList;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_comision_promotor")
    private List<ComisionPromotorAerolinea> comisionPromotorList;

    public Aerolinea() {
    }

    public Aerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public Aerolinea(Integer idAerolinea, String numero, String iata, String nit, String nombre, String representante) {
        this.idAerolinea = idAerolinea;
        this.numero = numero;
        this.iata = iata;
        this.nit = nit;
        this.nombre = nombre;
        this.representante = representante;
    }

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public Boolean getBsp() {
        return bsp;
    }

    public void setBsp(Boolean bsp) {
        this.bsp = bsp;
    }

    public BigDecimal getComisionPromInt() {
        return comisionPromInt;
    }

    public void setComisionPromInt(BigDecimal comisionPromInt) {
        this.comisionPromInt = comisionPromInt;
    }

    public String getComisionPromIntTipo() {
        return comisionPromIntTipo;
    }

    public void setComisionPromIntTipo(String comisionPromIntTipo) {
        this.comisionPromIntTipo = comisionPromIntTipo;
    }

    public BigDecimal getComisionPromNac() {
        return comisionPromNac;
    }

    public void setComisionPromNac(BigDecimal comisionPromNac) {
        this.comisionPromNac = comisionPromNac;
    }

    public String getComisionPromNacTipo() {
        return comisionPromNacTipo;
    }

    public void setComisionPromNacTipo(String comisionPromNacTipo) {
        this.comisionPromNacTipo = comisionPromNacTipo;
    }

    public String getEmitirFacturaA() {
        return emitirFacturaA;
    }

    public void setEmitirFacturaA(String emitirFacturaA) {
        this.emitirFacturaA = emitirFacturaA;
    }

    public Boolean getRoundComisionBob() {
        return roundComisionBob;
    }

    public void setRoundComisionBob(Boolean roundComisionBob) {
        this.roundComisionBob = roundComisionBob;
    }

    public Boolean getRoundComisionUsd() {
        return roundComisionUsd;
    }

    public void setRoundComisionUsd(Boolean roundComisionUsd) {
        this.roundComisionUsd = roundComisionUsd;
    }

    public Boolean getIvaItComision() {
        return ivaItComision;
    }

    public void setIvaItComision(Boolean ivaItComision) {
        this.ivaItComision = ivaItComision;
    }

    /*public BigInteger getCtaVentasMonNac() {
        return ctaVentasMonNac;
    }

    public void setCtaVentasMonNac(BigInteger ctaVentasMonNac) {
        this.ctaVentasMonNac = ctaVentasMonNac;
    }

    public BigInteger getCtaVentasMonExt() {
        return ctaVentasMonExt;
    }

    public void setCtaVentasMonExt(BigInteger ctaVentasMonExt) {
        this.ctaVentasMonExt = ctaVentasMonExt;
    }

    public BigInteger getCtaComisionMonNac() {
        return ctaComisionMonNac;
    }

    public void setCtaComisionMonNac(BigInteger ctaComisionMonNac) {
        this.ctaComisionMonNac = ctaComisionMonNac;
    }

    public BigInteger getCtaComisionMonExt() {
        return ctaComisionMonExt;
    }

    public void setCtaComisionMonExt(BigInteger ctaComisionMonExt) {
        this.ctaComisionMonExt = ctaComisionMonExt;
    }

    public BigInteger getCtaDevolucionMonNac() {
        return ctaDevolucionMonNac;
    }

    public void setCtaDevolucionMonNac(BigInteger ctaDevolucionMonNac) {
        this.ctaDevolucionMonNac = ctaDevolucionMonNac;
    }

    public BigInteger getCtaDevolucionMonExt() {
        return ctaDevolucionMonExt;
    }

    public void setCtaDevolucionMonExt(BigInteger ctaDevolucionMonExt) {
        this.ctaDevolucionMonExt = ctaDevolucionMonExt;
    }*/
    public Boolean getBoletosMonNac() {
        return boletosMonNac;
    }

    public void setBoletosMonNac(Boolean boletosMonNac) {
        this.boletosMonNac = boletosMonNac;
    }

    public Boolean getBoletosMonExt() {
        return boletosMonExt;
    }

    public void setBoletosMonExt(Boolean boletosMonExt) {
        this.boletosMonExt = boletosMonExt;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Boolean getImpuestoValorNeto() {
        return impuestoValorNeto;
    }

    public void setImpuestoValorNeto(Boolean impuestoValorNeto) {
        this.impuestoValorNeto = impuestoValorNeto;
    }

    public Boolean getImpuestoQm() {
        return impuestoQm;
    }

    public void setImpuestoQm(Boolean impuestoQm) {
        this.impuestoQm = impuestoQm;
    }

    public Boolean getCargoNoFiscal() {
        return cargoNoFiscal;
    }

    public void setCargoNoFiscal(Boolean cargoNoFiscal) {
        this.cargoNoFiscal = cargoNoFiscal;
    }

    public Boolean getModalidadBoleto() {
        return modalidadBoleto;
    }

    public void setModalidadBoleto(Boolean modalidadBoleto) {
        this.modalidadBoleto = modalidadBoleto;
    }

    public Boolean getRegistraPnr() {
        return registraPnr;
    }

    public void setRegistraPnr(Boolean registraPnr) {
        this.registraPnr = registraPnr;
    }

    public List<AerolineaImpuesto> getAerolineaImpuestoList() {
        return aerolineaImpuestoList;
    }

    public void setAerolineaImpuestoList(List<AerolineaImpuesto> aerolineaImpuestoList) {
        this.aerolineaImpuestoList = aerolineaImpuestoList;
    }

    public List<ComisionPromotorAerolinea> getComisionPromotorList() {
        return comisionPromotorList;
    }

    public void setComisionPromotorList(List<ComisionPromotorAerolinea> comisionPromotorList) {
        this.comisionPromotorList = comisionPromotorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAerolinea != null ? idAerolinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aerolinea)) {
            return false;
        }
        Aerolinea other = (Aerolinea) object;
        if ((this.idAerolinea == null && other.idAerolinea != null) || (this.idAerolinea != null && !this.idAerolinea.equals(other.idAerolinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.Aerolinea[ idAerolinea=" + idAerolinea + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getIdAerolinea();
    }

}
