/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.AeropuertoRemote;
import com.agencia.entities.Aeropuerto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.seguridad.EmpresaServices;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.Mensajes;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("aeropuerto")
@Stateless
public class AeropuertoResource {

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private AeropuertoRemote ejbAeropuerto;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AeropuertoResource
     */
    public AeropuertoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.AeropuertoResource
     *
     * @return an instance of java.lang.String
     */
    /**
     *
     * @param request
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("combo")
    public RestResponse getCombo() {
        RestResponse response = new RestResponse();
        try {
            response.setContent(ejbAeropuerto.getCombo());
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            return response;
        } catch (CRUDException ex) {
            Logger.getLogger(AeropuertoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getCause());
        }
        
        return response ;
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<Aeropuerto> l = ejbAeropuerto.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(l);

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;

    }

    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getEdit(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        Aeropuerto a = new Aeropuerto();
                        HashMap<String, Object> h = (HashMap<String, Object>) request.getContent();
                        a.setIdAeropuerto((String) h.get("idAeropuerto"));
                        a = (Aeropuerto) ejbAeropuerto.get(a);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(a);

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;

    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse guardar(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        HashMap<String, Object> hmap = (HashMap<String, Object>) request.getContent();

                        Aeropuerto a = new Aeropuerto();
                        a.setIdAeropuerto((String) hmap.get("idAeropuerto"));
                        a.setNombre((String) hmap.get("nombre"));

                        ejbAeropuerto.insert(a);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse delete(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        HashMap<String, Object> hmap = (HashMap<String, Object>) request.getContent();
                        Aeropuerto a = new Aeropuerto();
                        a.setIdAeropuerto((String) hmap.get("idAeropuerto"));

                        ejbAeropuerto.remove(a);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;

    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse update(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        Aeropuerto a = new Aeropuerto();

                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        a = gson.fromJson(object.toString(), Aeropuerto.class);

                        ejbAeropuerto.update(a);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;

    }

    /**
     * PUT method for updating or creating an instance of AeropuertoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
