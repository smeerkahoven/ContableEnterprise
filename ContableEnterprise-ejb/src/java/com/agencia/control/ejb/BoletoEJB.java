/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.entities.EmisionBoleto;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class BoletoEJB extends FacadeEJB implements BoletoRemote {

    @Override
    public List getTipoEmision() throws CRUDException {

        Query query = em.createNamedQuery("EmisionBoleto.findAll");
        List l = query.getResultList();
        
        List resp =new LinkedList();
        
        l.forEach((x)-> {
            EmisionBoleto e = (EmisionBoleto)x ;
            ComboSelect s  = new ComboSelect();
            s.setId(e.getIdEmision());
            s.setName(e.getNombre());
            
            resp.add(s);
        });
        
        return resp ;
    }

    @Override
    public boolean isBoletoRegistrado(Boleto b) throws CRUDException {
        
        Query q = em.createNamedQuery("Boleto.findNroBoleto", Boleto.class);
        q.setParameter("numero", b.getNumero());
        
        List l = q.getResultList() ;
        
        return !l.isEmpty() ;
        
    }



    
    
}
