/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
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
import java.util.LinkedList;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class IngresoCajaEJB extends FacadeEJB implements IngresoCajaRemote {

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
            ingreso.setMoneda(Moneda.EXTRANJERA);
            ingreso.setMontoAbonadoUsd(boleto.getTotalMontoCancelado());

        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            ingreso.setMoneda(Moneda.NACIONAL);
            ingreso.setMontoAbonadoBs(boleto.getTotalMontoCancelado());
        }

        if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
            ingreso.setIdTarjetaCredito(boleto.getTarjetaId());
            ingreso.setNroTarjeta(boleto.getTarjetaNumero());
            ingreso.setFormaPago(FormasPago.TARJETA);
        } else if (boleto.getFormaPago().equals(FormasPago.CONTADO)) {
            ingreso.setFormaPago(FormasPago.CONTADO);
            ingreso.setTipoContado(boleto.getContadoTipo());

            if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO)) {
                ingreso.setTipoContado(FormasPago.CONTADO_EFECTIVO);

            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                ingreso.setNroCheque(boleto.getContadoNroCheque());
                ingreso.setIdBanco(boleto.getContadoIdBanco());
                ingreso.setTipoContado(FormasPago.CONTADO_CHEQUE);
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                ingreso.setNroDeposito(boleto.getContadoNroDeposito());
                ingreso.setIdCuentaDeposito(boleto.getContadoIdCuenta());
                ingreso.setTipoContado(FormasPago.CONTADO_DEPOSITO);
            }
        } else if (boleto.getFormaPago().equals(FormasPago.COMBINADO)) {
            ingreso.setFormaPago(FormasPago.COMBINADO);
            if (boleto.getCombinadoContado() == 1) {
                ingreso.setCombinadoContado(new Short("1"));
                if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO)) {
                    ingreso.setTipoContado(FormasPago.CONTADO_EFECTIVO);

                } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                    ingreso.setNroCheque(boleto.getContadoNroCheque());
                    ingreso.setIdBanco(boleto.getContadoIdBanco());
                    ingreso.setTipoContado(FormasPago.CONTADO_CHEQUE);
                } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                    ingreso.setNroDeposito(boleto.getContadoNroDeposito());
                    ingreso.setIdCuentaDeposito(boleto.getContadoIdCuenta());
                    ingreso.setTipoContado(FormasPago.CONTADO_DEPOSITO);
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

    private String getMonedasOnBoletos(LinkedList<Boleto> boleto) {
        boolean nacional = false, extranjera = false;

        for (Boleto b : boleto) {
            if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                extranjera = true;
            } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
                nacional = true;
            }
        }

        if (nacional && extranjera) {
            return Moneda.AMBAS;
        }

        return nacional ? Moneda.NACIONAL : Moneda.EXTRANJERA;

    }

    @Override
    public IngresoCaja createIngresoCaja(LinkedList<Boleto> boleto, NotaDebito nota, LinkedList<NotaDebitoTransaccion> transacciones) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void anularTransaccion(Boleto boleto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
