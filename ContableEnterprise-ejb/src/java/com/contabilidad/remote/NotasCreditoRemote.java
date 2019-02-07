/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.response.json.boletaje.NotaCreditoSearchJson;
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
public interface NotasCreditoRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public Entidad get(Integer id, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    /**
     * Creacion de una Nueva Nota de Credito
     *
     * @param idUsuario
     * @param idEmpresa
     * @return
     * @throws CRUDException
     */
    public NotaCredito createNewNotaCredito(String idUsuario, Integer idEmpresa) throws CRUDException;

    /**
     * Busqueda de las notas de credito de acuerdo al patron search de busqueda
     *
     * @param search
     * @return
     * @throws CRUDException
     */
    public List<NotaCredito> findAllNotaCredito(NotaCreditoSearchJson search) throws CRUDException;

    /**
     * Devuelve todas las transacciones de acuerdo a un idNotaCredito
     *
     * @param idNotaCredito
     * @return
     * @throws CRUDException
     */
    public List<NotaCreditoTransaccion> findAllNotaCreditoTransacciones(Integer idNotaCredito) throws CRUDException;

    /**
     * Coloca en Pendiente una nota de Credito
     *
     * @param nota
     * @throws CRUDException
     */
    public void pendiente(NotaCredito nota) throws CRUDException;

    /**
     * Coloca la nota de credito a ANULADO y sus transacciones automaticamente
     * pasan a ANULADO por medio de los TRiggers en BD
     *
     * @param idNotaCredito
     * @throws CRUDException
     */
    public void anularNotaCredito(Integer idNotaCredito, String usuario) throws CRUDException;

    /**
     *
     * @param trx
     * @throws CRUDException
     */
    public void anularTransaccion(NotaCreditoTransaccion trx, String usuario) throws CRUDException;

    public void anularTransaccion(NotaDebitoTransaccion tr, String usuario) throws CRUDException;

    /**
     *
     * @param idNotaCredito
     * @return
     * @throws CRUDException
     */
    public NotaCredito finalizar(Integer idNotaCredito) throws CRUDException;

    /**
     *
     * @param data
     * @return
     * @throws CRUDException
     */
    public NotaCreditoTransaccion getNotaCreditoTransaccion(NotaCreditoTransaccion data) throws CRUDException;

    /**
     * Guarda la Transaccion de la Nota de Credito para la nota de Debito.
     *
     * @param trx
     * @return
     * @throws CRUDException
     */
    public NotaCreditoTransaccion saveTransaccion(NotaCreditoTransaccion trx, String usuario) throws CRUDException;

    /**
     * Actualiza la transaccion de la Nota de Credito verificando los valores
     * sean correctos
     *
     * @param trx
     * @return
     * @throws CRUDException
     */
    public NotaCreditoTransaccion updateTransaccion(NotaCreditoTransaccion trx) throws CRUDException;

    /**
     * Obtiene la lista de Notas de Creditos Transaccion por el id de la Nota de
     * Debito
     *
     * @param idNotaDebito
     * @return
     * @throws CRUDException
     */
    public List<NotaCreditoTransaccion> getNotaCreditoTransaccionByNotaDebito(NotaDebito idNotaDebito) throws CRUDException;

}
