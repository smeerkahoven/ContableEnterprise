/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.notadebito;

import com.agencia.entities.Boleto;
import com.agencia.entities.BoletoSearch;
import com.agencia.search.dto.BoletoSearchForm;
import com.contabilidad.entities.AsociacionBoletoNotaDebitoJSON;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.remote.NotaDebitoRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.contabilidad.NotaDebitoJSON;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.agencia.BoletoResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import java.math.BigDecimal;
import java.time.Clock;
import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("notadebito")
public class NotadebitoResource extends TemplateResource {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    /**
     * Creates a new instance of NotadebitoResource
     */
    public NotadebitoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.notadebito.NotadebitoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of NotadebitoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("boleto/asociar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse asociarBoleto(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {
            System.out.println(request.getContent());

            AsociacionBoletoNotaDebitoJSON json;
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            json = gson.fromJson(object.toString(), AsociacionBoletoNotaDebitoJSON.class); //Anhadimos cada boleto a una transaccion

            if (json.getBoletos().size() > 0) {
                for (Integer id : json.getBoletos()) {
                    Boleto boleto = new Boleto(id);
                    ejbNotaDebito.asociarBoletoNotaDebito(boleto, new NotaDebito(json.getIdNotaDebito()));
                }

                //actualizamos los montos con los boletos cargados
                ejbNotaDebito.actualizarMontosNotaDebito(json.getIdNotaDebito());

                //devolvemos la nota de debito con los montos actualizados
                NotaDebito notaResult = (NotaDebito) ejbNotaDebito.get(new NotaDebito(json.getIdNotaDebito()));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BOLETO_MULTIPLE_INSERTADO));
                response.setEntidad(notaResult);

                ejbLogger.add(Accion.TRANSACTION, user.getUserName(), com.view.menu.Formulario.BOLETOS_OTROS, user.getIp());
            }

        } catch (Exception ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            ex.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("all/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllTransactions(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                HashMap<String, BigDecimal> content = (HashMap<String, BigDecimal>) request.getContent();
                if (content.get("idNotaDebito") == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                BigDecimal idNotaDebito = content.get("idNotaDebito");

                List l = ejbNotaDebito.getAllTransacciones(idNotaDebito.intValue());

                if (l.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_WARNING.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(l);

                ejbLogger.add(Accion.SEARCH, user.getUserName(), com.view.menu.Formulario.BOLETOS_OTROS, user.getIp());
            }
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            ex.printStackTrace();
        }

        return response;
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse newNotaDebito(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

                NotaDebito n = ejbNotaDebito.createNotaDebito(user.getIdEmpleado().getIdEmpresa().getIdEmpresa(), user.getUserName());
                Optional op = Optional.ofNullable(n);
                if (op.isPresent()) {
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(NotaDebitoJSON.toNotaDebitoJSON(n));
                } else {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_CANT_CREATE_NOTA_DEBITO));
                }
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.BOLETOS_OTROS, user.getIp());

            }
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);

            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_CANT_CREATE_NOTA_DEBITO));
        }

        return response;
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllBoletos(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                BoletoSearchForm search;
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                search = gson.fromJson(object.toString(), BoletoSearchForm.class);

                search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                List<NotaDebito> l = ejbNotaDebito.getAllNotaDebito(search);

                if (!l.isEmpty()) {
                    List r = new LinkedList();

                    l.forEach(x -> {
                        NotaDebitoJSON j = NotaDebitoJSON.toNotaDebitoJSON(x);
                        r.add(j);
                    });

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(r);

                    ejbLogger.add(Accion.SEARCH, user.getUserName(), com.view.menu.Formulario.BOLETOS_OTROS, user.getIp());

                } else {
                    response.setCode(ResponseCode.RESTFUL_WARNING.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NO_RECORDS_FOUND));
                }
            }

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
