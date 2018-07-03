/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.PlanCuentas;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface PlanCuentasRemote extends DaoRemote {

    public List<PlanCuentas> get() throws CRUDException;

    public List<Object[]> getForCombo() throws CRUDException;

    public List<Object[]> getForCombo(Empresa idEmpresa);

    public List<PlanCuentas> getForCombo(Empresa idEmpresa, PlanCuentas p);

    public List<PlanCuentas> get(Empresa idEmpresa);

}
