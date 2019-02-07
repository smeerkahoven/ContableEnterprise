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
import com.seguridad.control.remote.LoggerRemote;
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
public class IngresoCajaEJB extends FacadeEJB implements IngresoCajaRemote {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;
    @EJB
    private CambioRemote ejbCambio;
    @EJB
    private BoletoRemote ejbBoleto;
    @EJB
    private ComprobanteRemote ejbComprobante;
    @EJB
    private LoggerRemote ejbLoger;

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
        ingreso.setEstado(Estado.EMITIDO);

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
        caja.setEstado(Estado.CREADO);
        caja.setFormaPago(FormasPago.EFECTIVO);
        caja.setFechaEmision(DateContable.getCurrentDate());

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
        ingreso.setEstado(Estado.EMITIDO);

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
        transaccion.setEstado(Estado.EMITIDO);

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
        transaccion.setEstado(Estado.EMITIDO);
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
    public List<IngresoTransaccion> getIngresoCajaTrxByIdNotaDebito(NotaDebito idNotaDebito) throws CRUDException {

        //String q = queries.getPropertie(Queries.GET_NOTA);
        Query query = em.createNamedQuery("IngresoTransaccion.findByIdNotaDebito", IngresoTransaccion.class);
        query.setParameter("idNotaDebito", idNotaDebito);

        List lreturn = query.getResultList();
        if (lreturn.isEmpty()) {
            return new LinkedList<>();
        }

        return lreturn;
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

        Optional op = Optional.ofNullable(search.getIdEmpresa());

        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = queries.getPropertie(Queries.GET_INGRESO_CAJA);

        // dentro de q ya esta el WHERE nd.id_empresa = :idEmpresa
        op = Optional.ofNullable(search.getIdCliente());
        if (op.isPresent()) {
            if (search.getIdCliente().getId() != null) {
                q += " AND c.id_cliente =?2 ";
            }
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            if (search.getFechaInicio().trim().length() > 1) {
                q += " AND c.fecha_emision>=?3";
            }
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            if (search.getFechaFin().trim().length() > 1) {
                q += " AND c.fecha_emision<=?4";
            }
        }

        q += " ORDER BY c.id_ingreso_caja";

        Query query = em.createNativeQuery(q, IngresoCaja.class);

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
    public void pendiente(IngresoCaja caja) throws CRUDException {

        IngresoCaja fromDb = em.find(IngresoCaja.class, caja.getIdIngresoCaja());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Ingreso de caja %s No Existe".replace("%s", caja.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuenta ANULADA. No puede pasar a PENDIENTE.".replace("%s", caja.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra en estado EMITIDO. No puede pasar a PENDIENTE".replace("%s", caja.getIdIngresoCaja().toString()));
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

        /*if (fromDb.getIngresoTransaccionCollection().isEmpty()) {
            throw new CRUDException("Debe al menos ingresar una transaccion para colocar en PENDIENTE");
        }*/
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
        q.setParameter("estado", Estado.PENDIENTE);
        q.setParameter("idIngresoCaja", caja.getIdIngresoCaja());

        q.executeUpdate();

    }

    @Override
    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException {
        //anulamos las transacciones de los Ingresos a Caja
        Optional op = Optional.ofNullable(tr);
        if (op.isPresent()) {
            op = Optional.ofNullable(tr.getIdNotaDebitoTransaccion());
            if (op.isPresent()) {

                Query q = em.createNamedQuery("IngresoTransaccion.findByIdNotaTransaccion", IngresoTransaccion.class);
                q.setParameter("idNotaTransaccion", tr);

                List<IngresoTransaccion> lit = q.getResultList();

                if (!lit.isEmpty()) {
                    for (IngresoTransaccion it : lit) {
                        if (!it.getEstado().equals(Estado.ANULADO)) {

                            it.setEstado(Estado.ANULADO);
                            em.merge(it);

                            updateMontosIngresoCaja(it.getIdIngresoCaja().getIdIngresoCaja());
                            //anulamos los asientos contables
                            ejbComprobante.anularAsientosContables(it, usuario);
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

    @Override
    public void anularIngresoCaja(Integer idIngresoCaja, String usuario) throws CRUDException {

        IngresoCaja fromDb = em.find(IngresoCaja.class, idIngresoCaja);
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("El Ingreso de Caja %s no existe.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Ingreso a caja ya se encuentra Anulado.");
        }

        User uFromDb = em.find(User.class, usuario);
        op = Optional.ofNullable(uFromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el usuario %s".replace("%s", usuario));
        };

        //revertimos los ingresos de Caja, los montos de cada Ingreso de Caja deben 
        //revertirse en las Notas de Debito
        //y si tiene asientos Contables, deben Anularse
        for (IngresoTransaccion it : fromDb.getIngresoTransaccionCollection()) {
            NotaDebitoTransaccion ndtx = it.getIdNotaTransaccion();
            ejbNotaDebito.revertirMontosIngresoDeCaja(it, ndtx);
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
    public void anularTransaccion(IngresoTransaccion trx, String usuario) throws CRUDException {

        IngresoTransaccion fromDb = em.find(IngresoTransaccion.class, trx.getIdTransaccion());
        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transaccion del Ingreso de Caja %s, no se ha encontrado.".replace("%s", trx.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La transaccion del Ingreso de Caja %s que ha ingresado ya se encuentra ANULADA".replace("%s", trx.getIdIngresoCaja().toString()));
        }

        if (fromDb.getEstado().equals(Estado.PENDIENTE)) {
            revertirMontosAnteriors(trx, fromDb);

            fromDb.setEstado(Estado.ANULADO);
            updateMontosIngresoCaja(trx.getIdIngresoCaja().getIdIngresoCaja());
        }

        // Si esta emitido, se revierten los montos
        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            revertirMontosAnteriors(trx, fromDb);

            fromDb.setEstado(Estado.ANULADO);
            em.merge(fromDb);

            updateMontosIngresoCaja(trx.getIdIngresoCaja().getIdIngresoCaja());
            //anulamos los asientos contables
            ejbComprobante.anularAsientosContables(fromDb, usuario);
        }

    }

    @Override
    public void anularTransaccion(Integer idTransaccion, String usuario) throws CRUDException {
        Optional op = Optional.ofNullable(idTransaccion);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la Transaccion %s de Ingreso de Caja.".replace("%s", idTransaccion.toString()));
        }

        IngresoTransaccion tmp = new IngresoTransaccion(idTransaccion);
        anularTransaccion(tmp, usuario);

    }

    @Override
    public IngresoCaja finalizar(IngresoCaja idIngresoCaja) throws CRUDException {

        Optional op = Optional.ofNullable(idIngresoCaja);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar un Numero de Ingreso de Caja.".replace("%s", idIngresoCaja.toString()));
        }

        IngresoCaja fromDb = em.find(IngresoCaja.class, idIngresoCaja.getIdIngresoCaja());
        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se ha encontrado el Ingreso de Caja %s.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra ANULADA.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("El Ingreso de Caja %s se encuentra EMITIDA.".replace("%s", idIngresoCaja.toString()));
        }

        if (fromDb.getIngresoTransaccionCollection().isEmpty()) {
            throw new CRUDException("Debe Ingresar al menos una Transaccion para crear los Comprobantes Contables.".replace("%s", idIngresoCaja.toString()));
        }

        fromDb.setEstado(Estado.EMITIDO);
        fromDb.setFormaPago(idIngresoCaja.getFormaPago());
        fromDb.setNroCheque(idIngresoCaja.getNroCheque());
        fromDb.setNroDeposito(idIngresoCaja.getNroDeposito());
        fromDb.setNroTarjeta(idIngresoCaja.getNroTarjeta());
        fromDb.setIdBanco(idIngresoCaja.getIdBanco());
        fromDb.setIdCuentaDeposito(idIngresoCaja.getIdCuentaDeposito());
        fromDb.setIdTarjetaCredito(idIngresoCaja.getIdTarjetaCredito());

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
                em.merge(ndt);

                tran.setEstado(Estado.EMITIDO);
                em.merge(tran);
            }
        }

        em.merge(fromDb);

        //Actualizamos los montos adeudados de las notas de Debito
        updateMontosAdeudadosNotaDebitos(fromDb);

        //Actualizamos los comprobantes Contables
        ejbComprobante.actualizarMontosFinalizar(comprobanteIngreso);

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
            buff.append(di.getIdNotaDebito().getIdNotaDebito().toString());

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

    @Override
    public IngresoTransaccion getIngresoTransaccion(IngresoTransaccion data) throws CRUDException {
        Optional op = Optional.ofNullable(data);
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Transaccion Valida");
        }

        op = Optional.ofNullable(data.getIdTransaccion());
        if (!op.isPresent()) {
            throw new CRUDException("Debe ingresar una Transaccion Valida");
        }

        IngresoTransaccion fromDb = em.find(IngresoTransaccion.class, data.getIdTransaccion());

        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la transaccion %id para el Ingreso de Caja".replace("%id", data.getIdTransaccion().toString()));
        }

        return fromDb;
    }

