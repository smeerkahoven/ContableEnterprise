/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.contabilidad.entities.BalanceDto;
import com.contabilidad.remote.BalanceGeneralRemote;
import com.response.json.contabilidad.BalanceGeneralSearchJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author xeio
 */
@Stateless
public class BalanceGeneralEJB extends FacadeEJB implements BalanceGeneralRemote {

    @Override
    public BalanceDto generarBalance(BalanceGeneralSearchJson search)
            throws CRUDException {

        if (search.getFechaFin() == null) {
            throw new CRUDException("Debe ingresar una fecha Fin");
        }

        if (search.getMoneda() == null) {
            throw new CRUDException("Debe ingresar una Moneda");
        }

        Date startDate = DateContable.toLatinAmericaDateFormat(search.getFechaInicio());
        Date endDate = DateContable.toLatinAmericaDateFormat(search.getFechaFin());

        StoredProcedureQuery stp = em.createNamedStoredProcedureQuery("PlanCuenta.balanceGeneral");
        stp.setParameter("in_start_date", startDate);
        stp.setParameter("in_end_date", endDate);
        stp.setParameter("in_moneda", search.getMoneda());
        stp.setParameter("in_id_empresa", search.getIdEmpresa());

        BalanceDto balance = new BalanceDto();

        StoredProcedureQuery stpActi = em.createNamedStoredProcedureQuery("PlanCuenta.balanceGeneralObtenerActivos");
        StoredProcedureQuery stpPas = em.createNamedStoredProcedureQuery("PlanCuenta.balanceGeneralObtenerPasivosPatrimonio");
        
        
        balance.setActivos(stpActi.getResultList());
        balance.setPasivos(stpPas.getResultList());
        
        return balance;

    }

}
