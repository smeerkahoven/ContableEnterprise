/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.ClienteRemote;
import com.agencia.entities.Cliente;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class ClienteEJB extends FacadeEJB implements ClienteRemote {

    public ClienteEJB() {
        this.findAll = "Cliente.findAll" ;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        
        Cliente c = em.find(Cliente.class, ((Cliente)e).getIdCliente());
        
        return c ;
        //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
