/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.remote;

import com.configuracion.entities.Gestion;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface GestionRemote extends DaoRemoteFacade{

    @Override
    public void remove(Entidad e) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(Entidad e, String q) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;
    
    public Gestion getCurrent() throws CRUDException ;
    
    public Gestion cerrarGestion(Integer idGestion) throws CRUDException ;
    
    public void iniciarGestion(Gestion g) throws CRUDException ;
    
    
}
