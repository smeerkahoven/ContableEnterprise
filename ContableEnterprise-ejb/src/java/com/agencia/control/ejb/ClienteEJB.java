/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.ClienteRemote;
import com.seguridad.control.FacadeEJB;
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
    
    
    
}
