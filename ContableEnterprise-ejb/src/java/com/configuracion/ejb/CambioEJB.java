/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.CambioUfv;
import com.configuracion.remote.CambioRemote;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */

@Stateless
public class CambioEJB implements CambioRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e) ;
    }

    @Override
    public int insert(Entidad e) throws CRUDException {

        em.persist(e);

        em.flush();

        return 1;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {

        if (e instanceof CambioDolar) {
            return em.find(CambioDolar.class, ((CambioDolar) e).getFecha());
        } else if (e instanceof CambioUfv) {
            return em.find(CambioUfv.class, ((CambioUfv) e).getFecha());
        }
        
        return null;
    }

    @Override
    public Entidad get(String q) throws CRUDException {
       
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get(Entidad e, String q) throws CRUDException {
        
         Query query = em.createNamedQuery(q);
        
        return query.getResultList() ;
        
    }

    @Override
    public void remove(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
