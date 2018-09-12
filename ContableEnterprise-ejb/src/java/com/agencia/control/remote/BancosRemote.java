/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.remote;

import com.agencia.entities.Bancos;
import com.agencia.entities.CuentaBanco;
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
public interface BancosRemote extends DaoRemoteFacade {

    public List<Bancos> get() throws CRUDException;

    public void remove(CuentaBanco e) throws CRUDException;

    public boolean hasCuentas(Bancos b) throws CRUDException;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public List getCombo() throws CRUDException;

    @Override
    public List getNative(String nativeQuery, HashMap parameters) throws CRUDException;
    
    

}
