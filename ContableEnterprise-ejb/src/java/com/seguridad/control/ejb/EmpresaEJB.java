/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.FacadeEJBFirst;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.remote.EmpresaRemote;
import com.seguridad.queries.Queries;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Cheyo
 */
@Stateless
public class EmpresaEJB extends FacadeEJBFirst implements EmpresaRemote {

    @Override
    public Empresa get(Empresa e) throws CRUDException {
        Empresa emp = em.find(Empresa.class, e.getIdEmpresa());

        if (emp == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.getIdEmpresa() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        } else {
            return emp;
        }
    }

    @Override
    public boolean update(Empresa e) throws CRUDException {
        em.merge(e);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
        return true;

    }

    @Override
    public int insert(Empresa e) throws CRUDException {

        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

        return e.getIdEmpresa();
    }

    @Override
    public Empresa getPrincipal(Empresa e) throws CRUDException {
        Query q = em.createNativeQuery(queries.getPropertie(Queries.GET_EMPRESA_CENTRAL), Empresa.class);
        List<Empresa> l = q.getResultList();

        for (Empresa emp : l) {
            if (emp == null) {
                String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.getTipo() + " not found";
                //LoggerContable.log(message, e, Level.SEVERE);
                throw new CRUDException(message);
            } else {
                return emp;
            }
        }
        
        return new Empresa() ;

    }

    @Override
    public List<Empresa> getSucursales(Empresa e) throws CRUDException {
       Query q = em.createNativeQuery(queries.getPropertie(Queries.GET_SUCURSALES), Empresa.class);
        List<Empresa> l = q.getResultList();

        for (Empresa emp : l) {
            if (emp == null) {
                String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.getTipo() + " not found";
                //LoggerContable.log(message, e, Level.SEVERE);
                throw new CRUDException(message);
            } else {
                return l;
            }
        }
        
        return new ArrayList<Empresa>() ;
    }

    @Override
    public List get(String q) throws CRUDException {
        Query query = em.createNamedQuery(q, Empresa.class) ;
        return (List<Empresa>)query.getResultList() ;
    }

    @Override
    public List getAll() throws CRUDException {
        Query query = em.createNamedQuery("Empresa.findAll", Empresa.class) ;
        return (List<Empresa>)query.getResultList() ;
    }
    
    

}
