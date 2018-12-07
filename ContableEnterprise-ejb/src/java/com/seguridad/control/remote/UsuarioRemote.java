/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import com.response.json.seguridad.UserPersonalJSON;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Cheyo
 */
@Remote
public interface UsuarioRemote {
    
    public UserToken get(UserToken u) throws CRUDException ;
    
    public User get(User e) throws CRUDException ;
    
    public List<User> get() throws CRUDException ;
    
    public List<Object[]> get(String query) throws CRUDException ;
    
    public List<Object[]> get(String query, HashMap<String, Object> param ) throws CRUDException ;
    
    public boolean update(User e) throws CRUDException ;
    
    public boolean update(User e, String query) throws CRUDException ;
    
    public boolean update(UserToken u) throws CRUDException ;
    
     public boolean updatePassword(User u) throws CRUDException ;
    
    public boolean update(UserLogin u) throws CRUDException ;
    
    public int insert(User e) throws CRUDException ;
    
    public int insert(UserToken u) throws CRUDException ;
    
    public int insert (UserLogin u)throws CRUDException ;

    public UserPersonalJSON getUsuario(User user) throws CRUDException;
    
}
