/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.ConfiguracionBoletajeRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.Optional;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class ConfiguracionBoletajeEJB extends FacadeEJB implements ConfiguracionBoletajeRemote{

    @Override
    public int insert(Entidad e) throws CRUDException {
        
        ContabilidadBoletaje search = (ContabilidadBoletaje)em.find(ContabilidadBoletaje.class, e.getId());
        
        Optional op = Optional.ofNullable(search);
        if (op.isPresent()){
            em.merge(e);
        }else {
            em.persist(e);
        }
        
        
        return e.getId(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
