/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.remote;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaImpuesto;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */

@Remote
public interface AerolineaRemote extends DaoRemoteFacade {
    
    public Aerolinea getAerolinea(Aerolinea a) throws CRUDException;

    public List<Aerolinea> get() throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;
    

    public List<AerolineaImpuesto> get(Aerolinea a) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;
    
    
    public void remove(AerolineaImpuesto a , String query) throws CRUDException;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public List getCombo() throws CRUDException;
    
    

}
