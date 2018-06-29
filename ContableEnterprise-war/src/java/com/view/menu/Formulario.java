/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.menu;

import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Cheyo
 */
@Named("formConstant")
public class Formulario implements Serializable {

    public static final String PERSONAL = "personal";
    public static final String EMPRESA = "empresa";
    public static final String SUCURSAL = "sucursal";
    public static final String ROLES = "roles";
    public static final String AEROPUERTO = "aeropuerto";
    public static final String USUARIO = "usuario";
    public static final String PLAN_CUENTAS = "plan-cuentas";
    public static final String TARJETA_CREDITO= "tarjeta-credito";
    public static final String BANCOS= "bancos";

    public int PERMITIR = 1;
    public int DENEGAR = 0;

    private String nombre;
    private String url;
    private int buscar;
    private int acceder;
    private int actualizar;
    private int eliminar;
    private int crear;
    private String restful;
    private String status;

    public int getPERMITIR() {
        return PERMITIR;
    }

    public int getDENEGAR() {
        return DENEGAR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRestful() {
        return restful;
    }

    public void setRestful(String restful) {
        this.restful = restful;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBuscar() {
        return buscar;
    }

    public void setBuscar(int buscar) {
        this.buscar = buscar;
    }

    public int getAcceder() {
        return acceder;
    }

    public void setAcceder(int acceder) {
        this.acceder = acceder;
    }

    public int getActualizar() {
        return actualizar;
    }

    public void setActualizar(int actualizar) {
        this.actualizar = actualizar;
    }

    public int getEliminar() {
        return eliminar;
    }

    public void setEliminar(int eliminar) {
        this.eliminar = eliminar;
    }

    public int getCrear() {
        return crear;
    }

    public void setCrear(int crear) {
        this.crear = crear;
    }

}
