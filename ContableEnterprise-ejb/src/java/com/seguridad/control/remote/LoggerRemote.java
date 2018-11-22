/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.seguridad.control.entities.Log;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface LoggerRemote extends Serializable {

    public boolean add(Log l) throws CRUDException;
    
    public boolean add(Accion accion, String usuario, String formulario, String ip) throws CRUDException ;
    
    public boolean add(Accion accion, String usuario, String formulario, String ip, String comentario) throws CRUDException ;

    public List<Log> getAllFrom(Log l) throws CRUDException ;
    
    public boolean add(UserLogin u) throws CRUDException ;
    
    public boolean add(String ip, int intento, String user) throws CRUDException ;
    
    
}
