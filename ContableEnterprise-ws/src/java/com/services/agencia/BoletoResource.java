/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.ejb.BoletoMultipleSearch;
import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.entities.BoletoSearch;
import com.agencia.search.dto.BoletoSearchForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.BoletoJSON;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import java.math.BigDecimal;
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
@Path("boletos")
public class BoletoResource extends TemplateResource {

    @EJB
    private BoletoRemote ejbBoleto;

    /**
     * Creates a new instance of BoletoResource
     */
    public BoletoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.BoletoResource
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
     * PUT method for updating or creating an instance of BoletoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cliente-pasajeros/{idCliente}")
    public RestResponse getClientePasajeros(@PathParam("idCliente") Integer idCliente) {
        RestResponse response = new RestResponse();

        Optional op = Optional.ofNullable(idCliente);
        if (op.isPresent()) {

            try {
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(ejbBoleto.getPasajerosPorCliente(idCliente));
            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getCause());
            }
        } else {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
        }

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("existe-boleto/{numero}")
    public RestResponse getExisteBoleto(@PathParam("numero") long numero) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(numero);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            boolean existe = ejbBoleto.isBoletoRegistrado(new Boleto(numero));

            if (existe) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NUMERO_BOLETO_INSERTADO));
            } else {
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_NUMERO_BOLETO_VALIDO));

            }

        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tipo-emision")
    public RestResponse getTipoEmision() {
        RestResponse response = new RestResponse();

        try {
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(ejbBoleto.getTipoEmision());
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("save-simple")
    public RestResponse saveSimple(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                BoletoJSON bjson;
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                bjson = gson.fromJson(object.toString(), BoletoJSON.class);

                bjson.setIdUsuarioCreador(user.getUserName());
                bjson.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Boleto boleto = BoletoJSON.toNewBoleto(bjson);

                Optional op = Optional.ofNullable(boleto);
                if (op.isPresent()) {

                    boleto = ejbBoleto.procesarBoleto(boleto);
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent("El Boleto ha sido ingresado Exitosamente!");

                    bjson.setIdBoleto(boleto.getIdBoleto());
                    bjson.setIdNotaDebitoTransaccion(boleto.getIdNotaDebitoTransaccion());
                    bjson.setIdIngresoCajaTransaccion(boleto.getIdIngresoCajaTransaccion());

                    bjson.setIdLibro(boleto.getIdLibro());
                    bjson.setIdNotaDebito(boleto.getIdNotaDebito());
                    bjson.setIdIngresoCaja(boleto.getIdIngresoCaja());

                    response.setEntidad(bjson);

                    ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.BOLETOS, user.getIp());
                }
            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        } else {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BOLETO_NO_CREADO));
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("anular")
    public RestResponse anular(final RestRequest request) {

        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            HashMap<String, java.math.BigDecimal> parameter = (HashMap<String, java.math.BigDecimal >) request.getContent();
            Optional p = Optional.ofNullable(parameter.get("idBoleto"));
            if (p.isPresent()) {
                try {
                    Integer idBoleto = parameter.get("idBoleto").intValue();
                    ejbBoleto.anular(new Boleto(idBoleto));
                } catch (CRUDException ex) {
                    Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(ex.getMessage());
                }
            } else {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
            }

        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update")
    public RestResponse updateSimple(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                BoletoJSON bjson = convertToBoletoJSON(request);
                Boleto b = BoletoJSON.toNewBoleto(bjson);
                ejbBoleto.update(b);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.UPDATE, user.getUserName(), Formulario.BOLETOS, user.getIp());

            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class
                        .getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_UPDATE_ERROR));
            }
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("all")
    public RestResponse getAll(final RestRequest request) {

        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {

                BoletoSearchForm search;
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                search = gson.fromJson(object.toString(), BoletoSearchForm.class);

                search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                List l = ejbBoleto.getBoletos(search);

                List r = new LinkedList();

                l.forEach((x) -> {
                    BoletoJSON bjson = BoletoJSON.toBoletoJSON((Boleto) x);
                    r.add(bjson);
                });

                response.setContent(r);
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

                ejbLogger.add(Accion.ACCESS, user.getUserName(), Formulario.BOLETOS, user.getIp());

            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class
                        .getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getCause());
            }
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("save-boleto")
    public RestResponse saveBoleto(final RestRequest request) {
        RestResponse response = new RestResponse();

        response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            BoletoJSON bjson;
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            System.out.println((String) request.getContent());
            bjson
                    = gson.fromJson(object.toString(), BoletoJSON.class
                    );

            try {
                bjson.setIdUsuarioCreador(user.getUserName());
                bjson.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Boleto boleto = BoletoJSON.toNewBoleto(bjson);

                Optional op = Optional.ofNullable(boleto);
                if (op.isPresent()) {

                    boleto = ejbBoleto.saveBoletoMultiple(boleto);
                    bjson.setIdBoleto(boleto.getIdBoleto());

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BOLETO_MULTIPLE_INSERTADO));
                    response.setEntidad(bjson);

                    ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.BOLETOS, user.getIp());

                }
            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class
                        .getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("save-void")
    public RestResponse saveVoid(final RestRequest request) {
        RestResponse response = new RestResponse();

        response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            BoletoJSON bjson;
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            System.out.println((String) request.getContent());
            bjson
                    = gson.fromJson(object.toString(), BoletoJSON.class
                    );

            bjson.setIdUsuarioCreador(user.getUserName());
            bjson.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
            try {
                Boleto boleto = BoletoJSON.toNewBoleto(bjson);

                Optional op = Optional.ofNullable(boleto);
                if (op.isPresent()) {

                    boleto = ejbBoleto.saveBoletoVoid(boleto);
                    bjson.setIdBoleto(boleto.getIdBoleto());

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BOLETO_VOID_INSERTADO));
                    response.setEntidad(bjson);

                    ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.BOLETOS, user.getIp());
                }

            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class
                        .getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }

        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("load-multiple")
    public RestResponse loadMultiple(final RestRequest request
    ) {
        RestResponse response = new RestResponse();

        response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            BoletoMultipleSearch bjson;
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            System.out.println((String) request.getContent());
            bjson
                    = gson.fromJson(object.toString(), BoletoMultipleSearch.class
                    );

            try {
                List l = ejbBoleto.getBoletosMultiples(bjson.getIdBoleto(), bjson.getIdBoletoPadre());

                List resp = new LinkedList();
                l.forEach(x -> {
                    BoletoJSON bj = BoletoJSON.toBoletoJSON((Boleto) x);

                    resp.add(bj);
                });

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(resp);

                ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.BOLETOS_MULTIPLES, user.getIp());

            } catch (CRUDException ex) {
                Logger.getLogger(BoletoResource.class
                        .getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }

        }

        return response;
    }

    private BoletoJSON convertToBoletoJSON(final RestRequest request) {
        BoletoJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson
                = gson.fromJson(object.toString(), BoletoJSON.class
                );

        return bjson;
    }
}
