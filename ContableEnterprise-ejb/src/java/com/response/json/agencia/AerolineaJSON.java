/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.Aerolinea;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class AerolineaJSON implements Serializable {

    private Integer idAerolinea;
    private String numero;
    private String iata;
    private String nit;
    private String emitirFacturaA;
    private String nombre;
    private String representante;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String personaContacto;
    private Boolean bsp;
    private BigDecimal comisionPromInt;
    private String comisionPromIntTipo;
    private BigDecimal comisionPromNac;
    private String comisionPromNacTipo;
    private Boolean roundComisionBob;
    private Boolean roundComisionUsd;
    private Boolean ivaItComision;
    private BigInteger ctaVentasMonNac;
    private BigInteger ctaVentasMonExt;
    private BigInteger ctaComisionMonNac;
    private BigInteger ctaComisionMonExt;
    private BigInteger ctaDevolucionMonNac;
    private BigInteger ctaDevolucionMonExt;
    private Boolean boletosMonNac;
    private Boolean boletosMonExt;
    private String moneda;
    private Boolean impuestoValorNeto;
    private Boolean impuestoQm;
    private Boolean cargoNoFiscal;
    private Boolean modalidadBoleto;
    private Boolean registraPnr;
    private List<AerolineaImpuestoJSON> aerolineaImpuestoList = new LinkedList<>();

    public AerolineaJSON() {

    }

    public static Aerolinea toAerolinea(AerolineaJSON json) {
        Aerolinea a = new Aerolinea();
        a.setBoletosMonExt(a.getBoletosMonExt());
        a.setBoletosMonNac(a.getBoletosMonNac());
        a.setBsp(json.getBsp());
        a.setCargoNoFiscal(json.getCargoNoFiscal());
        a.setCelular(json.getCelular());
        a.setComisionPromInt(json.getComisionPromInt());
        a.setComisionPromIntTipo(json.getComisionPromIntTipo());
        a.setComisionPromNac(json.getComisionPromNac());
        a.setComisionPromNacTipo(json.getComisionPromNacTipo());
        a.setCtaComisionMonExt(json.getCtaComisionMonExt());
        a.setCtaComisionMonNac(json.getCtaComisionMonNac());
        a.setCtaDevolucionMonExt(json.getCtaDevolucionMonExt());
        a.setCtaDevolucionMonNac(json.getCtaDevolucionMonNac());
        a.setCtaVentasMonExt(json.getCtaVentasMonExt());
        a.setCtaVentasMonNac(json.getCtaVentasMonNac());
        a.setDireccion(json.getDireccion());
        a.setEmail(json.getEmail());
        a.setEmitirFacturaA(json.getEmitirFacturaA());
        a.setIata(json.getIata());
        a.setIdAerolinea(json.getIdAerolinea());
        a.setImpuestoQm(json.getImpuestoQm());
        a.setImpuestoValorNeto(json.getImpuestoValorNeto());
        a.setIvaItComision(json.getIvaItComision());
        a.setModalidadBoleto(json.getModalidadBoleto());
        a.setMoneda(json.getMoneda());
        a.setNit(json.getNit());
        a.setNombre(json.getNombre());
        a.setNumero(json.getNumero());
        a.setPersonaContacto(json.getPersonaContacto());
        a.setRegistraPnr(json.getRegistraPnr());
        a.setRepresentante(json.getRepresentante());
        a.setRoundComisionBob(json.getRoundComisionBob());
        a.setRoundComisionUsd(json.getRoundComisionUsd());
        a.setTelefono(json.getTelefono());

        return a;
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

    public String getEmitirFacturaA() {
        return emitirFacturaA;
    }

    public void setEmitirFacturaA(String emitirFacturaA) {
        this.emitirFacturaA = emitirFacturaA;
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

    public BigInteger getCtaVentasMonNac() {
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
    }

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

    public List<AerolineaImpuestoJSON> getAerolineaImpuestoList() {
        return aerolineaImpuestoList;
    }

    public void setAerolineaImpuestoList(List<AerolineaImpuestoJSON> aerolineaImpuestoList) {
        this.aerolineaImpuestoList = aerolineaImpuestoList;
    }

}
