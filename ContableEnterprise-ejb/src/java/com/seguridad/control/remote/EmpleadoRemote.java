/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface EmpleadoRemote {

    public List<Object[]> getForCombo() throws CRUDException;

    public List<Empleado> get() throws CRUDException;

    public Empleado get(Empleado e) throws CRUDException;

    public boolean update(Empleado e) throws CRUDException;

    public int insert(Empleado e) throws CRUDException;

}
