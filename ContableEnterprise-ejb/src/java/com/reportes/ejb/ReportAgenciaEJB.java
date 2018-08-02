/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reportes.ejb;

import com.reportes.entities.Reportes;
import com.reportes.remote.ReportAgenciaRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class ReportAgenciaEJB extends FacadeEJB implements ReportAgenciaRemote {

    @Override
    public void update(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
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
    public void remove(Entidad e) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reportes> get(Formulario f) throws CRUDException {
        Query query = em.createNamedQuery("Reportes.findAllReportes", Reportes.class);
        query.setParameter("idFormulario", f);
        
        return query.getResultList() ;
    }

    @Override
    public List get() throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
