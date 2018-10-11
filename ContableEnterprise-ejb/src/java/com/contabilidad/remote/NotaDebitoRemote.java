/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Boleto;
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
     * @return
     * @throws CRUDException 
     */
    public List<NotaDebitoTransaccion> createNotaDebitoTransacction(List<Boleto> boleto, NotaDebito notaDebito) throws CRUDException ;
    
}
