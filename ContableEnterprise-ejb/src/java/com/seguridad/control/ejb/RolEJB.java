/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.FacadeEJBFirst;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.RolFormulario;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.RolRemoto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Cheyo
 */
@Stateless
public class RolEJB extends FacadeEJBFirst implements RolRemoto {

    @Override
    public Rol get(Rol e) throws CRUDException {
        Rol r = em.find(Rol.class, e.getIdRol());
        if (r == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.getIdRol() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        }
        return r;
    }

    @Override
    public boolean update(Rol e) throws CRUDException {
        em.merge(e);
        return true;
    }

    @Override
    public int insert(Rol e) throws CRUDException {
        em.persist(e);
        em.flush();
        return e.getIdRol();
    }

    @Override
    public List<Object[]> getForCombo() throws CRUDException {
        Query q = em.createNamedQuery("Rol.forCombo", Rol.class);
        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public List<Object[]> get(String qu, Rol r) throws CRUDException {
        Query q = em.createNativeQuery(qu);
        q.setParameter(1, r.getIdRol());

        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public List<Rol> get() throws CRUDException {
        Query q = em.createNamedQuery("Rol.findAll", Rol.class);
        List<Rol> l = q.getResultList();
        return l;
    }

    @Override
    public List<Object[]> get(String qu) throws CRUDException {
        Query q = em.createNativeQuery(qu);

        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public int insert(RolFormulario r) throws CRUDException {
        em.persist(r);
        em.flush();

        return r.getIdRolFormulario();
    }

    @Override
    public boolean update(RolFormulario r) throws CRUDException {
        em.merge(r);
        return true;
    }

    @Override
    public int remove(Rol e) throws CRUDException {
        //em.getTransaction().begin();;
        Query q = em.createNamedQuery("RolFormulario.remove");
        q.setParameter("idRol", new Rol( e.getIdRol()));

        q.executeUpdate();
        em.flush();
        
        e =  get(e) ;
        
        if (e != null)
            em.merge(e);
        
        em.remove(e);
        //em.getTransaction().commit();
        
        return 1 ;
    }

}