    private void revertirMontosAnteriors(IngresoTransaccion trx, IngresoTransaccion fromDb) throws CRUDException {

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

    @Override
    public IngresoTransaccion updateTransaccion(IngresoTransaccion trx) throws CRUDException {
        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transaccion.");
        }

        IngresoTransaccion fromDb = em.find(IngresoTransaccion.class, trx.getIdTransaccion());

        op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("La transaccion especificada NO existe.");
        }

        if (fromDb.getEstado().equals(Estado.EMITIDO)) {
            throw new CRUDException("La transaccion se encuentra EMITIDA, no puede modificar sus valores");
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("La transaccion se encuentra ANULADA, no puede modificar sus valores");
        }

        if (trx.getMontoBs() == null && trx.getMontoUsd() == null) {
            throw new CRUDException("Debe al menos ingresar un monto en la Transaccion del ingreso de Caja");
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

        updateMontosIngresoCaja(trx.getIdIngresoCaja().getIdIngresoCaja());

        em.flush();

        return fromDb;
    }

    @Override
    public IngresoTransaccion saveTransaccion(IngresoTransaccion trx) throws CRUDException {

        Optional op = Optional.ofNullable(trx);
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Transaccion.");
        }

        op = Optional.ofNullable(trx.getIdIngresoCaja());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar un Ingreso de Caja para la transaccion.");
        }

        op = Optional.ofNullable(trx.getIdNotaTransaccion());
        if (!op.isPresent()) {
            throw new CRUDException("Debe especificar una Nota de Debito para la transaccion.");
        }

        if (trx.getMontoBs() == null && trx.getMontoUsd() == null) {
            throw new CRUDException("Debe al menos ingresar un monto en la Transaccion del ingreso de Caja");
        }

        trx.setIdNotaTransaccion(em.find(NotaDebitoTransaccion.class, trx.getIdNotaTransaccion().getIdNotaDebitoTransaccion()));
        trx.setIdIngresoCaja(em.find(IngresoCaja.class, trx.getIdIngresoCaja().getIdIngresoCaja()));
        trx.setFechaInsert(DateContable.getCurrentDate());
        //Actualizamos la nota de Debito
        ejbNotaDebito.actualizarMontosAdeudadosTransaccion(trx);
        //Insertamos
        trx.setIdTransaccion(insert(trx));
        //actualizamos
        updateMontosIngresoCaja(trx.getIdIngresoCaja().getIdIngresoCaja());

        em.flush();

        Query q = em.createNamedQuery("DebitoIngreso.findByIngresoCajaNotaDebito");
        q.setParameter("idIngresoCaja", trx.getIdIngresoCaja());
        q.setParameter("idNotaDebito", trx.getIdNotaTransaccion().getIdNotaDebito());

        //Insertamos en la Tabla de DebitoIngreos para tener Registro de la Transaccion
        List<DebitoIngreso> l = q.getResultList();

        if (l.isEmpty()) {
            DebitoIngreso newDi = new DebitoIngreso();
            newDi.setIdIngresoCaja(trx.getIdIngresoCaja());
            newDi.setIdNotaDebito(trx.getIdNotaTransaccion().getIdNotaDebito());
            insert(newDi);
        }

        return trx;
    }

}
