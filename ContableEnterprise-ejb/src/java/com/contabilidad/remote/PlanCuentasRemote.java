/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.PlanCuentas;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface PlanCuentasRemote extends DaoRemoteFacade {

    public List<PlanCuentas> get() throws CRUDException;

    public List<Object[]> getForCombo() throws CRUDException;

    public List<Object[]> getForCombo(Empresa idEmpresa) throws CRUDException ;

    public List<PlanCuentas> getForCombo(Empresa idEmpresa, PlanCuentas p) throws CRUDException ;

    public List<PlanCuentas> get(Empresa idEmpresa) throws CRUDException ;
    
    public List<PlanCuentas> getForComboIdPLan(Empresa idEmpresa, PlanCuentas p) throws CRUDException ;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;
    
    

    
}
