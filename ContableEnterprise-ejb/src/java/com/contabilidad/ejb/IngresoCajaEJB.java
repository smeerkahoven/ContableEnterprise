/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Bancos;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.agencia.entities.TarjetaCredito;
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
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PlanCuentas;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.User;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
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
public class IngresoCajaEJB extends FacadeEJB implements IngresoCajaRemote {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;
    @EJB
    private CambioRemote ejbCambio;
    @EJB
    private BoletoRemote ejbBoleto;
    @EJB
    private ComprobanteRemote ejbComprobante;

    //YA NO SE USA
    @Override
    public IngresoCaja createIngresoCaja(final Boleto boleto, final NotaDebito nota) throws CRUDException {

        IngresoCaja ingreso = new IngresoCaja();

        ingreso.setFactorCambiario(nota.getFactorCambiario());
        ingreso.setFechaEmision(nota.getFechaEmision());
        ingreso.setFechaInsert(DateContable.getCurrentDate());
        ingreso.setFormaPago(boleto.getFormaPago());
        ingreso.setIdUsuario(boleto.getIdUsuarioCreador());
        ingreso.setIdCliente(boleto.getIdCliente());
        ingreso.setIdEmpresa(boleto.getIdEmpresa());
        //ingreso.setIdCounter(boleto.getIdPromotor());
        ingreso.setEstado(IngresoCaja.EMITIDO);

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            ingreso.setMontoAbonadoUsd(boleto.getTotalMontoCobrar());

        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            ingreso.setMontoAbonadoBs(boleto.getTotalMontoCobrar());
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
    public synchronized IngresoCaja createNewIngresoCaja(String idUsuario, Integer idEmpresa) throws CRUDException {

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

        IngresoCaja caja = new IngresoCaja();

        caja.setIdUsuario(idUsuario);
        caja.setIdEmpresa(idEmpresa);
        caja.setFechaInsert(DateContable.getCurrentDate());
        caja.setEstado(IngresoCaja.CREADO);

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

        caja.setFactorCambiario(diario.getValor());

        caja.setIdIngresoCaja(insert(caja));

        return caja;
    }

    @Override
    public IngresoCaja createIngresoCaja(NotaDebito nota) throws CRUDException {
        IngresoCaja ingreso = new IngresoCaja();
        ingreso.setFactorCambiario(nota.getFactorCambiario());
        ingreso.setFechaEmision(nota.getFechaEmision());
        ingreso.setFechaInsert(DateContable.getCurrentDate());
        ingreso.setIdUsuario(nota.getIdUsuarioCreador());
        ingreso.setIdCliente(nota.getIdCliente());
        ingreso.setIdEmpresa(nota.getIdEmpresa());
        //ingreso.setIdCounter(nota.getIdCounter());
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

    @Override
    public DebitoIngreso createDebitoIngresoCaja(NotaDebito nota, IngresoCaja caja) throws CRUDException {
        DebitoIngreso debitoIngreso = new DebitoIngreso();
        debitoIngreso.setIdIngresoCaja(caja);
        debitoIngreso.setIdNotaDebito(nota);

        return debitoIngreso;

    }

    // YA NO SE USA
    @Override
    public IngresoTransaccion createIngresoCajaTransaccion(Boleto boleto,
            NotaDebito nota, NotaDebitoTransaccion transacciones, IngresoCaja ingreso) throws CRUDException {
        IngresoTransaccion transaccion = new IngresoTransaccion();
        transaccion.setDescripcion(transacciones.getDescripcion());
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setIdIngresoCaja(ingreso);
        transaccion.setIdNotaTransaccion(transacciones);
        transaccion.setEstado(IngresoTransaccion.EMITIDO);

        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            transaccion.setMoneda(Moneda.EXTRANJERA);
            transaccion.setMontoUsd(boleto.getTotalMontoCobrar());
            transaccion.setMontoBs(BigDecimal.ZERO);
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            transaccion.setMoneda(Moneda.NACIONAL);
            transaccion.setMontoBs(boleto.getTotalMontoCobrar());
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
        transaccion.setIdNotaTransaccion(nt);
        transaccion.setEstado(IngresoTransaccion.EMITIDO);
        transaccion.setMoneda(nt.getMoneda());
        transaccion.setMontoBs(nt.getMontoBs());
        transaccion.setMontoUsd(nt.getMontoUsd());

        return transaccion;
    }

    @Override
    public void actualizarMontosFinalizar(IngresoCaja caja) throws CRUDException {

        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("IngresoCaja.updateMontosIngresoCajaFromFinalizar");
        spq.setParameter("in_id_ingreso", caja.getIdIngresoCaja());
        spq.executeUpdate();
    }

    @Override
    public List getIngresoCajaByNotaDebito(Integer idNotaDebito) throws CRUDException {

        Optional op = Optional.ofNullable(idNotaDebito);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar un Numero de Nota de Debito");
        }

        NotaDebito fromDb = em.find(NotaDebito.class, idNotaDebito);
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La Nota de Debito especificada, no Existe");
        }

        Query q = em.createNamedQuery("DebitoIngreso.findAllIngresoCajaByNotaDebito", DebitoIngreso.class);
        q.setParameter("idNotaDebito", fromDb);

        List<DebitoIngreso> l = q.getResultList();

        List<IngresoCaja> r = new LinkedList<>();

        for (DebitoIngreso i : l) {
            r.add(i.getIdIngresoCaja());
        }

        return r;
    }

