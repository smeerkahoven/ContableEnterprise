/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import com.seguridad.queries.Queries;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
public abstract class FacadeEJB implements DaoRemoteFacade {

    protected String findAll;

    protected Class<?> typeClass;

    @PersistenceContext
    protected EntityManager em;

    protected Queries queries = Queries.getQueries();

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException {
        Query q = em.createNamedQuery(namedQuery, typeClass);
        return q.getResultList();
    }

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException {
        Query q = em.createNamedQuery(namedQuery, typeClass);

        Iterator i = parameters.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry<String, Object>) i.next();
            q.setParameter((String) pair.getKey(), pair.getValue());
        }

        return q.getResultList();
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get(Entidad e, String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get() throws CRUDException {
        Query q = em.createNamedQuery(findAll, typeClass);
        return q.getResultList();
    }

    @Override
    public void remove(Entidad e) throws CRUDException {

        Entidad toRemove = em.merge(e);

        em.remove(toRemove);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

    }

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

    }

    @Override
    public int insert(Entidad e) throws CRUDException {

        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return e.getId();
    }

    @Override
    public Entidad insertE(Entidad e) throws CRUDException {

        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return e;

    }

    @Override
    public Entidad get(String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getList(String namedQuery, Class<?> typeClass) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException {
        Query q = em.createNativeQuery(queries.getPropertie(nativeQuery), typeClass);

        Iterator i = parameters.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry<String, Object>) i.next();
            q.setParameter((String) pair.getKey(), pair.getValue());
        }

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" , this, Level.FINE);

        return q.getResultList();
    }

    @Override
    public List getNative(String nativeQuery, HashMap parameters) throws CRUDException {
        Query q = em.createNativeQuery(queries.getPropertie(nativeQuery));

        Iterator i = parameters.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry<String, Object>) i.next();
            q.setParameter((String) pair.getKey(), pair.getValue());
        }

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" , this, Level.FINE);

        return q.getResultList();
    }
    
    

    @Override
    public void remove( String nativeQuery, HashMap<String, Object> parameters) throws CRUDException {

        Query q = em.createNativeQuery(queries.getPropertie(nativeQuery) );
        Iterator i = parameters.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pair = (Map.Entry<String, Object>) i.next();
            q.setParameter((String) pair.getKey(), pair.getValue());
        }

        q.executeUpdate();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" , this, Level.FINE);

    }

}
