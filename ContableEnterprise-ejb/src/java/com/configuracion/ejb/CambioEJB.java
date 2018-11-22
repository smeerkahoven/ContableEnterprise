/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.CambioUfv;
import com.configuracion.remote.CambioRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
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
public class CambioEJB extends FacadeEJB implements CambioRemote {

    @PersistenceContext
    private EntityManager em;

    public CambioEJB() {
        findAll = "CambioDolar.findAll";
    }

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e);
    }

    @Override
    public int insert(Entidad e) throws CRUDException {

        boolean merge = false;
        if (e instanceof CambioDolar) {
            if (em.find(CambioDolar.class, ((CambioDolar) e).getFecha()) != null) {
                em.merge(e);
                merge = true;
            }
        } else if (e instanceof CambioUfv) {
            if (em.find(CambioDolar.class, ((CambioUfv) e).getFecha()) != null) {
                em.merge(e);
                merge = true;
            }
        }

        if (!merge) {
            em.persist(e);
        }

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

        return query.getResultList();

    }

    @Override
    public void remove(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CambioDolar get(String fecha, String query) {
        Query q = em.createNamedQuery(query);
        q.setParameter("fecha", DateContable.toLatinAmericaDateFormat(fecha));

        if (q.getResultList().size() > 1) {
            return (CambioDolar) q.getSingleResult();
        }

        return null;
    }

    @Override
    public List get() throws CRUDException {
        
        Query q = em.createNamedQuery("CambioDolar.findAll");
        
        if (q.getResultList().size() >0 ){
            return q.getResultList();
        }
        
        return null ;
        
    }

}
