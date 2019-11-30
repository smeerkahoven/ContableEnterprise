/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.BalanceGeneralDto;
import com.response.json.contabilidad.BalanceGeneralSearchJson;
import com.seguridad.control.exception.CRUDException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface BalanceGeneralRemote {
    
    public List<BalanceGeneralDto> generarBalance (final BalanceGeneralSearchJson search) throws CRUDException ;
    
}
