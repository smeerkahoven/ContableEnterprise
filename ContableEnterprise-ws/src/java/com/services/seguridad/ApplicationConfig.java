/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author xeio
 */
@javax.ws.rs.ApplicationPath("ws-api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.security.CrossOriginResourceSharingFilter.class);
        resources.add(com.services.agencia.AerolineaResource.class);
        resources.add(com.services.agencia.AeropuertoResource.class);
        resources.add(com.services.agencia.BancosResource.class);
        resources.add(com.services.agencia.BoletoResource.class);
        resources.add(com.services.agencia.ClienteResource.class);
        resources.add(com.services.agencia.PromotorResource.class);
        resources.add(com.services.agencia.TarjetasCreditoResource.class);
        resources.add(com.services.agencia.boletaje.PlanillaBspResource.class);
        resources.add(com.services.cobranzas.KardexClienteResource.class);
        resources.add(com.services.configuracion.BoletajeResource.class);
        resources.add(com.services.configuracion.FactoresResource.class);
        resources.add(com.services.configuracion.UserPersonalResource.class);
        resources.add(com.services.contabilidad.ComprobantesResource.class);
        resources.add(com.services.contabilidad.DevolucionResource.class);
        resources.add(com.services.contabilidad.IngresoCajaResource.class);
        resources.add(com.services.contabilidad.MayoresResource.class);
        resources.add(com.services.contabilidad.NotasCreditoResource.class);
        resources.add(com.services.contabilidad.PagoAnticipadoResource.class);
        resources.add(com.services.contabilidad.PlanCuentasResource.class);
        resources.add(com.services.notadebito.NotadebitoResource.class);
        resources.add(com.services.reportes.ComisionClienteResource.class);
        resources.add(com.services.reportes.VentaBoletosResource.class);
        resources.add(com.services.seguridad.EmpresaServices.class);
        resources.add(com.services.seguridad.LogsResource.class);
        resources.add(com.services.seguridad.PersonalResource.class);
        resources.add(com.services.seguridad.RolesResource.class);
        resources.add(com.services.seguridad.UsuarioResource.class);
    }
    
}
