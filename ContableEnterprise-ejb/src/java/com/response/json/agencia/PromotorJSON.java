/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.Promotor;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author xeio
 */
public class PromotorJSON implements Serializable {

    private Integer idPromotor;
    private Integer idEmpresa;
    private String nombre;
    private String apellido;
    private String ci;
    private String direccion;
    private String telefono;
    private String celular;
    private Boolean reportaTotalVentas;
    private String observaciones;
    private String email;
    private String tipo;
    private String estado;

    private LinkedList<ComisionPromotorAerolineaJSON> listComisionNacional = new LinkedList<>();
    private LinkedList<ComisionPromotorAerolineaJSON> listComisionInternacional = new LinkedList<>();

    public static Promotor toPromotor(PromotorJSON json){
        Promotor newPromotor = new Promotor();
        newPromotor.setApellido(json.getApellido());
        newPromotor.setCelular(json.getCelular());
        newPromotor.setCi(json.getCi());
        newPromotor.setDireccion(json.getDireccion());
        newPromotor.setEmail(json.getEmail());
        newPromotor.setEstado(json.getEstado());
        newPromotor.setIdPromotor(json.getIdPromotor());
        newPromotor.setIdEmpresa(json.getIdEmpresa());
        newPromotor.setNombre(json.getNombre());
        newPromotor.setObservaciones(json.getObservaciones());
        newPromotor.setReportaTotalVentas(json.getReportaTotalVentas());
        //newPromotor.setSexo(json.getSexo());
        newPromotor.setTelefono(json.getTelefono());
        newPromotor.setTipo(json.getTipo());
        
        
        return newPromotor ;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public LinkedList<ComisionPromotorAerolineaJSON> getListComisionNacional() {
        return listComisionNacional;
    }

    public void setListComisionNacional(LinkedList<ComisionPromotorAerolineaJSON> listComisionNacional) {
        this.listComisionNacional = listComisionNacional;
    }

    public LinkedList<ComisionPromotorAerolineaJSON> getListComisionInternacional() {
        return listComisionInternacional;
    }

    public void setListComisionInternacional(LinkedList<ComisionPromotorAerolineaJSON> listComisionInternacional) {
        this.listComisionInternacional = listComisionInternacional;
    }

}
