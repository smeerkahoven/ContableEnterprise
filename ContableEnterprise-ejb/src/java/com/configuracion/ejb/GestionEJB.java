/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.configuracion.entities.Gestion;
import com.configuracion.remote.GestionRemote;
import com.configuracion.remote.NumeracionRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class GestionEJB extends FacadeEJB implements GestionRemote {
    
    @EJB
    private NumeracionRemote ejbNumeracion;
    
    @Override
    public Gestion cerrarGestion(Integer idGestion) throws CRUDException {
        
        Gestion fromDb = (Gestion) get(idGestion, Gestion.class);
        
        if (fromDb != null) {
            fromDb.setEstado(Gestion.FINALIZADO);
            fromDb.setFechaFinReal(DateContable.getCurrentDate());
            update(fromDb);
        }
        
        return fromDb;
        
    }
    
    @Override
    public Gestion getCurrent() throws CRUDException {
        
        Query query = em.createNamedQuery("Gestion.findActivo");
        
        List<Gestion> l = query.getResultList();
        
        if (l.isEmpty()) {
            return null;
        }
        
        return l.get(0);
        
    }
    
    @Override
    public int insert(Entidad e) throws CRUDException {
        em.persist(e);
        em.flush();
        
        return e.getId();
    }
    
    @Override
    public void iniciarGestion(Gestion g) throws CRUDException {
        Gestion fromDb = getCurrent();
        if (fromDb == null) {
            
            String q1 = "select * from tb_gestion where ?1 between fecha_inicio and fecha_fin";
            Query query = em.createNativeQuery(q1);
            query.setParameter("1", g.getFechaInicio());
            query.setParameter("2", g.getFechaFin());
            
            List l = query.getResultList();
            if (!l.isEmpty()) {
                throw new CRUDException(String.format("Existe(n) Gestion(es) Activa(s) entre fechas %s y %s. Finalicela para poder crear una nueva",
                        DateContable.getDateFormat(g.getFechaInicio(), DateContable.LATIN_AMERICA_FORMAT),
                        DateContable.getDateFormat(g.getFechaFin(), DateContable.LATIN_AMERICA_FORMAT)
                )
                );
            }
            
            q1 = "select * from tb_gestion where ?2 between fecha_inicio  and fecha_fin";
            query = em.createNativeQuery(q1);
            query.setParameter("1", g.getFechaInicio());
            query.setParameter("2", g.getFechaFin());
            
            l = query.getResultList();
            if (!l.isEmpty()) {
                throw new CRUDException("Existe una Gestion Activa. Finalicela para poder crear una nueva");
            }
            
            insert(g);
            
            ejbNumeracion.initiateNumeracion(g.getIdGestion());
            
        } else {
            throw new CRUDException("Existe una Gestion Activa. Finalicela para poder crear una nueva");
        }
    }
    
}
