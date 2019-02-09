/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Boleto;
import com.agencia.search.dto.BoletoSearchForm;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface NotaDebitoRemote extends DaoRemoteFacade {

    public ContabilidadBoletaje getConfiguracion(Integer idEmpresa) throws CRUDException;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public Entidad get(Entidad e) throws CRUDException;

    @Override
    public Entidad get(Integer id, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    /**
     * Crea la nota de debito a partir del Boleto Simple que se ha creado
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public NotaDebito createNotaDebito(Boleto boleto) throws CRUDException;

    /**
     * Crea la lista de Transacciones para la nota debito de un solo boleto
     *
     * @param boleto
     * @param notaDebito
     * @return
     * @throws CRUDException
     */
    public NotaDebitoTransaccion createNotaDebitoTransaccion(Boleto boleto, NotaDebito notaDebito) throws CRUDException;

    /**
     * Anula una transaccion. Verifica si exi
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public int anularTransaccion(Boleto boleto) throws CRUDException;

    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException;

    public List<NotaDebito> getAllNotaDebito(BoletoSearchForm search) throws CRUDException;

    public List<NotaDebito> getAllByCliente(Integer idCliente) throws CRUDException;

    /**
     * Creamos una nota de debito al momento de ingresar a la interfaz de Nuevo
     * Nota Debito
     *
     * @param idEmpresa
     * @param usuario
     * @return
     * @throws CRUDException
     */
    public NotaDebito createNotaDebito(Integer idEmpresa, String usuario) throws CRUDException;

    /**
     * Devuelve la lista de Transacciones para la Nota de Debito
     *
     * @param idNotaDebito
     * @return
     * @throws CRUDException
     */
    public List<NotaDebitoTransaccion> getAllTransacciones(Integer idNotaDebito) throws CRUDException;

    /**
     * Suma los montos de las transacciones y actualiza en los montos de la Nota
     * de Debito
     *
     * @param idNotaDebito
     * @return
     * @throws CRUDException
     */
    public Integer actualizarMontosNotaDebito(Integer idNotaDebito) throws CRUDException;

    public Integer actualizarMontosNotaDebitoEmitida(Integer idNotaDebito) throws CRUDException;

    /**
     * Asociacion en Base de datos del Boleto b con la Nota de Debito.
     *
     * @param b
     * @param n
     * @return El id de la transaccion a la cual esta relacionada el Boleto
     * @throws CRUDException
     */
    public Boleto asociarBoletoNotaDebito(NotaDebito n, Integer idBoleto, String usuario) throws CRUDException;

    public Boleto asociarBoletoNotaDebitoManual(NotaDebito n, Boleto b) throws CRUDException;

    /**
     *
     * @param b
     * @param n
     * @return
     * @throws CRUDException
     */
    public Integer updateBoletoNotaDebito(Boleto b, NotaDebito n) throws CRUDException;

    /**
     * Coloca la Nota de Debito en Pendiente. Debe actualizar los valores que el
     * usuario haya ingresado
     *
     * @param n
     * @throws CRUDException
     */
    public void pendiente(NotaDebito n) throws CRUDException;

    /**
     * Guarda la trnasaccion de Cargo y crea una transaccion para la nota de
     * Debito.
     *
     * @param cargo
     * @return
     * @throws CRUDException
     */
    public CargoBoleto saveCargo(CargoBoleto cargo) throws CRUDException;

    /**
     *
     * @param cargo
     * @return
     * @throws CRUDException
     */
    public CargoBoleto updateCargo(CargoBoleto cargo) throws CRUDException;

    /**
     * Obtiene la informacion del cargo de la Nota de Debito
     *
     * @param cargo
     * @return
     * @throws com.seguridad.control.exception.CRUDException
     */
    public CargoBoleto getCargo(CargoBoleto cargo) throws CRUDException;

    /**
     * Coloca a Emitido la Nota de Debito Pasa los boletos a sus Planes de
     * Cuentas
     *
     * @param idNotaDebito
     * @return
     * @throws CRUDException
     */
    public boolean finalizar(NotaDebito nota) throws CRUDException;

    /**
     *
     * @param idNotaTransaccion
     * @return
     * @throws CRUDException
     */
    public NotaDebitoTransaccion getNotaDebitoTransaccion(Integer idNotaTransaccion) throws CRUDException;

    /**
     * Actualiza los montos adeudados de la transacion de la nota de debito y
     * establece los estados de la nota de debito.
     *
     * @param trx
     * @throws CRUDException
     */
    public void actualizarMontosAdeudadosTransaccion(IngresoTransaccion trx) throws CRUDException;

    public void actualizarMontosAdeudadosTransaccion(NotaCreditoTransaccion trx) throws CRUDException;

    public void actualizarMontosAdeudadosTransaccion(PagoAnticipadoTransaccion trx) throws CRUDException;

    /**
     * REaliza la anulacion de la Nota de debito, sus transacciones, y todo lo
     * demas
     *
     * @param nota
     * @throws CRUDException
     */
    public void anularNotaDebito(NotaDebito nota, String usuario) throws CRUDException;

    /**
     * Devuelve todas las Notas de Debito que estan con forma de Pago al Credito
     * y su estado es EN MORA, EMITIDA
     *
     * @param idCliente
     * @return
     * @throws CRUDException
     */
    public List<NotaDebitoTransaccion> getAllNotaDebitoCreditoByCliente(Integer idCliente, Integer idEmpresa) throws CRUDException;

    /**
     * Revierte los montos de las transacciones hacia la nota de debito. Esto se
     * hace cuando la transaccion ha sido ANULADA
     *
     * @param it
     * @param ndtx
     * @throws CRUDException
     */
    public void revertirMontosIngresoDeCaja(IngresoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException;

    public void revertirMontosNotaCredito(NotaCreditoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException;

    public void revertirMontosPagosAnticipados(PagoAnticipadoTransaccion it, NotaDebitoTransaccion ndtx) throws CRUDException;
    
    public List<NotaDebito> getNotaDebitoCreditoWhereVencimientoWasYesterday() throws CRUDException ;
    
    public List<NotaDebito> getNotaDebitoEnMora() throws CRUDException ;

}
