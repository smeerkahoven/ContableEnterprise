/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface ComprobanteRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    public Integer getNextComprobantePK(Date fecha, String tipo) throws CRUDException;

    @Override
    public void beginTransaction() throws CRUDException;

    @Override
    public void endTransaction() throws CRUDException;

    @Override
    public void rollback() throws CRUDException;

    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF, Integer idEmpresa) throws CRUDException;

    public void pendiente(Integer idLibro, String usuario) throws CRUDException;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    public AsientoContable addTransaccion(AsientoContable asiento) throws CRUDException;

    public void procesarBoleto(Boleto boleto) throws CRUDException;

    /**
     * Se crea un comprobante Contable a partir de una nota de
     * DebitocreateComprobante
     *
     * @param a
     * @param boleto
     * @param cliente
     * @param tipo
     * @param nota
     * @return
     * @throws CRUDException
     */
    public ComprobanteContable createComprobante(Aerolinea a, Boleto boleto, Cliente cliente,
            String tipo, NotaDebito nota) throws CRUDException;

    public ComprobanteContable createComprobante(String tipo, String concepto,
            IngresoCaja ingreso) throws CRUDException;

    public ComprobanteContable createComprobante(String tipo, String concepto,
            NotaCredito ingreso) throws CRUDException;

    public ComprobanteContable createComprobante(String tipo, String concepto,
            PagoAnticipado pago) throws CRUDException;

    public ComprobanteContable createComprobante(String tipo, String concepto,
            Devolucion d) throws CRUDException;

    public ComprobanteContable createComprobante(Aerolinea a,
            Boleto boleto, Cliente cliente, String tipo) throws CRUDException;

    public ComprobanteContable createComprobante(String tipo, String concepto, NotaDebito nota) throws CRUDException;

    /**
     * Crea el Asiento Diario para el Boleto
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public ComprobanteContable createAsientoDiarioBoleto(Boleto boleto) throws CRUDException;

    public ComprobanteContable createAsientoDiarioBoleto(NotaDebito nota, String concepto) throws CRUDException;

    /**
     *
     * @param c
     * @param conf
     * @param pago
     * @return
     * @throws CRUDException
     */
    public AsientoContable createAsientoPagoAnticipadoDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipado pago) throws CRUDException;

    public AsientoContable createAsientoPagoAnticipadoHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipado pago) throws CRUDException;

    /**
     * Realiza la operaciones de proceso del comprobante contable y lo registra
     * en la tabla cnt_comprobante_contable
     *
     * @param comprobante
     * @return
     * @throws CRUDException
     */
    public ComprobanteContable procesarComprobante(ComprobanteContable comprobante) throws CRUDException;

    /**
     * *
     * Realiza las operaciones para el asiento diario del boleto y lo registra
     * en la tabla cnt_asiento_contable
     *
     * @param a
     * @param c
     * @return
     * @throws CRUDException
     */
    public AsientoContable procesarAsientoContable(AsientoContable a, ComprobanteContable c) throws CRUDException;

    public AsientoContable crearTotalCancelar(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearMontoPagarLineaAerea(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final AerolineaCuenta ac, NotaDebito nota, 
            final NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearMontoComision(final Boleto b, final ComprobanteContable cc,
            final Aerolinea a, final AerolineaCuenta ac, final NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearMontoFee(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, final NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearMontoDescuentos(final Boleto b, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, final Aerolinea a, NotaDebito nota,
            final NotaDebitoTransaccion idTransaccion) throws CRUDException;

    /**
     *
     * @param cargo
     * @param nota
     * @param cc
     * @param conf
     * @param idTransaccion
     * @return
     * @throws CRUDException
     */
    public AsientoContable crearClienteXCobrar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            final ContabilidadBoletaje conf, NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearOperadorMayotistaXPagar(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearComisionAgenciaHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public AsientoContable crearComisionCounterHaber(final CargoBoleto cargo, final NotaDebito nota, final ComprobanteContable cc,
            NotaDebitoTransaccion idTransaccion) throws CRUDException;

    public ComprobanteContable actualizarMontosFinalizar(ComprobanteContable comprobante) throws CRUDException;

    /**
     * Crea la transaccion para el comprobante de Ingreso a Caja al Haber
     *
     * @param a
     * @param c
     * @param conf
     * @param aerolinea
     * @param boleto
     * @return
     * @throws CRUDException
     */
    /*   public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException;
     */
    /**
     *
     * Crea la transaccion para el comprobante de Ingreso a Caja al Debe
     *
     * @param a
     * @param c
     * @param conf
     * @param aerolinea
     * @param boleto
     * @return
     * @throws CRUDException
     */
    /*public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException;*/
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota, Boleto boleto) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota, Boleto boleto) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota, CargoBoleto cargoBoleto) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaDebito nota, CargoBoleto cargo) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            Boleto boleto, IngresoTransaccion ing) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            Boleto boleto, IngresoTransaccion ing) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            CargoBoleto cargo, IngresoTransaccion ing) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaCredito nota,
            CargoBoleto cargo, NotaCreditoTransaccion tran) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, PagoAnticipado nota,
            CargoBoleto cargo, PagoAnticipadoTransaccion tran) throws CRUDException;

    public AsientoContable createTotalDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, Devolucion dev) throws CRUDException;

    public AsientoContable createTotalHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, Devolucion dev) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaCredito nota,
            Boleto boleto, NotaCreditoTransaccion trx) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, PagoAnticipado pago,
            Boleto boleto, PagoAnticipadoTransaccion trx) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, IngresoCaja caja,
            CargoBoleto cargo, IngresoTransaccion ing) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaCreditoTransaccion ndt, NotaCredito nota,
            Boleto b);

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, PagoAnticipadoTransaccion ndt, PagoAnticipado nota,
            Boleto b);

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, NotaCredito nota,
            CargoBoleto cargo, NotaCreditoTransaccion ing) throws CRUDException;

    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, NotaDebitoTransaccion ndt, PagoAnticipado nota,
            CargoBoleto cargo, PagoAnticipadoTransaccion ing) throws CRUDException;

    public int anularAsientosContables(final Boleto boleto) throws CRUDException;

    /**
     * Anula los comprobantes contables y sus asientos
     *
     * @param ing
     * @param usuario
     * @throws com.seguridad.control.exception.CRUDException
     */
    public void anularComprobanteContable(final IngresoCaja ing, String usuario) throws CRUDException;

    public void anularComprobanteContable(final NotaCredito nc, String usuario) throws CRUDException;

    public void anularComprobanteContable(final PagoAnticipado pa, String usuario) throws CRUDException;

    /**
     * Retorna una Lista de Comprobantes de acuerdo al Id Nota de Debito Retorna
     * una Lista de Comprobantes de acuerdo al Id Nota de Ingreso Retorna una
     * Lista de Comprobantes de acuerdo al Id Nota de Credito
     *
     * @param idNota
     * @return
     * @throws CRUDException
     */
    public List getComprobantesByNotaDebito(Integer idNota) throws CRUDException;

    public List getComprobantesByIngresoCaja(Integer idIngreso) throws CRUDException;

    public List getComprobantesByNotaCredito(Integer idNota) throws CRUDException;

    public List getComprobantesByPagoAnticipado(Integer idPagoAnticipado) throws CRUDException;

    /**
     *
     * @param tr
     * @param usuario
     * @throws CRUDException
     */
    public void anularAsientosContables(NotaDebitoTransaccion tr, String usuario) throws CRUDException;

    public void anularAsientosContables(IngresoTransaccion tr, String usuario) throws CRUDException;

    public void anularAsientosContables(NotaCreditoTransaccion tr, String usuario) throws CRUDException;

    public void anularAsientosContables(PagoAnticipadoTransaccion tr, String usuario) throws CRUDException;

}
