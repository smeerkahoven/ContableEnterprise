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
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.ComprobanteContablePK;
import com.contabilidad.entities.NotaDebito;
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

    public ComprobanteContablePK getNextComprobantePK(Date fecha, String tipo) throws CRUDException;

    @Override
    public void beginTransaction() throws CRUDException;

    @Override
    public void endTransaction() throws CRUDException;

    @Override
    public void rollback() throws CRUDException;

    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF, Integer idEmpresa) throws CRUDException;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    public void procesarBoleto(Boleto boleto) throws CRUDException;

    /**
     * Se crea un comprobante Contable a partir de una nota de DebitocreateComprobante
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

    public ComprobanteContable createComprobante(Aerolinea a,
            Boleto boleto, Cliente cliente, String tipo) throws CRUDException;

    /**
     * Crea el Asiento Diario para el Boleto
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public ComprobanteContable createAsientoDiarioBoleto(Boleto boleto) throws CRUDException;

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

    public AsientoContable crearTotalCancelar(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, Aerolinea a) throws CRUDException;

    public AsientoContable crearMontoPagarLineaAerea(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, AerolineaCuenta ac) throws CRUDException;

    public AsientoContable crearMontoComision(Boleto b, ComprobanteContable cc,
            Aerolinea a, AerolineaCuenta ac)
            throws CRUDException;

    public AsientoContable crearMontoFee(Boleto b, ComprobanteContable cc, ContabilidadBoletaje conf, Aerolinea a)
            throws CRUDException;

    public AsientoContable crearMontoDescuentos(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, Aerolinea a)
            throws CRUDException;

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
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException;

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
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c,
            ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException;
}
