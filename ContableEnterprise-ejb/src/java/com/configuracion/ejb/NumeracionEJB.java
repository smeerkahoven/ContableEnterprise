/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.configuracion.entities.Numeracion;
import com.configuracion.remote.NumeracionRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class NumeracionEJB extends FacadeEJB implements NumeracionRemote {

    @Override
    public void initiateNumeracion(Integer idGestion) throws CRUDException {

        Numeracion notaDebito = new Numeracion();
        notaDebito.setFormulario(Numeracion.NOTA_DEBITO);
        notaDebito.setIdGestion(idGestion);
        notaDebito.setValor(Numeracion.NOTA_DEBITO_NUM);

        insert(notaDebito);

        Numeracion ingresoCaja = new Numeracion();
        ingresoCaja.setFormulario(Numeracion.INGRESO_CAJA);
        ingresoCaja.setIdGestion(idGestion);
        ingresoCaja.setValor(Numeracion.INGRESO_CAJA_NUM);

        insert(ingresoCaja);

        Numeracion pagoAnticipado = new Numeracion();
        pagoAnticipado.setFormulario(Numeracion.PAGO_ANTICIPADO);
        pagoAnticipado.setIdGestion(idGestion);
        pagoAnticipado.setValor(Numeracion.PAGO_ANTICIPADO_NUM);

        insert(pagoAnticipado);

        Numeracion pagoAnticipadoTrx = new Numeracion();
        pagoAnticipadoTrx.setFormulario(Numeracion.PAGO_ANTICIPADO_TRANX);
        pagoAnticipadoTrx.setIdGestion(idGestion);
        pagoAnticipadoTrx.setValor(Numeracion.PAGO_ANTICIPADO_TRX_NUM);

        insert(pagoAnticipadoTrx);

        Numeracion notaCredito = new Numeracion();
        notaCredito.setFormulario(Numeracion.NOTA_CREDITO);
        notaCredito.setIdGestion(idGestion);
        notaCredito.setValor(Numeracion.NOTA_CREDITO_NUM);

        insert(notaCredito);
        
        Numeracion devolucion = new Numeracion();
        devolucion.setFormulario(Numeracion.DEVOLUCION);
        devolucion.setIdGestion(idGestion);
        devolucion.setValor(Numeracion.DEVOLUCION_NUM);

        insert(devolucion);
    }

    @Override
    public int getNotaDebito(Integer idGestion) throws CRUDException {

        Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.NOTA_DEBITO);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para la Nota de Debito");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }

    @Override
    public int getIngresoCaja(Integer idGestion) throws CRUDException {
        Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.INGRESO_CAJA);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para el Ingreso de Caja");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }

    @Override
    public int getPagoAnticipado(Integer idGestion) throws CRUDException {
         Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.PAGO_ANTICIPADO);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para el Pago Anticipado");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }

    @Override
    public int getPagoAnticipadoTrans(Integer idGestion) throws CRUDException {
        Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.PAGO_ANTICIPADO_TRANX);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para la transaccion del Pago Anticipado");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }

    @Override
    public int getNotaCredito(Integer idGestion) throws CRUDException {
        Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.NOTA_CREDITO);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para la Nota de Credito");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }

    @Override
    public int getDevolucion(Integer idGestion) throws CRUDException {
        Query query = em.createNamedQuery("Numeracion.findNumeracion");
        query.setParameter("idGestion", idGestion);
        query.setParameter("formulario", Numeracion.DEVOLUCION);

        List<Numeracion> l = query.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existe numeracion para la Devolucion");
        }

        Numeracion fromDb = l.get(0);

        int value = fromDb.getValor();

        fromDb.setValor(fromDb.getValor() + 1);

        update(fromDb);

        return value;
    }
    
    

}
