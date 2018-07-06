/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.PlanCuentas;
import com.contabilidad.remote.PlanCuentasRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.PlanCuentasJSON;
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
import com.util.resource.ComboSelect;
import com.util.resource.Mensajes;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("contabilidad")
public class ContabilidadResource {

    @Context
    private UriInfo context;

    @EJB
    private PlanCuentasRemote ejbPlanCuentas;

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private LoggerRemote ejbLogger;

    /**
     * Creates a new instance of ContabilidadResource
     */
    public ContabilidadResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.ContabilidadResource
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
     * PUT method for updating or creating an instance of ContabilidadResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("plan-cuentas/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getPlanCuentasAll(final RestRequest request) {

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

                        List<PlanCuentas> l = ejbPlanCuentas.get(u.getIdEmpleado().getIdEmpresa());
                        List<PlanCuentasJSON> json = getPlanCuentasJSon(l);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(json);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), request.getFormName(), "");

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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("plan-cuentas/combo")
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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;
    }

    @POST
    @Path("plan-cuentas/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse savePlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        PlanCuentasJSON pc = new PlanCuentasJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                        PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);
                        ejbPlanCuentas.insert(pcu);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.INSERT, t.getUserName(), request.getFormName(), "");

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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    @POST
    @Path("plan-cuentas/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        PlanCuentasJSON pc = new PlanCuentasJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                        PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);
                        ejbPlanCuentas.update(pcu);

                        ejbLogger.add(Accion.UPDATE, t.getUserName(), request.getFormName(), "");

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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;
    }

    @POST
    @Path("plan-cuentas/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deletePlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        //TODO
                        // Hay q revisar que no existan transacciones para la cuenta
                        //en la Eliminacion
                        PlanCuentasJSON pc = new PlanCuentasJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                        PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);
                        ejbPlanCuentas.remove(pcu);

                        ejbLogger.add(Accion.DELETE, t.getUserName(), request.getFormName(), "");

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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    @POST
    @Path("plan-cuentas/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getPlanCuentas(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        PlanCuentasJSON pc = new PlanCuentasJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                        PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);

                        pcu = (PlanCuentas) ejbPlanCuentas.get(pcu);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(PlanCuentasJSON.createJson(pcu));

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
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param l
     * @return
     */
    private List<PlanCuentasJSON> getPlanCuentasJSon(List<PlanCuentas> l) {

        List<PlanCuentasJSON> lista = new ArrayList<>();

        for (PlanCuentas p : l) {
            if (lista.isEmpty()) {
                lista.add(PlanCuentasJSON.createJson(p));
            } else {
                if (p.getIdPlanCuentaPadre() == null) {
                    lista.add(PlanCuentasJSON.createJson(p));
                } else {
                    getPlanCuentasJSon(lista, p);
                }
            }
        }
        return lista;
    }

    /**
     *
     * @param l
     * @param pc
     * @return
     */
    private void getPlanCuentasJSon(List<PlanCuentasJSON> l, PlanCuentas pc) {

        for (PlanCuentasJSON json : l) {
            if (json.getIdPlanCuentas().equals(pc.getIdPlanCuentaPadre())) {
                json = PlanCuentasJSON.addCuenta(pc, json);
                break;
                //return l;
            } else {
                String padre = pc.getIdPlanCuentaPadre().toString();
                String cuenta = json.getIdPlanCuentas().toString();
                if (padre.startsWith(cuenta)) {
                    getPlanCuentasJSon(json.getChildren(), pc);
                    break;
                }
            }

        }

    }

}
