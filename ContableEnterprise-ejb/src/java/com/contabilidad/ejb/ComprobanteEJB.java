/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.contabilidad.entities.ComprobanteContablePK;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import com.seguridad.queries.Queries;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class ComprobanteEJB extends FacadeEJB implements ComprobanteRemote {

    @Override
    public ComprobanteContablePK getNextComprobantePK(String fecha, String tipo) throws CRUDException {

        Number next = 0 ;
        String query = queries.getPropertie(Queries.GET_NEXT_ID_LIBRO);
        query =  query.replace("[partition]", fecha);
        
        Query q = em.createNativeQuery(query);
        q.setParameter("1", tipo);
        
        List l = q.getResultList();
        
        if (!l.isEmpty()){
            next = (Number)l.get(0);
        }
        
        ComprobanteContablePK pk =new ComprobanteContablePK(next.intValue(), Integer.parseInt(fecha));
        
        
        return pk;
    }

}
