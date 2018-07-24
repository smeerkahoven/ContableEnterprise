/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.Empresa;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author xeio
 */
public class EmpleadoJSON implements Serializable {

    private int idEmpleado;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String fechaAlta;
    private String fechaBaja;
    private String status;
    private int idEmpresa;
    private String razonSocial;
    private String sexo;
    private String cargo;
    
    public EmpleadoJSON(){
        
    }

    public EmpleadoJSON(Empleado e) {
        this.idEmpleado = e.getIdEmpleado();
        this.nombre = e.getNombre();
        this.apellido = e.getApellido();
        this.email = e.getEmail();
        this.telefono = e.getTelefono();
        this.fechaAlta = DateContable.getDateFormat(e.getFechaAlta(), "dd/MM/yyyy HH:mm:ss");
        this.fechaBaja = DateContable.getDateFormat(e.getFechaBaja(), "dd/MM/yyyy HH:mm:ss");
        this.status = e.getStatus();
        this.idEmpresa = e.getIdEmpresa().getIdEmpresa();
        this.razonSocial = e.getIdEmpresa().getRazonSocial();
        this.sexo =  e.getSexo();
        this.cargo = e.getCargo();
    }

    public static List<EmpleadoJSON> convertToJSON(List<Empleado> l) {
        ArrayList<EmpleadoJSON> lst = new ArrayList<>();
        Iterator<Empleado> i = l.iterator();
        while (i.hasNext()) {
            Empleado e = i.next();
            EmpleadoJSON emp = new EmpleadoJSON(e);
            lst.add(emp);
        }

        return lst;
    }

    public static EmpleadoJSON convertToJSON(Empleado e) {
        return new EmpleadoJSON(e);
    }
    
    public static Empleado convertoToEmpleado(EmpleadoJSON e){
        Empleado emp = new Empleado();
        emp.setIdEmpleado(e.getIdEmpleado());
        emp.setApellido(e.getApellido());
        emp.setEmail(e.getEmail());
        emp.setIdEmpresa(new Empresa (e.getIdEmpresa()));
        emp.setNombre(e.getNombre());
        emp.setApellido(e.getApellido());
        emp.setStatus(e.getStatus());
        emp.setTelefono(e.getTelefono());
        emp.setCargo(e.getCargo());
        emp.setSexo(e.getSexo());
        
        return emp ;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    
    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

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

}
