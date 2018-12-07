/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.remote.IngresoCajaRemote;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.response.json.contabilidad.IngresoCajaJSON;
import com.response.json.contabilidad.IngresoTransaccionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.agencia.BoletoResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("ingreso-caja")
public class IngresoCajaResource extends TemplateResource {

    @EJB
    private IngresoCajaRemote ejbIngresoCaja;

    /**
     * Creates a new instance of IngresoCajaResource
     */
    public IngresoCajaResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.IngresoCajaResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of IngresoCajaResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllIngresosCaja(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {

            IngresoCajaSearchJson search = BeanUtils.convertoToIngresoCajaSearchJson(request);
            search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

            List<IngresoCaja> list = ejbIngresoCaja.findAllIngresoCaja(search);

            List<IngresoCajaJSON> r = new LinkedList<>();

            for (IngresoCaja ic : list) {
                r.add(IngresoCajaJSON.toJSON(ic));
            }

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());

            /*String mensaje = Logger.NOTA_DEBITO_CARGO_GUARDAR.replace("<cargo>", cargo.getIdCargo().toString());
                    mensaje = mensaje.replace("<nota>", cargo.getIdNotaDebito().toString());
             */
            // ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);
        }

        return response;
    }

    @POST
    @Path("all/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllTransacciones(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);
                
                List<IngresoTransaccion> list = ejbIngresoCaja.findAllIngresoCajaTransacciones(json.getIdIngresoCaja()) ;

                if (list.isEmpty()){
                    response.setContent("No se encontraron Transacciones para el Ingreso de Caja %s".replace("%s", json.getIdIngresoCaja().toString()));
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    return response ;
                }
                
                List<IngresoTransaccionJson> r = new LinkedList<>();
                for (IngresoTransaccion i : list) {
                    IngresoTransaccionJson trJson = IngresoTransaccionJson.toIngresoTransaccionJson(i);
                    r.add(trJson);
                }
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(r);
                
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse newIngreso(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                IngresoCaja caja = ejbIngresoCaja.createNewIngresoCaja(user.getUserName(), user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Optional op = Optional.ofNullable(caja);
                if (!op.isPresent()) {
                    throw new CRUDException("No se puede crear Ingresos a Caja");
                }

                IngresoCajaJSON json = IngresoCajaJSON.toJSON(caja);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), Log.INGRESO_CAJA_NUEVO.replace("<id>", caja.getIdIngresoCaja().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("pendiente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse pendienteNotaDebito(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);

            IngresoCaja caja = IngresoCajaJSON.toIngresoCaja(json);

            ejbIngresoCaja.pendiente(caja);

            String mensaje = Log.NOTA_DEBITO_PENDIENTE.replace("<id>", json.getIdNotaDebito().toString());
            ejbLogger.add(Accion.PENDIENTE, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (Exception ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    @POST
    @Path("get/notadebito/credito")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getNotaDebitosCredito(final RestRequest request) {
        RestResponse response = doValidations(request);
        return response;
    }

    @GET
    @Path("getall/notadebito/{idNota}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getIngresosCajaByNotaDebito(@PathParam("idNota") Integer idNota) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(idNota);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            HashMap<String, Integer> parameters = new HashMap<>();
            parameters.put("idNotaDebito", idNota);

            List<IngresoCaja> l = ejbIngresoCaja.getIngresoCajaByNotaDebito(idNota);

            List r = new LinkedList();
            for (IngresoCaja c : l) {
                IngresoCajaJSON json = IngresoCajaJSON.toJSON(c);
                r.add(json);
            }

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }
}
