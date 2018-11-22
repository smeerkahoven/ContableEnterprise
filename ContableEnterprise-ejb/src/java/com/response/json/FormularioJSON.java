/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.seguridad.control.entities.Formulario;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.RolFormulario;

/**
 *
 * @author xeio
 */
public class FormularioJSON implements Comparable {

    public final static int IGUAL = 1;
    public final static int DIFERENTES = 0;

    private int idFormulario;
    private String formularioNombre;
    private String formularioStatus;
    private int idRolFormulario;
    private int rolCrear;
    private int rolActualizar;
    private int rolEliminar;
    private int rolAcceder;
    private int rolBuscar;
    private int rolEjecutar;
    private int rolVer;
    private int rolAnular;
    private int rolEditar;

    public FormularioJSON(Object[] o) {
        this.idFormulario = (int) o[4];
        this.formularioNombre = (String) o[5];
        this.formularioStatus = (String) o[6];
        this.rolCrear = (o[7] == null) ? -1 : (o[7] instanceof Long ? Long.signum((Long) o[7]) : (int) o[7]);
        this.rolActualizar = (o[7] == null) ? -1 : (o[8] instanceof Long ? Long.signum((Long) o[8]) : (int) o[8]);
        this.rolEliminar = (o[9] == null) ? -1 : (o[9] instanceof Long ? Long.signum((Long) o[9]) : (int) o[9]);
        this.rolAcceder = (o[10] == null) ? -1 : (o[10] instanceof Long ? Long.signum((Long) o[10]) : (int) o[10]);
        this.rolBuscar = (o[11] == null) ? -1 : (o[11] instanceof Long ? Long.signum((Long) o[11]) : (int) o[11]);
        this.rolVer = (o[12] == null) ? -1 : (o[12] instanceof Long ? Long.signum((Long) o[12]) : (int) o[12]);
        this.rolEditar = (o[13] == null) ? -1 : (o[13] instanceof Long ? Long.signum((Long) o[13]) : (int) o[13]);
        this.rolAnular = (o[14] == null) ? -1 : (o[14] instanceof Long ? Long.signum((Long) o[14]) : (int) o[14]);
        this.rolEjecutar = (o[15] == null) ? -1 : (o[15] instanceof Long ? Long.signum((Long) o[15]) : (int) o[15]);

        if (o.length > 16) {
            this.idRolFormulario = (o[16] == null ? -1 : (int) o[16]);

        }

    }

    public static RolFormulario convertToRolFormulario(RolJSON rj, FormularioJSON f) {
        RolFormulario r = new RolFormulario();
        r.setIdRol(new Rol(rj.getIdRol()));
        r.setIdFormularios(new Formulario(f.getIdFormulario()));
        r.setAcceder(new Integer(f.getRolAcceder()).shortValue());
        r.setActualizar(new Integer(f.getRolActualizar()).shortValue());
        r.setBuscar(new Integer(f.getRolBuscar()).shortValue());
        r.setCrear(new Integer(f.getRolCrear()).shortValue());
        r.setEliminar(new Integer(f.getRolEliminar()).shortValue());

        return r;
    }

    public int getRolEjecutar() {
        return rolEjecutar;
    }

    public void setRolEjecutar(int rolEjecutar) {
        this.rolEjecutar = rolEjecutar;
    }

    public int getRolVer() {
        return rolVer;
    }

    public void setRolVer(int rolVer) {
        this.rolVer = rolVer;
    }

    public int getRolAnular() {
        return rolAnular;
    }

    public void setRolAnular(int rolAnular) {
        this.rolAnular = rolAnular;
    }

    public int getRolEditar() {
        return rolEditar;
    }

    public void setRolEditar(int rolEditar) {
        this.rolEditar = rolEditar;
    }

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getFormularioNombre() {
        return formularioNombre;
    }

    public void setFormularioNombre(String formularioNombre) {
        this.formularioNombre = formularioNombre;
    }

    public String getFormularioStatus() {
        return formularioStatus;
    }

    public void setFormularioStatus(String formularioStatus) {
        this.formularioStatus = formularioStatus;
    }

    public int getIdRolFormulario() {
        return idRolFormulario;
    }

    public void setIdRolFormulario(int idRolFormulario) {
        this.idRolFormulario = idRolFormulario;
    }

    public int getRolCrear() {
        return rolCrear;
    }

    public void setRolCrear(int rolCrear) {
        this.rolCrear = rolCrear;
    }

    public int getRolActualizar() {
        return rolActualizar;
    }

    public void setRolActualizar(int rolActualizar) {
        this.rolActualizar = rolActualizar;
    }

    public int getRolEliminar() {
        return rolEliminar;
    }

    public void setRolEliminar(int rolEliminar) {
        this.rolEliminar = rolEliminar;
    }

    public int getRolAcceder() {
        return rolAcceder;
    }

    public void setRolAcceder(int rolAcceder) {
        this.rolAcceder = rolAcceder;
    }

    public int getRolBuscar() {
        return rolBuscar;
    }

    public void setRolBuscar(int rolBuscar) {
        this.rolBuscar = rolBuscar;
    }

    @Override
    public int compareTo(Object o) {
        return (this.formularioNombre.equalsIgnoreCase((String) o)) ? IGUAL : DIFERENTES;

    }

}
