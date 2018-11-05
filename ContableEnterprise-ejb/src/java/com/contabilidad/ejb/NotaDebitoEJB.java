/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
import com.agencia.search.dto.BoletoSearchForm;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Operacion;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author xeio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotaDebitoEJB extends FacadeEJB implements NotaDebitoRemote {

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        NotaDebito nd = em.find(NotaDebito.class, e.getId());
        if (nd != null)
        return nd ;
        return new NotaDebito();
    }

    
    @Override
    public NotaDebito createNotaDebito(Boleto boleto) throws CRUDException {

        NotaDebito notaDebito = new NotaDebito();

        notaDebito.setFactorCambiario(boleto.getFactorCambiario());
        notaDebito.setFechaEmision(boleto.getFechaEmision());
        notaDebito.setFechaInsert(DateContable.getCurrentDate());
        notaDebito.setIdCliente(boleto.getIdCliente());
        notaDebito.setIdCounter(boleto.getIdPromotor());
        notaDebito.setIdEmpresa(boleto.getIdEmpresa());
        notaDebito.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        notaDebito.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(DateContable.getCurrentDate(), DateContable.LATIN_AMERICA_FORMAT)));
        notaDebito.setEstado(NotaDebito.EMITIDO);

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
        transaccion.setMoneda(boleto.getMoneda());
        transaccion.setTipo(NotaDebitoTransaccion.Tipo.BOLETO);
        transaccion.setEstado(NotaDebito.EMITIDO);
        transaccion.setIdBoleto(boleto.getIdBoleto());

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

    @Override
    public int anularTransaccion(Boleto boleto) throws CRUDException {

        Query q = em.createNamedQuery("NotaDebitoTransaccion.updateBoletoEstado");
        q.setParameter("idBoleto", boleto.getIdBoleto());
        q.setParameter("estado", ComprobanteContable.ANULADO);
        q.executeUpdate();

        // Actualiza los totales del comprobante Contable. y si ya no tiene 
        //transacciones, anula el Comprobante Contable
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.updateNotaDebito");
        spq.setParameter("in_id_boleto", boleto.getIdBoleto());
        spq.executeUpdate();

        return Operacion.REALIZADA;
    }

    @Override
    public List<NotaDebito> getAllNotaDebito(BoletoSearchForm search) throws CRUDException {

        Optional op = Optional.ofNullable(search.getIdEmpresa());
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = queries.getPropertie(Queries.GET_NOTA_DEBITO_BOLETOS);

        // dentro de q ya esta el WHERE nd.id_empresa = :idEmpresa
        op = Optional.ofNullable(search.getNumeroBoleto());
        if (op.isPresent()) {
            q += " AND bo.numero=?2";
        }

        op = Optional.ofNullable(search.getNotaDebito());
        if (op.isPresent()) {
            q += " AND nd.id_nota_debito=?3";
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            q += " AND nd.fecha_emision>=?4";
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            q += " AND nd.fecha_emision<=?5";
        }

        Query query = em.createNativeQuery(q, NotaDebito.class);

        op = Optional.ofNullable(search.getNumeroBoleto());
        if (op.isPresent()) {
            query.setParameter("2", search.getNumeroBoleto());
        }

        op = Optional.ofNullable(search.getNotaDebito());
        if (op.isPresent()) {
            query.setParameter("3", search.getNotaDebito());
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("4", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("5", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        query.setParameter("1", search.getIdEmpresa());

        System.out.println(query);

        return query.getResultList();

    }

    @Override
    public NotaDebito createNotaDebito(Integer idEmpresa, String usuario) throws CRUDException {

        String date = DateContable.getCurrentDateStr().substring(0, 10);
        NotaDebito nota = new NotaDebito();
        nota.setIdEmpresa(idEmpresa);
        nota.setIdUsuarioCreador(usuario);
        nota.setEstado(NotaDebito.CREADO);
        nota.setFechaInsert(DateContable.getCurrentDate());
        nota.setFechaEmision(DateContable.getCurrentDate());
        nota.setGestion(DateContable.getPartitionDateInt(date));

        nota.setIdNotaDebito(insert(nota));

        return nota;

    }

    @Override
    public List<NotaDebitoTransaccion> getAllTransacciones(Integer idNotaDebito) throws CRUDException {

        Query q = em.createNamedQuery("NotaDebitoTransaccion.findAllByIdNotaDebito", NotaDebitoTransaccion.class);

        q.setParameter("idNotaDebito", idNotaDebito);

        return q.getResultList();
    }

    @Override
    public Integer actualizarMontosNotaDebito(Integer idNotaDebito) throws CRUDException {
        // Se actualizan los montos del Boleto
        StoredProcedureQuery spq2 = em.createNamedStoredProcedureQuery("NotaDebito.updateMontosNotaDebitoEnPendiente");
        spq2.setParameter("in_id_nota_debito", idNotaDebito);

        spq2.executeUpdate();

        return Operacion.REALIZADA;

    }
    
     @Override
    public Integer asociarBoletoNotaDebito(Boleto b, NotaDebito n) throws CRUDException {
        Integer idTransaccion = -1;

        // En la BD crea la transaccion de la nota de debito asociandola con los montos del Boleto
        // Asocia el Boleto con la Transaccion
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.asociarBoletoNotaDebito");
        spq.setParameter("in_id_boleto", b.getIdBoleto());
        spq.setParameter("in_id_nota_debito", n.getIdNotaDebito());
        spq.setParameter("out_id_transacion", idTransaccion);

        spq.executeUpdate();

        

        return Operacion.REALIZADA;
    }


}
