/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import com.seguridad.control.remote.DaoRemoteFacade;
import com.seguridad.queries.Queries;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
public abstract class FacadeEJBFirst {

    protected String findAll ;
    
    protected Class<?> typeClass ;
    
    @PersistenceContext
    protected EntityManager em;

    protected Queries queries = Queries.getQueries();


    
}
