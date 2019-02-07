/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.CambioRemote;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.DevolucionRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.PagoAnticipadoRemote;
import com.response.json.boletaje.PagoAnticipadoSearchJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.User;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author xeio
 */
@Stateless
public class PagoAnticipadoEJB extends FacadeEJB implements PagoAnticipadoRemote {

    @EJB
    private CambioRemote ejbCambio;

    @EJB
    private ComprobanteRemote ejbComprobante;

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @EJB
    private DevolucionRemote ejbDevolucion;

    @Override
    public PagoAnticipado createNewPagoAnticipado(String idUsuario, Integer idEmpresa) throws CRUDException {
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

        PagoAnticipado pago = new PagoAnticipado();
        pago.setIdUsuarioCreador(idUsuario);
        pago.setIdEmpresa(idEmpresa);
        pago.setFechaInsert(DateContable.getCurrentDate());
        pago.setEstado(Estado.PENDIENTE);
        pago.setFechaEmision(DateContable.getCurrentDate());
        pago.setFormaPago(FormasPago.EFECTIVO);

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
        pago.setFactorCambiario(diario.getValor());

        pago.setIdPagoAnticipado(insert(pago));

        return pago;
    }

    @Override
    public List<PagoAnticipado> findAllPagoAnticipado(PagoAnticipadoSearchJson search) throws CRUDException {

        Optional op = Optional.ofNullable(search.getIdEmpresa());
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = queries.getPropertie(Queries.GET_PAGO_ANTICIPADO);

        // dentro de q ya esta el WHERE nd.id_empresa = :idEmpresa
        op = Optional.ofNullable(search.getIdCliente());
        if (op.isPresent()) {
            if (search.getIdCliente().getId() != null) {
                q += " AND p.id_cliente =?2 ";
            }
        }

        op = Optional.ofNullable(search.getConcepto());
        if (op.isPresent()) {
            q += " AND p.concepto like ?3 ";
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            if (search.getFechaInicio().trim().length() > 1) {
                q += " AND p.fecha_emision>=?4";
            }
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            if (search.getFechaFin().trim().length() > 1) {
                q += " AND p.fecha_emision<=?5";
            }
        }

        op = Optional.ofNullable(search.getNroDeposito());
        if (op.isPresent()) {
            if (search.getFechaFin().trim().length() > 1) {
                q += " AND pat.id_pago_anticipado_transaccion = ?6";
            }
        }

        q += " ORDER BY p.id_pago_anticipado";

        Query query = em.createNativeQuery(q, PagoAnticipado.class);

        op = Optional.ofNullable(search.getIdCliente());
        if (op.isPresent()) {
            query.setParameter("2", search.getIdCliente().getId());
        }

        op = Optional.ofNullable(search.getConcepto());
        if (op.isPresent()) {
            query.setParameter("3", search.getConcepto() + "%");
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("4", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent() && !op.equals("")) {
            query.setParameter("5", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        op = Optional.ofNullable(search.getNroDeposito());
        if (op.isPresent()) {
            query.setParameter("6", search.getNroDeposito());
        }

        query.setParameter("1", search.getIdEmpresa());

        System.out.println("Query:" + q);

        return query.getResultList();

    }

    @Override
    public List<PagoAnticipadoTransaccion> findAllPagoAnticipadoTransacciones(Integer idPagoAnticipado) throws CRUDException {
        Optional op = Optional.ofNullable(idPagoAnticipado);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Pago Anticipado");
        }

        PagoAnticipado fromDb = em.find(PagoAnticipado.class, idPagoAnticipado);
        op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Numero de Pago Anticipado");
        }

        Query q = em.createNamedQuery("PagoAnticipadoTransaccion.findByIdPagoAnticipado");
        q.setParameter("idPagoAnticipado", fromDb);

        List<PagoAnticipadoTransaccion> l = q.getResultList();

        return l;

    }

    @Override
    public void pendiente(PagoAnticipado pago) throws CRUDException {
        PagoAnticipado fromDb = em.find(PagoAnticipado.class, pago.getIdPagoAnticipado());
        Optional op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("El Pago Anticipado %s No Existe".replace("%s", pago.getIdPagoAnticipado().toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Pago Anticipado %s se encuenta ANULADA. No puede pasar a PENDIENTE.".replace("%s", pago.getIdPagoAnticipado().toString()));
        }

        if (fromDb.getEstado().equals(Estado.SIN_SALDO)) {
            throw new CRUDException("El Pago Anticipado %s se encuentra en estado SIN SALDO. No puede pasar a PENDIENTE".replace("%s", pago.getIdPagoAnticipado().toString()));
        }

        op = Optional.ofNullable(pago.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Cliente");
        }

        Cliente cFromDb = em.find(Cliente.class, pago.getIdCliente().getIdCliente());
        op = Optional.ofNullable(cFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Cliente %s no existe!".replace("%s", pago.getIdCliente().getIdCliente().toString()));
        }

        Query q = em.createNamedQuery("PagoAnticipado.updateToPendiente");
        q.setParameter("concepto", pago.getConcepto());
        q.setParameter("estado", Estado.PENDIENTE);
        q.setParameter("factorCambiario", pago.getFactorCambiario());
        q.setParameter("fechaEmision", pago.getFechaEmision());
        q.setParameter("formaPago", pago.getFormaPago());
        q.setParameter("idBanco", pago.getIdBanco());
        q.setParameter("idCliente", pago.getIdCliente());
        q.setParameter("idCuentaDeposito", pago.getIdCuentaDeposito());
        q.setParameter("idTarjetaCredito", pago.getIdTarjetaCredito());
        q.setParameter("moneda", pago.getMoneda());
        q.setParameter("nroCheque", pago.getNroCheque());
        q.setParameter("nroDeposito", pago.getNroDeposito());
        q.setParameter("nroTarjeta", pago.getNroTarjeta());
        q.setParameter("idPagoAnticipado", pago.getIdPagoAnticipado());
        q.executeUpdate();
    }

    @Override
    public void anularPagoAnticipado(Integer idPagoAnticipado, String usuario) throws CRUDException {
        PagoAnticipado fromDb = em.find(PagoAnticipado.class, idPagoAnticipado);
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Pago Anticipado %s no existe.".replace("%s", idPagoAnticipado.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Pago Anticipado %s ya se encuentra ANULADO.".replace("%s", idPagoAnticipado.toString()));
        }

        User uFromDb = em.find(User.class, usuario);
        op = Optional.ofNullable(uFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el usuario %s".replace("%s", usuario));
        };

        for (PagoAnticipadoTransaccion it : fromDb.getPagoAnticipadoTransaccionCollection()) {
            anularTransaccion(it, usuario);
        }

        ejbComprobante.anularComprobanteContable(fromDb, usuario);

        updateMontosPagosAnticipados(fromDb.getIdPagoAnticipado());

        fromDb.setEstado(Estado.ANULADO);
        em.merge(fromDb);
    }

