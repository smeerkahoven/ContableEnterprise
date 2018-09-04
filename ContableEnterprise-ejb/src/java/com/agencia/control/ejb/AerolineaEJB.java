/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.AerolineaRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaImpuesto;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javafx.scene.control.ComboBox;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class AerolineaEJB extends FacadeEJB implements AerolineaRemote {

    @Override
    public Aerolinea getAerolinea(Aerolinea a) throws CRUDException {
        Aerolinea l = (Aerolinea) em.find(Aerolinea.class, a.getIdAerolinea());

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + a.toString(), this, Level.SEVERE);

        return l;
    }

    @Override
    public void update(Entidad e) throws CRUDException {
        em.merge((Aerolinea) e);

        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Aerolinea l = (Aerolinea) em.find(Aerolinea.class, ((Aerolinea) e).getIdAerolinea());

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

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

    @Override
    public void remove(Entidad e) throws CRUDException {

        if (e instanceof AerolineaImpuesto) {
            if (!em.contains((AerolineaImpuesto) e)) {
                e = (AerolineaImpuesto) em.merge(e);
                em.flush();
            }
            em.remove((AerolineaImpuesto) e);

        } else {
            em.remove(e);
        }
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);

    }

    @Override
    public List<Aerolinea> get() throws CRUDException {
        Query query = em.createNamedQuery("Aerolinea.findAll", Aerolinea.class);

        return (List<Aerolinea>) query.getResultList();
    }

    @Override
    public List<AerolineaImpuesto> get(Aerolinea a) throws CRUDException {
        Query query = em.createNamedQuery("AerolineaImpuesto.findAll", Aerolinea.class);
        query.setParameter("idAerolinea", a.getIdAerolinea());

        return (List<AerolineaImpuesto>) query.getResultList();
    }

    @Override
    public void remove(AerolineaImpuesto a, String q) throws CRUDException {
        Query query = em.createNativeQuery(queries.getPropertie(q));
        query.setParameter("1", a.getIdAerolineaImpuesto());

        query.executeUpdate();
    }

    @Override
    public List getCombo() throws CRUDException {

        Query query = em.createNamedQuery("Aerolinea.allCombo", Aerolinea.class);
        List aux = query.getResultList();
        List resp = new LinkedList();

        aux.forEach((x) -> {
            ComboSelect s = new ComboSelect();
            Object[] o = (Object[]) x;
            s.setId((Integer) o[0]);
            s.setName((String) o[1] + "-" + (String) o[2]);
            resp.add(s);
        });

        return resp;
    }

}
