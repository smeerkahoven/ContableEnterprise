/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Contabilidad;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import com.seguridad.utils.Operacion;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
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
public class ComprobanteEJB extends FacadeEJB implements ComprobanteRemote {

    @Override
    public void pendiente(Integer idLibro, String usuario) throws CRUDException {

        ComprobanteContable fromDb = em.find(ComprobanteContable.class, idLibro);

        Optional op = Optional.ofNullable(fromDb);
        if (!op.isPresent()) {
            throw new CRUDException("No se encontro el Libro %s en la Base de datos.".replace("%s", idLibro.toString()));
        }

        if (fromDb.getEstado().equals(Estado.ANULADO)) {
            throw new CRUDException("El comprobante se encuentra ANULADO. No puede pasar a PENDIENTE");
        }

        op = Optional.ofNullable(fromDb.getIdNotaCredito());
        if (!op.isPresent()) {
            throw new CRUDException("No se puede colocar en Pendiente si tiene ");
        }

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("1", ComprobanteContable.PENDIENTE);
        parameters.put("2", usuario);
        parameters.put("3", idLibro);

        executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_ESTADO, parameters);

    }

    @Override
    public Integer getNextComprobantePK(Date fecha, String tipo) throws CRUDException {

        Integer gestion = Integer.parseInt(DateContable.getPartitionDate(DateContable.getDateFormat(fecha, DateContable.LATIN_AMERICA_FORMAT)));
        Number next = 0;
        String query = queries.getPropertie(Queries.GET_NEXT_ID_LIBRO);
        //query = query.replace("[partition]", gestion.toString());

        Query q = em.createNativeQuery(query);
        q.setParameter("1", tipo);
        q.setParameter("2", gestion);

        List l = q.getResultList();

        if (!l.isEmpty()) {
            next = (Number) l.get(0);
        }

        //ComprobanteContablePK pk = new ComprobanteContablePK(next.intValue(), gestion);
        return next.intValue();
    }

    @Override
    public AsientoContable addTransaccion(AsientoContable asiento) throws CRUDException {

        ComprobanteContable fromDb = em.find(ComprobanteContable.class, asiento.getIdLibro().getIdLibro());
        if (fromDb == null) {
            throw new CRUDException("No existe el Comprobante Contable:" + asiento.getIdLibro().getIdLibro());
        }

        asiento.setIdAsiento(insert(asiento));

        actualizarMontosFinalizar(asiento.getIdLibro());

        return asiento;

    }

    @Override
    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF,
            Integer idEmpresa) throws CRUDException {

        String q = "SELECT c FROM ComprobanteContable c ";
        if (tipo.trim().length() > 0 || estado.trim().length() > 0
                || fechaI.trim().length() > 0 || fechaF.trim().length() > 0) {
            q += "WHERE ";

            if (tipo.trim().length() > 0) {
                q += " c.tipo=:tipo AND";
            }

            if (estado.trim().length() > 0) {
                q += " c.estado=:estado AND";
            }
            if (fechaI.trim().length() > 0) {
                q += " c.fecha>=:fechaI AND";
            }

            if (fechaF.trim().length() > 0) {
                q += " c.fecha<=:fechaF AND";
            }

            q += " c.idEmpresa=:idEmpresa ";
        }

        Query query = em.createQuery(q, ComprobanteContable.class);
        if (tipo.trim().length() > 0) {
            query.setParameter("tipo", tipo);
        }

        if (estado.trim().length() > 0) {
            query.setParameter("estado", estado);
        }
        if (fechaI.trim().length() > 0) {
            query.setParameter("fechaI", DateContable.toLatinAmericaDateFormat(fechaI));
        }

        if (fechaF.trim().length() > 0) {
            query.setParameter("fechaF", DateContable.toLatinAmericaDateFormat(fechaF));
        }

        query.setParameter("idEmpresa", idEmpresa);
        /*System.out.println(q);
        System.out.println(fechaI);
        System.out.println(fechaF);
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaI));
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaF));*/

        return query.getResultList();

    }

    /**
     * Se realiza el proceso de pasar un boleto a un comprobante contable 1. Se
     * obtiene la configuracion del b
     *
     * @param boleto
     * @throws CRUDException
     */
    @Override
    public void procesarBoleto(Boleto boleto) throws CRUDException {

        //1 Obtenemos la configuracion de la contabilidad del boleto de acuerdo a la empresa
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("idEmpresa", boleto.getIdEmpresa());

        ContabilidadBoletaje contabilidad = (ContabilidadBoletaje) get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);

        Optional op = Optional.ofNullable(contabilidad);
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion de Boletaje para la Entidad " + boleto.getIdEmpresa());
        }

    }

    @Override
    public ComprobanteContable createComprobante(String tipo, String concepto,
            NotaDebito nota) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(nota.getIdCliente());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(nota.getFactorCambiario());
        comprobante.setFecha(nota.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(nota.getIdEmpresa());
        comprobante.setIdUsuarioCreador(nota.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        comprobante.setIdNotaDebito(nota.getIdNotaDebito());
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        Integer numero = getNextComprobantePK(nota.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    @Override
    public ComprobanteContable createComprobante(String tipo, String concepto,
            PagoAnticipado pago) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(pago.getIdCliente());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(pago.getFactorCambiario());
        comprobante.setFecha(pago.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(pago.getIdEmpresa());
        comprobante.setIdUsuarioCreador(pago.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        comprobante.setIdPagoAnticipado(pago.getIdPagoAnticipado());
        comprobante.setEstado(ComprobanteContable.EMITIDO);

        Integer numero = getNextComprobantePK(pago.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(pago.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    @Override
    public ComprobanteContable createComprobante(String tipo, String concepto,
            Devolucion dev) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(dev.getIdCliente());
        comprobante.setNombre(dev.getIdCliente().getNombre());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(dev.getFactorCambiario());
        comprobante.setFecha(dev.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(dev.getIdEmpresa());
        comprobante.setIdUsuarioCreador(dev.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        comprobante.setIdDevolucion(dev.getIdDevolucion());
        comprobante.setEstado(ComprobanteContable.EMITIDO);

        Integer numero = getNextComprobantePK(dev.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(dev.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    @Override
    public ComprobanteContable createComprobante(String tipo, String concepto, IngresoCaja ingreso) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(ingreso.getIdCliente());
        comprobante.setNombre(ingreso.getIdCliente().getNombre());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(ingreso.getFactorCambiario());
        comprobante.setFecha(ingreso.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(ingreso.getIdEmpresa());
        comprobante.setIdUsuarioCreador(ingreso.getIdUsuario());
        comprobante.setTipo(tipo);
        comprobante.setIdIngresoCaja(ingreso.getIdIngresoCaja());
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        Integer numero = getNextComprobantePK(ingreso.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(ingreso.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    @Override
    public ComprobanteContable createComprobante(String tipo, String concepto, NotaCredito nota) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(nota.getIdCliente());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(nota.getFactorCambiario());
        comprobante.setFecha(nota.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(nota.getIdEmpresa());
        comprobante.setIdUsuarioCreador(nota.getIdUsuario());
        comprobante.setTipo(tipo);
        comprobante.setIdNotaCredito(nota.getIdNotaCredito());
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        Integer numero = getNextComprobantePK(nota.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    // YA NO SE VA USAR
    @Override
    public ComprobanteContable createComprobante(Aerolinea a, Boleto boleto, Cliente cliente, String tipo, NotaDebito nota) throws CRUDException {

        ComprobanteContable comprobante = new ComprobanteContable();
        //ComprobanteContablePK pk = new ComprobanteContablePK();
        //pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));

        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        buff.append(a.getNumero());

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

        //jala el nombre cliente
        comprobante.setIdCliente(cliente);
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        //comprobante.setComprobanteContablePK(pk);

        //ComprobanteContablePK numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        Integer numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        return comprobante;
    }

    // YA NO SE VA USAR
    @Override
    public ComprobanteContable createComprobante(
            Aerolinea a, Boleto boleto, Cliente cliente, String tipo) throws CRUDException {
        Optional op;
        ComprobanteContable comprobante = new ComprobanteContable();
        //ComprobanteContablePK pk = new ComprobanteContablePK();
        //pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        //comprobante.setComprobanteContablePK(pk);
        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        buff.append(a.getNumero());

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

        //jala el nombre cliente
        comprobante.setIdCliente(cliente);
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(tipo);

        //ComprobanteContablePK numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        Integer numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));

        return comprobante;
    }

    // YA NO SE VA USAR
    @Override
    public ComprobanteContable createAsientoDiarioBoleto(Boleto boleto) throws CRUDException {

        Optional op;
        ComprobanteContable comprobante = new ComprobanteContable();
        //ComprobanteContablePK pk = new ComprobanteContablePK();
        //pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        //comprobante.setComprobanteContablePK(pk);

        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        /*Aerolinea a = em.find(Aerolinea.class, boleto.getIdAerolinea().getIdAerolinea());
        if (a != null) {
            buff.append(a.getNumero());
        }*/
        buff.append(boleto.getIdAerolinea().getNumero());

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

        //jala el nombre cliente
        Cliente c = em.find(Cliente.class, boleto.getIdCliente().getIdCliente());
        op = Optional.ofNullable(c);

        if (!op.isPresent()) {
            throw new CRUDException("No se encontro un Cliente para el boleto");
        }

        comprobante.setIdCliente(c);
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(ComprobanteContable.Tipo.ASIENTO_DIARIO);

        Integer numero = getNextComprobantePK(boleto.getFechaEmision(), ComprobanteContable.Tipo.ASIENTO_DIARIO);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setIdNumeroGestion(numero);

        return comprobante;
    }

    @Override
    public ComprobanteContable createAsientoDiarioBoleto(NotaDebito nota, String concepto) throws CRUDException {

        Optional op;
        ComprobanteContable comprobante = new ComprobanteContable();
        //ComprobanteContablePK pk = new ComprobanteContablePK();
        //pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setEstado(ComprobanteContable.EMITIDO);
        //comprobante.setComprobanteContablePK(pk);
        comprobante.setIdNotaDebito(nota.getIdNotaDebito());

        comprobante.setIdCliente(nota.getIdCliente());
        comprobante.setConcepto(concepto);
        comprobante.setFactorCambiario(nota.getFactorCambiario());
        comprobante.setFecha(nota.getFechaEmision());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setIdEmpresa(nota.getIdEmpresa());
        comprobante.setIdUsuarioCreador(nota.getIdUsuarioCreador());
        comprobante.setTipo(ComprobanteContable.Tipo.ASIENTO_DIARIO);

        Integer numero = getNextComprobantePK(nota.getFechaEmision(), ComprobanteContable.Tipo.ASIENTO_DIARIO);
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(nota.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setIdNumeroGestion(numero);

        return comprobante;
    }

    @Override
    public synchronized ComprobanteContable procesarComprobante(ComprobanteContable comprobante) throws CRUDException {

        //ComprobanteContablePK numero = getNextComprobantePK(comprobante.getFecha(), comprobante.getTipo());
        Integer numero = getNextComprobantePK(comprobante.getFecha(), comprobante.getTipo());

        comprobante.setIdNumeroGestion(numero);
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        //comprobante.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(comprobante.getFecha(), DateContable.LATIN_AMERICA_FORMAT)));
        Integer idLibro = insert(comprobante);

        comprobante.setIdLibro(idLibro);

        return comprobante;

    }

    public ComprobanteContable procesarComprobanteBoleto(ComprobanteContable comprobante) throws CRUDException {
        Integer numero = getNextComprobantePK(comprobante.getFecha(), comprobante.getTipo());

        comprobante.setIdNumeroGestion(numero);
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(comprobante.getFecha(), DateContable.LATIN_AMERICA_FORMAT)));
        //comprobante.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));

        return comprobante;

    }

    @Override
    public AsientoContable procesarAsientoContable(AsientoContable a, ComprobanteContable c) throws CRUDException {

        a.setFechaMovimiento(DateContable.getCurrentDate());
        a.setIdLibro(c);
        a.setGestion(c.getGestion());
        a.setIdAsiento(insert(a));

        return a;

    }

    /**
     * Crea un asiento Contable para el total a cancelar
     *
     * @param b Boleto
     * @param cc Comprobante Contable
     * @param conf Configuracion de la Contabilidad del Boleto
     * @param a Aerolinea
     * @param idTransaccion
     * @return
     */
    @Override
    public AsientoContable crearTotalCancelar(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, final NotaDebitoTransaccion idTransaccion) {

        AsientoContable totalCancelar = new AsientoContable();

        totalCancelar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        totalCancelar.setIdLibro(cc);
        totalCancelar.setGestion(cc.getGestion());
        totalCancelar.setFechaMovimiento(DateContable.getCurrentDate());
        totalCancelar.setEstado(ComprobanteContable.EMITIDO);
        //totalCancelar.setIdBoleto(b.getIdBoleto);
        totalCancelar.setIdBoleto(b);
        totalCancelar.setIdNotaTransaccion(idTransaccion);
        totalCancelar.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            totalCancelar.setMontoDebeExt(b.getTotalMontoCobrar());
            totalCancelar.setMontoDebeNac(b.getTotalMontoCobrar() != null
                    ? b.getTotalMontoCobrar().multiply(b.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN))
                    : null);
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoUs());
            totalCancelar.setMoneda(Moneda.EXTRANJERA);
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            Double montoHaber = b.getTotalMontoCobrar() != null ? b.getTotalMontoCobrar().doubleValue() / b.getFactorCambiario().doubleValue() : 0.0f;
            totalCancelar.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            totalCancelar.setMontoDebeNac(b.getTotalMontoCobrar());
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoBs());
            totalCancelar.setMoneda(Moneda.NACIONAL);
        }

        return totalCancelar;
    }

    @Override
    public AsientoContable crearClienteXCobrar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, NotaDebitoTransaccion idTransaccion) {

        AsientoContable clientexCobrar = new AsientoContable();

        clientexCobrar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        clientexCobrar.setIdLibro(cc);
        clientexCobrar.setGestion(cc.getGestion());
        clientexCobrar.setFechaMovimiento(DateContable.getCurrentDate());
        clientexCobrar.setEstado(ComprobanteContable.EMITIDO);
        clientexCobrar.setIdCargo(cargo);
        clientexCobrar.setIdNotaTransaccion(idTransaccion);
        clientexCobrar.setTipo(AsientoContable.Tipo.CARGO);

        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        total = total.add(cargo.getComisionMayorista() == null ? BigDecimal.ZERO : cargo.getComisionMayorista());
        total = total.add(cargo.getComisionAgencia() == null ? BigDecimal.ZERO : cargo.getComisionAgencia());
        total = total.add(cargo.getComisionPromotor() == null ? BigDecimal.ZERO : cargo.getComisionPromotor());

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            clientexCobrar.setMontoDebeExt(total);
            clientexCobrar.setMontoDebeNac(total.multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN)));
            clientexCobrar.setIdPlanCuenta(conf.getOtrosCargosClienteCobrarDebeUsd());
            clientexCobrar.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = total != null ? total.doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;

            clientexCobrar.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            clientexCobrar.setMontoDebeNac(total);
            clientexCobrar.setIdPlanCuenta(conf.getOtrosCargosClienteCobrarDebeBs());
            clientexCobrar.setMoneda(Moneda.NACIONAL);
        }

        return clientexCobrar;
    }

    @Override
    public AsientoContable crearOperadorMayotistaXPagar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException {
        AsientoContable mayoristaXPagar = new AsientoContable();

        mayoristaXPagar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        mayoristaXPagar.setIdLibro(cc);
        mayoristaXPagar.setGestion(cc.getGestion());
        mayoristaXPagar.setFechaMovimiento(DateContable.getCurrentDate());
        mayoristaXPagar.setEstado(ComprobanteContable.EMITIDO);
        mayoristaXPagar.setIdCargo(cargo);
        mayoristaXPagar.setIdNotaTransaccion(idTransaccion);
        mayoristaXPagar.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            mayoristaXPagar.setMontoHaberExt(cargo.getComisionMayorista());
            mayoristaXPagar.setMontoHaberNac(cargo.getComisionMayorista() != null
                    ? cargo.getComisionMayorista().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN))
                    : null);
            mayoristaXPagar.setIdPlanCuenta(cargo.getIdCuentaMayorista().getIdPlanCuentas());
            mayoristaXPagar.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = cargo.getComisionMayorista() != null ? cargo.getComisionMayorista().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            mayoristaXPagar.setMontoHaberNac(cargo.getComisionMayorista());
            mayoristaXPagar.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            mayoristaXPagar.setIdPlanCuenta(cargo.getIdCuentaMayorista().getIdPlanCuentas());
            mayoristaXPagar.setMoneda(Moneda.NACIONAL);
        }

        return mayoristaXPagar;
    }

    @Override
    public AsientoContable crearComisionAgenciaHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException {
        AsientoContable comisionAgenciaHaber = new AsientoContable();

        comisionAgenciaHaber.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        comisionAgenciaHaber.setIdLibro(cc);
        comisionAgenciaHaber.setGestion(cc.getGestion());
        comisionAgenciaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        comisionAgenciaHaber.setEstado(ComprobanteContable.EMITIDO);
        comisionAgenciaHaber.setIdCargo(cargo);
        comisionAgenciaHaber.setIdNotaTransaccion(idTransaccion);
        comisionAgenciaHaber.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            comisionAgenciaHaber.setMontoHaberExt(cargo.getComisionAgencia());
            comisionAgenciaHaber.setMontoHaberNac(cargo.getComisionAgencia() != null
                    ? cargo.getComisionAgencia().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN))
                    : null);
            comisionAgenciaHaber.setIdPlanCuenta(cargo.getIdCuentaAgencia().getIdPlanCuentas());
            comisionAgenciaHaber.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = cargo.getComisionAgencia() != null ? cargo.getComisionAgencia().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            comisionAgenciaHaber.setMontoHaberNac(cargo.getComisionAgencia());
            comisionAgenciaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            comisionAgenciaHaber.setIdPlanCuenta(cargo.getIdCuentaAgencia().getIdPlanCuentas());
            comisionAgenciaHaber.setMoneda(Moneda.NACIONAL);
        }

        return comisionAgenciaHaber;
    }

    @Override
    public AsientoContable crearComisionCounterHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException {
        AsientoContable comisionCounterHaber = new AsientoContable();

        comisionCounterHaber.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        comisionCounterHaber.setIdLibro(cc);
        comisionCounterHaber.setGestion(cc.getGestion());
        comisionCounterHaber.setFechaMovimiento(DateContable.getCurrentDate());
        comisionCounterHaber.setEstado(ComprobanteContable.EMITIDO);
        comisionCounterHaber.setIdCargo(cargo);
        comisionCounterHaber.setIdNotaTransaccion(idTransaccion);
        comisionCounterHaber.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            comisionCounterHaber.setMontoHaberExt(cargo.getComisionPromotor());
            comisionCounterHaber.setMontoHaberNac(cargo.getComisionPromotor() != null
                    ? cargo.getComisionPromotor().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN))
                    : null);
            comisionCounterHaber.setIdPlanCuenta(cargo.getIdCuentaPromotor().getIdPlanCuentas());
            comisionCounterHaber.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = cargo.getComisionPromotor() != null ? cargo.getComisionPromotor().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            comisionCounterHaber.setMontoHaberNac(cargo.getComisionPromotor());
            comisionCounterHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            comisionCounterHaber.setIdPlanCuenta(cargo.getIdCuentaPromotor().getIdPlanCuentas());
            comisionCounterHaber.setMoneda(Moneda.NACIONAL);
        }

        return comisionCounterHaber;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param ac
     * @param nota
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoPagarLineaAerea(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final AerolineaCuenta ac, NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException {
        Optional op;
        try {

            AsientoContable montoPagar = new AsientoContable();
            montoPagar.setGestion(cc.getGestion());
            montoPagar.setIdLibro(cc);
            montoPagar.setFechaMovimiento(DateContable.getCurrentDate());
            montoPagar.setEstado(ComprobanteContable.EMITIDO);
            montoPagar.setIdBoleto(b);
            //montoPagar.setIdBoleto(b.getIdBoleto());
            montoPagar.setIdNotaTransaccion(idTransaccion);
            montoPagar.setTipo(AsientoContable.Tipo.BOLETO);

            if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                op = Optional.ofNullable(ac);
                if (!op.isPresent()) {
                    throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonExt para la linea Aerea");
                }
                montoPagar.setIdPlanCuenta(ac.getIdPlanCuentas());
                montoPagar.setMoneda(Moneda.EXTRANJERA);

                Double factorCambiario = nota.getFactorCambiario().doubleValue();
                Double montoLineaAerea = b.getMontoPagarLineaAerea() != null ? b.getMontoPagarLineaAerea().doubleValue() : 0d;
                Double montoHaberNac = montoLineaAerea * factorCambiario;

                montoPagar.setMontoHaberExt(b.getMontoPagarLineaAerea());
                montoPagar.setMontoHaberNac(new BigDecimal(montoHaberNac));

            } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {

                op = Optional.ofNullable(ac);
                if (!op.isPresent()) {
                    throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonNac para la Aerolinea");
                }
                montoPagar.setIdPlanCuenta(ac.getIdPlanCuentas());
                montoPagar.setMoneda(Moneda.NACIONAL);

                Double factorCambiario = nota.getFactorCambiario().doubleValue();
                Double montoLineaAerea = b.getMontoPagarLineaAerea() != null ? b.getMontoPagarLineaAerea().doubleValue() : 0d;
                Double montoHaberNac = montoLineaAerea / factorCambiario;

                montoPagar.setMontoHaberExt(new BigDecimal(montoHaberNac));
                montoPagar.setMontoHaberNac(b.getMontoPagarLineaAerea());

            }
            return montoPagar;
        } catch (CRUDException ex) {
            throw new CRUDException("Exisitio un error al momento de crear el asiento crearMontoPagarLineaAerea");
        }
    }

    /**
     *
     * @param b
     * @param cc
     * @param a
     * @param ac
     * @param nota
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoComision(final Boleto b, final ComprobanteContable cc,
            final Aerolinea a, final AerolineaCuenta ac, NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException {
        Optional op;
        AsientoContable montoComision = new AsientoContable();

        montoComision.setGestion(cc.getGestion());
        montoComision.setIdLibro(cc);
        montoComision.setFechaMovimiento(DateContable.getCurrentDate());
        montoComision.setEstado(ComprobanteContable.EMITIDO);
        //montoComision.setIdBoleto(b.getIdBoleto());
        montoComision.setIdBoleto(b);
        //montoComision.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
        montoComision.setIdNotaTransaccion(idTransaccion);
        montoComision.setTipo(AsientoContable.Tipo.BOLETO);

        System.out.println("Monto Comision: " + b.getMontoComision());

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            op = Optional.ofNullable(ac);
            if (!op.isPresent()) {
                throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonExt para la Aerolinea");
            }
            montoComision.setIdPlanCuenta(ac.getIdPlanCuentas());
            montoComision.setMoneda(Moneda.EXTRANJERA);

            op = Optional.ofNullable(b.getMontoComision());
            if (op.isPresent()) {
                montoComision.setMontoHaberExt(b.getMontoComision());
                montoComision.setMontoHaberNac(b.getMontoComision().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)));
            } else {
                return null;
            }

        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            op = Optional.ofNullable(ac);
            if (!op.isPresent()) {
                throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonNac para la Aerolinea");
            }
            montoComision.setIdPlanCuenta(ac.getIdPlanCuentas());
            montoComision.setMoneda(Moneda.NACIONAL);
            op = Optional.ofNullable(b.getMontoComision());
            if (op.isPresent()) {
                Double montoHaber = b.getMontoComision() != null ? b.getMontoComision().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
                montoComision.setMontoHaberNac(b.getMontoComision());
                montoComision.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            } else {
                return null;
            }
        }
        return montoComision;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param a
     * @param nota
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoFee(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, final NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException {
        AsientoContable montoFee = new AsientoContable();

        montoFee.setGestion(cc.getGestion());
        montoFee.setIdLibro(cc);
        montoFee.setFechaMovimiento(DateContable.getCurrentDate());
        montoFee.setEstado(ComprobanteContable.EMITIDO);
        //montoFee.setIdBoleto(b.getIdBoleto());
        montoFee.setIdBoleto(b);
        //montoFee.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
        montoFee.setIdNotaTransaccion(idTransaccion);
        montoFee.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                montoFee.setMontoHaberExt(b.getMontoFee());
                montoFee.setMontoHaberNac(b.getMontoFee().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)));
            } else {
                return null;
            }
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                Double montoHaber = b.getMontoFee() != null ? b.getMontoFee().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
                montoFee.setMontoHaberNac(b.getMontoFee());
                montoFee.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            } else {
                return null;
            }
        }
        return montoFee;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param a
     * @param nota
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoDescuentos(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException {
        AsientoContable montoDescuentos = new AsientoContable();

        montoDescuentos.setGestion(cc.getGestion());
        montoDescuentos.setIdLibro(cc);
        montoDescuentos.setFechaMovimiento(DateContable.getCurrentDate());
        montoDescuentos.setEstado(ComprobanteContable.EMITIDO);
        //montoDescuentos.setIdBoleto(b.getIdBoleto());
        montoDescuentos.setIdBoleto(b);
        //montoDescuentos.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
        montoDescuentos.setIdNotaTransaccion(idTransaccion);
        montoDescuentos.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(b.getMontoDescuento().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)));
            } else {
                return null;
            }

        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                Double montoHaber = b.getMontoDescuento() != null ? b.getMontoDescuento().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            } else {
                return null;
            }
        }
        return montoDescuentos;
    }

    @Override
    public AsientoContable createTotalDebe(ComprobanteContable c, ContabilidadBoletaje conf, Devolucion dev) throws CRUDException {
        AsientoContable ingDebe = new AsientoContable();

        ingDebe.setIdLibro(c);
        ingDebe.setGestion(c.getGestion());
        ingDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingDebe.setEstado(ComprobanteContable.EMITIDO);
        ingDebe.setMoneda(dev.getMoneda());
        ingDebe.setIdDevolucion(dev);
        ingDebe.setTipo(AsientoContable.Tipo.DEVOLUCION);

        if (dev.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingDebe.setMontoDebeNac(dev.getMonto() != null
                    ? dev.getMonto().multiply(dev.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingDebe.setMontoDebeExt(dev.getMonto());

            if (dev.getTipoDevolucion().equals(FormasPago.CHEQUE)) {
                ingDebe.setIdPlanCuenta(dev.getIdCuentaDeposito());
            } else {
                ingDebe.setIdPlanCuenta(conf.getDevolucionDepositoAnticipadoDebeUsd());
            }

        } else if (dev.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = dev.getMonto() != null ? dev.getMonto().doubleValue() / dev.getFactorCambiario().doubleValue() : 0.0f;
            ingDebe.setMontoDebeNac(dev.getMonto());
            ingDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            if (dev.getTipoDevolucion().equals(FormasPago.CHEQUE)) {
                ingDebe.setIdPlanCuenta(dev.getIdCuentaDeposito());
            } else {
                ingDebe.setIdPlanCuenta(conf.getDevolucionDepositoAnticipadoDebeBs());
            }

        }

        return ingDebe;
    }

    @Override
    public AsientoContable createTotalHaber(ComprobanteContable c, ContabilidadBoletaje conf, Devolucion dev) throws CRUDException {
        AsientoContable ingDebe = new AsientoContable();

        ingDebe.setIdLibro(c);
        ingDebe.setGestion(c.getGestion());
        ingDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingDebe.setEstado(ComprobanteContable.EMITIDO);
        ingDebe.setMoneda(dev.getMoneda());
        ingDebe.setIdDevolucion(dev);
        ingDebe.setTipo(AsientoContable.Tipo.DEVOLUCION);

        if (dev.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingDebe.setMontoHaberNac(dev.getMonto() != null
                    ? dev.getMonto().multiply(dev.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingDebe.setMontoHaberExt(dev.getMonto());

            if (dev.getTipoDevolucion().equals(FormasPago.CHEQUE)) {
                ingDebe.setIdPlanCuenta(dev.getIdCuentaDeposito());
            } else {
                ingDebe.setIdPlanCuenta(conf.getDevolucionDepositoAnticipadoHaberUsd());
            }

        } else if (dev.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = dev.getMonto() != null ? dev.getMonto().doubleValue() / dev.getFactorCambiario().doubleValue() : 0.0f;
            ingDebe.setMontoHaberNac(dev.getMonto());
            ingDebe.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            if (dev.getTipoDevolucion().equals(FormasPago.CHEQUE)) {
                ingDebe.setIdPlanCuenta(dev.getIdCuentaDeposito());
            } else {
                ingDebe.setIdPlanCuenta(conf.getDevolucionDepositoAnticipadoHaberBs());
            }

        }

        return ingDebe;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            final ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt, final NotaDebito nota,
            final Boleto boleto) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        //ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setIdBoleto(boleto);
        ingCajaDebe.setIdNotaTransaccion(ndt);
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ndt.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(nota.getIdCuentaDeposito());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeUsd());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeUsd());
                }
            }

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMontoBs() != null ? ndt.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(ndt.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(nota.getIdCuentaDeposito());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeBs());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeBs());
                }
            }
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final IngresoCaja caja,
            final Boleto boleto, final IngresoTransaccion ing) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        //ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setIdBoleto(boleto);
        ingCajaDebe.setTipo(AsientoContable.Tipo.INGRESO_CAJA_TRANSACCION);
        ingCajaDebe.setIdIngresoCajaTransaccion(ing);
        ingCajaDebe.setMoneda(ing.getMoneda());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ing.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(caja.getIdCuentaDeposito());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeUsd());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeUsd());
                }
            }

        } else if (ing.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ing.getMontoBs() != null ? ing.getMontoBs().doubleValue() / caja.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(ing.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(caja.getIdCuentaDeposito());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeBs());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeBs());
                }
            }
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt, final IngresoCaja caja,
            final CargoBoleto cargo, final IngresoTransaccion ing) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargo);
        ingCajaDebe.setTipo(AsientoContable.Tipo.INGRESO_CAJA_TRANSACCION);
        ingCajaDebe.setIdIngresoCajaTransaccion(ing);
        ingCajaDebe.setMoneda(ing.getMoneda());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ing.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(caja.getIdCuentaDeposito());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                /*if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeUsd());
                } else {*/
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeUsd());
                //}
            }

        } else if (ing.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ing.getMontoBs() != null ? ing.getMontoBs().doubleValue() / caja.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(ing.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(caja.getIdCuentaDeposito());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                /*if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeBs());
                } else {*/
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeBs());
                //}
            }
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final NotaDebito nota, final Boleto boleto) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        //ingCajaHaber.setIdBoleto(boleto.getIdBoleto());
        ingCajaHaber.setIdBoleto(boleto);
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ndt.getMoneda());
        ingCajaHaber.setIdNotaTransaccion(ndt);

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(ndt.getMontoUsd());
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO) || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberUsd());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberUsd());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberUsd());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberUsd());
                }
            }

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMontoBs() != null ? ndt.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ndt.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberBs());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                //ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberBs());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberBs());
                }
            }
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaCreditoTransaccion ndt,
            final NotaCredito nota, Boleto b) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(Estado.EMITIDO);
        //ingCajaHaber.setIdBoleto(b.getIdBoleto());
        ingCajaHaber.setIdBoleto(b);
        ingCajaHaber.setTipo(AsientoContable.Tipo.NOTA_CREDITO_TRANSACCION);
        //ingCajaHaber.setIdNotaCreditoTransaccion(ndt.getIdNotaCreditoTransaccion());
        ingCajaHaber.setIdNotaCreditoTransaccion(ndt);
        ingCajaHaber.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(ndt.getMontoUsd());
            ingCajaHaber.setIdPlanCuenta(conf.getNotaCreditoHaberBs());

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMontoBs() != null ? ndt.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ndt.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            ingCajaHaber.setIdPlanCuenta(conf.getNotaCreditoHaberUsd());
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipadoTransaccion ndt, PagoAnticipado nota, Boleto b) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(Estado.EMITIDO);
        //ingCajaHaber.setIdBoleto(b.getIdBoleto());
        ingCajaHaber.setIdBoleto(b);
        ingCajaHaber.setTipo(AsientoContable.Tipo.PAGO_ANTICIPADO);
        ingCajaHaber.setIdPagoAnticipadoTransaccion(ndt);
        ingCajaHaber.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ndt.getMonto() != null
                    ? ndt.getMonto().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(ndt.getMonto());
            ingCajaHaber.setIdPlanCuenta(conf.getDepositoClienteAnticipadoUsd());

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMonto() != null ? ndt.getMonto().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ndt.getMonto());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            ingCajaHaber.setIdPlanCuenta(conf.getDepositoClienteAnticipadoBs());
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, PagoAnticipado pago,
            Boleto boleto, PagoAnticipadoTransaccion trx) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(Estado.EMITIDO);
        //ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setIdBoleto(boleto);
        ingCajaDebe.setTipo(AsientoContable.Tipo.PAGO_ANTICIPADO_TRANSACCION);
        ingCajaDebe.setIdPagoAnticipadoTransaccion(trx);
        ingCajaDebe.setMoneda(trx.getMoneda());

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(trx.getMonto());
            ingCajaDebe.setMontoDebeNac(trx.getMonto() != null
                    ? trx.getMonto().multiply(pago.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getDepositoClienteAnticipadoUsd());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMonto() != null ? trx.getMonto().doubleValue() / pago.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(trx.getMonto());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getDepositoClienteAnticipadoBs());
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt,
            PagoAnticipado nota, CargoBoleto cargo, PagoAnticipadoTransaccion trx) throws CRUDException {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo);
        ingCajaHaber.setTipo(AsientoContable.Tipo.PAGO_ANTICIPADO_TRANSACCION);
        ingCajaHaber.setMoneda(trx.getMoneda());
        ingCajaHaber.setIdPagoAnticipadoTransaccion(trx);

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(trx.getMonto() != null
                    ? trx.getMonto().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(trx.getMonto());
            //Si es BSP
            ingCajaHaber.setIdPlanCuenta(conf.getAcreditacionDepositoAnticipadoHaberUsd());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMonto() != null ? trx.getMonto().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(trx.getMonto());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            /*ingCajaHaber.setMontoHaberExt(trx.getMonto() != null
                    ? trx.getMonto().divide(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);*/
            //Si es BSP
            ingCajaHaber.setIdPlanCuenta(conf.getAcreditacionDepositoAnticipadoHaberBs());
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final NotaCredito nota, final CargoBoleto cargo,
            final NotaCreditoTransaccion trx) throws CRUDException {

        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo);
        ingCajaHaber.setTipo(AsientoContable.Tipo.NOTA_CREDITO_TRANSACCION);
        ingCajaHaber.setMoneda(trx.getMoneda());
        ingCajaHaber.setIdNotaCreditoTransaccion(trx);

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(trx.getMontoUsd() != null
                    ? trx.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(trx.getMontoUsd());
            //Si es BSP
            ingCajaHaber.setIdPlanCuenta(conf.getNotaCreditoHaberUsd());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMontoBs() != null ? trx.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(trx.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            ingCajaHaber.setIdPlanCuenta(conf.getNotaCreditoHaberUsd());
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final NotaCredito nota, final CargoBoleto cargo,
            final NotaCreditoTransaccion trx) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargo);
        ingCajaDebe.setTipo(AsientoContable.Tipo.NOTA_CREDITO_TRANSACCION);
        ingCajaDebe.setIdNotaCreditoTransaccion(trx);
        ingCajaDebe.setMoneda(trx.getMoneda());

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(trx.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(trx.getMontoUsd() != null
                    ? trx.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getNotaCreditoHaberUsd());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMontoBs() != null ? trx.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(trx.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getNotaCreditoHaberBs());
        }

        return ingCajaDebe;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt,
            PagoAnticipado nota, CargoBoleto cargo, PagoAnticipadoTransaccion trx) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargo);
        ingCajaDebe.setTipo(AsientoContable.Tipo.PAGO_ANTICIPADO_TRANSACCION);
        ingCajaDebe.setIdPagoAnticipadoTransaccion(trx);
        ingCajaDebe.setMoneda(trx.getMoneda());

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(trx.getMonto());
            ingCajaDebe.setMontoDebeNac(trx.getMonto() != null
                    ? trx.getMonto().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getAcreditacionDepositoAnticipadoDebeUsd());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMonto() != null ? trx.getMonto().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(trx.getMonto());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(conf.getAcreditacionDepositoAnticipadoDebeBs());
        }

        return ingCajaDebe;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final NotaCredito nota, final Boleto boleto,
            final NotaCreditoTransaccion trx) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(Estado.EMITIDO);
        //ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setIdBoleto(boleto);
        ingCajaDebe.setTipo(ndt.getTipo());
        //ingCajaDebe.setIdNotaCreditoTransaccion(trx.getIdNotaCreditoTransaccion());
        ingCajaDebe.setIdNotaCreditoTransaccion(trx);
        ingCajaDebe.setMoneda(trx.getMoneda());

        if (trx.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(trx.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(trx.getMontoUsd() != null
                    ? trx.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(trx.getIdPlanCuenta().getIdPlanCuentas());

        } else if (trx.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = trx.getMontoBs() != null ? trx.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(trx.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            ingCajaDebe.setIdPlanCuenta(trx.getIdPlanCuenta().getIdPlanCuentas());
        }

        return ingCajaDebe;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt, final IngresoCaja caja,
            final Boleto boleto, final IngresoTransaccion ing) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        //ingCajaHaber.setIdBoleto(boleto.getIdBoleto());
        ingCajaHaber.setIdBoleto(boleto);
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ing.getMoneda());
        // ingCajaHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
        ingCajaHaber.setIdIngresoCajaTransaccion(ing);

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(ing.getMontoUsd());
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO) || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberUsd());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberUsd());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberUsd());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberUsd());
                }
            }

        } else if (ing.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ing.getMontoBs() != null ? ing.getMontoBs().doubleValue() / caja.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ing.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberBs());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                //ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberBs());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberBs());
                }
            }
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, final NotaDebitoTransaccion ndt,
            final IngresoCaja caja,
            final CargoBoleto cargo, final IngresoTransaccion ing) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo);
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ing.getMoneda());
        //ingCajaHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
        ingCajaHaber.setIdIngresoCajaTransaccion(ing);

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            ingCajaHaber.setMontoHaberExt(ing.getMontoUsd());
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO) || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberUsd());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberUsd());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                /*if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberUsd());
                } else {*/
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberUsd());
                //}
            }

        } else if (ing.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ing.getMontoBs() != null ? ing.getMontoBs().doubleValue() / caja.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ing.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (caja.getFormaPago().equals(FormasPago.EFECTIVO)
                    || caja.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberBs());
            } else if (caja.getFormaPago().equals(FormasPago.DEPOSITO)) {
                //ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
            } else if (caja.getFormaPago().equals(FormasPago.TARJETA)) {
                /*if (boleto.getIdAerolinea().getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberBs());
                } else {*/
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberBs());
                //}
            }
        }

        return ingCajaHaber;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota,
            CargoBoleto cargoBoleto) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargoBoleto);
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setMoneda(ndt.getMoneda());
        //ingCajaDebe.setIdNotaTransaccion(ndt.getIdNotaDebitoTransaccion());
        ingCajaDebe.setIdNotaTransaccion(ndt);

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ndt.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(nota.getIdCuentaDeposito());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeUsd());
            }

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMontoBs() != null ? ndt.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(ndt.getMontoBs());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(nota.getIdCuentaDeposito());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeBs());
            }
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createAsientoPagoAnticipadoDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipado pago) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c);
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdPagoAnticipado(pago);
        ingCajaDebe.setMoneda(pago.getMoneda());

        if (pago.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(pago.getMontoAnticipado());
            ingCajaDebe.setMontoDebeNac(pago.getMontoAnticipado() != null
                    ? pago.getMontoAnticipado().multiply(pago.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            if (pago.getFormaPago().equals(FormasPago.EFECTIVO)
                    || pago.getFormaPago().equals(FormasPago.CHEQUE)
                    || pago.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (pago.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(pago.getIdCuentaDeposito());
            }

        } else if (pago.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = pago.getMontoAnticipado() != null ? pago.getMontoAnticipado().doubleValue() / pago.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaDebe.setMontoDebeNac(pago.getMontoAnticipado());
            ingCajaDebe.setMontoDebeExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            if (pago.getFormaPago().equals(FormasPago.EFECTIVO)
                    || pago.getFormaPago().equals(FormasPago.CHEQUE)
                    || pago.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (pago.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(pago.getIdCuentaDeposito());
            }
        }

        return ingCajaDebe;

    }

    @Override
    public AsientoContable createAsientoPagoAnticipadoHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipado pago) throws CRUDException {

        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdPagoAnticipado(pago);
        ingCajaHaber.setMoneda(pago.getMoneda());

        if (pago.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberExt(pago.getMontoAnticipado());
            ingCajaHaber.setMontoHaberNac(pago.getMontoAnticipado() != null
                    ? pago.getMontoAnticipado().multiply(pago.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            /*if (pago.getFormaPago().equals(FormasPago.EFECTIVO)
                    || pago.getFormaPago().equals(FormasPago.CHEQUE)
                    || pago.getFormaPago().equals(FormasPago.TARJETA)) {*/
            ingCajaHaber.setIdPlanCuenta(conf.getDepositoClienteAnticipadoUsd());
            /* } else if (pago.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(pago.getIdCuentaDeposito());
            }*/

        } else if (pago.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = pago.getMontoAnticipado() != null ? pago.getMontoAnticipado().doubleValue() / pago.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(pago.getMontoAnticipado());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            /*if (pago.getFormaPago().equals(FormasPago.EFECTIVO)
                    || pago.getFormaPago().equals(FormasPago.CHEQUE)
                    || pago.getFormaPago().equals(FormasPago.TARJETA)) {*/
            ingCajaHaber.setIdPlanCuenta(conf.getDepositoClienteAnticipadoBs());
            /*} else if (pago.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(pago.getIdCuentaDeposito());
            }*/
        }

        return ingCajaHaber;

    }

    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota,
            CargoBoleto cargo) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c);
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo);
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ndt.getMoneda());
        //ingCajaHaber.setIdNotaTransaccion(ndt.getIdNotaDebitoTransaccion());
        ingCajaHaber.setIdNotaTransaccion(ndt);

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberExt(ndt.getMontoUsd());
            ingCajaHaber.setMontoHaberNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN)
                    : null);
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO) || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberUsd());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberUsd());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberUsd());
            }

        } else if (ndt.getMoneda().equals(Moneda.NACIONAL)) {
            Double montoHaber = ndt.getMontoBs() != null ? ndt.getMontoBs().doubleValue() / nota.getFactorCambiario().doubleValue() : 0.0f;
            ingCajaHaber.setMontoHaberNac(ndt.getMontoBs());
            ingCajaHaber.setMontoHaberExt(new BigDecimal(montoHaber).setScale(Contabilidad.VALOR_DECIMAL_2, BigDecimal.ROUND_DOWN));
            //Si es BSP
            if (nota.getFormaPago().equals(FormasPago.EFECTIVO)
                    || nota.getFormaPago().equals(FormasPago.CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberBs());
            } else if (nota.getFormaPago().equals(FormasPago.DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
            } else if (nota.getFormaPago().equals(FormasPago.TARJETA)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberBs());
            }
        }

        return ingCajaHaber;
    }

    /**
     * Cambia el estado a las transacciones (Asientos Contables) colocandolas en
     * estado Anulado. Estas transacciones no se toman en cuenta para realizar
     * los libros. Realiza la suma nuevamente de los totales
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    @Override
    public int anularAsientosContables(Boleto boleto) throws CRUDException {
        //Anulamos las transacciones contables para el id_boleto y su respectivo Libro
        //Eso para tener en cuenta los tipos de Asientos Contables
        //AD Asiento Diario
        //CI Comprobante Ingreso
        //CE Comprobante Egreso
        //CT Comprobante de Traspso
        //AJ Asiento de Ajuste
        Query q = em.createNamedQuery("AsientoContable.updateEstadoFromBoleto");
        q.setParameter("idBoleto", boleto.getIdBoleto());
        q.setParameter("estado", ComprobanteContable.ANULADO);
        q.executeUpdate();

        // Actualiza los totales del comprobante Contable. y si ya no tiene 
        //transacciones, anula el Comprobante Contable
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("ComprobanteContable.updateComprobanteContable");
        spq.setParameter("in_id_boleto", boleto.getIdBoleto());
        spq.executeUpdate();

        return Operacion.REALIZADA;
    }

    @Override
    public ComprobanteContable actualizarMontosFinalizar(ComprobanteContable comprobante) throws CRUDException {

        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("ComprobanteContable.updateMontosComprobanteContableFromAsientos");
        spq.setParameter("in_id_libro", comprobante.getIdLibro());
        spq.executeUpdate();

        comprobante = em.find(ComprobanteContable.class, comprobante.getIdLibro());

        return comprobante;

    }

    public void actualizarMontosFinalizar(Integer comprobante) throws CRUDException {

        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("ComprobanteContable.updateMontosComprobanteContableFromAsientos");
        spq.setParameter("in_id_libro", comprobante);
        spq.executeUpdate();

    }

    @Override
    public List getComprobantesByNotaDebito(Integer idNota) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByNotaDebito", ComprobanteContable.class);
        q.setParameter("idNotaDebito", idNota);

        List<ComprobanteContable> l = q.getResultList();

        return l;
    }

    @Override
    public List getComprobantesByPagoAnticipado(Integer idPagoAnticipado) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByPagoAnticipado", ComprobanteContable.class);
        q.setParameter("idPagoAnticipado", idPagoAnticipado);

        List<ComprobanteContable> l = q.getResultList();

        return l;
    }

    @Override
    public List getComprobantesByNotaCredito(Integer idNota) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByNotaCredito", ComprobanteContable.class);
        q.setParameter("idNotaCredito", idNota);

        List<ComprobanteContable> l = q.getResultList();

        return l;
    }

    @Override
    public List getComprobantesByIngresoCaja(Integer idIngreso) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByIngresoCaja", IngresoCaja.class);
        q.setParameter("idIngresoCaja", idIngreso);

        List<IngresoCaja> l = q.getResultList();

        return l;
    }

    @Override
    public void anularComprobanteContable(IngresoCaja ing, String usuario) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByIngresoCaja");
        q.setParameter("idIngresoCaja", ing.getIdIngresoCaja());

        List<ComprobanteContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen Comprobantes Contables para la Transaccion");
        }

        l.stream().map((ac) -> {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnulado(usuario);
                em.merge(ac);
            }
            return ac;
        }).forEachOrdered((ac) -> {
            actualizarComprobanteContableMontosAnularTransaccion(ac);
        });//actualizamos los montos de los Comprobantes Contables

    }

    @Override
    public void anularComprobanteContable(NotaCredito nc, String usuario) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByNotaCredito");
        q.setParameter("idNotaCredito", nc.getIdNotaCredito());

        List<ComprobanteContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen Comprobantes Contables para la Transaccion");
        }

        l.stream().map((ac) -> {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnulado(usuario);
                em.merge(ac);
            }
            return ac;
        }).forEachOrdered((ac) -> {
            actualizarComprobanteContableMontosAnularTransaccion(ac);
        });//actualizamos los montos de los Comprobantes Contables

    }

    @Override
    public void anularComprobanteContable(PagoAnticipado pa, String usuario) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByPagoAnticipado");
        q.setParameter("idPagoAnticipado", pa.getIdPagoAnticipado());

        List<ComprobanteContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen Comprobantes Contables para la Transaccion");
        }

        l.stream().map((ac) -> {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnulado(usuario);
                em.merge(ac);
            }
            return ac;
        }).forEachOrdered((ac) -> {
            actualizarComprobanteContableMontosAnularTransaccion(ac);
        });//actualizamos los montos de los Comprobantes Contables

    }

    @Override
    public void anularAsientosContables(NotaDebitoTransaccion tr, String usuario) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByIdNotaDebitoTransaccion");
        q.setParameter("idNotaTransaccion", tr);

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnular(usuario);
                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdNotaDebito().getIdNotaDebito());
    }

    @Override
    public void anularAsientosContables(IngresoTransaccion tr, String usuario) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByIdIngresoTransaccion");
        q.setParameter("idIngresoCajaTransaccion", tr);

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnular(usuario);
                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdIngresoCaja());
    }

    @Override
    public void anularAsientosContables(NotaCreditoTransaccion tr, String usuario) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByNotaCreditoTransaccion");
        q.setParameter("idNotaCreditoTransaccion", tr);

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnular(usuario);
                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdNotaCredito());
    }

    @Override
    public void anularAsientosContables(PagoAnticipadoTransaccion tr, String usuario) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByPagoAnticipadoTransaccion");
        q.setParameter("idPagoAnticipadoTransaccion", tr);

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                ac.setIdUsuarioAnular(usuario);
                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdPagoAnticipado());
    }

    private void actualizarMontosAnularAsientosContables(Integer idNotaDebito) throws CRUDException {
        List<ComprobanteContable> lcc = getComprobantesByNotaDebito(idNotaDebito);

        for (ComprobanteContable cc : lcc) {
            actualizarComprobanteContableMontosAnularTransaccion(cc);
        }
    }

    private void actualizarMontosAnularAsientosContables(IngresoCaja idIngresoCaja) throws CRUDException {
        List<ComprobanteContable> lcc = getComprobantesByNotaDebito(idIngresoCaja.getIdIngresoCaja());

        for (ComprobanteContable cc : lcc) {
            actualizarComprobanteContableMontosAnularTransaccion(cc);
        }
    }

    private void actualizarMontosAnularAsientosContables(NotaCredito idNotaCredito) throws CRUDException {
        List<ComprobanteContable> lcc = getComprobantesByNotaDebito(idNotaCredito.getIdNotaCredito());

        for (ComprobanteContable cc : lcc) {
            actualizarComprobanteContableMontosAnularTransaccion(cc);
        }
    }

    private void actualizarMontosAnularAsientosContables(PagoAnticipado idPagoAnticipado) throws CRUDException {
        List<ComprobanteContable> lcc = getComprobantesByNotaDebito(idPagoAnticipado.getIdPagoAnticipado());

        for (ComprobanteContable cc : lcc) {
            actualizarComprobanteContableMontosAnularTransaccion(cc);
        }
    }

    private void actualizarComprobanteContableMontosAnularTransaccion(ComprobanteContable cc) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("ComprobanteContable.updateComprobanteContableAnularTransaccion");
        spq.setParameter("in_id_libro", cc.getIdLibro());
        spq.executeUpdate();
    }

}
