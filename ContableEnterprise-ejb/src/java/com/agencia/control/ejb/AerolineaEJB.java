/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.AerolineaRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaImpuesto;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class AerolineaEJB implements AerolineaRemote {

    @PersistenceContext
    private EntityManager em;

    private Queries queries = Queries.getQueries();

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge((Aerolinea) e);

        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();
        em.refresh(e);

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

        if (e instanceof Aerolinea) {
            return ((Aerolinea) e).getIdAerolinea();
        } else if (e instanceof AerolineaImpuesto) {
            return ((AerolineaImpuesto) e).getIdAerolineaImpuesto();
        }

        return 1;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Aerolinea l = (Aerolinea) em.find(Aerolinea.class, ((Aerolinea) e).getIdAerolinea());

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

        return l;
    }

    @Override
    public Entidad get(String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get(Entidad e, String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Entidad e) throws CRUDException {

        if (e instanceof AerolineaImpuesto) {
            if (!em.contains((AerolineaImpuesto) e)) {
                e = (AerolineaImpuesto) em.merge(e);
                em.flush();
            }
            em.remove((AerolineaImpuesto) e);

        } else {
            em.remove(e);
        }
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

    }

    @Override
    public List<Aerolinea> get() throws CRUDException {
        Query query = em.createNamedQuery("Aerolinea.findAll", Aerolinea.class);

        return (List<Aerolinea>) query.getResultList();
    }

    @Override
    public List<AerolineaImpuesto> get(Aerolinea a) throws CRUDException {
        Query query = em.createNamedQuery("AerolineaImpuesto.findAll", Aerolinea.class);
        query.setParameter("idAerolinea", a.getIdAerolinea());

        return (List<AerolineaImpuesto>) query.getResultList();
    }

    @Override
    public void remove(AerolineaImpuesto a, String q) throws CRUDException {
        Query query = em.createNativeQuery(queries.getPropertie(q));
        query.setParameter("1", a.getIdAerolineaImpuesto());

        query.executeUpdate();
    }

}
