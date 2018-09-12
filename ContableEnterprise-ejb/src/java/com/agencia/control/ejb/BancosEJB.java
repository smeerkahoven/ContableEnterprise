/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.BancosRemote;
import com.agencia.entities.Bancos;
import com.agencia.entities.CuentaBanco;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.ComboSelect;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class BancosEJB extends FacadeEJB implements BancosRemote {

    private Queries querie = Queries.getQueries();

    @Override
    public void update(Entidad e) throws CRUDException {

        em.merge((Bancos) e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

        if (e instanceof Bancos) {
            return ((Bancos) e).getIdBanco();
        } else if (e instanceof CuentaBanco) {
            return ((CuentaBanco) e).getIdCuentaBanco();
        }

        return 0;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {

        Bancos b = em.find(Bancos.class, ((Bancos) e).getIdBanco());

        if (b == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + ((Bancos) e).getIdBanco() + " not found";

        }
        return b;

    }

    @Override
    public Entidad get(String q) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List get(Entidad e, String q) throws CRUDException {

        Bancos b = (Bancos) e;
        Query query = em.createNativeQuery(querie.getPropertie(q));
        query.setParameter(1, b.getIdBanco());

        return query.getResultList();

    }

    @Override
    public void remove(Entidad e) throws CRUDException {

        if (!em.contains(e)) {
            e = em.merge(e);
        }
        em.remove(e);
        em.flush();

    }

    @Override
    public List<Bancos> get() throws CRUDException {
        Query q = em.createNamedQuery("Bancos.findAll", Bancos.class);
        return (List<Bancos>) q.getResultList();
    }

    @Override
    public void remove(CuentaBanco e) throws CRUDException {
        if (!em.contains(e)) {
            e = em.merge(e);
        }
        em.remove(e);

    }

    @Override
    public boolean hasCuentas(Bancos b) throws CRUDException {

        Query q = em.createNamedQuery("CuentaBanco.countCuentasByBanco");
        q.setParameter("idBanco", b.getIdBanco());

        long value = (long) q.getSingleResult();

        return value > 0;
    }

    @Override
    public List getCombo() throws CRUDException {
        Query q = em.createNamedQuery("Bancos.combo");
        List l = q.getResultList();
        List r = new LinkedList();
        
        l.forEach((x) -> {
            Object[] o = (Object[])x ;
            ComboSelect s = new ComboSelect();
            s.setId((Integer)o[0]);
            s.setName((String)o[1]);
            r.add(s);
        });
        
        return r ;
    }
    
    

}
