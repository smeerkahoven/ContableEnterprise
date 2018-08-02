/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.ClienteSolicitado;

/**
 *
 * @author xeio
 */
public class ClienteSolicitadoJSON {

    private Integer idCliente;
    private Integer idClienteSolicitado;
    private String nombre;

    public static ClienteSolicitado toClienteSolicitado(ClienteSolicitadoJSON json){
        ClienteSolicitado cliente = new ClienteSolicitado();
        cliente.setIdCliente(json.getIdCliente());
        cliente.setIdClienteSolicitado(json.getIdClienteSolicitado());
        cliente.setNombre(json.getNombre());
        
        return cliente ;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdClienteSolicitado() {
        return idClienteSolicitado;
    }

    public void setIdClienteSolicitado(Integer idClienteSolicitado) {
        this.idClienteSolicitado = idClienteSolicitado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
