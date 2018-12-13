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
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Contabilidad;
import com.seguridad.utils.DateContable;
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
    public ComprobanteContable createComprobante(String tipo, String concepto, NotaDebito nota) throws CRUDException {
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
    public ComprobanteContable createComprobante(String tipo, String concepto, IngresoCaja ingreso) throws CRUDException {
        ComprobanteContable comprobante = new ComprobanteContable();
        comprobante.setIdCliente(ingreso.getIdCliente());
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

        //creamos el concepto
        //StringBuilder buff = new StringBuilder(concepto);
        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        /*Aerolinea a = em.find(Aerolinea.class, boleto.getIdAerolinea().getIdAerolinea());
        if (a != null) {
            buff.append(a.getNumero());
        }*/
 /*buff.append(boleto.getIdAerolinea().getNumero());

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
        buff.append(boleto.getIdRuta5() != null ? boleto.getIdRuta5() : "");*/
        //jala el nombre cliente
        /*Cliente c = em.find(Cliente.class, boleto.getIdCliente().getIdCliente());
        op = Optional.ofNullable(c);

        if (!op.isPresent()) {
            throw new CRUDException("No se encontro un Cliente para el boleto");
        }*/
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
        a.setIdLibro(c.getIdLibro());
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
            final ContabilidadBoletaje conf, final Aerolinea a, Integer idTransaccion) {

        AsientoContable totalCancelar = new AsientoContable();

        totalCancelar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        totalCancelar.setIdLibro(cc.getIdLibro());
        totalCancelar.setGestion(cc.getGestion());
        totalCancelar.setFechaMovimiento(DateContable.getCurrentDate());
        totalCancelar.setEstado(ComprobanteContable.EMITIDO);
        totalCancelar.setIdBoleto(b.getIdBoleto());
        totalCancelar.setIdNotaTransaccion(idTransaccion);
        totalCancelar.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            totalCancelar.setMontoDebeExt(b.getTotalMontoCobrar());
            totalCancelar.setMontoDebeNac(b.getTotalMontoCobrar() != null
                    ? b.getTotalMontoCobrar().multiply(b.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN))
                    : null);
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoUs());
            totalCancelar.setMoneda(Moneda.EXTRANJERA);
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            totalCancelar.setMontoDebeExt(b.getTotalMontoCobrar() != null
                    ? b.getTotalMontoCobrar().divide(b.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
            totalCancelar.setMontoDebeNac(b.getTotalMontoCobrar());
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoBs());
            totalCancelar.setMoneda(Moneda.NACIONAL);
        }

        return totalCancelar;
    }

    @Override
    public AsientoContable crearClienteXCobrar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, Integer idTransaccion) {

        AsientoContable clientexCobrar = new AsientoContable();

        clientexCobrar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        clientexCobrar.setIdLibro(cc.getIdLibro());
        clientexCobrar.setGestion(cc.getGestion());
        clientexCobrar.setFechaMovimiento(DateContable.getCurrentDate());
        clientexCobrar.setEstado(ComprobanteContable.EMITIDO);
        clientexCobrar.setIdCargo(cargo.getIdCargo());
        clientexCobrar.setIdNotaTransaccion(idTransaccion);
        clientexCobrar.setTipo(AsientoContable.Tipo.CARGO);

        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        total = total.add(cargo.getComisionMayorista() == null ? BigDecimal.ZERO : cargo.getComisionMayorista());
        total = total.add(cargo.getComisionAgencia() == null ? BigDecimal.ZERO : cargo.getComisionAgencia());
        total = total.add(cargo.getComisionPromotor() == null ? BigDecimal.ZERO : cargo.getComisionPromotor());

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            clientexCobrar.setMontoDebeExt(total);
            clientexCobrar.setMontoDebeNac(total.multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, RoundingMode.HALF_DOWN)));
            clientexCobrar.setIdPlanCuenta(conf.getOtrosCargosClienteCobrarDebeUsd());
            clientexCobrar.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            clientexCobrar.setMontoDebeExt(total.divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN));
            clientexCobrar.setMontoDebeNac(total);
            clientexCobrar.setIdPlanCuenta(conf.getOtroCargosClienteCobrarDebeBs());
            clientexCobrar.setMoneda(Moneda.NACIONAL);
        }

        return clientexCobrar;
    }

    @Override
    public AsientoContable crearOperadorMayotistaXPagar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            Integer idTransaccion) throws CRUDException {
        AsientoContable mayoristaXPagar = new AsientoContable();

        mayoristaXPagar.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        mayoristaXPagar.setIdLibro(cc.getIdLibro());
        mayoristaXPagar.setGestion(cc.getGestion());
        mayoristaXPagar.setFechaMovimiento(DateContable.getCurrentDate());
        mayoristaXPagar.setEstado(ComprobanteContable.EMITIDO);
        mayoristaXPagar.setIdCargo(cargo.getIdCargo());
        mayoristaXPagar.setIdNotaTransaccion(idTransaccion);
        mayoristaXPagar.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            mayoristaXPagar.setMontoHaberExt(cargo.getComisionMayorista());
            mayoristaXPagar.setMontoHaberNac(cargo.getComisionMayorista() != null
                    ? cargo.getComisionMayorista().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN))
                    : null);
            mayoristaXPagar.setIdPlanCuenta(cargo.getIdCuentaMayorista().getIdPlanCuentas());
            mayoristaXPagar.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            mayoristaXPagar.setMontoHaberNac(cargo.getComisionMayorista());
            mayoristaXPagar.setMontoHaberExt(cargo.getComisionMayorista() != null
                    ? cargo.getComisionMayorista().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
            mayoristaXPagar.setIdPlanCuenta(cargo.getIdCuentaMayorista().getIdPlanCuentas());
            mayoristaXPagar.setMoneda(Moneda.NACIONAL);
        }

        return mayoristaXPagar;
    }

    @Override
    public AsientoContable crearComisionAgenciaHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            Integer idTransaccion) throws CRUDException {
        AsientoContable comisionAgenciaHaber = new AsientoContable();

        comisionAgenciaHaber.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        comisionAgenciaHaber.setIdLibro(cc.getIdLibro());
        comisionAgenciaHaber.setGestion(cc.getGestion());
        comisionAgenciaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        comisionAgenciaHaber.setEstado(ComprobanteContable.EMITIDO);
        comisionAgenciaHaber.setIdCargo(cargo.getIdCargo());
        comisionAgenciaHaber.setIdNotaTransaccion(idTransaccion);
        comisionAgenciaHaber.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            comisionAgenciaHaber.setMontoHaberExt(cargo.getComisionAgencia());
            comisionAgenciaHaber.setMontoHaberNac(cargo.getComisionAgencia() != null
                    ? cargo.getComisionAgencia().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN))
                    : null);
            comisionAgenciaHaber.setIdPlanCuenta(cargo.getIdCuentaAgencia().getIdPlanCuentas());
            comisionAgenciaHaber.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            comisionAgenciaHaber.setMontoHaberNac(cargo.getComisionAgencia());
            comisionAgenciaHaber.setMontoHaberExt(cargo.getComisionAgencia() != null
                    ? cargo.getComisionAgencia().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
            comisionAgenciaHaber.setIdPlanCuenta(cargo.getIdCuentaAgencia().getIdPlanCuentas());
            comisionAgenciaHaber.setMoneda(Moneda.NACIONAL);
        }

        return comisionAgenciaHaber;
    }

    @Override
    public AsientoContable crearComisionCounterHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            Integer idTransaccion) throws CRUDException {
        AsientoContable comisionCounterHaber = new AsientoContable();

        comisionCounterHaber.setGestion(cc.getGestion());
        //totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getGestion()));
        comisionCounterHaber.setIdLibro(cc.getIdLibro());
        comisionCounterHaber.setGestion(cc.getGestion());
        comisionCounterHaber.setFechaMovimiento(DateContable.getCurrentDate());
        comisionCounterHaber.setEstado(ComprobanteContable.EMITIDO);
        comisionCounterHaber.setIdCargo(cargo.getIdCargo());
        comisionCounterHaber.setIdNotaTransaccion(idTransaccion);
        comisionCounterHaber.setTipo(AsientoContable.Tipo.CARGO);

        if (cargo.getMoneda().equals(Moneda.EXTRANJERA)) {
            comisionCounterHaber.setMontoHaberExt(cargo.getComisionPromotor());
            comisionCounterHaber.setMontoHaberNac(cargo.getComisionPromotor() != null
                    ? cargo.getComisionPromotor().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN))
                    : null);
            comisionCounterHaber.setIdPlanCuenta(cargo.getIdCuentaPromotor().getIdPlanCuentas());
            comisionCounterHaber.setMoneda(Moneda.EXTRANJERA);
        } else if (cargo.getMoneda().equals(Moneda.NACIONAL)) {
            comisionCounterHaber.setMontoHaberNac(cargo.getComisionPromotor());
            comisionCounterHaber.setMontoHaberExt(cargo.getComisionPromotor() != null
                    ? cargo.getComisionPromotor().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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
     * @param idTransaccion
     * @param a
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoPagarLineaAerea(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final AerolineaCuenta ac, NotaDebito nota) throws CRUDException {
        Optional op;
        try {

            AsientoContable montoPagar = new AsientoContable();
            montoPagar.setGestion(cc.getGestion());
            montoPagar.setIdLibro(cc.getIdLibro());
            montoPagar.setFechaMovimiento(DateContable.getCurrentDate());
            montoPagar.setEstado(ComprobanteContable.EMITIDO);
            montoPagar.setIdBoleto(b.getIdBoleto());
            montoPagar.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
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
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoComision(final Boleto b, final ComprobanteContable cc,
            final Aerolinea a, final AerolineaCuenta ac, NotaDebito nota) throws CRUDException {
        Optional op;
        AsientoContable montoComision = new AsientoContable();

        montoComision.setGestion(cc.getGestion());
        montoComision.setIdLibro(cc.getIdLibro());
        montoComision.setFechaMovimiento(DateContable.getCurrentDate());
        montoComision.setEstado(ComprobanteContable.EMITIDO);
        montoComision.setIdBoleto(b.getIdBoleto());
        montoComision.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
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
                montoComision.setMontoHaberNac(b.getMontoComision().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)));
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
                montoComision.setMontoHaberNac(b.getMontoComision());
                montoComision.setMontoHaberExt(b.getMontoComision()
                        .divide(nota.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN));
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
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoFee(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, NotaDebito nota) throws CRUDException {
        AsientoContable montoFee = new AsientoContable();

        montoFee.setGestion(cc.getGestion());
        montoFee.setIdLibro(cc.getIdLibro());
        montoFee.setFechaMovimiento(DateContable.getCurrentDate());
        montoFee.setEstado(ComprobanteContable.EMITIDO);
        montoFee.setIdBoleto(b.getIdBoleto());
        montoFee.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
        montoFee.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                montoFee.setMontoHaberExt(b.getMontoFee());
                montoFee.setMontoHaberNac(b.getMontoFee().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)));
            } else {
                return null;
            }
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                montoFee.setMontoHaberNac(b.getMontoFee());
                montoFee.setMontoHaberExt(b.getMontoFee()
                        .divide(nota.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN));
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
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoDescuentos(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, NotaDebito nota) throws CRUDException {
        AsientoContable montoDescuentos = new AsientoContable();

        montoDescuentos.setGestion(cc.getGestion());
        montoDescuentos.setIdLibro(cc.getIdLibro());
        montoDescuentos.setFechaMovimiento(DateContable.getCurrentDate());
        montoDescuentos.setEstado(ComprobanteContable.EMITIDO);
        montoDescuentos.setIdBoleto(b.getIdBoleto());
        montoDescuentos.setIdNotaTransaccion(b.getIdNotaDebitoTransaccion());
        montoDescuentos.setTipo(AsientoContable.Tipo.BOLETO);

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(b.getMontoDescuento().multiply(nota.getFactorCambiario().setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)));
            } else {
                return null;
            }

        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(b.getMontoDescuento()
                        .divide(nota.getFactorCambiario(), RoundingMode.HALF_UP).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN));
            } else {
                return null;
            }
        }
        return montoDescuentos;
    }

    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota,
            Boleto boleto) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c.getIdLibro());
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ndt.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaDebe.setMontoDebeNac(ndt.getMontoBs());
            ingCajaDebe.setMontoDebeExt(ndt.getMontoBs() != null
                    ? ndt.getMontoBs().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            Boleto boleto, IngresoTransaccion ing) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c.getIdLibro());
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
        ingCajaDebe.setMoneda(ing.getMoneda());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ing.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaDebe.setMontoDebeNac(ing.getMontoBs());
            ingCajaDebe.setMontoDebeExt(ing.getMontoBs() != null
                    ? ing.getMontoBs().divide(caja.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            CargoBoleto cargo, IngresoTransaccion ing) throws CRUDException {

        AsientoContable ingCajaDebe = new AsientoContable();

        ingCajaDebe.setIdLibro(c.getIdLibro());
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargo.getIdCargo());
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setIdIngresoCajaTransaccion(ing.getIdTransaccion());
        ingCajaDebe.setMoneda(ing.getMoneda());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ing.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaDebe.setMontoDebeNac(ing.getMontoBs());
            ingCajaDebe.setMontoDebeExt(ing.getMontoBs() != null
                    ? ing.getMontoBs().divide(caja.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota,
            Boleto boleto) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c.getIdLibro());
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdBoleto(boleto.getIdBoleto());
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaHaber.setMontoHaberNac(ndt.getMontoBs());
            ingCajaHaber.setMontoHaberExt(ndt.getMontoBs() != null
                    ? ndt.getMontoBs().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            Boleto boleto, IngresoTransaccion ing) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c.getIdLibro());
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdBoleto(boleto.getIdBoleto());
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ing.getMoneda());
        ingCajaHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaHaber.setMontoHaberNac(ing.getMontoBs());
            ingCajaHaber.setMontoHaberExt(ing.getMontoBs() != null
                    ? ing.getMontoBs().divide(caja.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            CargoBoleto cargo, IngresoTransaccion ing) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c.getIdLibro());
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo.getIdCargo());
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ing.getMoneda());
        ingCajaHaber.setIdIngresoCajaTransaccion(ing.getIdTransaccion());

        if (ing.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberNac(ing.getMontoUsd() != null
                    ? ing.getMontoUsd().multiply(caja.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaHaber.setMontoHaberNac(ing.getMontoBs());
            ingCajaHaber.setMontoHaberExt(ing.getMontoBs() != null
                    ? ing.getMontoBs().divide(caja.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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

        ingCajaDebe.setIdLibro(c.getIdLibro());
        ingCajaDebe.setGestion(c.getGestion());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.EMITIDO);
        ingCajaDebe.setIdCargo(cargoBoleto.getIdCargo());
        ingCajaDebe.setTipo(ndt.getTipo());
        ingCajaDebe.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaDebe.setMontoDebeExt(ndt.getMontoUsd());
            ingCajaDebe.setMontoDebeNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaDebe.setMontoDebeNac(ndt.getMontoBs());
            ingCajaDebe.setMontoDebeExt(ndt.getMontoBs() != null
                    ? ndt.getMontoBs().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota,
            CargoBoleto cargo) {
        AsientoContable ingCajaHaber = new AsientoContable();

        ingCajaHaber.setIdLibro(c.getIdLibro());
        ingCajaHaber.setGestion(c.getGestion());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.EMITIDO);
        ingCajaHaber.setIdCargo(cargo.getIdCargo());
        ingCajaHaber.setTipo(ndt.getTipo());
        ingCajaHaber.setMoneda(ndt.getMoneda());

        if (ndt.getMoneda().equals(Moneda.EXTRANJERA)) {
            ingCajaHaber.setMontoHaberExt(ndt.getMontoUsd());
            ingCajaHaber.setMontoHaberNac(ndt.getMontoUsd() != null
                    ? ndt.getMontoUsd().multiply(nota.getFactorCambiario()).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
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
            ingCajaHaber.setMontoHaberNac(ndt.getMontoBs());
            ingCajaHaber.setMontoHaberExt(ndt.getMontoBs() != null
                    ? ndt.getMontoBs().divide(nota.getFactorCambiario(), RoundingMode.HALF_DOWN).setScale(Contabilidad.VALOR_REDONDEO, BigDecimal.ROUND_DOWN)
                    : null);
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

    @Override
    public List getComprobantesByNotaDebito(Integer idNota) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByNotaDebito", ComprobanteContable.class);
        q.setParameter("idNotaDebito", idNota);

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
    public void anularComprobanteContable(IngresoCaja ing) throws CRUDException {
        Query q = em.createNamedQuery("ComprobanteContable.findAllComprobanteByIngresoCaja");
        q.setParameter("idIngresoCaja", ing.getIdIngresoCaja());

        List<ComprobanteContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen Comprobantes Contables para la Transaccion");
        }

        l.stream().map((ac) -> {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);
                em.merge(ac);
            }
            return ac;
        }).forEachOrdered((ac) -> {
            actualizarComprobanteContableMontosAnularTransaccion(ac);
        });//actualizamos los montos de los Comprobantes Contables

    }

    @Override
    public void anularAsientosContables(NotaDebitoTransaccion tr) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByIdNotaDebitoTransaccion");
        q.setParameter("idNotaTransaccion", tr.getIdNotaDebitoTransaccion());

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);

                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdNotaDebito().getIdNotaDebito());
    }

    @Override
    public void anularAsientosContables(IngresoTransaccion tr) throws CRUDException {

        Query q = em.createNamedQuery("AsientoContable.findAllByIdIngresoTransaccion");
        q.setParameter("idIngresoCajaTransaccion", tr.getIdTransaccion());

        List<AsientoContable> l = q.getResultList();

        if (l.isEmpty()) {
            throw new CRUDException("No existen asientos Contables para la Transaccion");
        }

        for (AsientoContable ac : l) {
            if (!ac.getEstado().equals(ComprobanteContable.ANULADO)) {
                ac.setEstado(ComprobanteContable.ANULADO);

                em.merge(ac);
            }
        }

        //actualizamos los montos de los Comprobantes Contables
        actualizarMontosAnularAsientosContables(tr.getIdIngresoCaja());
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

    private void actualizarComprobanteContableMontosAnularTransaccion(ComprobanteContable cc) {
        StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("ComprobanteContable.updateComprobanteContableAnularTransaccion");
        spq.setParameter("in_id_libro", cc.getIdLibro());
        spq.executeUpdate();
    }

}
