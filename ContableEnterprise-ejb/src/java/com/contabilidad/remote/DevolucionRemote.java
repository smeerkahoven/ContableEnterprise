/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.PagoAnticipado;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import javax.ejb.Remote;


/**
 *
 * @author xeio
 */
@Remote
public interface DevolucionRemote extends DaoRemoteFacade {
    
    public Devolucion saveDevolucionFromPagoAnticipado (Devolucion devolucion, PagoAnticipado pFromDb ) throws CRUDException ;
}
