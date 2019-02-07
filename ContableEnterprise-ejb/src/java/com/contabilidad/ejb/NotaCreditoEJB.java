/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.CambioRemote;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.NotasCreditoRemote;
import com.response.json.boletaje.NotaCreditoSearchJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.User;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class NotaCreditoEJB extends FacadeEJB implements NotasCreditoRemote {

    @EJB
    private BoletoRemote ejbBoleto;
    @EJB
    private CambioRemote ejbCambio;
    @EJB
    private ComprobanteRemote ejbComprobante;
    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @Override
    public NotaCredito createNewNotaCredito(String idUsuario, Integer idEmpresa) throws CRUDException {
        User uFromDb = em.find(User.class, idUsuario);
        Optional op = Optional.ofNullable(uFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el usuario %s".replace("%s", idUsuario));
        }

        Empresa eFromDb = em.find(Empresa.class, idEmpresa);
        op = Optional.ofNullable(eFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe la empresa %s".replace("%s", idEmpresa.toString()));
        }

        NotaCredito nota = new NotaCredito();
        nota.setIdUsuario(idUsuario);
        nota.setIdEmpresa(idEmpresa);
        nota.setFechaInsert(DateContable.getCurrentDate());
        nota.setEstado(Estado.CREADO);
        nota.setFechaEmision(DateContable.getCurrentDate());
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

        nota.setIdNotaCredito(insert(nota));

        return nota;
    }

    @Override
    public List<NotaCredito> findAllNotaCredito(NotaCreditoSearchJson search) throws CRUDException {
        Optional op = Optional.ofNullable(search.getIdEmpresa());

        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = queries.getPropertie(Queries.GET_NOTA_CREDITO);

        // dentro de q ya esta el WHERE nd.id_empresa = :idEmpresa
        op = Optional.ofNullable(search.getIdCliente());
        if (op.isPresent()) {
            if (search.getIdCliente().getId() != null) {
                q += " AND nc.id_cliente =?2 ";
            }
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            if (search.getFechaInicio().trim().length() > 1) {
                q += " AND nc.fecha_emision>=?3";
            }
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            if (search.getFechaFin().trim().length() > 1) {
                q += " AND nc.fecha_emision<=?4";
            }
        }

        q += " ORDER BY nc.id_nota_credito";

        Query query = em.createNativeQuery(q, NotaCredito.class);

        op = Optional.ofNullable(search.getIdCliente());
        if (op.isPresent()) {
            if (search.getIdCliente().getId() != null) {
                Integer idCliente = ((Double) search.getIdCliente().getId()).intValue();
                query.setParameter("2", idCliente);
            }
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("3", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("4", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        query.setParameter("1", search.getIdEmpresa());

        return query.getResultList();
    }

    @Override
    public List<NotaCreditoTransaccion> findAllNotaCreditoTransacciones(Integer idNotaCredito) throws CRUDException {
        Optional op = Optional.ofNullable(idNotaCredito);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Nota de Crédito");
        }

        NotaCredito fromDb = em.find(NotaCredito.class, idNotaCredito);
        op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Numero de Nota de Crédito");
        }

        Query q = em.createNamedQuery("NotaCreditoTransaccion.findByNotaCredito");
        q.setParameter("idNotaCredito", fromDb);

        List<NotaCreditoTransaccion> l = q.getResultList();

        return l;
    }

    @Override
    public void pendiente(NotaCredito nota) throws CRUDException {
        NotaCredito fromDb = em.find(NotaCredito.class, nota.getIdNotaCredito());
        Optional op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("La Nota de Crédito %s No Existe".replace("%s", nota.getIdNotaCredito().toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La Nota de Crédito %s se encuenta ANULADA. No puede pasar a PENDIENTE.".replace("%s", nota.getIdNotaCredito().toString()));
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("El Nota de Crédito %s se encuentra en estado EMITIDO. No puede pasar a PENDIENTE".replace("%s", nota.getIdNotaCredito().toString()));
        }

        op = Optional.ofNullable(nota.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Cliente");
        }

        Cliente cFromDb = em.find(Cliente.class, nota.getIdCliente().getIdCliente());
        op = Optional.ofNullable(cFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Cliente %s no existe!".replace("%s", nota.getIdCliente().getIdCliente().toString()));
        }
        Query q = em.createNamedQuery("NotaCredito.updateToPendiente");
        q.setParameter("idCliente", nota.getIdCliente());
        q.setParameter("fechaEmision", nota.getFechaEmision());
        q.setParameter("estado", Estado.PENDIENTE);
        q.setParameter("concepto", nota.getConcepto());
        q.setParameter("idNotaCredito", nota.getIdNotaCredito());
        q.executeUpdate();
    }

    @Override
    public void anularNotaCredito(Integer idNotaCredito, String usuario) throws CRUDException {
        NotaCredito fromDb = em.find(NotaCredito.class, idNotaCredito);
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La Nota de Crédito %s no existe.".replace("%s", idNotaCredito.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La Nota de Crédito ya se encuentra Anulado.");
        }

        User uFromDb = em.find(User.class, usuario);
        op = Optional.ofNullable(uFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el usuario %s".replace("%s", usuario));
        };

        //revertimos los ingresos de Caja, los montos de cada Ingreso de Caja deben 
        //revertirse en las Notas de Debito
        //y si tiene asientos Contables, deben Anularse
        for (NotaCreditoTransaccion it : fromDb.getNotaCreditoTransaccionList()) {
            NotaDebitoTransaccion ndtx = it.getIdNotaTransaccion();
            ejbNotaDebito.revertirMontosNotaCredito(it, ndtx);
        }

        //Anulamos los comprobantes Contables solo si han sido emitidos
        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            ejbComprobante.anularComprobanteContable(fromDb, usuario);
        }

        // Cuando se ingresa el estado ANULADO, se lanza un TRIGGER en BD
        // y anula todas sus Transacciones
        fromDb.setEstado(Estado.ANULADO);
        em.merge(fromDb);

    }

    @Override
    public void anularTransaccion(NotaCreditoTransaccion trx, String usuario) throws CRUDException {

        NotaCreditoTransaccion fromDb = em.find(NotaCreditoTransaccion.class, trx.getIdNotaCreditoTransaccion());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transaccion %s de la Nota de Credito que ha ingresado, no se ha encontrado.".replace("%s", trx.getIdNotaCreditoTransaccion().toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La transaccion %s de la Nota de Credito que ha ingresado ya se encuentra ANULADA".replace("%s", trx.getIdNotaCreditoTransaccion().toString()));
        }

        if (fromDb.getEstado().equals(Estado.PENDIENTE)) {
            revertirMontosAnteriors(trx, fromDb);

            fromDb.setEstado(Estado.ANULADO);
            updateMontosNotaCredito(trx.getIdNotaCredito().getIdNotaCredito());
        } else if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            revertirMontosAnteriors(trx, fromDb);

            fromDb.setEstado(Estado.ANULADO);
            em.merge(fromDb);

            updateMontosNotaCredito(trx.getIdNotaCredito().getIdNotaCredito());
            //anulamos los asientos contables
            ejbComprobante.anularAsientosContables(fromDb, usuario);
        }

    }

    private void updateMontosAdeudadosNotaDebitos(List<NotaCreditoTransaccion> list) {

        HashMap<String, Integer> map = new HashMap<>();

        for (NotaCreditoTransaccion di : list) {
            map.put(di.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito().toString(), di.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer idNotaDebito = entry.getValue();
            StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.updateMontosAdeudadosNotaDebito");
            spq.setParameter("in_id_nota_debito", idNotaDebito);
            spq.executeUpdate();
        }
    }

    private void revertirMontosAnteriors(NotaCreditoTransaccion trx, NotaCreditoTransaccion fromDb) throws CRUDException {
        //Sacamos el mmonto anterior de la transaccion
        //y lo sumaremos al monto adeudado para actualizarlo
        Double montoBdIc = 0d;
        if (fromDb.getMoneda().equals(Moneda.NACIONAL)) {
            montoBdIc = fromDb.getMontoBs().doubleValue();
            fromDb.setMontoBs(trx.getMontoBs());
            fromDb.setMontoUsd(null);
        } else {
            montoBdIc = fromDb.getMontoUsd().doubleValue();
            fromDb.setMontoUsd(trx.getMontoUsd());
            fromDb.setMontoBs(null);
        }
        em.merge(fromDb);

        Double montoBdNdTrx = 0d;
        //Nuevo monto Adeudado
        if (fromDb.getIdNotaTransaccion().getMoneda().equals(Moneda.NACIONAL)) {
            montoBdNdTrx = fromDb.getIdNotaTransaccion().getMontoAdeudadoBs().doubleValue();
            Double montoUpdated = montoBdIc + montoBdNdTrx;
            fromDb.getIdNotaTransaccion().setMontoAdeudadoBs(new BigDecimal(montoUpdated));
        } else {
            montoBdNdTrx = fromDb.getIdNotaTransaccion().getMontoAdeudadoUsd().doubleValue();
            Double montoUpdated = montoBdIc + montoBdNdTrx;
            fromDb.getIdNotaTransaccion().setMontoAdeudadoUsd(new BigDecimal(montoUpdated));
        }
        em.merge(fromDb.getIdNotaTransaccion());
        //actualizamos los nuevos montos adeudados.
        ejbNotaDebito.actualizarMontosAdeudadosTransaccion(fromDb);
    }

    private void updateMontosNotaCredito(Integer idNotaCredito) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaCredito.updateMontosNotaCredito");
        spq.setParameter("in_id_nota_credito", idNotaCredito);
        spq.executeUpdate();
    }

    @Override
    public NotaCreditoTransaccion getNotaCreditoTransaccion(NotaCreditoTransaccion data) throws CRUDException {
        Optional op = Optional.ofNullable(data);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Transacción Válida");
        }

        op = Optional.ofNullable(data.getIdNotaCreditoTransaccion());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Transacción Válida");
        }

        NotaCreditoTransaccion fromDb = em.find(NotaCreditoTransaccion.class, data.getIdNotaCreditoTransaccion());

        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la transacción %id para la Nota de Crédito ".replace("%id", data.getIdNotaCreditoTransaccion().toString()));
        }

        return fromDb;
    }

    @Override
    public NotaCreditoTransaccion saveTransaccion(NotaCreditoTransaccion trx, String usuario) throws CRUDException {
        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transacción.");
        }

        op = Optional.ofNullable(trx.getIdNotaCredito());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Nota de Crédito para la transacción.");
        }

        op = Optional.ofNullable(trx.getIdNotaTransaccion());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Nota de Débito para la transacción.");
        }

        if (trx.getMontoBs() == null && trx.getMontoUsd() == null) {
            throw new CRUDException("Debe al menos ingresar un monto en la Transacción de la Nota de Crédito.");
        }

        NotaDebitoTransaccion trNDFromDB = em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        op = Optional.ofNullable(trNDFromDB);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Transacción de Nota de Débito.");
        }

        NotaCredito ncFromDb = em.find(NotaCredito.class, trx.getIdNotaCredito().getIdNotaCredito());
        op = Optional.ofNullable(ncFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Nota de Crédito Válida.");
        }

        trx.setIdNotaTransaccion(trNDFromDB);
        trx.setIdNotaCredito(ncFromDb);
        trx.setFechaInsert(DateContable.getCurrentDate());
        trx.setIdUsuarioCreador(usuario);
        //Actualizamos la nota de Debito
        ejbNotaDebito.actualizarMontosAdeudadosTransaccion(trx);
        //Insertamos
        trx.setIdNotaCreditoTransaccion(insert(trx));
        //actualizamos
        updateMontosNotaCredito(trx.getIdNotaCredito().getIdNotaCredito());

        em.flush();

        return trx;
    }

    @Override
    public NotaCreditoTransaccion updateTransaccion(NotaCreditoTransaccion trx) throws CRUDException {

        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transacción.");
        }

        NotaCreditoTransaccion fromDb = em.find(NotaCreditoTransaccion.class, trx.getIdNotaCreditoTransaccion());

        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transacción especificada NO existe.");
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("La transacción se encuentra EMITIDA, no puede modificar sus valores");
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La transacción se encuentra ANULADA, no puede modificar sus valores");
        }

        if (trx.getMontoBs() == null && trx.getMontoUsd() == null) {
            throw new CRUDException("Debe al menos ingresar un monto en la transacción de la Nota de Crédito");
        }

        fromDb.setMoneda(trx.getMoneda());

        //revertirmos los montos
        revertirMontosAnteriors(trx, fromDb);

        if (fromDb.getMoneda().equals(Moneda.NACIONAL)) {
            fromDb.setMontoBs(trx.getMontoBs());
            fromDb.setMontoUsd(null);
        } else {
            fromDb.setMontoUsd(trx.getMontoUsd());
            fromDb.setMontoBs(null);
        }

        updateMontosNotaCredito(trx.getIdNotaCredito().getIdNotaCredito());

        em.flush();

        return fromDb;
    }

    @Override
    public NotaCredito finalizar(Integer idNotaCredito) throws CRUDException {
        Optional op = Optional.ofNullable(idNotaCredito);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar un Numero de Nota de Crédito.".replace("%s", idNotaCredito.toString()));
        }

        NotaCredito fromDb = em.find(NotaCredito.class, idNotaCredito);
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado la Nota de Crédito %s.".replace("%s", idNotaCredito.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La Nota de Crédito %s se encuentra ANULADA.".replace("%s", idNotaCredito.toString()));
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("La Nota de Crédito %s se encuentra EMITIDA.".replace("%s", idNotaCredito.toString()));
        }

        if (fromDb.getNotaCreditoTransaccionList().isEmpty()) {
            throw new CRUDException("Debe Ingresar al menos una Transaccion para crear los Comprobantes Contables.".replace("%s", idNotaCredito.toString()));
        }

        fromDb.setEstado(Estado.EMITIDO);

        ContabilidadBoletaje conf = ejbNotaDebito.getConfiguracion(fromDb.getIdEmpresa());

        String glosa = fromDb.getConcepto();
        // Insertamos el comprobante
        ComprobanteContable comprobanteIngreso = ejbComprobante.createComprobante(ComprobanteContable.Tipo.ASIENTO_DIARIO, glosa, fromDb);
        comprobanteIngreso.setIdLibro(insert(comprobanteIngreso));

        if (ejbBoleto.validarConfiguracion(conf)) {
            for (NotaCreditoTransaccion tran : fromDb.getNotaCreditoTransaccionList()) {
                // Duda, 
                NotaDebitoTransaccion ndt = tran.getIdNotaTransaccion();
                if (ndt.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                    Boleto b = em.find(Boleto.class, ndt.getIdBoleto());
                    if (b == null) {
                        String msg = "No existe el ID Boleto : %b en la Transaccion %t de la Nota de Credito %n";
                        msg = msg.replace("%b", ndt.getIdBoleto().toString());
                        msg = msg.replace("%t", ndt.getIdNotaDebitoTransaccion().toString());
                        msg = msg.replace("%n", ndt.getIdNotaDebito().toString());
                        throw new CRUDException(msg);
                    }

                    AsientoContable haber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, conf, tran, fromDb, b);
                    insert(haber);

                    AsientoContable debe = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, conf, ndt, fromDb, b, tran);
                    insert(debe);
                } else {
                    CargoBoleto c = em.find(CargoBoleto.class, ndt.getIdCargo());
                    if (c == null) {
                        String msg = "No existe el ID Cargo: %c en la Transaccion %t de la Nota de Credito %n";
                        msg = msg.replace("%c", ndt.getIdCargo().toString());
                        msg = msg.replace("%t", ndt.getIdNotaDebitoTransaccion().toString());
                        msg = msg.replace("%n", ndt.getIdNotaDebito().toString());
                        throw new CRUDException(msg);
                    }

                    AsientoContable haber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, conf, ndt, fromDb, c, tran);
                    insert(haber);

                    AsientoContable debe = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, conf, ndt, fromDb, c, tran);
                    insert(haber);

                }
                em.merge(ndt);

                tran.setEstado(Estado.EMITIDO);
                em.merge(tran);
            }
        }

        em.merge(fromDb);

        //Actualizamos los montos adeudados de las notas de Debito
        updateMontosAdeudadosNotaDebitos(fromDb.getNotaCreditoTransaccionList());

        //Actualizamos los comprobantes Contables
        ejbComprobante.actualizarMontosFinalizar(comprobanteIngreso);

        return fromDb;

    }

    @Override
    public List<NotaCreditoTransaccion> getNotaCreditoTransaccionByNotaDebito(NotaDebito idNotaDebito) throws CRUDException {

        List<NotaCreditoTransaccion> lreturn = null;

        //String q = queries.getPropertie(Queries.GET_NOTA_CREDITO_TRX_BY_ID_NOTA_DEBITO);
        Query query = em.createNamedQuery("NotaCreditoTransaccion.findByIdNotaCreditoTransaccion", NotaCreditoTransaccion.class);
        query.setParameter("idNotaDebito", idNotaDebito);

        lreturn = query.getResultList();

        if (lreturn.isEmpty()) {
            lreturn = new LinkedList<>();
        }

        return lreturn;
    }

    @Override
    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException {
        //anulamos las transacciones de los Ingresos a Caja
        Optional op = Optional.ofNullable(tr);
        if (op.isPresent()) {
            op = Optional.ofNullable(tr.getIdNotaDebitoTransaccion());
            if (op.isPresent()) {

                Query q = em.createNamedQuery("NotaCreditoTransaccion.findByNotadebitoTransaccion", NotaCreditoTransaccion.class);
                q.setParameter("idNotaTransaccion", tr);

                List<NotaCreditoTransaccion> lit = q.getResultList();

                /**
                 * if (lit.isEmpty()) { throw new CRUDException("No existen
                 * Transacciones de Nota de Credito para la Transaccion %s de la
                 * Nota de Debito".replace("%s",
                 * tr.getIdNotaDebitoTransaccion().toString())); }*
                 */
                if (!lit.isEmpty()) {
                    for (NotaCreditoTransaccion it : lit) {
                        if (!it.getEstado().equals(Estado.ANULADO)) {
                            it.setEstado(Estado.ANULADO);
                            em.merge(it);

                            //actualizamos los montos. Es lo mismo que el finalizar
                            //ejecuta el procedure stored de las transacciones que estan en 
                            //estado emitido
                            updateMontosNotaCredito(it.getIdNotaCredito().getIdNotaCredito());
                            //anulamos los asientos contables
                            try {
                                ejbComprobante.anularAsientosContables(it, usuario);
                            } catch (CRUDException ex) {

                            }

                        }
                    }
                }

            } else {
                //throw new CRUDException("No se especifico un ID de Transaccion para el Ingreso de Caja");
            }
        } else {
            //throw new CRUDException("No se especifico una Nota de Debito");
        }
    }

}
