/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.util.List;

/**
 *
 * @author Cheyo
 */
public interface DaoRemote {

    public void update(Entidad e) throws CRUDException;

    public int insert(Entidad e) throws CRUDException;

    public Entidad get(Entidad e) throws CRUDException;

    public Entidad get(String q) throws CRUDException;

            
    public List get() throws CRUDException;

    public List get(Entidad e, String q) throws CRUDException;
    

    public void remove(Entidad e) throws CRUDException;

}
