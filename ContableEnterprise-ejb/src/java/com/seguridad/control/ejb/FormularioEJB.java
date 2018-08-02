/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.FormularioRemote;
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
public class FormularioEJB extends FacadeEJB implements FormularioRemote{

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge(e);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
    }

    @Override
    public int insert(Entidad e) throws CRUDException {

        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return ((Formulario) e).getIdFormulario();        
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Formulario f = (Formulario)e ;
        Formulario emp = em.find(Formulario.class, f.getIdFormulario());

        if (emp == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + f.getIdFormulario() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        }

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return emp;
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
