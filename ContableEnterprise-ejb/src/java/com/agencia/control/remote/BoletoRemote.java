/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.remote;

import com.agencia.entities.Boleto;
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
public interface BoletoRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List getList(String namedQuery, Class<?> typeClass) throws CRUDException;
    
    /**
     * Obtiene los tipos de Emision del Boleto que se almacenan en la cnt_tipo_emision
     * @return
     * @throws CRUDException 
     */
    
    public List getTipoEmision() throws CRUDException ;
    
    public boolean isBoletoRegistrado(Boleto b) throws CRUDException ;
    
    public Boleto procesarBoleto (Boleto b)throws CRUDException ;
    
    public List getPasajerosPorCliente(Integer idCliente) throws CRUDException ;
    
    
    public Boleto saveBoleto(Boleto b) throws CRUDException;
    
    /**
     * Busqueda de Boletos por medio de los parametros de entrada
     * @param idCliente
     * @param idAerolinea
     * @param idEmpresa
     * @param fechaI
     * @param fechaF
     * @return
     * @throws CRUDException 
     */
    public List getBoletos(Integer idCliente, Integer idAerolinea, Integer idEmpresa, String fechaI, String fechaF) throws CRUDException ;
    
    
    /**
     * Retorna la lista de Boletos Multiples para visualizar en la pantalla como una 
     * Tabla
     * @param idBoleto
     * @param idBoletoPadre
     * @return
     * @throws CRUDException 
     */
    public List getBoletosMultiples(Integer idBoleto , Integer idBoletoPadre) throws CRUDException;
}
