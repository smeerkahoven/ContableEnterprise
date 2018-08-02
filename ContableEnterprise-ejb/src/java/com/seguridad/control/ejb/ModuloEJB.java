/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.entities.Modulo;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.ModuloRemote;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cheyo
 */
@Stateless
public class ModuloEJB extends FacadeEJB implements ModuloRemote {

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();
        return ((Modulo) e).getIdModulo();
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Modulo m = (Modulo)e ;
        Modulo mod = em.find(Modulo.class, m.getIdModulo());
        
        if (mod == null){
            String msg = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + m.getIdModulo() + " not found";
            throw new CRUDException(msg);
        }
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return mod ;
    }

    @Override
    public void remove(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public List get() throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
