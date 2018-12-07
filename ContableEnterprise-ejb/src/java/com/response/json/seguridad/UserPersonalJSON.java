/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.seguridad;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.User;
import java.io.Serializable;

/**
 *
 * @author xeio
 */
public class UserPersonalJSON implements Serializable {

    public static User toUser(UserPersonalJSON u) {
        User data = new User();
        data.setUserName(u.getUserName());
        data.setPassword(u.getPassword());
        
        return data ;
    }
    
    public static Empleado toEmpleado (UserPersonalJSON u) {
        Empleado e = new Empleado();
        e.setApellido(u.getApellido());
        e.setEmail(u.getEmail());
        e.setNombre(u.getNombre());
        e.setSexo(u.getSexo());
        e.setTelefono(u.getTelefono());
        e.setIdEmpleado(u.getIdEmpleado());
        
        return e ;
    }
    
    private Integer idEmpleado ;
    private String nombre ;
    private String apellido ;
    private String email ;
    private String telefono ;
    private String cargo ;
    private String sexo ;
    
    private String userName;
    private String password;

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
