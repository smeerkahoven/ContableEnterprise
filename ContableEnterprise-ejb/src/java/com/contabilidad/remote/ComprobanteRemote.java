/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.ComprobanteContablePK;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface ComprobanteRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    public ComprobanteContablePK getNextComprobantePK (String fecha, String tipo) throws CRUDException ;

    @Override
    public void beginTransaction() throws CRUDException;

    @Override
    public void endTransaction() throws CRUDException;

    @Override
    public void rollback() throws CRUDException;
    
    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF) throws CRUDException ;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;
    
    
    
    
}
