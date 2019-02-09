/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.remote;

import com.cobranzas.dto.KardexClienteDto;
import com.cobranzas.json.KardexClienteSearchJson;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemote;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface KardexClienteRemote extends DaoRemote {
    
    /**
     * Genera el kardex del cliente. El parametro mas importante es el cliente, 
     * ya que con el se obtiene toda la informacion.
     * @param search
     * @return
     * @throws CRUDException 
     */
    public List<KardexClienteDto> generarKardexCliente (KardexClienteSearchJson search) throws CRUDException ;
    
}
