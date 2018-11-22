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
import com.agencia.entities.FormasPago;
import com.agencia.search.dto.BoletoSearchForm;
import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.ContabilidadBoletaje;
import com.configuracion.remote.CambioRemote;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Operacion;
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

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        NotaDebito nd = em.find(NotaDebito.class, e.getId());
        if (nd != null) {
            return nd;
        }
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

        query.setParameter("1", search.getIdEmpresa());

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

        Query q = em.createNamedQuery("NotaDebitoTransaccion.findAllByIdNotaDebito", NotaDebitoTransaccion.class);

        q.setParameter("idNotaDebito", idNotaDebito);

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
    public Integer asociarBoletoNotaDebito(Boleto b, NotaDebito n) throws CRUDException {
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

        return Operacion.REALIZADA;
    }

    @Override
    public void pendiente(NotaDebito n) throws CRUDException {
        
        NotaDebito fromDB = em.find(NotaDebito.class, n.getIdNotaDebito()) ;
        Optional op = Optional.ofNullable(fromDB);
        if (!op.isPresent()){
            throw new CRUDException("La Nota de Debito %s No existe" .replace("%s", n.getIdNotaDebito().toString()));
        }
        
        if (fromDB.getEstado().equals(NotaDebito.ANULADO)){
            throw new CRUDException("La Nota de Debito %s se encuentra ANULADA. No se puede pasar a PENDIENTE" .replace("%s", fromDB.getIdNotaDebito().toString()));
        }
        
        if (fromDB.getEstado().equals(NotaDebito.EMITIDO)){
            throw new CRUDException("La Nota de Debito %s se encuentra EMITIDA. No se puede pasar a PENDIENTE" .replace("%s", fromDB.getIdNotaDebito().toString()));
        }

        Query q = em.createNativeQuery(queries.getPropertie(Queries.UPDATE_NOTA_DEBITO_ESTADO));

        q.setParameter("1", n.getIdNotaDebito());
        q.setParameter("2", n.getIdCliente().getIdCliente());
        q.setParameter("3", n.getIdCounter().getIdPromotor());
        q.setParameter("4", n.getFechaEmision());
        q.setParameter("5", n.getFactorCambiario());
        q.setParameter("6", NotaDebito.PENDIENTE);
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
        transaccion.setEstado(NotaDebito.PENDIENTE);
        transaccion.setFechaInsert(DateContable.getCurrentDate());
        transaccion.setIdCargo(cargo.getIdCargo());
        transaccion.setIdNotaDebito(cargo.getIdNotaDebito());
        transaccion.setMoneda(cargo.getMoneda());
        if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            transaccion.setMontoBs(cargo.getComisionAgencia());
            transaccion.setMontoBs(transaccion.getMontoBs().add(cargo.getComisionMayorista()));
            transaccion.setMontoBs(transaccion.getMontoBs().add(cargo.getComisionPromotor()));
        } else {
            transaccion.setMontoUsd(cargo.getComisionAgencia());
            transaccion.setMontoUsd(transaccion.getMontoBs().add(cargo.getComisionMayorista()));
            transaccion.setMontoUsd(transaccion.getMontoBs().add(cargo.getComisionPromotor()));
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
    public CargoBoleto getCargo(CargoBoleto cargo) throws CRUDException {

        CargoBoleto c = em.find(CargoBoleto.class, cargo.getIdCargo());
        Optional o = Optional.ofNullable(c);

        if (o.isPresent()) {
            return c;
        }

        return null;
    }

    @Override
    public boolean finalizar(NotaDebito nota) throws CRUDException {

        NotaDebito fromBD = em.find(NotaDebito.class, nota.getId());
        Optional op = Optional.ofNullable(fromBD);
        if (!op.isPresent()) {
            throw new CRUDException("No se encontro la Nota de Debito");
        } else {
            
            if (fromBD.getEstado().equals(NotaDebito.ANULADO)) {
                throw new CRUDException("La nota de Debito : %s Se encuentra ANULADA".replace("%s", fromBD.getIdNotaDebito().toString()));
            }else if (fromBD.getEstado().equals(NotaDebito.EMITIDO)){
                throw new CRUDException("La nota de Debito : %s Se encuentra EMITIDA".replace("%s", fromBD.getIdNotaDebito().toString()));
            }
            
            fromBD.setIdCliente(nota.getIdCliente());
            fromBD.setIdCounter(nota.getIdCounter());
            fromBD.setFechaEmision(nota.getFechaEmision());
            fromBD.setFactorCambiario(nota.getFactorCambiario());
            int gestion = DateContable.getPartitionDateInt(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT));
            fromBD.setGestion(gestion);
        }

        // 1. Obtenemos las transacciones de las notas de debito
        Query q = em.createNamedQuery("NotaDebitoTransaccion.findAllByIdNotaDebitoAndPendientes", NotaDebitoTransaccion.class);
        q.setParameter("idNotaDebito", nota.getIdNotaDebito());

        List<NotaDebitoTransaccion> list = q.getResultList();

        // 2. Obtenemos la configuracion de Contabilidad
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("idEmpresa", nota.getIdEmpresa());
        List lconf = ejbComprobante.get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);
        if (lconf.isEmpty()) {
            throw new CRUDException("Los parametros de Contabilidad para la empresa no estan Configurados");
        }

        ContabilidadBoletaje conf = (ContabilidadBoletaje) lconf.get(0);
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
                }

                if (nota.getFormaPago() == null) {
                    throw new CRUDException("No se ha establecido una forma de pago para la nota de debito: " + nota.getIdNotaDebito());
                }

                //Pasamos a Contabilidad
                //ComprobanteDiario
                ComprobanteContable diario = null;//= ejbComprobante.createAsientoDiarioBoleto(boleto);
                ComprobanteContable ingreso = null;//= ejbComprobante.createAsientoDiarioBoleto(boleto);
                IngresoCaja caja = null;

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
                }

                for (NotaDebitoTransaccion nt : list) {
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
                                        boleto.setIdIngresoCaja(caja.getIdIngresoCaja());
                                        //creamos la transaccion para el ingreso de caja
                                        IngresoTransaccion ing = ejbIngresoCaja.createIngresoCajaTransaccion(nt, nota, caja);
                                        ing.setIdTransaccion(insert(ing));

                                        boleto.setIdIngresoCajaTransaccion(ing.getIdTransaccion());

                                        AsientoContable ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(ingreso, conf, nt, nota, boleto);
                                        ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                        insert(ingTotalCancelarCaja);

                                        AsientoContable ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(ingreso, conf, nt, nota, boleto);
                                        ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                        insert(ingTotalCancelarHaber);
                                    }
                                }
                            } catch (CRUDException ex) {
                                throw new CRUDException(ex.getMessage() + " Nro Nota Debito : " + nota.getIdNotaDebito());
                                //agregar logs que el boleto ya existe.
                            }
                            boleto.setEstado(Boleto.Estado.EMITIDO);

                            em.merge(boleto);
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
                                    cargo.setIdIngresoCaja(caja.getIdIngresoCaja());

                                    IngresoTransaccion ing = ejbIngresoCaja.createIngresoCajaTransaccion(nt, nota, caja);
                                    ing.setIdTransaccion(insert(ing));

                                    cargo.setIdIngresoCajaTransaccion(ing.getIdTransaccion());

                                    AsientoContable ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(ingreso, conf, nt, nota, cargo);
                                    ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                    insert(ingTotalCancelarCaja);

                                    AsientoContable ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(ingreso, conf, nt, nota, cargo);
                                    ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
                                    insert(ingTotalCancelarHaber);
                                }
                            } catch (CRUDException ex) {
                                throw new CRUDException(ex.getMessage() + " Nro Nota Debito : " + nota.getIdNotaDebito());
                            }

                            cargo.setEstado(Boleto.Estado.EMITIDO);
                            em.merge(cargo);
                        }
                    }

                    nt.setEstado(NotaDebito.EMITIDO);
                    em.merge(nt);

                }
                // Actualizamos el comprobante contable de los AD de los Boletos
                ejbComprobante.actualizarMontosFinalizar(diario);
                if (caja != null) {
                    // Actualizamos el comprobante contable de los CI de los Boletos
                    ejbComprobante.actualizarMontosFinalizar(ingreso);
                    // Actualizamos los montos del ingreso de Caja
                    ejbIngresoCaja.actualizarMontosFinalizar(caja);
                }

                fromBD.setEstado(NotaDebito.EMITIDO);
                em.merge(fromBD);
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

}
