/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.TarjetaCreditoRemote;
import com.agencia.entities.TarjetaCredito;
import com.contabilidad.entities.PlanCuentas;
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
public class TarjetaCreditoEJB extends FacadeEJB implements TarjetaCreditoRemote {

    @Override
    public List<TarjetaCredito> get() throws CRUDException {
        
        Query q = em.createNamedQuery("TarjetaCredito.findAll", TarjetaCredito.class);
        List <TarjetaCredito> l = q.getResultList();
        
        return l ;
        
    }

    @Override
    public List<Object[]> getForCombo() throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Entidad e) throws CRUDException {
        //TarjetaCredito tc = (TarjetaCredito) get((TarjetaCredito) e);

        em.merge((TarjetaCredito) e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
    }

    @Override
    public int insert(Entidad e) throws CRUDException {
         em.persist(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return ((TarjetaCredito)e).getIdTarjetaCredito();

    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {

        TarjetaCredito tc = em.find(TarjetaCredito.class, ((TarjetaCredito) e).getIdTarjetaCredito());

        if (tc == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + ((TarjetaCredito) e).getIdTarjetaCredito() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        }
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return tc;
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
                if (!em.contains((TarjetaCredito)e)){
            e = em.merge(e);
        }
        em.remove((TarjetaCredito)e);
    }

}
