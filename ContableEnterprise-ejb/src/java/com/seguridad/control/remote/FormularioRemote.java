/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;


import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface FormularioRemote extends DaoRemote {


    @Override
    public Entidad get(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;
  
    
    
   
}
