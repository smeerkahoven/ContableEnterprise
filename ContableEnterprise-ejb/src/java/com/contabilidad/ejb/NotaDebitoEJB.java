/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.agencia.search.dto.BoletoSearchForm;
import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.CambioRemote;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.DebitoIngreso;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.NotasCreditoRemote;
import com.contabilidad.remote.PagoAnticipadoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import com.seguridad.utils.Operacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
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

    @EJB
    private IngresoCajaRemote ejbIngresoCaja;

    @EJB
    private CambioRemote ejbCambio;

    @EJB
    private BoletoRemote ejbBoleto;

    @EJB
    private ComprobanteRemote ejbComprobante;
    
    @EJB
    private NotasCreditoRemote ejbNotaCredito ;
    
    @EJB
    private PagoAnticipadoRemote ejbPagoAnticipado ;

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        NotaDebito nd = em.find(NotaDebito.class, e.getId());
        if (nd != null) {
            return nd;
        }
        return new NotaDebito();
    }

    @Override
    public synchronized NotaDebito createNotaDebito(Boleto boleto) throws CRUDException {

        NotaDebito notaDebito = new NotaDebito();

        notaDebito.setFactorCambiario(boleto.getFactorCambiario());
        notaDebito.setFechaEmision(boleto.getFechaEmision());
        notaDebito.setFechaInsert(DateContable.getCurrentDate());
        notaDebito.setIdCliente(boleto.getIdCliente());
        notaDebito.setIdCounter(boleto.getIdPromotor());
        notaDebito.setIdEmpresa(boleto.getIdEmpresa());
        notaDebito.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        notaDebito.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(DateContable.getCurrentDate(), DateContable.LATIN_AMERICA_FORMAT)));
        notaDebito.setEstado(Estado.EMITIDO);

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            notaDebito.setMontoTotalUsd(boleto.getTotalMontoCobrar());
            notaDebito.setMontoAdeudadoUsd(boleto.getTotalMontoCobrar());
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            notaDebito.setMontoTotalBs(boleto.getTotalMontoCobrar());
            notaDebito.setMontoAdeudadoBs(boleto.getTotalMontoCobrar());
        }

        if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
            notaDebito.setIdTarjetaCredito(boleto.getTarjetaId());
            notaDebito.setNroTarjeta(boleto.getTarjetaNumero());
        } else if (boleto.getFormaPago().equals(FormasPago.CONTADO)) {
            notaDebito.setFormaPago(FormasPago.CONTADO);

            if (boleto.getContadoTipo().equals(FormasPago.EFECTIVO)) {
            } else if (boleto.getContadoTipo().equals(FormasPago.CHEQUE)) {
                notaDebito.setNroCheque(boleto.getContadoNroCheque());
                notaDebito.setIdBanco(boleto.getContadoIdBanco());
            } else if (boleto.getContadoTipo().equals(FormasPago.DEPOSITO)) {
                notaDebito.setNroDeposito(boleto.getContadoNroDeposito());
                notaDebito.setIdCuentaDeposito(boleto.getContadoIdCuenta());
            }
        } else if (boleto.getFormaPago().equals(FormasPago.COMBINADO)) {
            if (boleto.getCombinadoContado() == 1) {
                notaDebito.setCombinadoContado(new Short("1"));
                if (boleto.getContadoTipo().equals(FormasPago.EFECTIVO)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.EFECTIVO);
                } else if (boleto.getCombinadoContadoTipo().equals(FormasPago.CHEQUE)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.CHEQUE);
                    notaDebito.setNroCheque(boleto.getCombinadoContadoNroCheque());
                    notaDebito.setIdBanco(boleto.getCombinadoContadoIdBanco());
                } else if (boleto.getCombinadoContadoTipo().equals(FormasPago.DEPOSITO)) {
                    notaDebito.setCombinadoContadoTipo(FormasPago.DEPOSITO);
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
    public NotaDebitoTransaccion createNotaDebitoTransaccion(Boleto boleto, NotaDebito notaDebito) throws CRUDException {
        NotaDebitoTransaccion transaccion = new NotaDebitoTransaccion();

        transaccion.setIdNotaDebito(notaDebito);
        transaccion.setGestion(notaDebito.getGestion());
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setMoneda(boleto.getMoneda());
        transaccion.setTipo(NotaDebitoTransaccion.Tipo.BOLETO);
        transaccion.setEstado(Estado.EMITIDO);
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
            transaccion.setMontoUsd(boleto.getTotalMontoCobrar());
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            transaccion.setMontoBs(boleto.getTotalMontoCobrar());
        }

        return transaccion;

    }

    @Override
    public List<NotaDebito> getAllByCliente(Integer idCliente) throws CRUDException {

        Optional op = Optional.ofNullable(idCliente);
        if (!op.isPresent()) {
            throw new CRUDException("No existe el Cliente con el id %s.".replace("%s", idCliente.toString()));
        }

        List<NotaDebito> l = (List<NotaDebito>) em.createNamedQuery("NotaDebito.findAllByIdCliente");

        if (l.isEmpty()) {
            throw new CRUDException("No existen Notas de Debito para el Cliente Ingresado");
        }

        return l;

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
            if (search.getFechaInicio().trim().length() > 1) {
                q += " AND nd.fecha_emision>=?4";
            }
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            if (search.getFechaFin().trim().length() > 1) {
                q += " AND nd.fecha_emision<=?5";
            }
        }

        op = Optional.ofNullable(search.getNombrePasajero());
        if (op.isPresent()) {
            q += " AND bo.nombre_pasajero like ?6 ";
        }

        q += " ORDER BY nd.id_nota_debito";

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

        op = Optional.ofNullable(search.getNombrePasajero());
        if (op.isPresent()) {
            query.setParameter("6", "%" + search.getNombrePasajero() + "%");
        }

        query.setParameter("1", search.getIdEmpresa());

        System.out.println("Query:" + q);

        return query.getResultList();

    }

    @Override
    public NotaDebito createNotaDebito(Integer idEmpresa, String usuario) throws CRUDException {

        String date = DateContable.getCurrentDateStr().substring(0, 10);
        NotaDebito nota = new NotaDebito();
        nota.setIdEmpresa(idEmpresa);
        nota.setIdUsuarioCreador(usuario);
        nota.setEstado(Estado.CREADO);
        nota.setFechaInsert(DateContable.getCurrentDate());
        nota.setFechaEmision(DateContable.getCurrentDate());
        nota.setGestion(DateContable.getPartitionDateInt(date));
        nota.setFormaPago(FormasPago.EFECTIVO);

        String fechaEmision = DateContable.getCurrentDateStr(DateContable.LATIN_AMERICA_FORMAT);
        CambioDolar diario = ejbCambio.get(fechaEmision, "CambioDolar.findFecha");

        if (diario == null) {
            List l = ejbCambio.get();
            if (l.isEmpty()) {
                throw new CRUDException("No existe Cambio de Dolar para la fecha Actual");
            } else {
                //Tomamos
                diario = (CambioDolar) l.get(l.size() - 1);
            }
        }

        nota.setFactorCambiario(diario.getValor());
        nota.setIdNotaDebito(insert(nota));

        return nota;

    }

    @Override
    public List<NotaDebitoTransaccion> getAllTransacciones(Integer idNotaDebito) throws CRUDException {

        NotaDebito fromDb = em.find(NotaDebito.class, idNotaDebito);
        Optional op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe la Nota de Debito %s.".replace("%s", idNotaDebito.toString()));
        }

        Query q = em.createNamedQuery("NotaDebitoTransaccion.findAllByIdNotaDebito", NotaDebitoTransaccion.class);

        q.setParameter("idNotaDebito", fromDb);

        return q.getResultList();
    }

    @Override
    public Integer actualizarMontosNotaDebito(Integer idNotaDebito) throws CRUDException {
        // Se actualizan los montos del Boleto de acuerdo al idNotaDebito
        // actualizara solo los que esten en P
        StoredProcedureQuery spq2 = em.createNamedStoredProcedureQuery("NotaDebito.updateMontosNotaDebitoEnPendiente");
        spq2.setParameter("in_id_nota_debito", idNotaDebito);

        spq2.executeUpdate();

        return Operacion.REALIZADA;

    }

    @Override
    public Integer actualizarMontosNotaDebitoEmitida(Integer idNotaDebito) throws CRUDException {
        // Se actualizan los montos del Boleto de acuerdo al idNotaDebito
        // actualizara solo los que esten en P
        StoredProcedureQuery spq2 = em.createNamedStoredProcedureQuery("NotaDebito.updateMontosNotaDebitoEmitidos");
        spq2.setParameter("in_id_nota_debito", idNotaDebito);

        spq2.executeUpdate();

        return Operacion.REALIZADA;

    }
    
    @Override
    public Boleto asociarBoletoNotaDebito( NotaDebito n, Integer idBoleto, String user) throws CRUDException {
        
        Boleto bFromDb = em.find(Boleto.class, idBoleto);
        
        if (bFromDb == null){
            throw new CRUDException("No existe el boleto %s".replace("%s", idBoleto.toString()));
        }
        
        if (bFromDb.getIdNotaDebito() != null) {
            throw new CRUDException("El Boleto ya se encuentra asociado a la nota de Debito %s.".replace("%s", bFromDb.getIdNotaDebito().toString()));
        }
        
        Integer idTransaccion = -1;

        // En la BD crea la transaccion de la nota de debito asociandola con los montos del Boleto
        // Asocia el Boleto con la Transaccion
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.asociarBoletoNotaDebito");
        spq.setParameter("in_id_boleto", bFromDb.getIdBoleto());
        spq.setParameter("in_id_nota_debito", n.getIdNotaDebito());
        spq.setParameter("in_id_cliente", n.getIdCliente().getIdCliente());
        spq.setParameter("in_id_counter", n.getIdCounter().getIdPromotor());
        spq.setParameter("in_factor", n.getFactorCambiario());
        spq.setParameter("in_usuario_creador", bFromDb.getIdUsuarioCreador());
        spq.setParameter("out_id_transacion", idTransaccion);

        System.out.println("factor:" + n.getFactorCambiario());

        spq.executeUpdate();

        return bFromDb;
    }

    @Override
    public Boleto asociarBoletoNotaDebitoManual( NotaDebito n, Boleto b) throws CRUDException {
        
        Integer idTransaccion = -1;
        // En la BD crea la transaccion de la nota de debito asociandola con los montos del Boleto
        // Asocia el Boleto con la Transaccion
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.asociarBoletoNotaDebito");
        spq.setParameter("in_id_boleto", b.getIdBoleto());
        spq.setParameter("in_id_nota_debito", n.getIdNotaDebito());
        spq.setParameter("in_id_cliente", n.getIdCliente().getIdCliente());
        spq.setParameter("in_id_counter", n.getIdCounter().getIdPromotor());
        spq.setParameter("in_factor", n.getFactorCambiario());
        spq.setParameter("in_usuario_creador", b.getIdUsuarioCreador());
        spq.setParameter("out_id_transacion", idTransaccion);

        System.out.println("factor:" + n.getFactorCambiario());

        spq.executeUpdate();

        return b;
    }

    @Override
    public Integer updateBoletoNotaDebito(Boleto b, NotaDebito n) throws CRUDException {

        // En la BD crea la transaccion de la nota de debito asociandola con los montos del Boleto
        // Asocia el Boleto con la Transaccion
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.updateBoletoNotaDebito");
        spq.setParameter("in_id_boleto", b.getIdBoleto());
        spq.setParameter("in_id_nota_transaccion", b.getIdNotaDebitoTransaccion());

        spq.executeUpdate();

        return Operacion.REALIZADA;
    }

    @Override
    public void pendiente(NotaDebito n) throws CRUDException {

        NotaDebito fromDB = em.find(NotaDebito.class, n.getIdNotaDebito());
        Optional op = Optional.ofNullable(fromDB);
        if (!op.isPresent()) {
            throw new CRUDException("La Nota de Debito %s No existe".replace("%s", n.getIdNotaDebito().toString()));
        }

        if (fromDB.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La Nota de Debito %s se encuentra ANULADA. No se puede pasar a PENDIENTE".replace("%s", fromDB.getIdNotaDebito().toString()));
        }

        if (fromDB.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("La Nota de Debito %s se encuentra EMITIDA. No se puede pasar a PENDIENTE".replace("%s", fromDB.getIdNotaDebito().toString()));
        }

        if (fromDB.getEstado().equals(Estado.CANCELADO)) {
            throw new CRUDException("La Nota de Debito %s se encuentra CANCELADA. No se puede pasar a PENDIENTE".replace("%s", fromDB.getIdNotaDebito().toString()));
        }

        Query q = em.createNativeQuery(queries.getPropertie(Queries.UPDATE_NOTA_DEBITO_ESTADO));

        q.setParameter("1", n.getIdNotaDebito());
        q.setParameter("2", n.getIdCliente().getIdCliente());
        q.setParameter("3", n.getIdCounter().getIdPromotor());
        q.setParameter("4", n.getFechaEmision());
        q.setParameter("5", n.getFactorCambiario());
        q.setParameter("6", Estado.PENDIENTE);
        q.setParameter("7", n.getFormaPago());
        q.setParameter("8", n.getIdBanco());
        q.setParameter("9", n.getNroCheque());
        q.setParameter("10", n.getIdTarjetaCredito());
        q.setParameter("11", n.getNroTarjeta());
        q.setParameter("12", n.getNroDeposito());
        q.setParameter("13", n.getIdCuentaDeposito());
        q.setParameter("14", n.getCreditoDias());
        q.setParameter("15", n.getCreditoVencimiento());
        q.setParameter("16", DateContable.getDateFormat(n.getFechaEmision(), DateContable.PARTITION_FORMAT));

        q.executeUpdate();
    }

    @Override
    public CargoBoleto saveCargo(CargoBoleto cargo) throws CRUDException {
        // 1. Insertamo el cargo
        cargo.setIdCargo(insert(cargo));

        NotaDebitoTransaccion transaccion = new NotaDebitoTransaccion();
        transaccion.setDescripcion(cargo.getConcepto());
        transaccion.setGestion(DateContable.getPartitionDateInt(DateContable.getCurrentDateStr(DateContable.LATIN_AMERICA_FORMAT)));
        transaccion.setEstado(Estado.PENDIENTE);
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setIdCargo(cargo.getIdCargo());

        NotaDebito ndFromDb = em.find(NotaDebito.class, cargo.getIdNotaDebito());
        if (ndFromDb == null) {
            throw new CRUDException("El cargo no tiene asociado una Nota de debito");
        }
        transaccion.setIdNotaDebito(ndFromDb);

        transaccion.setMoneda(cargo.getMoneda());
        if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            transaccion.setMontoBs(cargo.getComisionAgencia());
            transaccion.setMontoBs(transaccion.getMontoBs().add(cargo.getComisionMayorista()));
            transaccion.setMontoBs(transaccion.getMontoBs().add(cargo.getComisionPromotor()));
            transaccion.setMontoAdeudadoBs(transaccion.getMontoBs());
        } else {
            transaccion.setMontoUsd(cargo.getComisionAgencia());
            transaccion.setMontoUsd(transaccion.getMontoUsd().add(cargo.getComisionMayorista()));
            transaccion.setMontoUsd(transaccion.getMontoUsd().add(cargo.getComisionPromotor()));
            transaccion.setMontoAdeudadoUsd(transaccion.getMontoUsd());
        }
        transaccion.setTipo(cargo.getTipo());

        //2. Insertramos la transaccion
        transaccion.setIdNotaDebitoTransaccion(insert(transaccion));

        //3. Actualizamos la transaccion al cargo
        cargo.setIdNotaDebitoTransaccion(transaccion.getIdNotaDebitoTransaccion());
        update(cargo);

        //4. Actualizamos los montos de la Nota de Debito
        actualizarMontosNotaDebito(cargo.getIdNotaDebito());

        return cargo;
    }
    
    
    @Override
       public CargoBoleto updateCargo(CargoBoleto cargo) throws CRUDException {
           CargoBoleto fromDb = em.find(CargoBoleto.class, cargo.getIdCargo());
          Optional op = Optional.ofNullable(fromDb);
          
           if (!op.isPresent()){
               throw new CRUDException("No se encontra el Cargo del Boleto para actualizar.");
           }
        // 1. Insertamo el cargo
        cargo.setIdCargo(insert(cargo));

        NotaDebitoTransaccion trxFromDb = em.find(NotaDebitoTransaccion.class, fromDb.getIdNotaDebitoTransaccion());
        op = Optional.ofNullable(trxFromDb);
        if (!op.isPresent()){
            throw new CRUDException("No se encuentra la transaccion de la Nota de Debito para actualizar.");
        }
        
        trxFromDb.setDescripcion(cargo.getConcepto());
        trxFromDb.setGestion(DateContable.getPartitionDateInt(DateContable.getCurrentDateStr(DateContable.LATIN_AMERICA_FORMAT)));
        trxFromDb.setEstado(Estado.PENDIENTE);
        trxFromDb.setIdCargo(cargo.getIdCargo());


        trxFromDb.setMoneda(cargo.getMoneda());
        if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            trxFromDb.setMontoBs(cargo.getComisionAgencia());
            trxFromDb.setMontoBs(trxFromDb.getMontoBs().add(cargo.getComisionMayorista()));
            trxFromDb.setMontoBs(trxFromDb.getMontoBs().add(cargo.getComisionPromotor()));
            trxFromDb.setMontoAdeudadoBs(trxFromDb.getMontoBs());
        } else {
            trxFromDb.setMontoUsd(cargo.getComisionAgencia());
            trxFromDb.setMontoUsd(trxFromDb.getMontoUsd().add(cargo.getComisionMayorista()));
            trxFromDb.setMontoUsd(trxFromDb.getMontoUsd().add(cargo.getComisionPromotor()));
            trxFromDb.setMontoAdeudadoUsd(trxFromDb.getMontoUsd());
        }
        trxFromDb.setTipo(cargo.getTipo());

        //2. ACtualizamos la transaccion
        update(trxFromDb);

        //3. Actualizamos la transaccion al cargo
        update(cargo);

        //4. Actualizamos los montos de la Nota de Debito
        actualizarMontosNotaDebito(cargo.getIdNotaDebito());

        return cargo;
    }


    @Override
    public CargoBoleto getCargo(CargoBoleto cargo) throws CRUDException {

        CargoBoleto c = em.find(CargoBoleto.class, cargo.getIdCargo());
        Optional o = Optional.ofNullable(c);

        if (o.isPresent()) {
            return c;
        }

        return null;
    }

    @Override
    public ContabilidadBoletaje getConfiguracion(Integer idEmpresa) throws CRUDException {
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("idEmpresa", idEmpresa);
        List lconf = ejbComprobante.get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);

        if (lconf.isEmpty()) {
            throw new CRUDException("Los parametros de Contabilidad para la empresa no estan Configurados");
        }

        ContabilidadBoletaje conf = (ContabilidadBoletaje) lconf.get(0);

        return conf;
    }

    @Override
    public synchronized boolean finalizar(NotaDebito nota) throws CRUDException {

        NotaDebito fromBD = em.find(NotaDebito.class, nota.getId());
        Optional op = Optional.ofNullable(fromBD);
        if (!op.isPresent()) {
            throw new CRUDException("No se encontro la Nota de Debito");
        }

        if (fromBD.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La nota de Debito : %s Se encuentra ANULADA".replace("%s", fromBD.getIdNotaDebito().toString()));
        } else if (fromBD.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("La nota de Debito : %s Se encuentra EMITIDA".replace("%s", fromBD.getIdNotaDebito().toString()));
        } else if (fromBD.getEstado().equals(Estado.CANCELADO)) {
            throw new CRUDException("La nota de Debito : %s Se encuentra CANCELADA".replace("%s", fromBD.getIdNotaDebito().toString()));
        } else if (fromBD.getEstado().equals(Estado.EN_MORA)) {
            throw new CRUDException("La nota de Debito : %s Se encuentra EN MORA".replace("%s", fromBD.getIdNotaDebito().toString()));
        }

        fromBD.setIdCliente(nota.getIdCliente());
        fromBD.setIdCounter(nota.getIdCounter());
        fromBD.setFechaEmision(nota.getFechaEmision());
        fromBD.setFactorCambiario(nota.getFactorCambiario());
        int gestion = DateContable.getPartitionDateInt(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
        fromBD.setGestion(gestion);
        fromBD.setFormaPago(nota.getFormaPago());

        fromBD.setCreditoDias(nota.getCreditoDias());
        fromBD.setIdBanco(nota.getIdBanco());
        fromBD.setIdCuentaDeposito(nota.getIdCuentaDeposito());
        fromBD.setIdTarjetaCredito(nota.getIdTarjetaCredito());
        fromBD.setNroTarjeta(nota.getNroTarjeta());
        fromBD.setNroCheque(nota.getNroCheque());
        fromBD.setNroDeposito(nota.getNroDeposito());
        fromBD.setCreditoVencimiento(nota.getCreditoVencimiento());

        // 1. Obtenemos las transacciones de las notas de debito
        Query q = em.createNamedQuery("NotaDebitoTransaccion.findAllByIdNotaDebitoAndPendientes", NotaDebitoTransaccion.class);

        q.setParameter("idNotaDebito", nota);

        List<NotaDebitoTransaccion> list = q.getResultList();

        ContabilidadBoletaje conf = getConfiguracion(nota.getIdEmpresa());

        int cntBoletos = 0, cntPaquetes = 0, cntAlquileres = 0, cntSeguro = 0;

        try {
            if (ejbBoleto.validarConfiguracion(conf)) {

                //validamos que todos los boletos esten configurada la linea aerea
                for (NotaDebitoTransaccion nt : list) {
                    if (nt.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                        cntBoletos++;
                        Boleto b = (Boleto) ejbBoleto.get(nt.getIdBoleto(), Boleto.class);
                        AerolineaCuenta av = ejbBoleto.getAerolineCuenta(b, "V");
                        if (av == null) {
                            throw new CRUDException("No existe cuenta asociada a la Aerolinea %s para Ventas".replace("%s", b.getIdAerolinea().getNumero()));
                        }

                        AerolineaCuenta ac = ejbBoleto.getAerolineCuenta(b, "C");
                        if (ac == null) {
                            throw new CRUDException("No existe cuenta asociada a la Aerolinea %s para Comisiones".replace("%s", b.getIdAerolinea().getNumero()));
                        }
                    } else if (nt.getTipo().equals(NotaDebitoTransaccion.Tipo.ALQUILER_AUTO)) {
                        cntAlquileres++;
                    } else if (nt.getTipo().equals(NotaDebitoTransaccion.Tipo.PAQUETE)) {
                        cntPaquetes++;
                    } else if (nt.getTipo().equals(NotaDebitoTransaccion.Tipo.SEGURO)) {
                        cntSeguro++;
                    }

                    if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                            || nota.getFormaPago().equals(FormasPago.CHEQUE)
                            || nota.getFormaPago().equals(FormasPago.DEPOSITO)
                            || nota.getFormaPago().equals(FormasPago.TARJETA)) {
                        nt.setEstado(Estado.CANCELADO);
                    } else {
                        nt.setEstado(Estado.EMITIDO);
                    }
                }

                if (nota.getFormaPago() == null) {
                    throw new CRUDException("No se ha establecido una forma de pago para la nota de debito: " + nota.getIdNotaDebito());
                }

                //Pasamos a Contabilidad
                //ComprobanteDiario
                ComprobanteContable diario = null;//= ejbComprobante.createAsientoDiarioBoleto(boleto);
                ComprobanteContable ingreso = null;//= ejbComprobante.createAsientoDiarioBoleto(boleto);
                IngresoCaja caja = null;
                DebitoIngreso debitoIngreso = null;

                boolean insertRecibo = false;

                StringBuffer glosa = getglosa(cntBoletos, cntPaquetes, cntAlquileres, cntSeguro);

                if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                        || nota.getFormaPago().equals(FormasPago.CHEQUE)
                        || nota.getFormaPago().equals(FormasPago.DEPOSITO)
                        || nota.getFormaPago().equals(FormasPago.TARJETA)) {
                    insertRecibo = true;

                    caja = ejbIngresoCaja.createIngresoCaja(nota);
                    caja.setIdIngresoCaja(insert(caja));

                    ingreso = ejbComprobante.createComprobante(ComprobanteContable.Tipo.COMPROBANTE_INGRESO,
                            glosa.toString(), nota);
                    ingreso.setIdLibro(insert(ingreso));

                    // COn esto solucionamos el problema de la nota de debito y los ingresos de Caja
                    debitoIngreso = ejbIngresoCaja.createDebitoIngresoCaja(nota, caja);
                    debitoIngreso.setIdDebitoIngreso(insert(debitoIngreso));

                    fromBD.setEstado(Estado.CANCELADO);
                    fromBD.setMontoAdeudadoBs(BigDecimal.ZERO);
                    fromBD.setMontoAdeudadoUsd(BigDecimal.ZERO);
                } else {
                    fromBD.setEstado(Estado.EMITIDO);
                }

                for (NotaDebitoTransaccion nt : list) {
                    nt = em.find(NotaDebitoTransaccion.class, nt.getIdNotaDebitoTransaccion());
                    //Si es Boleto
                    if (nt.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                        Boleto boleto = (Boleto) ejbBoleto.get(nt.getIdBoleto(), Boleto.class);
                        op = Optional.ofNullable(boleto);
                        if (op.isPresent()) {
                            try {

                                if (boleto.getTipoBoleto().equals(Boleto.Tipo.AMADEUS)
                                        || boleto.getTipoBoleto().equals(Boleto.Tipo.SABRE)
                                        || boleto.getTipoBoleto().equals(Boleto.Tipo.MANUAL)) {

                                    if (diario == null) {
                                        diario = ejbComprobante.createAsientoDiarioBoleto(nota, glosa.toString());
                                        diario.setIdLibro(insert(diario));
                                    }
                                    boleto.setIdLibro(diario.getIdLibro());
                                    //Creamos el asiento diario para el boleto
                                    ejbBoleto.enviarAsientoDiario(boleto, nota, diario, conf);

                                    if (caja != null) {
                                        //Al boleto no le sirve 1 solo ingreso de caja
                                        //boleto.setIdIngresoCaja(caja.getIdIngresoCaja());
                                        //creamos la transaccion para el ingreso de caja
                                        IngresoTransaccion ing = ejbIngresoCaja.createIngresoCajaTransaccion(nt, nota, caja);
                                        ing.setIdTransaccion(insert(ing));

                                        //Al boleto no le sirve un solo ingreso de caja
                                        //boleto.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                        AsientoContable ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(ingreso, conf, nt, nota, boleto);
                                        //ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                        ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing);
                                        insert(ingTotalCancelarCaja);

                                        AsientoContable ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(ingreso, conf, nt, nota, boleto);
                                        //ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                        ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing);
                                        insert(ingTotalCancelarHaber);
                                    }
                                }
                            } catch (CRUDException ex) {
                                throw new CRUDException(ex.getMessage() + " Nro Nota Debito : " + nota.getIdNotaDebito());
                                //agregar logs que el boleto ya existe.
                            }

                            if (insertRecibo) {
                                boleto.setEstado(Boleto.Estado.CANCELADO);
                            } else {
                                boleto.setEstado(Boleto.Estado.EMITIDO);
                            }
                        }
                    } else {
                        //Si es un paquete, alquiler, u otro
                        CargoBoleto cargo = (CargoBoleto) get(nt.getIdCargo(), CargoBoleto.class);
                        op = Optional.ofNullable(cargo);
                        if (op.isPresent()) {
                            try {
                                if (diario == null) {
                                    diario = ejbComprobante.createAsientoDiarioBoleto(nota, glosa.toString());
                                    diario.setIdLibro(insert(diario));
                                }
                                cargo.setIdLibro(diario.getIdLibro());

                                //Creamos el asiento diario para el cargo
                                ejbBoleto.enviarAsientoDiario(cargo, nota, diario, conf);

                                if (caja != null) {
                                    IngresoTransaccion ing = ejbIngresoCaja.createIngresoCajaTransaccion(nt, nota, caja);
                                    ing.setIdTransaccion(insert(ing));

                                    //cargo.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                    AsientoContable ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(ingreso, conf, nt, nota, cargo);
                                    //ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                    ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing);
                                    insert(ingTotalCancelarCaja);

                                    AsientoContable ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(ingreso, conf, nt, nota, cargo);
                                    //ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                    ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing);
                                    insert(ingTotalCancelarHaber);
                                }
                            } catch (CRUDException ex) {
                                throw new CRUDException(ex.getMessage() + " Nro Nota Debito : " + nota.getIdNotaDebito());
                            }

                            if (insertRecibo) {
                                cargo.setEstado(Boleto.Estado.CANCELADO);
                            } else {
                                cargo.setEstado(Boleto.Estado.EMITIDO);
                            }
                        }
                    }
                }
                // Actualizamos el comprobante contable de los AD de los Boletos
                ejbComprobante.actualizarMontosFinalizar(diario);
                if (caja != null) {
                    System.out.println("Actualizando Montos Comprobante para %s.".replace("%s", ingreso.getIdLibro().toString()));
                    // Actualizamos el comprobante contable de los CI de los Boletos
                    ejbComprobante.actualizarMontosFinalizar(ingreso);
                    System.out.println("Actualizando Montos Ingreso a Caja para %s.".replace("%s", caja.getIdIngresoCaja().toString()));
                    // Actualizamos los montos del ingreso de Caja                    
                    ejbIngresoCaja.actualizarMontosFinalizar(caja);
                }

                // Actualizamos el Comprobante Contable de Ingreso CI
                // Actualizamos los montos del Recibo de Caja -- NO es necesario, ya que se crean de arriba
                //EFECTIVO CHEQUE 
                return true;
            } else {
                return false;
            }

        } catch (CRUDException ex) {
            throw new CRUDException(ex.getMessage() + " : Nro Nota Debito : " + nota.getIdNotaDebito());
        }

    }

    private StringBuffer getglosa(int cntBoletos, int cntPaquete, int cntAlquiler, int cntSeguro) {
        StringBuffer glosa = new StringBuffer("EMISION :");
        if (cntBoletos > 0) {
            glosa.append(cntBoletos);
            glosa.append(" BOLETO(S), ");
        }
        if (cntPaquete > 0) {
            glosa.append(cntPaquete);
            glosa.append(" PAQUETE(S), ");
        }
        if (cntAlquiler > 0) {
            glosa.append(cntAlquiler);
            glosa.append(" ALQUILER(ES), ");
        }
        if (cntSeguro > 0) {
            glosa.append(cntSeguro);
            glosa.append(" SEGURO(S)");
        }

        return glosa;
    }

    @Override
    public NotaDebitoTransaccion getNotaDebitoTransaccion(Integer idNotaTransaccion) throws CRUDException {

        if (idNotaTransaccion == null) {
            return null;

        }

        NotaDebitoTransaccion nt = em.find(NotaDebitoTransaccion.class,
                idNotaTransaccion);
        Optional op = Optional.ofNullable(nt);
        if (op.isPresent()) {
            return nt;
        }
        return null;
    }

    //TODO falta al anular una nota de debito que anule la Nota de Credito
    @Override
    public synchronized void anularNotaDebito(NotaDebito nota , String usuario) throws CRUDException {

        Optional op = Optional.ofNullable(nota.getIdNotaDebito());

        if (op.isPresent()) {

            NotaDebito fromDB = em.find(NotaDebito.class,
                    nota.getIdNotaDebito());
            op = Optional.ofNullable(fromDB);

            if (op.isPresent()) {
                if (!fromDB.getEstado().equals(Estado.ANULADO)) {
                    //obtenemos las transacciones de la nota de debito
                    List<NotaDebitoTransaccion> l = getAllTransacciones(fromDB.getIdNotaDebito());
                    for (NotaDebitoTransaccion n : l) {
                        try {
                            anularTransaccion(n, usuario);
                            
                            /*ejbIngresoCaja.anularTransaccion(n, usuario);
                            
                            ejbNotaCredito.anularTransaccion(n, usuario);
                            
                            ejbPagoAnticipado.anularTransaccion(n, usuario);*/
                            
                        } catch (CRUDException ex2) {
                            // aqui no hacemos nada ya que anular transaccion enviara la notificacion
                        }
                    }
                    //Obtenemos los Ingresos a Caja
                    
                    /*List<IngresoTransaccion> lictr = ejbIngresoCaja.getIngresoCajaTrxByIdNotaDebito(fromDB);
                    for (IngresoTransaccion it : lictr) {
                        ejbIngresoCaja.anularTransaccion(it, usuario);
                    }*/
                    
                    //TODO
                    //Anular las transacciones de las Notas de Credito
                    /*List<NotaCreditoTransaccion> lnctr =ejbNotaCredito.getNotaCreditoTransaccionByNotaDebito(fromDB);

                    for (NotaCreditoTransaccion nctr : lnctr){
                        ejbNotaCredito.anularTransaccion(nctr, usuario);
                    }*/
                    //TODO
                    //Pago Anticipado 100
                    // Pago Anticipado Transaccion 100
                    //DEvolucion
                    /*List<PagoAnticipadoTransaccion> lpatr = ejbPagoAnticipado.getPagoAnticipadoTransaccionByNotaDebito(fromDB);
                    for (PagoAnticipadoTransaccion patr : lpatr){
                        ejbPagoAnticipado.anularTransaccion(patr, usuario);
                    }*/
                    
                    //Obtenemos los Comprobantes Contables
                    List<ComprobanteContable> lcc = ejbComprobante.getComprobantesByNotaDebito(fromDB.getIdNotaDebito());
                    for (ComprobanteContable cc : lcc) {
                        cc.setEstado(ComprobanteContable.ANULADO);
                        em.merge(cc);
                    }
                    
                    
                    fromDB.setEstado(Estado.ANULADO);

                    em.flush();

                } else {
                    throw new CRUDException("La Nota de Debito: %s se encuentra ANULADA.".replace("%s", nota.getIdNotaDebito().toString()));
                }
            } else {
                throw new CRUDException("No se encontro la Nota de Debito : " + nota.getIdNotaDebito());
            }
        } else {
            throw new CRUDException("No se especifico la Nota de Debito");
        }
    }

    @Override
    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException {

        NotaDebitoTransaccion fromDb = em.find(NotaDebitoTransaccion.class,
                tr.getIdNotaDebitoTransaccion());

        Optional op = Optional.ofNullable(fromDb);
        if (op.isPresent()) {
            if (!fromDb.getEstado().equals(Estado.ANULADO)) {
                //anular los ingresos de caja de la transaccion
                if (fromDb.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                    //anular boleto
                    try {
                        ejbBoleto.anularBoleto(fromDb.getIdBoleto());
                    } catch (CRUDException ex) {

                    }
                } else {
                    //anular cargo
                    try {
                        ejbBoleto.anularCargo(fromDb.getIdCargo());
                    } catch (CRUDException ex) {

                    }
                }

                fromDb.setEstado(Estado.ANULADO);
                em.merge(fromDb);

                //anular Transaccion del Ingreso a Caja
                try {
                    ejbIngresoCaja.anularTransaccion(fromDb, usuario);
                } catch (CRUDException ex) {

                }
                
                // Anular Transaccion de las Notas de Credito
                ejbNotaCredito.anularTransaccion(fromDb, usuario);
                // Anular TRansaccion de los Pagos Anticiapdos
                
                ejbPagoAnticipado.anularTransaccion(tr, usuario);
                
                //anular los asientos Contables
                try {
                    ejbComprobante.anularAsientosContables(fromDb,  usuario);
                } catch (CRUDException ex) {

                }

                //updateMontosNotaDebito(fromDb.getIdNotaDebito().getIdNotaDebito());
            } else {
                throw new CRUDException("La transaccion %s se encuentra ANULADA.".replace("%s", fromDb.getIdNotaDebitoTransaccion().toString()));
            }
        } else {
            throw new CRUDException("No se especifico la Transaccion de la Nota de Debito");
        }
    }

    public void anularTransaccionNotaDebito(NotaDebitoTransaccion tr) throws CRUDException {

        Optional op = Optional.ofNullable(tr);
        if (op.isPresent()) {
            if (!tr.getEstado().equals(Estado.ANULADO)) {
                //anular los ingresos de caja de la transaccion
                if (tr.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                    //anular boleto
                    ejbBoleto.anularBoleto(tr.getIdBoleto());
                } else {
                    //anular cargo
                    ejbBoleto.anularCargo(tr.getIdCargo());
                }

            } else {
                //throw new CRUDException("La transaccion %s se encuentra ANULADA.".replace("%s", tr.getIdNotaDebitoTransaccion().toString()));
            }
        } else {
            //throw new CRUDException("No se especifico la Transaccion de la Nota de Debito");
        }
    }

    private void updateMontosNotaDebito(Integer idNotaDebito) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.updateNotaDebitoAnularTransaccion");
        spq.setParameter("in_id_nota_debito", idNotaDebito);
        spq.executeUpdate();
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
    public List<NotaDebitoTransaccion> getAllNotaDebitoCreditoByCliente(Integer idCliente, Integer idEmpresa) throws CRUDException {

        Cliente fromDbCl = em.find(Cliente.class, idCliente);
        Optional op = Optional.ofNullable(fromDbCl);
        if (!op.isPresent()) {
            throw new CRUDException("No existe el Cliente %s.".replace("%s", idCliente.toString()));
        }

        Query q = em.createNativeQuery(queries.getPropertie(Queries.GET_NOTA_DEBITO_TRANSACCIONES_CREDITO_DEBE), NotaDebitoTransaccion.class);
        q.setParameter("1", idCliente);
        q.setParameter("2", idEmpresa);

        List<NotaDebitoTransaccion> r = q.getResultList();

        return r;
    }

    @Override
    public void actualizarMontosAdeudadosTransaccion(IngresoTransaccion trx) throws CRUDException {
        /*BEGIN  */
        // Calculo de la reduccion del monto adeudado sea cual sea la moneda del pago

        // del ingreso de caja
        NotaDebitoTransaccion notaFromDb = em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        Optional op = Optional.ofNullable(notaFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado la Nota de Debito para la transaccion del ingreso de Caja.");
        }

        //obtenemos el factor del ingreso de caja
        Double factorCambio = trx.getIdIngresoCaja().getFactorCambiario().doubleValue();

        op = Optional.ofNullable(factorCambio);
        if (!op.isPresent()) {
            throw new CRUDException("No se especifico un Factor Cambiario para el ingreso de caja : " + trx.getIdIngresoCaja().getIdIngresoCaja().toString());
        }

        Double montoAdeudado = 0d;
        if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoAdeudado = notaFromDb.getMontoAdeudadoUsd().doubleValue();
        } else {
            montoAdeudado = notaFromDb.getMontoAdeudadoBs().doubleValue();
        }

        op = Optional.ofNullable(montoAdeudado);
        if (!op.isPresent()) {
            throw new CRUDException("No existe un monto Adeudado para la moneda especificada en la Transaccion " + notaFromDb.getIdNotaDebitoTransaccion().toString() + " de la nota de debito : " + notaFromDb.getIdNotaDebito().getIdNotaDebito().toString());
        }

        Double montoIngresado = 0d;
        //Si las monedas son iguales
        if (trx.getMoneda().equals(notaFromDb.getMoneda())) {
            if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
                montoIngresado = trx.getMontoUsd().doubleValue();
            } else {
                montoIngresado = trx.getMontoBs().doubleValue();
            }
        } else {
            if (trx.getMoneda().equals(Moneda.EXTRANJERA)
                    && notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
                montoIngresado = trx.getMontoUsd().doubleValue() * factorCambio;
            } else if (trx.getMoneda().equals(Moneda.NACIONAL)
                    && notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
                montoIngresado = trx.getMontoBs().doubleValue() / factorCambio;
            }
        }

        if (montoIngresado > montoAdeudado) {
            String mensaje = "El monto de Pago de la transaccion de Ingreso:";
            mensaje += montoIngresado.toString();
            mensaje += " es mayor que el monto adeudado: ";
            mensaje += montoAdeudado.toString();
            throw new CRUDException(mensaje);
        }

        Double montoTotal = montoAdeudado - montoIngresado;
        if (notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
            notaFromDb.setMontoAdeudadoBs(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoBs().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }

        } else if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            notaFromDb.setMontoAdeudadoUsd(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoUsd().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }
        }

        em.merge(notaFromDb);

        //actualizamos notas debitos
        actualizarMontosNotaDebitoEmitida(notaFromDb.getIdNotaDebito().getIdNotaDebito());
    }

    @Override
    public void actualizarMontosAdeudadosTransaccion(NotaCreditoTransaccion trx) throws CRUDException {
        /*BEGIN  */
        // Calculo de la reduccion del monto adeudado sea cual sea la moneda del pago

        // del ingreso de caja
        NotaDebitoTransaccion notaFromDb = em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        Optional op = Optional.ofNullable(notaFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado la Nota de Debito para la transaccion de la Nota de Crédito.");
        }

        //obtenemos el factor del ingreso de caja
        Double factorCambio = trx.getIdNotaCredito().getFactorCambiario().doubleValue();

        op = Optional.ofNullable(factorCambio);
        if (!op.isPresent()) {
            throw new CRUDException("No se especifico un Factor Cambiario para la Nota de Crédito : " + trx.getIdNotaCredito().getIdNotaCredito().toString());
        }

        Double montoAdeudado = 0d;
        if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoAdeudado = notaFromDb.getMontoAdeudadoUsd().doubleValue();
        } else {
            montoAdeudado = notaFromDb.getMontoAdeudadoBs().doubleValue();
        }

        op = Optional.ofNullable(montoAdeudado);
        if (!op.isPresent()) {
            throw new CRUDException("No existe un monto Adeudado para la moneda especificada en la Transaccion " + notaFromDb.getIdNotaDebitoTransaccion().toString() + " de la Nota de Credito : " + notaFromDb.getIdNotaDebito().getIdNotaDebito().toString());
        }

        Double montoIngresado = 0d;
        //Si las monedas son iguales
        if (trx.getMoneda().equals(notaFromDb.getMoneda())) {
            if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
                montoIngresado = trx.getMontoUsd().doubleValue();
            } else {
                montoIngresado = trx.getMontoBs().doubleValue();
            }
        } else {
            if (trx.getMoneda().equals(Moneda.EXTRANJERA)
                    && notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
                montoIngresado = trx.getMontoUsd().doubleValue() * factorCambio;
            } else if (trx.getMoneda().equals(Moneda.NACIONAL)
                    && notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
                montoIngresado = trx.getMontoBs().doubleValue() / factorCambio;
            }
        }

        if (montoIngresado > montoAdeudado) {
            String mensaje = "El monto de Pago de la transaccion de Nota de Crédito:";
            mensaje += montoIngresado.toString();
            mensaje += " es mayor que el monto adeudado: ";
            mensaje += montoAdeudado.toString();
            throw new CRUDException(mensaje);
        }

        Double montoTotal = montoAdeudado - montoIngresado;
        if (notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
            notaFromDb.setMontoAdeudadoBs(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoBs().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }

        } else if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            notaFromDb.setMontoAdeudadoUsd(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoUsd().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }
        }

        em.merge(notaFromDb);

        //actualizamos notas debitos
        actualizarMontosNotaDebitoEmitida(notaFromDb.getIdNotaDebito().getIdNotaDebito());
    }
    
    
        @Override
    public void actualizarMontosAdeudadosTransaccion(PagoAnticipadoTransaccion trx) throws CRUDException {
        /*BEGIN  */
        // Calculo de la reduccion del monto adeudado sea cual sea la moneda del pago

        // del ingreso de caja
        NotaDebitoTransaccion notaFromDb = em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        Optional op = Optional.ofNullable(notaFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado la Nota de Debito para la transaccion del Pago Anticipado.");
        }

        //obtenemos el factor del ingreso de caja
        Double factorCambio = trx.getIdPagoAnticipado().getFactorCambiario().doubleValue();

        op = Optional.ofNullable(factorCambio);
        if (!op.isPresent()) {
            throw new CRUDException("No se especifico un Factor Cambiario para el Pago Anticipado : " + trx.getIdPagoAnticipado().getIdPagoAnticipado().toString());
        }

        Double montoAdeudado = 0d;
        if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoAdeudado = notaFromDb.getMontoAdeudadoUsd().doubleValue();
        } else {
            montoAdeudado = notaFromDb.getMontoAdeudadoBs().doubleValue();
        }

        op = Optional.ofNullable(montoAdeudado);
        if (!op.isPresent()) {
            throw new CRUDException("No existe un monto Adeudado para la moneda especificada en la Transaccion " + notaFromDb.getIdNotaDebitoTransaccion().toString() + " del Pago Anticipado : " + notaFromDb.getIdNotaDebito().getIdNotaDebito().toString());
        }

        Double montoIngresado = 0d;
        //Si las monedas son iguales
        if (trx.getMoneda().equals(notaFromDb.getMoneda())) {
                montoIngresado = trx.getMonto().doubleValue();
        } else {
            if (trx.getMoneda().equals(Moneda.EXTRANJERA)
                    && notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
                montoIngresado = trx.getMonto().doubleValue() * factorCambio;
            } else if (trx.getMoneda().equals(Moneda.NACIONAL)
                    && notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
                montoIngresado = trx.getMonto().doubleValue() / factorCambio;
            }
        }

        if (montoIngresado > montoAdeudado) {
            String mensaje = "El monto de Pago de la transaccion de Nota de Crédito:";
            mensaje += montoIngresado.toString();
            mensaje += " es mayor que el monto adeudado: ";
            mensaje += montoAdeudado.toString();
            throw new CRUDException(mensaje);
        }

        //El monto adeudado se suma al monto que se anulo
        Double montoTotal = montoAdeudado - montoIngresado;
        
        if (notaFromDb.getMoneda().equals(Moneda.NACIONAL)) {
            notaFromDb.setMontoAdeudadoBs(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoBs().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }

        } else if (notaFromDb.getMoneda().equals(Moneda.EXTRANJERA)) {
            notaFromDb.setMontoAdeudadoUsd(new BigDecimal(montoTotal));

            if (notaFromDb.getMontoAdeudadoUsd().doubleValue() == 0d) {
                notaFromDb.setEstado(Estado.CANCELADO);
            }
        }

        em.merge(notaFromDb);

        //actualizamos notas debitos
        actualizarMontosNotaDebitoEmitida(notaFromDb.getIdNotaDebito().getIdNotaDebito());
    }

    @Override
    public void revertirMontosIngresoDeCaja(IngresoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException {

        //Monto del ingreso de Caja
        Double montoIngreso = 0d;
        if (it.getMoneda().equals(Moneda.NACIONAL)) {
            montoIngreso = it.getMontoBs() == null ? 0d : it.getMontoBs().doubleValue();
        } else {
            montoIngreso = it.getMontoUsd() == null ? 0d : it.getMontoUsd().doubleValue();
        }

        //Monto adeudado de la Nota de Debito
        Double montoAdeudadoNota = 0d;
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoAdeudadoNota = ndtx.getMontoAdeudadoBs() == null ? 0 : ndtx.getMontoAdeudadoBs().doubleValue();
        } else {
            montoAdeudadoNota = ndtx.getMontoAdeudadoUsd() == null ? 0 : ndtx.getMontoAdeudadoUsd().doubleValue();
        }

        //MOnto adeudado Actual
        Double montoActual = 0d;
        Double factorCambiario = it.getIdIngresoCaja().getFactorCambiario().doubleValue();

        if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = (montoIngreso / factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = (montoIngreso * factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        }

        //Actualizamos el monto Adeudado
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            ndtx.setMontoAdeudadoBs(new BigDecimal(montoActual));
        } else {
            ndtx.setMontoAdeudadoUsd(new BigDecimal(montoActual));
        }

        //Actualizamos el estado de la Transaccion de la Nota de Debito
        if (ndtx.getEstado().equals(Estado.CANCELADO)) {
            ndtx.setEstado(Estado.EMITIDO);
        }

        //Actualizamos los montos de la Nota de Debito
        em.merge(ndtx);

        //Verificamos el estado de la nota de Debito
        if (ndtx.getIdNotaDebito().getEstado().equals(Estado.CANCELADO)) {
            ndtx.getIdNotaDebito().setEstado(Estado.EMITIDO);
        }

        em.merge(ndtx.getIdNotaDebito());

    }

    @Override
    public void revertirMontosNotaCredito(NotaCreditoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException {

        //Monto del ingreso de Caja
        Double montoIngreso = 0d;
        if (it.getMoneda().equals(Moneda.NACIONAL)) {
            montoIngreso = it.getMontoBs() == null ? 0d : it.getMontoBs().doubleValue();
        } else {
            montoIngreso = it.getMontoUsd() == null ? 0d : it.getMontoUsd().doubleValue();
        }

        //Monto adeudado de la Nota de Debito
        Double montoAdeudadoNota = 0d;
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoAdeudadoNota = ndtx.getMontoAdeudadoBs() == null ? 0 : ndtx.getMontoAdeudadoBs().doubleValue();
        } else {
            montoAdeudadoNota = ndtx.getMontoAdeudadoUsd() == null ? 0 : ndtx.getMontoAdeudadoUsd().doubleValue();
        }

        //MOnto adeudado Actual
        Double montoActual = 0d;
        Double factorCambiario = it.getIdNotaCredito().getFactorCambiario().doubleValue();

        if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = (montoIngreso / factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = (montoIngreso * factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        }

        //Actualizamos el monto Adeudado
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            ndtx.setMontoAdeudadoBs(new BigDecimal(montoActual));
        } else {
            ndtx.setMontoAdeudadoUsd(new BigDecimal(montoActual));
        }

        //Actualizamos el estado de la Transaccion de la Nota de Debito
        if (ndtx.getEstado().equals(Estado.CANCELADO)) {
            ndtx.setEstado(Estado.EMITIDO);
        }

        //Actualizamos los montos de la Nota de Debito
        em.merge(ndtx);

        //Verificamos el estado de la nota de Debito
        if (ndtx.getIdNotaDebito().getEstado().equals(Estado.CANCELADO)) {
            ndtx.getIdNotaDebito().setEstado(Estado.EMITIDO);
        }

        em.merge(ndtx.getIdNotaDebito());

    }

    @Override
    public void revertirMontosPagosAnticipados(PagoAnticipadoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException {

        //Monto del ingreso de Caja
        Double montoIngreso = 0d;
        montoIngreso = it.getMonto() == null ? 0d : it.getMonto().doubleValue();

        //Monto adeudado de la Nota de Debito
        Double montoAdeudadoNota = 0d;
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoAdeudadoNota = ndtx.getMontoAdeudadoBs() == null ? 0 : ndtx.getMontoAdeudadoBs().doubleValue();
        } else {
            montoAdeudadoNota = ndtx.getMontoAdeudadoUsd() == null ? 0 : ndtx.getMontoAdeudadoUsd().doubleValue();
        }

        //MOnto adeudado Actual
        Double montoActual = 0d;
        Double factorCambiario = it.getIdPagoAnticipado().getFactorCambiario().doubleValue();

        if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.NACIONAL) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = (montoIngreso / factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            montoActual = (montoIngreso * factorCambiario) + montoAdeudadoNota;
        } else if (it.getMoneda().equals(Moneda.EXTRANJERA) && ndtx.getMoneda().equals(Moneda.EXTRANJERA)) {
            montoActual = montoIngreso + montoAdeudadoNota;
        }

        //Actualizamos el monto Adeudado
        if (ndtx.getMoneda().equals(Moneda.NACIONAL)) {
            ndtx.setMontoAdeudadoBs(new BigDecimal(montoActual));
        } else {
            ndtx.setMontoAdeudadoUsd(new BigDecimal(montoActual));
        }

        //Actualizamos el estado de la Transaccion de la Nota de Debito
        if (ndtx.getEstado().equals(Estado.CANCELADO)) {
            ndtx.setEstado(Estado.EMITIDO);
        }

        //Actualizamos los montos de la Nota de Debito
        em.merge(ndtx);

        //Verificamos el estado de la nota de Debito
        if (ndtx.getIdNotaDebito().getEstado().equals(Estado.CANCELADO)) {
            ndtx.getIdNotaDebito().setEstado(Estado.EMITIDO);
        }

        em.merge(ndtx.getIdNotaDebito());

    }

    @Override
    public List<NotaDebito> getNotaDebitoCreditoWhereVencimientoWasYesterday() throws CRUDException {
        Date yesterday = DateContable.getYesterday();
        
        Query query = em.createNamedQuery("NotaDebito.getAllNotaDebitoWhereVencimientoIsYesterday");
        query.setParameter("yesterday", yesterday);
        
        List l = query.getResultList() ;
        
        return l ;
        
    }

    @Override
    public List<NotaDebito> getNotaDebitoEnMora() throws CRUDException {
        
        Query query = em.createNamedQuery("NotaDebito.getAllNotaDebitoEnMora");
        
        List l = query.getResultList() ;
        
        return l ;
        
    }

    
    
}
