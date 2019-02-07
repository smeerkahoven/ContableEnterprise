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
    public static final String TARJETA_CREDITO = "tarjeta-credito";
    public static final String BANCOS = "bancos";
    public static final String FACTORES = "factores";
    public static final String AEROLINEA = "aerolinea";
    public static final String CLIENTES = "clientes";
    public static final String BOLETOS = "boletos";
    public static final String PROMOTORES = "promotores";
    public static final String COMPROBANTES = "comprobantes";
    public static final String INGRESO_CAJA = "ingreso-caja";
    public static final String NOTA_CREDITO = "notas-credito";
    public static final String PAGOS_ANTICIPADOS = "pago-anticipado";
    public static final String BOLETOS_OTROS = "boletaje-otros";
    public static final String NOTA_DEBITO = "nota-debito";
    public static final String CONFIGURACION_BOLETOS = "configuracion-boletaje";
    public static final String CONFIGURACION_USER = "user-personal";
    public static final String MAYORES = "mayores";
    public static final String SISTEMA = "sistema";
    public static final String LOGS = "logs";
    //---REPORTES
    public static final String REPORTES_AGENCIA = "reportes-agencia";
    public static final String REPORTES_CONTABILIDAD = "reportes-contabilidad";
    public static final String REPORTES_VENTAS_BOLETOS = "reportes-ventas-boletos";
    public static final String REPORTES_COMISION_CLIENTE = "reportes-comision-cliente";
    //cobranzas
    public static final String KARDEX_CLIENTE = "kardex-cliente";

    public int PERMITIR = 1;
    public int DENEGAR = 0;

    private String nombre;
    private String modulo;
    private String url;
    private int buscar;
    private int acceder;
    private int actualizar;
    private int eliminar;
    private int crear;
    private String restful;
    private String status;
    private Integer idFormulario;
    private Integer editar;
    private Integer ejecutar;
    private Integer anular ;
    private Integer ver ;
    private String fullPath;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
    
    public Integer getEditar() {
        return editar;
    }

    public void setEditar(Integer editar) {
        this.editar = editar;
    }

    public Integer getEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(Integer ejecutar) {
        this.ejecutar = ejecutar;
    }

    public Integer getAnular() {
        return anular;
    }

    public void setAnular(Integer anular) {
        this.anular = anular;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    
    public Integer getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

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
