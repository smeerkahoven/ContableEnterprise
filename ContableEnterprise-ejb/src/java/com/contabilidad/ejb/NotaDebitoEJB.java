/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author xeio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotaDebitoEJB extends FacadeEJB implements NotaDebitoRemote {

    @Override
    public NotaDebito createNotaDebito(Boleto boleto) throws CRUDException {

        NotaDebito notaDebito = new NotaDebito();

        notaDebito.setFactorCambiario(boleto.getFactorCambiario());
        notaDebito.setFechaEmision(boleto.getFechaEmision());
        notaDebito.setFechaInsert(DateContable.getCurrentDate());
        notaDebito.setIdCliente(boleto.getIdCliente().getIdCliente());
        notaDebito.setIdCounter(boleto.getIdPromotor());
        notaDebito.setIdEmpresa(boleto.getIdEmpresa());
        notaDebito.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        notaDebito.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(DateContable.getCurrentDate(), DateContable.LATIN_AMERICA_FORMAT)));

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            notaDebito.setMontoTotalUsd(boleto.getTotalMontoCancelado());
            notaDebito.setMontoAdeudadoUsd(boleto.getTotalMontoCancelado());
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            notaDebito.setMontoTotalBs(boleto.getTotalMontoCancelado());
            notaDebito.setMontoAdeudadoBs(boleto.getTotalMontoCancelado());
        }

        if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
            notaDebito.setIdTarjetaCredito(boleto.getTarjetaId());
            notaDebito.setNroTarjeta(boleto.getTarjetaNumero());
        } else if (boleto.getFormaPago().equals(FormasPago.CONTADO)) {
            notaDebito.setFormaPago(FormasPago.CONTADO);
            notaDebito.setTipoContado(boleto.getContadoTipo());

            if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO)) {
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                notaDebito.setNroCheque(boleto.getContadoNroCheque());
                notaDebito.setIdBanco(boleto.getContadoIdBanco());
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                notaDebito.setNroDeposito(boleto.getContadoNroDeposito());
                notaDebito.setIdCuentaDeposito(boleto.getContadoIdCuenta());
            }
        } else if (boleto.getFormaPago().equals(FormasPago.COMBINADO)) {
            if (boleto.getCombinadoContado() == 1) {
                notaDebito.setCombinadoContado(new Short("1"));
                if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.CONTADO_EFECTIVO);
                } else if (boleto.getCombinadoContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.CONTADO_CHEQUE);
                    notaDebito.setNroCheque(boleto.getCombinadoContadoNroCheque());
                    notaDebito.setIdBanco(boleto.getCombinadoContadoIdBanco());
                } else if (boleto.getCombinadoContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.CONTADO_DEPOSITO);
                    notaDebito.setNroDeposito(boleto.getCombinadoContadoNroDeposito());
                    notaDebito.setIdCuentaDeposito(boleto.getCombinadoContadoIdCuenta());
                }
            }

            if (boleto.getCombinadoCredito() == 1) {
                notaDebito.setCombinadoCredito(new Short("1"));
                notaDebito.setCreditoDias(boleto.getCombinadoCreditoDias());
                notaDebito.setCreditoVencimiento(boleto.getCombinadoCreditoVencimiento());
            }

            if (boleto.getCombinadoTarjeta() == 1) {
                notaDebito.setCombinadoTarjeta(new Short("1"));
                notaDebito.setIdTarjetaCredito(boleto.getCombinadoTarjetaId());
                notaDebito.setNroTarjeta(boleto.getCombinadoTarjetaNumero());
            }
        }

        return notaDebito;
    }

    @Override
    public NotaDebito createNotaDebito(List<Boleto> boleto) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotaDebitoTransaccion createNotaDebitoTransaccion(Boleto boleto, NotaDebito notaDebito) throws CRUDException {
        NotaDebitoTransaccion transaccion = new NotaDebitoTransaccion();

        transaccion.setIdNotaDebito(notaDebito.getIdNotaDebito());
        transaccion.setGestion(notaDebito.getGestion());
        transaccion.setFechaInsert(DateContable.getCurrentDate());

        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        Aerolinea a = em.find(Aerolinea.class, boleto.getIdAerolinea().getIdAerolinea());
        if (a != null) {
            buff.append(a.getNumero());
        }

        buff.append("/");

        //numero boleto
        buff.append("#");
        buff.append(boleto.getNumero());
        buff.append("/");

        //Pasajero
        buff.append(boleto.getNombrePasajero().toUpperCase());

        // Rutas
        buff.append("/");
        buff.append(boleto.getIdRuta1() != null ? boleto.getIdRuta1() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta2() != null ? boleto.getIdRuta2() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta3() != null ? boleto.getIdRuta3() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta4() != null ? boleto.getIdRuta4() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta5() != null ? boleto.getIdRuta5() : "");

        transaccion.setDescripcion(buff.toString());
        // montos

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            transaccion.setMontoUsd(boleto.getTotalMontoCancelado());
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            transaccion.setMontoBs(boleto.getTotalMontoCancelado());
        }

        return transaccion;

    }

    @Override
    public List<NotaDebitoTransaccion> createNotaDebitoTransacction(List<Boleto> boleto, NotaDebito notaDebito) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
