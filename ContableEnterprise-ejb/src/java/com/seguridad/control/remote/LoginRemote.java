/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;


import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface LoginRemote {
    
    public  ResponseCode autenticar(User u) throws CRUDException;
    
    public void logOut(User u,  UserLogin ul) throws CRUDException; 
   
}
