/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.remote;

import com.configuracion.entities.CambioDolar;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface CambioRemote extends DaoRemote {

    public CambioDolar get(String fecha, String query) throws CRUDException;

    @Override
    public List get() throws CRUDException;

}
