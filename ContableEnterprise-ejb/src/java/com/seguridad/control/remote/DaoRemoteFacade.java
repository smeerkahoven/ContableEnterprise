/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Cheyo
 */
public interface DaoRemoteFacade {

    public void update(Entidad e) throws CRUDException;

    public int insert(Entidad e) throws CRUDException;

    public Entidad insertE(Entidad e) throws CRUDException;

    public Entidad get(Entidad e) throws CRUDException;

    public Entidad get(String q) throws CRUDException;

    public List get() throws CRUDException;

    public List get(Entidad e, String q) throws CRUDException;

    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;
    
    public List getNative(String nativeQuery, HashMap parameters) throws CRUDException;

    public List getList(String namedQuery, Class<?> typeClass) throws CRUDException;

    public void remove(Entidad e) throws CRUDException;

    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;
    
    public void beginTransaction() throws CRUDException ;
    
    public void endTransaction() throws CRUDException ;
    
    public void rollback() throws CRUDException ;
    
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException ;

}
