/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.Cliente;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 *
 * @author xeio
 */
public class ClienteJSON {
    
    private Integer idCliente;
    private Integer idPromotor;
    private Integer idClienteGrupo;
    private Integer idClienteCorporativo;
    private String nombre;
    private String ci;
    private String nit;
    private String direccion;
    private String telefonoFijo;
    private String telefonoCelular;
    private String email;
    private String estado;
    private BigDecimal limiteCredito;
    private String monedaCredito;
    private Integer plazoMaximo;
    private Boolean calAutomaticoInteres;
    private String interesDesde;
    private BigDecimal interesMora;
    private String representante;
    private String representanteDireccion;
    private String representanteTelefono;
    private String representanteCelular;
    private String representanteCi;
    
    private LinkedList<ClienteSolicitadoJSON> listSolicitadoPor = new LinkedList<>();
    
    public static Cliente toCliente(ClienteJSON json) {
        Cliente cliente = new Cliente();
        cliente.setCalcAutomaticoInteres(json.getCalAutomaticoInteres());
        cliente.setCi(json.getCi());
        cliente.setDireccion(json.getDireccion());
        cliente.setEmail(json.getEmail());
        cliente.setEstado(json.getEstado());
        cliente.setFechaAlta(DateContable.getCurrentDate());
        cliente.setIdCliente(json.getIdCliente());
        cliente.setIdClienteCorporativo(json.getIdClienteCorporativo());
        cliente.setIdClienteGrupo(json.getIdClienteGrupo());
        cliente.setIdPromotor(json.getIdPromotor());
        cliente.setInteresDesde(json.getInteresDesde());
        cliente.setInteresMora(json.getInteresMora());
        cliente.setLimiteCredito(json.getLimiteCredito());
        cliente.setMonedaCredito(json.getMonedaCredito());
        cliente.setNit(json.getNit());
        cliente.setNombre(json.getNombre());
        cliente.setPlazoMaximo(json.getPlazoMaximo());
        cliente.setRepresentante(json.getRepresentante());
        cliente.setRepresentanteCelular(json.getRepresentanteCelular());
        cliente.setRepresentanteCi(json.getRepresentanteCi());
        cliente.setRepresentanteDireccion(json.getRepresentanteDireccion());
        cliente.setRepresentanteTelefono(json.getRepresentanteTelefono());
        cliente.setTelefonoFijo(json.getTelefonoFijo());
        cliente.setTelefonoCelular(json.getTelefonoCelular());
        
        return cliente;
    }
    
    public String getMonedaCredito() {
        return monedaCredito;
    }
    
    public void setMonedaCredito(String monedaCredito) {
        this.monedaCredito = monedaCredito;
    }
    
    public Integer getIdClienteGrupo() {
        return idClienteGrupo;
    }
    
    public void setIdClienteGrupo(Integer idClienteGrupo) {
        this.idClienteGrupo = idClienteGrupo;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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
    
    public void setIdClienteCorporativo(Integer idClienteCorporativo) {
        this.idClienteCorporativo = idClienteCorporativo;
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
    
    public Boolean getCalAutomaticoInteres() {
        return calAutomaticoInteres;
    }
    
    public void setCalAutomaticoInteres(Boolean calAutomaticoInteres) {
        this.calAutomaticoInteres = calAutomaticoInteres;
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
    
    public LinkedList<ClienteSolicitadoJSON> getListSolicitadoPor() {
        return listSolicitadoPor;
    }
    
    public void setListSolicitadoPor(LinkedList<ClienteSolicitadoJSON> listSolicitadoPor) {
        this.listSolicitadoPor = listSolicitadoPor;
    }
    
}
