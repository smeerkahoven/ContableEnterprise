/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.seguridad;

import com.seguridad.control.entities.Empresa;

/**
 *
 * @author xeio
 */
public class SucursalJSON extends EmpresaJSON{
    
    
    
    public Empresa toSucursal(SucursalJSON json){
        Empresa e = new Empresa();
        e.setDireccion(json.getDireccion());
        e.setEmail(json.getEmail());
        e.setIdEmpresa(json.getIdEmpresa());
        e.setNroIata(json.getNroIata());
        e.setPaginaWeb(json.getPaginaWeb());
        e.setTelefonoCelular(json.getTelefonoCelular());
        e.setTelefonoFijo(json.getTelefonoFijo());
        e.setRazonSocial(json.getRazonSocial());
        return e ;
    }

}
