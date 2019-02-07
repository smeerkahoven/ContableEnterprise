/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Log;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.search.LogSearch;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.Query;

/**
 *
 * @author Cheyo
 */
@Stateful
public class LoggerEJB extends FacadeEJB implements LoggerRemote {

    static final long serialVersionUID = 42L;

    @Override
    public boolean add(Log l) {

        em.persist(l);

        return true;
    }

    @Override
    public List<Log> getAllFrom(Log l) throws CRUDException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(Accion accion, String usuario, String formulario, String ip) throws CRUDException {
        Log l = new Log();
        l.setAccion(accion.getValue());
        l.setUsuario(usuario);
        l.setFormulario(formulario);
        l.setIp(ip);
        l.setFechaLog(DateContable.getCurrentDate());

        return add(l);

    }

    @Override
    public boolean add(String ip, int intento, String user) throws CRUDException {
        UserLogin l = new UserLogin();
        l.setFechaLogin(DateContable.getCurrentDate());
        l.setIp(ip);
        l.setNroIntento(intento);
        l.setUserName(user);

        return add(l);
    }

    @Override
    public boolean add(UserLogin u) throws CRUDException {
        em.persist(u);
        em.flush();
        return true;
    }

    @Override
    public boolean add(Accion accion, String usuario, String formulario, String ip, String comentario) throws CRUDException {
        Log l = new Log();
        l.setAccion(accion.getValue());
        l.setUsuario(usuario);
        l.setFormulario(formulario);
        l.setIp(ip);
        l.setFechaLog(DateContable.getCurrentDate());
        l.setComentario(comentario);
        return add(l);
    }

    @Override
    public List<Log> find(LogSearch search) throws CRUDException {
        String q = "SELECT l FROM Log l WHERE 1=1 ";
        List<Log> returnList = new LinkedList<>();

        if (search.getFechaFin() != null) {
            q += " and l.fechaLog <= :fechaFin";
        }

        if (search.getUsername() != null) {
            q += " and l.usuario = :usuario ";
        }

        if (search.getFechaInicio() != null) {
            q += " and l.fechaLog >= :fechaInicio";
        }

        if (search.getFechaFin() != null) {
            q += " and l.fechaLog <= :fechaFin";
        }

        Query query = em.createQuery(q, Log.class);

        if (search.getUsername() != null) {
            query.setParameter("usuario", search.getUsername().getId());
        }

        if (search.getFechaInicio() != null) {
            query.setParameter("fechaInicio", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        if (search.getFechaFin() != null) {
            query.setParameter("fechaFin", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        returnList = query.getResultList();

        return returnList;

    }

}
