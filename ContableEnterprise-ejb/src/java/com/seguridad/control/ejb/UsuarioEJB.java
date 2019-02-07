/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.response.json.seguridad.UserPersonalJSON;
import com.seguridad.control.FacadeEJBFirst;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.encryption.EncryptionContable;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.queries.Queries;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Cheyo
 */
@Stateless
public class UsuarioEJB extends FacadeEJBFirst implements UsuarioRemote {

    public String autenticar(User u) {
        return "";
    }

    
    @Override
    public User get(User u) throws CRUDException {
        Query q = em.createNamedQuery("User.findUserName", User.class);
        q.setParameter("user_name", u.getUserName());
        List<User> l = q.getResultList();

        for (User usr : l) {
            return usr;
        }

        return null;
    }

    @Override
    public UserToken get(UserToken u) throws CRUDException {
        UserToken t = em.find(UserToken.class, u.getIdToken());

        if (t == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + u.getIdToken() + " not found";
            //LoggerContable.log(message, e, L
            throw new CRUDException(message);
        } else {
            return t;
        }
    }

    @Override
    public boolean update(User e) throws CRUDException {
        e.setPassword(EncryptionContable.encrypt(e.getPassword()));
        em.merge(e);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
        return true;
    }

    @Override
    public int insert(User e) throws CRUDException {
        e.setPassword(EncryptionContable.encrypt(e.getPassword()));
        em.persist(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.SEVERE);
        return 1;
    }

    @Override
    public int insert(UserToken u) throws CRUDException {
        em.persist(u);
        em.flush();
        return 1;
    }

    @Override
    public int insert(UserLogin u) throws CRUDException {
        em.persist(u);
        em.flush();
        return u.getIdUserLogin();
    }

    @Override
    public boolean update(UserToken u) throws CRUDException {
        em.merge(u);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + u.toString(), this, Level.SEVERE);
        return true;
    }

    @Override
    public List<Object[]> get(String query) throws CRUDException {

        queries = Queries.getQueries();
        Query q = em.createNativeQuery(queries.getPropertie(query));

        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public List<Object[]> get(String query, HashMap<String, Object> param) throws CRUDException {

        queries = Queries.getQueries();
        Query q = em.createNativeQuery(queries.getPropertie(query));

        param.entrySet().forEach((Map.Entry<String, Object> entry) -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            q.setParameter(key, value);
        });

        List<Object[]> l = q.getResultList();

        return l;
    }

    @Override
    public boolean update(UserLogin u) throws CRUDException {
        em.merge(u);
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + u.toString(), this, Level.SEVERE);
        return true;
    }

    @Override
    public List<User> get() throws CRUDException {

        Query q = em.createNamedQuery("User.findAll", User.class);
        return (List<User>) q.getResultList();

    }

    @Override
    public boolean update(User e, String query) throws CRUDException {
        Query q = em.createNamedQuery(query);
        q.setParameter("p", e.getUserName());
        q.setParameter("r", e.getIdRol());
        q.setParameter("s", e.getStatus());

        q.executeUpdate();

        return true;
    }
    
       @Override
    public boolean updatePassword(User u) throws CRUDException {
        Query q = em.createNamedQuery("User.updatePassword");
        q.setParameter("userName", u.getUserName());
        q.setParameter("password",EncryptionContable.encrypt(u.getPassword())) ;

        q.executeUpdate();

        return true;
    }


    public UserPersonalJSON getUsuario(User user)  throws CRUDException{
        
        UserPersonalJSON u = new UserPersonalJSON();
        u.setApellido(user.getIdEmpleado().getApellido());
        u.setCargo(user.getIdEmpleado().getCargo());
        u.setEmail(user.getIdEmpleado().getEmail());
        u.setIdEmpleado(user.getIdEmpleado().getIdEmpleado());
        u.setNombre(user.getIdEmpleado().getNombre());
        u.setPassword(user.getPassword());
        u.setSexo(user.getIdEmpleado().getSexo());
        u.setTelefono(user.getIdEmpleado().getTelefono());
        u.setUserName(user.getUserName());
        
        return u ;
        
    }

}
