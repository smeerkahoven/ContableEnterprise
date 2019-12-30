/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.remote;

import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface NumeracionRemote extends DaoRemoteFacade {
    
    public void initiateNumeracion(Integer idGestion) throws CRUDException;
    
    public int getNotaDebito(Integer idGestion) throws CRUDException ;
    
    public int getIngresoCaja(Integer idGestion) throws CRUDException ;
    
    public int getPagoAnticipado(Integer idGestion) throws CRUDException ;
    
    public int getPagoAnticipadoTrans(Integer idGestion) throws CRUDException ;
    
    public int getNotaCredito(Integer idGestion) throws CRUDException ;
    
    public int getDevolucion(Integer idGestion) throws CRUDException ;
    
}
