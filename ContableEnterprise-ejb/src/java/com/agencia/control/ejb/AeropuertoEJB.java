/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.AeropuertoRemote;
import com.agencia.entities.Aeropuerto;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
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
public class AeropuertoEJB extends FacadeEJB implements AeropuertoRemote {

    @Override
    public List<Aeropuerto> get() throws CRUDException {

        Query q = em.createNamedQuery("Aeropuerto.findAll", Aeropuerto.class);
        
        return (List<Aeropuerto>) q.getResultList();
    }


    @Override
    public void update(Entidad e) throws CRUDException {
        
        em.merge((Aeropuerto)e) ;
        em.flush();
        
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
        return 1 ;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Aeropuerto a = em.find(Aeropuerto.class, ((Aeropuerto)e).getIdAeropuerto()) ;
        
        if (a == null){
            String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + ((Aeropuerto)e).getIdAeropuerto() + " not found";
        }
        
        return a ; 
    }

    @Override
    public void remove(Entidad e) throws CRUDException {
        em.remove((Aeropuerto) e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
    }

    @Override
    public Entidad get(String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get(Entidad e, String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
