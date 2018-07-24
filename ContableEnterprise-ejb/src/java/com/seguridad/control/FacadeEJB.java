/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control;

import com.seguridad.queries.Queries;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author xeio
 */
public abstract class FacadeEJB  {

    @PersistenceContext
    protected EntityManager em;

    protected Queries queries = Queries.getQueries();

}
