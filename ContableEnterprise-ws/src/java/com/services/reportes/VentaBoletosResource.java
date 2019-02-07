/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.reportes;

import com.services.agencia.boletaje.*;
import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.BoletoPlanillaBsp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.boletaje.PlanillaSearchForm;
import com.response.json.boletaje.VentaBoletosSearchJson;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("reportes/ventas-boletos")
public class VentaBoletosResource extends TemplateResource {

    @EJB
    private BoletoRemote ejbBoleto;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PlanillaBspResource
     */
    public VentaBoletosResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.boletaje.PlanillaBspResource
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
     * PUT method for updating or creating an instance of PlanillaBspResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse getPlanillaBsp(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            VentaBoletosSearchJson search = convertToBoletoJSON(request);

            List<BoletoPlanillaBsp> list = ejbBoleto.getReporteVentas(search);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(list);

        } catch (CRUDException ex) {
            Logger.getLogger(VentaBoletosResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setContent(ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        }
        return response;
    }

    private VentaBoletosSearchJson convertToBoletoJSON(final RestRequest request) {
        VentaBoletosSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), VentaBoletosSearchJson.class);

        bjson.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

        return bjson;
    }

}
