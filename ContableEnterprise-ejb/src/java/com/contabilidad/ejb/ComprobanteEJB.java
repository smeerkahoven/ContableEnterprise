/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.ComprobanteContablePK;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
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

        Number next = 0;
        String query = queries.getPropertie(Queries.GET_NEXT_ID_LIBRO);
        query = query.replace("[partition]", DateContable.getPartitionDate(fecha));

        Query q = em.createNativeQuery(query);
        q.setParameter("1", tipo);

        List l = q.getResultList();

        if (!l.isEmpty()) {
            next = (Number) l.get(0);
        }

        ComprobanteContablePK pk = new ComprobanteContablePK(next.intValue(), Integer.parseInt(DateContable.getPartitionDate(fecha)));

        return pk;
    }

    @Override
    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF,
            Integer idEmpresa) throws CRUDException {

        String q = "SELECT c FROM ComprobanteContable c ";
        if (tipo.trim().length() > 0 || estado.trim().length() > 0
                || fechaI.trim().length() > 0 || fechaF.trim().length() > 0) {
            q += "WHERE ";

            if (tipo.trim().length() > 0) {
                q += " c.tipo=:tipo AND";
            }

            if (estado.trim().length() > 0) {
                q += " c.estado=:estado AND";
            }
            if (fechaI.trim().length() > 0) {
                q += " c.fecha>=:fechaI AND";
            }

            if (fechaF.trim().length() > 0) {
                q += " c.fecha<=:fechaF AND";
            }

            q += " c.idEmpresa=:idEmpresa ";
        }

        Query query = em.createQuery(q, ComprobanteContable.class);
        if (tipo.trim().length() > 0) {
            query.setParameter("tipo", tipo);
        }

        if (estado.trim().length() > 0) {
            query.setParameter("estado", estado);
        }
        if (fechaI.trim().length() > 0) {
            query.setParameter("fechaI", DateContable.toLatinAmericaDateFormat(fechaI));
        }

        if (fechaF.trim().length() > 0) {
            query.setParameter("fechaF", DateContable.toLatinAmericaDateFormat(fechaF));
        }

        query.setParameter("idEmpresa", idEmpresa);
        /*System.out.println(q);
        System.out.println(fechaI);
        System.out.println(fechaF);
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaI));
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaF));*/

        return query.getResultList();

    }

}
