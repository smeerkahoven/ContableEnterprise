/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reportes.remote;

import com.reportes.entities.Reportes;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface ReportAgenciaRemote extends DaoRemote {
    
    public List<Reportes> get(Formulario f) throws CRUDException ;
    
}
