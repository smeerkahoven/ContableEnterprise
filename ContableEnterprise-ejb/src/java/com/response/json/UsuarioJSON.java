/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.User;
import com.seguridad.utils.DateContable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author xeio
 */
public class UsuarioJSON {

    private String username;

    private String password;

    private int idRol;

    private String nombreRol;

    private int idEmpleado;

    private String nombreEmpleado;

    private String status;

    private String fechaAlta;

    private String fechaBaja;

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsuarioJSON() {
    }

    public UsuarioJSON(User u) {
        this.username = u.getUserName();
        this.password = u.getPassword();
        this.idEmpleado = u.getIdEmpleado().getIdEmpleado();
        this.nombreEmpleado = u.getIdEmpleado().getNombre() + " " + u.getIdEmpleado().getApellido();
        this.idRol = u.getIdRol().getIdRol();
        this.nombreRol = u.getIdRol().getNombre();
        this.fechaAlta = DateContable.getDateFormat(u.getFechaAlta(), "dd/MM/yyyy HH:mm:ss");
        this.fechaBaja = DateContable.getDateFormat(u.getFechaBaja(), "dd/MM/yyyy HH:mm:ss");
        this.status = u.getStatus() ;

    }

    public static UsuarioJSON convertToJSON(User u) {
        return new UsuarioJSON(u);
    }

    public static List<UsuarioJSON> convertToJSON(List<User> l) {
        ArrayList<UsuarioJSON> lst = new ArrayList<>();
        Iterator<User> i = l.iterator();
        while (i.hasNext()) {
            User e = i.next();
            UsuarioJSON emp = new UsuarioJSON(e);
            lst.add(emp);
        }

        return lst;
    }

    public static User convertoToUsuario(UsuarioJSON u) {
        User user = new User();
        user.setUserName(u.getUsername());
        user.setStatus(u.getStatus());
        user.setIdRol(new Rol(u.getIdRol()));
        user.setIdEmpleado(new Empleado(u.getIdEmpleado()));
        user.setPassword(u.getPassword());

        return user;
    }

    public UsuarioJSON(String username, String password, int idRol, int idEmpleado) {
        this.username = username;
        this.password = password;
        this.idRol = idRol;
        this.idEmpleado = idEmpleado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

}
