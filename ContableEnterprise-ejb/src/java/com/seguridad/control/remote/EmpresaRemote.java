/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.entities.Empresa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface EmpresaRemote {    
    
    public Empresa getPrincipal(Empresa e) throws CRUDException ;
    
    public List<Empresa> getSucursales(Empresa e) throws CRUDException ;
    
    public Empresa get(Empresa e) throws CRUDException ;
    
    public List<Empresa> get(String q) throws CRUDException ;
    
    public List getAll() throws CRUDException ;
    
    public boolean update(Empresa e) throws CRUDException ;
    
    public int insert (Empresa e) throws CRUDException ;
    
    
}
