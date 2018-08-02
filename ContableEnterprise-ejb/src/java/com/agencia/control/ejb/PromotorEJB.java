/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.PromotorRemote;
import com.agencia.entities.Promotor;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class PromotorEJB extends FacadeEJB implements PromotorRemote {

    public PromotorEJB() {
        this.findAll = "Promotor.findAll";
        this.typeClass = Promotor.class ;
    }
    
    
}
