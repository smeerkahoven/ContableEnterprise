/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.TarjetaCreditoRemote;
import com.agencia.entities.TarjetaCredito;
import com.contabilidad.remote.PlanCuentasRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.TarjetaCreditoJSON;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.seguridad.EmpresaServices;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.services.TemplateResource;
import com.util.resource.Mensajes;
import com.view.menu.Formulario;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@Path("tarjetas-credito")
public class TarjetasCreditoResource extends TemplateResource {

    @Context
    private UriInfo context;

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private TarjetaCreditoRemote ejbTarjeta;

    @EJB
    private LoggerRemote ejbLogger;

    @EJB
    private PlanCuentasRemote ejbPlanCuentas;

    /**
     * Creates a new instance of TarjetasCreditoResource
     */
    public TarjetasCreditoResource() {
    }

    @GET
    @Path("combo/all")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getComboAll() {
        RestResponse response = new RestResponse();
        try {

            List l = ejbTarjeta.get();
            List r = new LinkedList();
            l.forEach((x) -> {
                ComboSelect s = new ComboSelect();
                s.setId(((TarjetaCredito)x).getIdTarjetaCredito());
                s.setName(((TarjetaCredito)x).getDenominacion());
                r.add(s);
            });

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(TarjetasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.TarjetasCreditoResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getPlanCuentasAll(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        doValidations(request);
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<TarjetaCredito> l = ejbTarjeta.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.TARJETA_CREDITO, user.getIp());

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
    @Path("combo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getCombo(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                User u = ejbUsuario.get(new User(t.getUserName()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<Object[]> l = ejbPlanCuentas.getForCombo(u.getIdEmpleado().getIdEmpresa());

                        ArrayList hm = new ArrayList();
                        for (Object[] o : l) {
                            ComboSelect cm = new ComboSelect();
                            cm.setId((BigInteger) o[0]);
                            cm.setName((String) o[1]);
                            hm.add(cm);
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(hm);

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
    public RestResponse savePlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        doValidations(request);
        
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        TarjetaCreditoJSON json = new TarjetaCreditoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), TarjetaCreditoJSON.class);

                        TarjetaCredito tc = TarjetaCreditoJSON.toTarjetaCredito(json);
                        ejbTarjeta.insert(tc);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.INSERT, t.getUserName(), request.getFormName(), user.getIp());

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
    public RestResponse updatePlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        doValidations(request);
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        TarjetaCreditoJSON json = new TarjetaCreditoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), TarjetaCreditoJSON.class);

                        TarjetaCredito pcu = TarjetaCreditoJSON.toTarjetaCredito(json);
                        ejbTarjeta.update(pcu);

                        ejbLogger.add(Accion.UPDATE, t.getUserName(), request.getFormName(), user.getIp());

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
     * PUT method for updating or creating an instance of
     * TarjetasCreditoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
