/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class ModuloJSON {

    private int idModulo;
    private String moduloNombre;
    private String moduloStatus;
    private String moduloClassMenu;

    private List<FormularioJSON> flist;

    public ModuloJSON(Object[] o) {

        this.idModulo = (int) o[0];
        this.moduloNombre = (String) o[1];
        this.moduloStatus = (String) o[2];
        this.moduloClassMenu = (String) o[3];

    }

    public void addFormulario(Object[] o) {
        FormularioJSON f = new FormularioJSON(o);

        if (flist == null) {
            flist = new ArrayList<>();
        }

        flist.add(f);
    }

    public int getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    public String getModuloNombre() {
        return moduloNombre;
    }

    public void setModuloNombre(String moduloNombre) {
        this.moduloNombre = moduloNombre;
    }

    public String getModuloStatus() {
        return moduloStatus;
    }

    public void setModuloStatus(String moduloStatus) {
        this.moduloStatus = moduloStatus;
    }

    public String getModuloClassMenu() {
        return moduloClassMenu;
    }

    public void setModuloClassMenu(String moduloClassMenu) {
        this.moduloClassMenu = moduloClassMenu;
    }

    public List<FormularioJSON> getFlist() {
        return flist;
    }

    public void setFlist(List<FormularioJSON> flist) {
        this.flist = flist;
    }

}