    @Override
    public List<IngresoCaja> findAllIngresoCaja(IngresoCajaSearchJson search) throws CRUDException {

        Optional op = Optional.ofNullable(search.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un Cliente Valido");
        }

        Cliente fromDb = (Cliente) em.find(Cliente.class, (Integer) search.getIdCliente().getId());
        op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("El Cliente ingresado no existe");
        }

        Query q = em.createNamedQuery("IngresoCaja.findByIdCliente");
        q.setParameter("idCliente", fromDb);
        q.setParameter("idEmpresa", search.getIdEmpresa());

        List<IngresoCaja> l = q.getResultList();

        return l;
    }

    @Override
    public List<IngresoTransaccion> findAllIngresoCajaTransacciones(Integer idIngresoCaja) throws CRUDException {

        Optional op = Optional.ofNullable(idIngresoCaja);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar un Ingreso Caja");
        }

        IngresoCaja fromDb = em.find(IngresoCaja.class, idIngresoCaja);
        op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Numero de Ingreso Caja");
        }

        Query q = em.createNamedQuery("IngresoTransaccion.findByIdIngresoCaja");
        q.setParameter("idIngresoCaja", fromDb);

        List<IngresoTransaccion> l = q.getResultList();

        return l;
    }

    @Override
    public void anularTransaccion(NotaDebitoTransaccion tr, boolean update) throws CRUDException {
        //anulamos las transacciones de los Ingresos a Caja
        Optional op = Optional.ofNullable(tr);
        if (op.isPresent()) {
            op = Optional.ofNullable(tr.getIdNotaDebitoTransaccion());
            if (op.isPresent()) {

                Query q = em.createNamedQuery("IngresoTransaccion.findByIdNotaTransaccion", IngresoTransaccion.class);
                q.setParameter("idNotaTransaccion", tr.getIdNotaDebitoTransaccion());

                List<IngresoTransaccion> lit = q.getResultList();

                if (lit.isEmpty()) {
                    throw new CRUDException("No existen Transacciones de Ingreso de Caja para la Transaccion %s de la Nota de Debito".replace("%s", tr.getIdNotaDebitoTransaccion().toString()));
                }

                for (IngresoTransaccion it : lit) {
                    if (!it.getEstado().equals(IngresoCaja.ANULADO)) {
                        it.setEstado(IngresoCaja.ANULADO);
                        em.merge(it);

                        //actualizamos los montos. Es lo mismo que el finalizar
                        //ejecuta el procedure stored de las transacciones que estan en 
                        //estado emitido
                        actualizarMontosFinalizar(it.getIdIngresoCaja());
                    }
                }

            } else {
                throw new CRUDException("No se especifico un ID de Transaccion para el Ingreso de Caja");
            }
        } else {
            throw new CRUDException("No se especifico una Nota de Debito");
        }
    }

    @Override
    public void pendiente(IngresoCaja caja) throws CRUDException {

        IngresoCaja fromDb = em.find(IngresoCaja.class, caja.getIdIngresoCaja());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Ingreso de caja %s No Existe".replace("%s", caja.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.ANULADO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuenta ANULADA. No puede pasar a PENDIENTE.".replace("%s", caja.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.EMITIDO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra en estado EMITIDO. No puede pasar a PENDIENTE");
        }

        op = Optional.ofNullable(caja.getIdCliente());
        if (!op.isPresent()) {
            throw new CRUDException("No se ha especificado un Cliente");
        }

        Cliente cFromDb = em.find(Cliente.class, caja.getIdCliente().getIdCliente());
        op = Optional.ofNullable(cFromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Cliente %s no existe!".replace("%s", caja.getIdCliente().getIdCliente().toString()));
        }

        if (caja.getIdTarjetaCredito() != null) {
            TarjetaCredito tcFromDb = em.find(TarjetaCredito.class, caja.getIdTarjetaCredito());
            if (tcFromDb == null) {
                throw new CRUDException("No existe la Tarjeta de Credito");
            }
        }

        if (caja.getIdCuentaDeposito() != null) {
            PlanCuentas pcFromDb = em.find(PlanCuentas.class, caja.getIdCuentaDeposito());
            if (pcFromDb == null) {
                throw new CRUDException("No existe el Plan de Cuentas para el Deposito");
            }
        }

        if (caja.getIdBanco() != null) {
            Bancos bFromDb = em.find(Bancos.class, caja.getIdBanco());
            if (bFromDb == null) {
                throw new CRUDException("No existe el Banco ingresado");
            }
        }

        Query q = em.createNamedQuery("IngresoCaja.updateToPendiente");
        q.setParameter("idCliente", caja.getIdCliente());
        q.setParameter("fechaEmision", caja.getFechaEmision());
        q.setParameter("formaPago", caja.getFormaPago());
        q.setParameter("idBanco", caja.getIdBanco());
        q.setParameter("nroCheque", caja.getNroCheque());
        q.setParameter("idTarjetaCredito", caja.getIdTarjetaCredito());
        q.setParameter("nroTarjeta", caja.getNroTarjeta());
        q.setParameter("nroDeposito", caja.getNroDeposito());
        q.setParameter("idCuentaDeposito", caja.getIdCuentaDeposito());
        q.setParameter("estado", IngresoCaja.PENDIENTE);
        q.setParameter("idIngresoCaja", caja.getIdIngresoCaja());

    }

    @Override
    public void anularIngresoCaja(Integer idIngresoCaja) throws CRUDException {

        NotaDebito fromDb = em.find(NotaDebito.class, idIngresoCaja);
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Ingreso de Caja %s no existe.".replace("%s", idIngresoCaja.toString()));
        }

        fromDb.setEstado(IngresoCaja.ANULADO);

        em.merge(fromDb);

    }

    @Override
    public void anularTransaccion(Integer idTransaccion) throws CRUDException {
        Optional op = Optional.ofNullable(idTransaccion);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la Transaccion %s de Ingreso de Caja.".replace("%s", idTransaccion.toString()));
        }

        IngresoTransaccion fromDb = em.find(IngresoTransaccion.class, idTransaccion);
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transaccion %s del Ingreso de Caja, no existe.".replace("%s", idTransaccion.toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.ANULADO)) {
            throw new CRUDException("La transaccion %s se encuentra ANULADA.".replace("%s", idTransaccion.toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.EMITIDO)) {
            throw new CRUDException("La transaccion %s se encuentra EMITIDA. No puede Anularse.".replace("%s", idTransaccion.toString()));
        }

        fromDb.setEstado(IngresoCaja.ANULADO);

        em.merge(fromDb);

        //actualizamos los montos en los ingresos de Caja o pasa a Anulado.
        updateMontosIngresoCaja(fromDb.getIdIngresoCaja().getIdIngresoCaja());

    }

    @Override
    public IngresoCaja finalizar(Integer idIngresoCaja) throws CRUDException {

        Optional op = Optional.ofNullable(idIngresoCaja);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar un Numero de Ingreso de Caja.".replace("%s", idIngresoCaja.toString()));
        }

        IngresoCaja fromDb = em.find(IngresoCaja.class, idIngresoCaja);
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado el Ingreso de Caja %s.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.ANULADO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra ANULADA.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getEstado().equals(IngresoCaja.EMITIDO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra EMITIDA.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getIngresoTransaccionCollection().isEmpty()) {
            throw new CRUDException("Debe Ingresar al menos una Transaccion para crear los Comprobantes Contables.".replace("%s", idIngresoCaja.toString()));
        }

        fromDb.setEstado(IngresoCaja.EMITIDO);

        ContabilidadBoletaje conf = ejbNotaDebito.getConfiguracion(fromDb.getIdEmpresa());

        String glosa = getGlosa(fromDb);
        // Insertamos el comprobante
        ComprobanteContable comprobanteIngreso = ejbComprobante.createComprobante(ComprobanteContable.Tipo.COMPROBANTE_INGRESO, glosa, fromDb);
        comprobanteIngreso.setIdLibro(insert(comprobanteIngreso));

        if (ejbBoleto.validarConfiguracion(conf)) {
            for (IngresoTransaccion tran : fromDb.getIngresoTransaccionCollection()) {
                // Duda, 
                NotaDebitoTransaccion ndt = tran.getIdNotaTransaccion();
                if (ndt.getTipo().equals(NotaDebitoTransaccion.Tipo.BOLETO)) {
                    Boleto b = em.find(Boleto.class, ndt.getIdBoleto());
                    if (b == null) {
                        String msg = "No existe el ID Boleto : %b en la Transaccion %t de la Nota de Debito %n";
                        msg = msg.replace("%b", ndt.getIdBoleto().toString());
                        msg = msg.replace("%t", ndt.getIdNotaDebitoTransaccion().toString());
                        msg = msg.replace("%n", ndt.getIdNotaDebito().toString());
                        throw new CRUDException(msg);
                    }

                    AsientoContable haber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, conf, ndt, fromDb, b, tran);
                    insert(haber);

                    AsientoContable debe = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, conf, ndt, fromDb, b, tran);
                    insert(debe);
                } else {
                    CargoBoleto c = em.find(CargoBoleto.class, ndt.getIdCargo());
                    if (c == null) {
                        String msg = "No existe el ID Cargo: %c en la Transaccion %t de la Nota de Debito %n";
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

                //Si me Pagan en USD
                if (tran.getMoneda().equals(Moneda.EXTRANJERA)) {
                    //Si la moneda de la transaccion de la nota de debito esta en USD
                    //Descuento el monto adeudado por el monto cancelado
                    if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
                        ndt.setMontoAdeudadoUsd(ndt.getMontoAdeudadoUsd().subtract(tran.getMontoUsd()));
                    } else {
                        //Si la moneda de la transaccion de la nota de debito esta en BS
                        //convierto el monto cancelado a Bs
                        //Descuento el monto adeudado por el monto cancelado
                        Double montoCancelado = tran.getMontoBs().doubleValue() * fromDb.getFactorCambiario().doubleValue();
                        ndt.setMontoAdeudadoBs(ndt.getMontoAdeudadoBs().subtract(new BigDecimal(montoCancelado)));
                    }
                    //Si pagan en BS
                } else {
                    //Si la moneda de la transaccion de la nota de debito esta en BS
                    if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
                        //Descuento el monto adeudado por el monto cancelado
                        ndt.setMontoAdeudadoBs(ndt.getMontoAdeudadoBs().subtract(tran.getMontoBs()));
                    } else {
                        //Si la moneda de la transaccion de la nota de debito esta en USD
                        //convierto el monto cancelado a USD
                        //Descuento el monto adeudado por el monto cancelado
                        Double montoCancelado = tran.getMontoBs().doubleValue() / fromDb.getFactorCambiario().doubleValue();
                        ndt.setMontoAdeudadoBs(ndt.getMontoAdeudadoUsd().subtract(new BigDecimal(montoCancelado)));
                    }
                }

                em.merge(ndt);

                tran.setEstado(NotaDebito.EMITIDO);
                em.merge(tran);
            }
        }

        em.merge(fromDb);

        
        updateMontosAdeudadosNotaDebitos(fromDb);
        
        return fromDb;

    }

    private void updateMontosIngresoCaja(Integer idIngresoCaja) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("IngresoCaja.updateMontosIngresoCaja");
        spq.setParameter("in_id_ingreso_caja", idIngresoCaja);
        spq.executeUpdate();
    }

    private String getGlosa(IngresoCaja idIngresoCaja) {
        StringBuffer buff = new StringBuffer();
        buff.append("PAGO POR NOTAS DE DEBITO #:");
        Query q = em.createNamedQuery("DebitoIngreso.findAllByIngresoCaja");
        q.setParameter("idIngresoCaja", idIngresoCaja);

        List<DebitoIngreso> list = (List<DebitoIngreso>) q.getResultList();

        for (int i = 0; i < list.size(); i++) {
            DebitoIngreso di = list.get(i);
            buff.append(di.getIdNotaDebito().toString());

            if (i + 1 < list.size()) {
                buff.append(", ");
            }

        }

        return buff.toString();

    }

    private void updateMontosAdeudadosNotaDebitos(IngresoCaja idIngresoCaja) {
        Query q = em.createNamedQuery("DebitoIngreso.findAllByIngresoCaja");
        q.setParameter("idIngresoCaja", idIngresoCaja);

        List<DebitoIngreso> list = (List<DebitoIngreso>) q.getResultList();

        for (DebitoIngreso di : list) {
            StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("NotaDebito.updateMontosAdeudadosNotaDebito");
            spq.setParameter("in_id_nota_debito", di.getIdNotaDebito().getIdNotaDebito());
            spq.executeUpdate();
        }

    }

}
