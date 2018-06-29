/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Cheyo
 */
public class Menu implements Serializable, Comparable<String> {

    private String action;
    private String nombre;
    private String status;
    private String classMenu;
    private String urlAcceso;

    private ArrayList<Menu> submenus = new ArrayList<>();
    private Formulario formulario;

    public static final int IGUAL = 1;

    public String getClassMenu() {
        return classMenu;
    }

    public void setClassMenu(String classMenu) {
        this.classMenu = classMenu;
    }

    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Menu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(ArrayList<Menu> submenus) {
        this.submenus = submenus;
    }

    @Override
    public int compareTo(String o) {
        return (this.nombre.contentEquals(o)) ? 1 : 0;
    }

}
