/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.FacadeEJB;
import com.seguridad.control.FacadeEJBFirst;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Empleado;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import com.seguridad.queries.Queries;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cheyo
 */
@Stateless
public class EmpleadoEJB extends FacadeEJBFirst implements EmpleadoRemote {

    private String mensaje;
    //ResourceBundle.getBundle("/servlet/Mensajes").getString("MENSAJITO")

    @Override
    public Empleado get(Empleado e) throws CRUDException {
        Empleado emp = em.find(Empleado.class, e.getIdEmpleado());

        if (emp == null) {
            String message = Thread.currentThread().getStackTrace()[1].getMethodName()
                    + ":" + e.getIdEmpleado() + " not found";
            //LoggerContable.log(message, e, Level.SEVERE);
            throw new CRUDException(message);
        }

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return emp;
    }

    @Override
    public boolean update(Empleado e) throws CRUDException {
        em.merge(e);
        em.flush();
        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);
        return true;
    }

    @Override
    public int insert(Empleado e) throws CRUDException {
        em.persist(e);
        em.flush();

        LoggerContable.log(Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.toString(), this, Level.FINE);

        return e.getIdEmpleado();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public List<Empleado> get() throws CRUDException {
        Query q = em.createNativeQuery(queries.getPropertie(Queries.GET_EMPLEADOS), Empleado.class);
        List<Empleado> l = q.getResultList();
        return l;

    }

    @Override
    public List<Object[]> getForCombo() throws CRUDException {
        Query q = em.createNativeQuery(queries.getPropertie(Queries.GET_EMPLEADOS_COMBO_USUARIO));
        List<Object[]> l = q.getResultList();
        
        return l ;
    }

}
