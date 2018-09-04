/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.agencia.entities.Bancos;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class BancoJSON implements Serializable {

    private Integer idBanco;
    private String nombre;
    private String direccion;
    private String telefono;
    private String celular;
    private String contacto;
    private String abreviacion;
    private String nit;
    private String email;
    
    public static BancoJSON toJSON(Bancos b){
        BancoJSON json = new BancoJSON();
        json.setIdBanco(b.getIdBanco());
        json.setNombre(b.getNombre());
        json.setDireccion(b.getDireccion());
        json.setTelefono(b.getTelefono());
        json.setCelular(b.getCelular());
        json.setContacto(b.getContacto());
        json.setAbreviacion(b.getAbreviacion());
        json.setEmail(b.getEmail());
        json.setNit(b.getNit());
        
        return json ;
    }
    
    public static Bancos toBancos(BancoJSON json){
        Bancos b = new Bancos();
        b.setIdBanco(json.getIdBanco());
        b.setNombre(json.getNombre());
        b.setDireccion(json.getDireccion());
        b.setTelefono(json.getTelefono());
        b.setCelular(json.getCelular());
        b.setContacto(json.getContacto());
        b.setAbreviacion(json.getAbreviacion());
        b.setNit(json.getNit());
        b.setEmail(json.getEmail());
        
        return b ;
    }


    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