    @Override
    public void anularTransaccion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException {

        PagoAnticipadoTransaccion fromDb = em.find(PagoAnticipadoTransaccion.class, trx.getIdPagoAnticipadoTransaccion());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transacción que ha ingresado, no se ha encontrado.");
        }

        User uFromDb = em.find(User.class, usuario);
        op = Optional.ofNullable(uFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el usuario %s".replace("%s", usuario));
        };

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La transaccion que ha ingresado ya se encuentra ANULADA");
        } else if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            revertirMontosAnteriors(trx, fromDb);

            fromDb.setEstado(Estado.ANULADO);
            em.merge(fromDb);

            // Para el caso si el pago Anticipado estaba sin Saldo pero 
            //ahora se encuentra con saldo
            if (fromDb.getIdPagoAnticipado().getEstado().equals(Estado.SIN_SALDO)) {
                fromDb.getIdPagoAnticipado().setEstado(Estado.CON_SALDO);
            }

            updateMontosPagosAnticipados(trx.getIdPagoAnticipado().getIdPagoAnticipado());
            //anulamos los asientos contables
            ejbComprobante.anularAsientosContables(fromDb, usuario);
        }

        em.flush();

    }

    @Override
    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException {
        //anulamos las transacciones de los Ingresos a Caja
        Optional op = Optional.ofNullable(tr);
        if (op.isPresent()) {
            op = Optional.ofNullable(tr.getIdNotaDebitoTransaccion());
            if (op.isPresent()) {

                Query q = em.createNamedQuery("PagoAnticipadoTransaccion.findByNotadebitoTransaccion", PagoAnticipadoTransaccion.class);
                q.setParameter("idNotaTransaccion", tr);

                List<PagoAnticipadoTransaccion> lit = q.getResultList();

                if (!lit.isEmpty()) {
                    for (PagoAnticipadoTransaccion it : lit) {
                        if (!it.getEstado().equals(Estado.ANULADO)) {
                            it.setEstado(Estado.ANULADO);
                            em.merge(it);

                            //actualizamos los montos. Es lo mismo que el finalizar
                            //ejecuta el procedure stored de las transacciones que estan en 
                            //estado emitido
                            updateMontosPagosAnticipados(it.getIdPagoAnticipado().getIdPagoAnticipado());
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

    private void updateMontosPagosAnticipados(Integer idPagoAnticipado) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("PagoAnticipado.updateMontosPagoAnticipado");
        spq.setParameter("in_id_pago_anticipado", idPagoAnticipado);
        spq.executeUpdate();
    }

    private void revertirMontosAnteriors(PagoAnticipadoTransaccion trx, PagoAnticipadoTransaccion fromDb) throws CRUDException {

        //Sacamos el mmonto anterior de la transaccion
        //y lo sumaremos al monto adeudado para actualizarlo
        Double montoBdIc = 0d;
        montoBdIc = fromDb.getMonto().doubleValue();
        //fromDb.setMonto(null);
        //em.merge(fromDb);

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
        ejbNotaDebito.actualizarMontosNotaDebitoEmitida(fromDb.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
    }

    @Override
    public void guardar(PagoAnticipado idPagoAnticipado) throws CRUDException {
        PagoAnticipado fromDB = em.find(PagoAnticipado.class, idPagoAnticipado.getIdPagoAnticipado());

        Optional op = Optional.ofNullable(idPagoAnticipado);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un Pago Anticipado válido");
        }

        op = Optional.ofNullable(fromDB);
        if (!op.isPresent()) {
            throw new CRUDException("No existe el pago Anticipado");
        }

        op = Optional.ofNullable(idPagoAnticipado.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un Cliente.");
        }

        op = Optional.ofNullable(idPagoAnticipado.getFechaEmision());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Fecha.");
        }

        op = Optional.ofNullable(idPagoAnticipado.getFormaPago());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Forma de pago.");
        }

        op = Optional.ofNullable(idPagoAnticipado.getConcepto());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un Concepto.");
        }

        op = Optional.ofNullable(idPagoAnticipado.getMontoAnticipado());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un valor del Monto Anticipado.");
        }
        fromDB.setEstado(Estado.CON_SALDO);
        fromDB.setConcepto(idPagoAnticipado.getConcepto());
        fromDB.setFactorCambiario(idPagoAnticipado.getFactorCambiario());
        fromDB.setFechaEmision(idPagoAnticipado.getFechaEmision());
        fromDB.setFormaPago(idPagoAnticipado.getFormaPago());
        fromDB.setIdBanco(idPagoAnticipado.getIdBanco());
        fromDB.setIdCliente(idPagoAnticipado.getIdCliente());
        fromDB.setIdCuentaDeposito(idPagoAnticipado.getIdCuentaDeposito());
        fromDB.setIdTarjetaCredito(idPagoAnticipado.getIdTarjetaCredito());
        fromDB.setMoneda(idPagoAnticipado.getMoneda());
        fromDB.setMontoAnticipado(idPagoAnticipado.getMontoAnticipado());
        fromDB.setNroCheque(idPagoAnticipado.getNroCheque());
        fromDB.setNroDeposito(idPagoAnticipado.getNroDeposito());
        fromDB.setNroTarjeta(idPagoAnticipado.getNroTarjeta());

        ContabilidadBoletaje conf = ejbNotaDebito.getConfiguracion(fromDB.getIdEmpresa());

        ComprobanteContable comprobante = ejbComprobante.createComprobante(ComprobanteContable.Tipo.COMPROBANTE_INGRESO, fromDB.getConcepto(), fromDB);
        comprobante.setIdLibro(insert(comprobante));

        AsientoContable debe; //1500 CAJA

        debe = ejbComprobante.createAsientoPagoAnticipadoDebe(comprobante, conf, fromDB);
        insert(debe);

        AsientoContable haber; // 1500 DEPOSITO
        haber = ejbComprobante.createAsientoPagoAnticipadoHaber(comprobante, conf, fromDB);
        insert(haber);

        ejbComprobante.actualizarMontosFinalizar(comprobante);

        em.merge(fromDB);

    }

    @Override
    public void updatePago(PagoAnticipado pago) throws CRUDException {
        PagoAnticipado fromDb = em.find(PagoAnticipado.class, pago.getIdPagoAnticipado());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No existe el Pago Anticipado");
        }

        op = Optional.ofNullable(pago.getConcepto());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un concepto");
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Pago Anticipado %s se encuentra ANULADO. No se puede Actualizar.".replace("%s", pago.getIdPagoAnticipado().toString()));
        }

        fromDb.setConcepto(pago.getConcepto());

        em.merge(fromDb);

    }

    @Override
    public PagoAnticipadoTransaccion getPagoAnticipadoTransaccion(Integer idPagoAnticipado) throws CRUDException {
        Optional op = Optional.ofNullable(idPagoAnticipado);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Transacción Válida");
        }

        PagoAnticipadoTransaccion fromDb = em.find(PagoAnticipadoTransaccion.class, idPagoAnticipado);
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la transacción %id para el Pago Anticipado".replace("%id", idPagoAnticipado.toString()));
        }

        return fromDb;

    }

    @Override
    public PagoAnticipadoTransaccion saveTransaccionDevolucion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException {
        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transacción.");
        }

        trx.setFechaInsert(DateContable.getCurrentDate());
        trx.setEstado(Estado.EMITIDO);
        trx.setIdUsuarioCreador(usuario);

        //ejbNotaDebito.actualizarMontosAdeudadosTransaccion(trx);

        trx.setIdPagoAnticipadoTransaccion(insert(trx));

        updateMontosPagosAnticipados(trx.getIdPagoAnticipado().getIdPagoAnticipado());

        return trx;

    }

    @Override
    public PagoAnticipadoTransaccion saveTransaccion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException {
        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transacción.");
        }

        op = Optional.ofNullable(trx.getIdPagoAnticipado());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar el Pago Anticipado para la transacción.");
        }

        /*
        op = Optional.ofNullable(trx.getIdNotaTransaccion());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Nota de Débito para la transacción.");
        }*/
        op = Optional.ofNullable(trx.getMonto());
        if (!op.isPresent()) {
            throw new CRUDException("Debe al menos ingresar un monto en la Transacción del Pago Anticipado.");
        }

        NotaDebitoTransaccion trNDFromDB = em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
        op = Optional.ofNullable(trNDFromDB);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una Transacción de Nota de Débito.");
        }

        PagoAnticipado paFromDb = em.find(PagoAnticipado.class, trx.getIdPagoAnticipado().getIdPagoAnticipado());
        op = Optional.ofNullable(paFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Pago Anticipado Válida.");
        }

        trx.setIdNotaTransaccion(trNDFromDB);
        trx.setIdPagoAnticipado(paFromDb);
        trx.setFechaInsert(DateContable.getCurrentDate());
        trx.setEstado(Estado.EMITIDO);
        trx.setDescripcion(trNDFromDB.getDescripcion());
        trx.setIdUsuarioCreador(usuario);

        ejbNotaDebito.actualizarMontosAdeudadosTransaccion(trx);

        trx.setIdPagoAnticipadoTransaccion(insert(trx));

        updateMontosPagosAnticipados(trx.getIdPagoAnticipado().getIdPagoAnticipado());

        //Parte Contable
        ContabilidadBoletaje conf = ejbNotaDebito.getConfiguracion(paFromDb.getIdEmpresa());
        //Comprobante Contable
        ComprobanteContable comprobanteIngreso = ejbComprobante.createComprobante(ComprobanteContable.Tipo.ASIENTO_DIARIO,
                trNDFromDB.getDescripcion(), paFromDb);
        comprobanteIngreso.setIdLibro(insert(comprobanteIngreso));

        if (trNDFromDB.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
            Boleto b = em.find(Boleto.class, trNDFromDB.getIdBoleto());
            if (b == null) {
                String msg = "No existe el ID Boleto : %b en la Transaccion %t del Pago Anticipado %n";
                msg = msg.replace("%b", trNDFromDB.getIdBoleto().toString());
                msg = msg.replace("%t", trNDFromDB.getIdNotaDebitoTransaccion().toString());
                msg = msg.replace("%n", trNDFromDB.getIdNotaDebito().toString());
                throw new CRUDException(msg);
            }
            //Asientos Contables

            AsientoContable debe = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, conf, trNDFromDB, paFromDb, b, trx);
            insert(debe);

            AsientoContable haber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, conf, trx, paFromDb, b);
            insert(haber);

        } else {
            CargoBoleto c = em.find(CargoBoleto.class, trNDFromDB.getIdCargo());
            if (c == null) {
                String msg = "No existe el ID Cargo: %c en la Transaccion %t del Pago Anticipado %n";
                msg = msg.replace("%c", trNDFromDB.getIdCargo().toString());
                msg = msg.replace("%t", trNDFromDB.getIdNotaDebitoTransaccion().toString());
                msg = msg.replace("%n", trNDFromDB.getIdNotaDebito().toString());
                throw new CRUDException(msg);
            }

            AsientoContable haber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, conf, trNDFromDB, paFromDb, c, trx);
            insert(haber);

            AsientoContable debe = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, conf, trNDFromDB, paFromDb, c, trx);
            insert(debe);
        }

        //Actualizamos los comprobantes Contables
        ejbComprobante.actualizarMontosFinalizar(comprobanteIngreso);

        return trx;

    }

    @Override
    public Devolucion devolver(Devolucion d, String usuario) throws CRUDException {

        Optional op = Optional.ofNullable(d.getIdPagoAnticipado());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Pago Anticipado");
        }

        op = Optional.ofNullable(d);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado una devolución");
        }

        op = Optional.ofNullable(d.getIdPagoAnticipado());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Pago Anticipado");
        }

        op = Optional.ofNullable(d.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Cliente");
        }

        op = Optional.ofNullable(d.getConcepto());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Concepto");
        }

        op = Optional.ofNullable(d.getMonto());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Monto.");
        }

        op = Optional.ofNullable(d.getTipoDevolucion());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un tipo de Devolucion");
        }

        if (d.getTipoDevolucion().equals(FormasPago.CHEQUE)) {
            if (d.getNroCheque().isEmpty()) {
                throw new CRUDException("No se ha especificado un nro de Cheque");
            }
        }

        PagoAnticipado pFromDb = em.find(PagoAnticipado.class, d.getIdPagoAnticipado().getIdPagoAnticipado());

        op = Optional.ofNullable(pFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No existe el pago Anticipado especificado");
        }

        //Double montoDevuelto = d.getMonto().doubleValue();
        //Double montoPagoAnticipado = pFromDb.getMontoAnticipado().doubleValue();
        //Double nuevoMontoAnticipado = montoPagoAnticipado - montoDevuelto;
        //pFromDb.setMontoAnticipado(new BigDecimal(nuevoMontoAnticipado));
        d.setFechaEmision(DateContable.getCurrentDate());

        //Salvamos la devolucion
        d = ejbDevolucion.saveDevolucionFromPagoAnticipado(d, pFromDb);

        PagoAnticipadoTransaccion trxDev = new PagoAnticipadoTransaccion();
        trxDev.setDescripcion(d.getConcepto());
        trxDev.setEstado(Estado.EMITIDO);
        trxDev.setFechaInsert(DateContable.getCurrentDate());
        trxDev.setIdPagoAnticipado(d.getIdPagoAnticipado());
        trxDev.setIdUsuarioCreador(usuario);
        trxDev.setMonto(d.getMonto());
        trxDev.setMoneda(d.getMoneda());
        //Aqui se sabe que la transaccion es una devolucion
        trxDev.setTipo(PagoAnticipadoTransaccion.Tipo.DEVOLUCION);

        //salvamos la Transaccion del pago
        saveTransaccionDevolucion(trxDev, usuario);

        em.merge(pFromDb);

        return d;
    }

    @Override
    public PagoAnticipadoTransaccion updateTransaccion(PagoAnticipadoTransaccion trx) throws CRUDException {

        throw new CRUDException("No es posible realizar la operacion");

    }

    @Override
    public List<PagoAnticipadoTransaccion> getPagoAnticipadoTransaccionByNotaDebito(NotaDebito idNotaDebito) throws CRUDException {

        // String q = queries.getPropertie(Queries.GET_PAGO_ANTICIPADO_TRX_BY_ID_NOTA_DEBITO);
        Query query = em.createNamedQuery("PagoAnticipadoTransaccion.findByNotaDebito", PagoAnticipadoTransaccion.class);
        query.setParameter("idNotaDebito", idNotaDebito);

        List<PagoAnticipadoTransaccion> lreturn = null;

        lreturn = query.getResultList();

        if (lreturn.isEmpty()) {
            return new LinkedList<>();
        }

        return lreturn;

    }

}
