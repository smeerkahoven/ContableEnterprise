/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Boleto;
import com.contabilidad.entities.DebitoIngreso;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.response.json.boletaje.IngresoCajaSearchJson;
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
public interface IngresoCajaRemote extends DaoRemoteFacade {

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

    public IngresoCaja createNewIngresoCaja(String idUsuario, Integer idEmpresa) throws CRUDException;

    public IngresoCaja createIngresoCaja(final Boleto boleto, final NotaDebito nota) throws CRUDException;

    public IngresoCaja createIngresoCaja(final NotaDebito nota) throws CRUDException;

    public DebitoIngreso createDebitoIngresoCaja(final NotaDebito nota, final IngresoCaja caja) throws CRUDException;

    public IngresoTransaccion createIngresoCajaTransaccion(final Boleto boleto, final NotaDebito nota,
            final NotaDebitoTransaccion transacciones, IngresoCaja ingreso) throws CRUDException;

    public IngresoTransaccion createIngresoCajaTransaccion(NotaDebitoTransaccion nt,
            NotaDebito nota, IngresoCaja ingreso) throws CRUDException;

    public void actualizarMontosFinalizar(IngresoCaja caja) throws CRUDException;

    public List getIngresoCajaByNotaDebito(Integer idNotaDebito) throws CRUDException;

    /**
     * Anula la Transaccion de acuerdo a la Transaccion de la Nota de Debito
     *
     * @param tr
     * @param update
     * @throws CRUDException
     */
    public void anularTransaccion(NotaDebitoTransaccion tr, boolean update) throws CRUDException;

    /**
     * Retorna todos los ingresos de Caja por Cliente y por empresa
     *
     * @param search
     * @return
     * @throws CRUDException
     */
    public List<IngresoCaja> findAllIngresoCaja(IngresoCajaSearchJson search) throws CRUDException;

    public List<IngresoTransaccion> findAllIngresoCajaTransacciones(Integer idIngresoCaja) throws CRUDException;

    /**
     * Valida los datos y coloca en estado PENDIENTE
     *
     * @param caja
     * @throws CRUDException
     */
    public void pendiente(IngresoCaja caja) throws CRUDException;

    /**
     * Coloca el ingreso a Caja a ANULADO y sus transacciones automaticamente pasan a ANULADO
     * por medio de los TRiggers en BD
     * @param idIngresoCaja
     * @throws CRUDException 
     */
    public void anularIngresoCaja(Integer idIngresoCaja) throws CRUDException;
    
    /**
     * 
     * @param idTransaccion
     * @throws CRUDException 
     */
    public void anularTransaccion (Integer idTransaccion) throws CRUDException ;
    
    /**
     * 
     * @param idIngresoCaja
     * @return
     * @throws CRUDException 
     */
    public IngresoCaja finalizar(Integer idIngresoCaja) throws CRUDException ;

}

