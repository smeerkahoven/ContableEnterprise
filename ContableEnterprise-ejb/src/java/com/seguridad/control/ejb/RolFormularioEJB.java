/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.entities.RolFormulario;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.RolFormularioRemote;
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
public class RolFormularioEJB implements RolFormularioRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();
        return ((RolFormulario)e).getIdRolFormulario();
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        RolFormulario r = (RolFormulario)e;
        RolFormulario rf = em.find(RolFormulario.class, r.getIdFormularios()) ;
        
        if (rf == null){
            String msg = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + r.getIdRolFormulario() + " not found";
            throw new CRUDException(msg);
        }
                
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return rf ;
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

}
