/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.RolFormulario;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface RolRemoto {

    public List<Object[]> get(String qu ,Rol r) throws CRUDException;
    
    public List<Object[]> get(String q) throws CRUDException;

    public List<Object[]> getForCombo() throws CRUDException;

    public List<Rol> get() throws CRUDException ;
    
    public Rol get(Rol e) throws CRUDException;

    public boolean update(Rol e) throws CRUDException;
    
    public boolean update(RolFormulario r) throws CRUDException ;

    public int insert(Rol e) throws CRUDException;
    
    public int insert(RolFormulario r) throws CRUDException ;
    
    public int remove(Rol e) throws CRUDException ;
}
