/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.contabilidad.entities.PlanCuentas;
import com.contabilidad.remote.PlanCuentasRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class PlanCuentasEJB extends FacadeEJB implements PlanCuentasRemote {

    @Override
    public void update(Entidad e) throws CRUDException {
        PlanCuentas p = (PlanCuentas) get((PlanCuentas) e);

        em.merge(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        /*PlanCuentas p = null;
        try {
            p = (PlanCuentas) get((PlanCuentas) e);
        } catch (Exception ex) {

        }*/

        //if (p == null) {
            em.persist(e);
            em.flush();
            LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

            return 1;

        /*} else {
            throw new CRUDException("La numero de cuenta ya existe");
        }*/

    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        PlanCuentas pc = em.find(PlanCuentas.class, ((PlanCuentas) e).getIdPlanCuentas());

        if (pc == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + ((PlanCuentas) e).getCuenta() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        }

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return pc;
    }

    @Override
    public void remove(Entidad e) throws CRUDException {
        if (!em.contains((PlanCuentas) e)) {
            e = em.merge(e);
        }
        em.remove((PlanCuentas) e);
    }

    @Override
    public List<PlanCuentas> get() throws CRUDException {
        Query q = em.createNamedQuery("PlanCuentas.findAll", PlanCuentas.class);
        List<PlanCuentas> l = q.getResultList();

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

    public List<Object[]> getForCombo() throws CRUDException {
        Query q = em.createNamedQuery("PlanCuentas.forCombo", PlanCuentas.class);
        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public List<Object[]> getForCombo(Empresa idEmpresa)  throws CRUDException {
        Query q = em.createNamedQuery("PlanCuentas.forCombo", PlanCuentas.class);
        q.setParameter("idEmpresa", idEmpresa.getIdEmpresa());
        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public List<PlanCuentas> get(Empresa idEmpresa) throws CRUDException  {
        Query q = em.createNamedQuery("PlanCuentas.findAll", PlanCuentas.class);
        q.setParameter("idEmpresa", idEmpresa.getIdEmpresa());
        List<PlanCuentas> l = q.getResultList();

        return l;
    }

    @Override
    public List<PlanCuentas> getForCombo(Empresa idEmpresa, PlanCuentas p) throws CRUDException  {
        Query q = em.createNamedQuery("PlanCuentas.forComboPlan", PlanCuentas.class);
        q.setParameter("idEmpresa", idEmpresa.getIdEmpresa());
        q.setParameter("cuenta",p.getCuenta());
        
        List<PlanCuentas> l = q.getResultList();

        return l;
    }
    
     @Override
    public List<PlanCuentas> getForComboIdPLan(Empresa idEmpresa, PlanCuentas p) throws CRUDException  {
        Query q = em.createNamedQuery("PlanCuentas.forComboIdPlan", PlanCuentas.class);
        q.setParameter("idEmpresa", idEmpresa.getIdEmpresa());
        q.setParameter("idPlanCuentas",p.getIdPlanCuentas());
        
        List<PlanCuentas> l = q.getResultList();

        return l;
    }
    
    

}
