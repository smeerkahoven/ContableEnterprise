/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Boleto;
import com.agencia.search.dto.BoletoSearchForm;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
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
public interface NotaDebitoRemote extends DaoRemoteFacade  {

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public Entidad get(Entidad e) throws CRUDException;
    

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;
    
    /**
     * Crea la nota de debito a partir del Boleto Simple que se ha creado
     * @param boleto
     * @return
     * @throws CRUDException 
     */
    public NotaDebito createNotaDebito (Boleto boleto) throws CRUDException ;
    
    /**
     * Crea una nota de debito a partir de la lista de Boletos Multiples
     * @param boleto
     * @return
     * @throws CRUDException 
     */
    public NotaDebito createNotaDebito (List<Boleto> boleto) throws CRUDException ;
    
    
    /**
     * Crea la lista de Transacciones para la nota debito de un solo boleto
     * @param boleto
     * @param notaDebito
     * @return
     * @throws CRUDException 
     */
    public NotaDebitoTransaccion createNotaDebitoTransaccion(Boleto boleto, NotaDebito notaDebito) throws CRUDException;
    
    /**
     * crea una lista de Transacciones para la nota de debito a partir de una lista de 
     * Boletos
     * @param boleto
     * @param notaDebito
     * @return
     * @throws CRUDException 
     */
    public List<NotaDebitoTransaccion> createNotaDebitoTransacction(List<Boleto> boleto, NotaDebito notaDebito) throws CRUDException ;
    
    /**
     * Anula una transaccion. Verifica si exi
     * 
     * @param boleto
     * @return
     * @throws CRUDException 
     */
    public int anularTransaccion(Boleto boleto) throws CRUDException;

    public List<NotaDebito> getAllNotaDebito(BoletoSearchForm search) throws CRUDException ;
    
    /**
     * Creamos una nota de debito al momento de ingresar a la interfaz de Nuevo Nota Debito
     * @param idEmpresa
     * @param usuario
     * @return
     * @throws CRUDException 
     */
    public NotaDebito createNotaDebito (Integer idEmpresa, String usuario) throws CRUDException ;
    
    /**
     * Devuelve la lista de Transacciones para la Nota de Debito
     * 
     * @param idNotaDebito
     * @return
     * @throws CRUDException 
     */
    public List<NotaDebitoTransaccion> getAllTransacciones (Integer idNotaDebito)throws CRUDException;
    
    /**
     * Suma los montos de las transacciones y actualiza en los montos de la Nota de Debito
     * @param idNotaDebito
     * @return
     * @throws CRUDException 
     */
    public Integer actualizarMontosNotaDebito (Integer idNotaDebito) throws CRUDException ;
    
        /**
     * Asociacion en Base de datos del Boleto b con la Nota de Debito.
     * @param b
     * @param n
     * @return El id de la transaccion a la cual esta relacionada el Boleto
     * @throws CRUDException 
     */
    public Integer asociarBoletoNotaDebito(Boleto b , NotaDebito n) throws CRUDException;
}
