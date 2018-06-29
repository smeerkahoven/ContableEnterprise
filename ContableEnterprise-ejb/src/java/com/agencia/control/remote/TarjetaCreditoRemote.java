/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.remote;

import com.agencia.entities.TarjetaCredito;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface TarjetaCreditoRemote extends DaoRemote {

    public List<TarjetaCredito> get() throws CRUDException;

    public List<Object[]> getForCombo() throws CRUDException;

}
