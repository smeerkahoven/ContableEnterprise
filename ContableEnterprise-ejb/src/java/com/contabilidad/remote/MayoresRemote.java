/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.search.dto.MayoresSearch;
import com.contabilidad.entities.Mayores;
import com.contabilidad.entities.MayoresAcumulados;
import com.response.json.boletaje.GridMayores;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;

/**
 *
 * @author xeio
 */
public interface MayoresRemote extends DaoRemote {
    
    public List<  Mayores> getListaMayores(MayoresSearch search) throws CRUDException;
    
    public MayoresAcumulados getAcumulado(MayoresSearch search) throws CRUDException ;
    
    public GridMayores getGridMayores(MayoresSearch search) throws CRUDException ;
}
