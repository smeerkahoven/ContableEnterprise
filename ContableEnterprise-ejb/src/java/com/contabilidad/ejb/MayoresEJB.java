/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.search.dto.MayoresSearch;
import com.contabilidad.entities.Mayores;
import com.contabilidad.entities.MayoresAcumulados;
import com.contabilidad.entities.Moneda;
import com.contabilidad.remote.MayoresRemote;
import com.response.json.boletaje.GridMayores;
import com.response.json.boletaje.MayoresJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class MayoresEJB extends FacadeEJB implements MayoresRemote {

    @Override
    public GridMayores getGridMayores(MayoresSearch search) throws CRUDException {

        MayoresAcumulados acumulados = getAcumulado(search);
        
        MayoresAcumulados totales = new MayoresAcumulados();
        MayoresAcumulados totalesAcumulados = new MayoresAcumulados();

        Double saldoAcumuladoDebe = 0.0d;
        Double saldoAcumuladoHaber = 0.0d;
        Double saldoDebe = 0.0d;
        Double saldoHaber = 0.0d;
        Double totalDebe = 0d;
        Double totalHaber = 0d ;

        // hacemos la suma de los acumulados
        if (acumulados != null) {
            if (acumulados.getMontoAcumuladoDebe() != null) {
                saldoAcumuladoDebe = acumulados.getMontoAcumuladoDebe().doubleValue();
            } else {
                saldoAcumuladoDebe = 0d;
            }
            
            if (acumulados.getMontoAcumuladoHaber() != null) {
                saldoAcumuladoHaber = acumulados.getMontoAcumuladoHaber().doubleValue();
            } else {
                saldoAcumuladoHaber = 0d;
            }

            if (saldoAcumuladoDebe - saldoAcumuladoHaber > 0) {
                saldoDebe = (saldoAcumuladoDebe - saldoAcumuladoHaber);
            } else {
                saldoHaber = (saldoAcumuladoHaber - saldoAcumuladoDebe);
            }
            acumulados.setSaldoDebe(new BigDecimal(saldoDebe));
            acumulados.setSaldoHaber(new BigDecimal(saldoHaber));
        }

        // Obtenemos la lista de mayores 
        List<Mayores> l = getListaMayores(search);
        List<MayoresJson> r = new LinkedList<>();

        if (l.isEmpty()) {
            throw new CRUDException("No se encontraron datos para la búsqueda solicitada");
        }

        for (Mayores m : l) {

            if (m.getMontoDebe().doubleValue() != 0d) {
                if (saldoDebe >= 0 && saldoHaber == 0) {
                    saldoDebe = saldoDebe + m.getMontoDebe().doubleValue();
                    m.setSaldoDebe(saldoDebe);
                } else if (saldoHaber > 0) {
                    if (saldoHaber - m.getMontoDebe().doubleValue() < 0) {
                        saldoDebe = m.getMontoDebe().doubleValue() - saldoHaber;
                        saldoHaber = 0d;
                        m.setSaldoDebe(saldoDebe);
                    } else {
                        saldoHaber = saldoHaber - m.getMontoDebe().doubleValue();
                        m.setSaldoHaber(saldoHaber);
                    }
                }
                
                totalDebe = totalDebe + m.getMontoDebe().doubleValue();
                
            } else if (m.getMontoHaber().doubleValue() != 0d) {

                if (saldoHaber >=0 && saldoDebe==0) {
                    saldoHaber = saldoHaber + m.getMontoHaber().doubleValue();
                    m.setSaldoHaber(saldoHaber);
                } else if (saldoDebe > 0) {
                    if (saldoDebe - m.getMontoHaber().doubleValue() < 0) {
                        saldoHaber = m.getMontoHaber().doubleValue() - saldoDebe;
                        saldoDebe = 0d;
                        m.setSaldoHaber(saldoHaber);
                    } else {
                        saldoDebe = saldoDebe - m.getMontoHaber().doubleValue();
                        m.setSaldoDebe(saldoDebe);
                    }
                }
                
                totalHaber = totalHaber  + m.getMontoHaber().doubleValue();
            }

            r.add(MayoresJson.toMayoresJson(m));

        }
        
        
        
        totales.setSaldoDebe(new BigDecimal(totalDebe));
        totales.setSaldoHaber(new BigDecimal(totalHaber));
        
        
        totalesAcumulados.setSaldoDebe(new BigDecimal(saldoDebe));
        totalesAcumulados.setSaldoHaber(new BigDecimal(saldoHaber));
        totalesAcumulados.setMontoAcumuladoDebe(new BigDecimal(totalDebe + saldoAcumuladoDebe));
        totalesAcumulados.setMontoAcumuladoHaber(new BigDecimal(totalHaber + saldoAcumuladoHaber));

        GridMayores gm = new GridMayores();
        gm.setAcumulados(acumulados);
        gm.setMayores(r);
        gm.setTotales(totales);
        gm.setTotalesAcumulado(totalesAcumulados);

        return gm;

    }

    @Override
    public List<Mayores> getListaMayores(MayoresSearch search) throws CRUDException {
        Optional op = Optional.ofNullable(search.getIdEmpresa());
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        Query query = null;

        op = Optional.ofNullable(search.getMoneda());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Moneda.");
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Fecha Inicio.");
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Fecha Fin.");
        }

        op = Optional.ofNullable(search.getIdCuenta());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Cuenta.");
        }

        if (search.getMoneda().equals(Moneda.NACIONAL)) {
            query = em.createNamedQuery("Comprobante.getMayoresNac");
        } else {
            query = em.createNamedQuery("Comprobante.getMayoresExt");
        }

        query.setParameter("1", search.getIdEmpresa());

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            query.setParameter("2", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            query.setParameter("3", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        op = Optional.ofNullable(search.getIdCuenta().getId());
        if (op.isPresent()) {
            query.setParameter("4", search.getIdCuenta().getId());
        }

        return query.getResultList();
    }

    @Override
    public MayoresAcumulados getAcumulado(MayoresSearch search) throws CRUDException {
        Optional op = Optional.ofNullable(search.getIdEmpresa());
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        Query query = null;

        op = Optional.ofNullable(search.getMoneda());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Moneda.");
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Fecha Inicio.");
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Fecha Fin.");
        }

        op = Optional.ofNullable(search.getIdCuenta());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Cuenta.");
        }

        if (search.getMoneda().equals(Moneda.NACIONAL)) {
            query = em.createNamedQuery("Comprobante.getAcumuladosNac");
        } else {
            query = em.createNamedQuery("Comprobante.getAcumuladosExt");
        }

        query.setParameter("1", search.getIdEmpresa());

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            query.setParameter("2", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getIdCuenta());
        if (op.isPresent()) {
            query.setParameter("3", (search.getIdCuenta().getId()));
        }

        List l = query.getResultList();

        if (l.isEmpty()) {
            return new MayoresAcumulados();
        }
        return (MayoresAcumulados) l.get(0);
    }

}
