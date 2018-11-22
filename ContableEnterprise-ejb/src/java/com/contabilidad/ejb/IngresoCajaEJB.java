/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.IngresoCajaRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author xeio
 */
@Stateless
public class IngresoCajaEJB extends FacadeEJB implements IngresoCajaRemote {

    //YA NO SE USA
    @Override
    public IngresoCaja createIngresoCaja(final Boleto boleto, final NotaDebito nota) throws CRUDException {

        IngresoCaja ingreso = new IngresoCaja();

        ingreso.setFactorCambiario(nota.getFactorCambiario());
        ingreso.setFechaEmision(nota.getFechaEmision());
        ingreso.setFechaInsert(DateContable.getCurrentDate());
        ingreso.setFormaPago(boleto.getFormaPago());
        ingreso.setIdNotaDebito(nota.getIdNotaDebito());
        ingreso.setIdUsuario(boleto.getIdUsuarioCreador());
        ingreso.setIdCliente(boleto.getIdCliente());
        ingreso.setIdEmpresa(boleto.getIdEmpresa());
        ingreso.setIdCounter(boleto.getIdPromotor());
        ingreso.setEstado(IngresoCaja.EMITIDO);

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            ingreso.setMontoAbonadoUsd(boleto.getTotalMontoCancelado());

        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            ingreso.setMontoAbonadoBs(boleto.getTotalMontoCancelado());
        }

        if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
            ingreso.setIdTarjetaCredito(boleto.getTarjetaId());
            ingreso.setNroTarjeta(boleto.getTarjetaNumero());
            ingreso.setFormaPago(FormasPago.TARJETA);
        } else if (boleto.getFormaPago().equals(FormasPago.CONTADO)) {
            ingreso.setFormaPago(FormasPago.CONTADO);

            if (boleto.getContadoTipo().equals(FormasPago.EFECTIVO)) {

            } else if (boleto.getContadoTipo().equals(FormasPago.CHEQUE)) {
                ingreso.setNroCheque(boleto.getContadoNroCheque());
                ingreso.setIdBanco(boleto.getContadoIdBanco());
            } else if (boleto.getContadoTipo().equals(FormasPago.DEPOSITO)) {
                ingreso.setNroDeposito(boleto.getContadoNroDeposito());
                ingreso.setIdCuentaDeposito(boleto.getContadoIdCuenta());
            }
        } else if (boleto.getFormaPago().equals(FormasPago.COMBINADO)) {
            ingreso.setFormaPago(FormasPago.COMBINADO);
            if (boleto.getCombinadoContado() == 1) {
                ingreso.setCombinadoContado(new Short("1"));
                if (boleto.getContadoTipo().equals(FormasPago.EFECTIVO)) {

                } else if (boleto.getContadoTipo().equals(FormasPago.CHEQUE)) {
                    ingreso.setNroCheque(boleto.getContadoNroCheque());
                    ingreso.setIdBanco(boleto.getContadoIdBanco());
                } else if (boleto.getContadoTipo().equals(FormasPago.DEPOSITO)) {
                    ingreso.setNroDeposito(boleto.getContadoNroDeposito());
                    ingreso.setIdCuentaDeposito(boleto.getContadoIdCuenta());
                }
            }

            if (boleto.getCombinadoTarjeta() == 1) {
                ingreso.setCombinadoTarjeta(new Short("1"));
                ingreso.setIdTarjetaCredito(boleto.getCombinadoTarjetaId());
                ingreso.setNroTarjeta(boleto.getCombinadoTarjetaNumero());
            }
        }

        return ingreso;
    }

    @Override
    public IngresoCaja createIngresoCaja(NotaDebito nota) throws CRUDException {
        IngresoCaja ingreso = new IngresoCaja();
        ingreso.setFactorCambiario(nota.getFactorCambiario());
        ingreso.setFechaEmision(nota.getFechaEmision());
        ingreso.setFechaInsert(DateContable.getCurrentDate());
        ingreso.setIdNotaDebito(nota.getIdNotaDebito());
        ingreso.setIdUsuario(nota.getIdUsuarioCreador());
        ingreso.setIdCliente(nota.getIdCliente());
        ingreso.setIdEmpresa(nota.getIdEmpresa());
        ingreso.setIdCounter(nota.getIdCounter());
        ingreso.setEstado(IngresoCaja.EMITIDO);

        ingreso.setFormaPago(nota.getFormaPago());

        if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
            ingreso.setIdTarjetaCredito(nota.getIdTarjetaCredito());
            ingreso.setNroTarjeta(nota.getNroTarjeta());
        } else if (nota.getFormaPago().equals(FormasPago.CHEQUE)) {
            ingreso.setIdBanco(nota.getIdBanco());
            ingreso.setNroCheque(nota.getNroCheque());
        } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
            ingreso.setNroDeposito(nota.getNroDeposito());
            ingreso.setIdCuentaDeposito(nota.getIdCuentaDeposito());
        }

        return ingreso;

    }

    // YA NO SE USA
    @Override
    public IngresoTransaccion createIngresoCajaTransaccion(Boleto boleto,
            NotaDebito nota, NotaDebitoTransaccion transacciones, IngresoCaja ingreso) throws CRUDException {
        IngresoTransaccion transaccion = new IngresoTransaccion();
        transaccion.setDescripcion(transacciones.getDescripcion());
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setIdIngresoCaja(ingreso);
        transaccion.setIdNotaTransaccion(transacciones.getIdNotaDebitoTransaccion());
        transaccion.setEstado(IngresoTransaccion.EMITIDO);

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            transaccion.setMoneda(Moneda.EXTRANJERA);
            transaccion.setMontoUsd(boleto.getTotalMontoCancelado());
            transaccion.setMontoBs(BigDecimal.ZERO);
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            transaccion.setMoneda(Moneda.NACIONAL);
            transaccion.setMontoBs(boleto.getTotalMontoCancelado());
            transaccion.setMontoUsd(BigDecimal.ZERO);
        }

        return transaccion;
    }

    @Override
    public IngresoTransaccion createIngresoCajaTransaccion(NotaDebitoTransaccion nt,
            NotaDebito nota, IngresoCaja ingreso) throws CRUDException {

        IngresoTransaccion transaccion = new IngresoTransaccion();
        transaccion.setDescripcion(nt.getDescripcion());
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setIdIngresoCaja(ingreso);
        transaccion.setIdNotaTransaccion(nt.getIdNotaDebitoTransaccion());
        transaccion.setEstado(IngresoTransaccion.EMITIDO);
        transaccion.setMoneda(nt.getMoneda());
        transaccion.setMontoBs(nt.getMontoBs());
        transaccion.setMontoUsd(nt.getMontoUsd());

        return transaccion;
    }

    @Override
    public void anularTransaccion(Boleto boleto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarMontosFinalizar(IngresoCaja caja) throws CRUDException {
        
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("IngresoCaja.updateMontosIngresoCajaFromFinalizar");
        spq.setParameter("in_id_ingreso", caja.getIdIngresoCaja());
        spq.executeUpdate();

    }

    
    
}
