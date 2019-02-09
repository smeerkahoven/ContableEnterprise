/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.BancosRemote;
import com.agencia.entities.Bancos;
import com.agencia.entities.CuentaBanco;
import com.contabilidad.entities.PlanCuentas;
import com.contabilidad.remote.PlanCuentasRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.BancoJSON;
import com.response.json.CuentaBancoJSON;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.TemplateResource;
import com.services.seguridad.EmpresaServices;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.util.resource.Mensajes;
import com.view.menu.Formulario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
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
@Path("bancos")
public class BancosResource extends TemplateResource {

    @EJB
    private BancosRemote ejbBancos;

    @EJB
    private PlanCuentasRemote ejbPlanCuentas;

    /**
     * Creates a new instance of TarjetasCreditoResource
     */
    public BancosResource() {
    }

    @GET
    @Path("combo/all")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getComboAll() {
        RestResponse response = new RestResponse();
        try {

            List l = ejbBancos.getCombo();

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(l);

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BancosResource.class.getName()).log(Level.SEVERE, null, ex);
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

                        List<Bancos> l = ejbBancos.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.BANCOS, user.getIp());

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

    @GET
    @Path("getBancosCuentas/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getBancosCuentas(@PathParam("idEmpresa") Integer idEmpresa) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(idEmpresa);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("1", idEmpresa);

            List l = ejbBancos.getNative(Queries.GET_BANCOS_CUENTAS_EMPRESA, parameters);
            if (l.isEmpty()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                return response;
            }

            List r = new LinkedList();
            l.forEach((x) -> {
                ComboSelect s = new ComboSelect();
                Object[] o = (Object[]) x;
                s.setId((Integer) o[0]);
                s.setName((String) o[1]);
                r.add(s);
            });

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            Logger.getLogger(BancosResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("combo/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getCombo(final RestRequest request,
            @PathParam("idEmpresa") final Integer idEmpresa) {

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

                        // Este plan de cuenta ya debe estar creado
                        PlanCuentas p = new PlanCuentas();
                        p.setCuenta("BANCOS");

                        List<PlanCuentas> l = ejbPlanCuentas.getForCombo(new Empresa(idEmpresa), p);

                        ArrayList hm = new ArrayList();
                        for (PlanCuentas o : l) {
                            ComboSelect cm = new ComboSelect();
                            cm.setId(o.getIdPlanCuentas());
                            cm.setName(o.getCuenta());
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
    public RestResponse save(final RestRequest request) {

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

                        BancoJSON json = new BancoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), BancoJSON.class);

                        Bancos b = BancoJSON.toBancos(json);
                        ejbBancos.insert(b);

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
    public RestResponse update(final RestRequest request) {

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

                        BancoJSON json = new BancoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), BancoJSON.class);

                        Bancos pcu = BancoJSON.toBancos(json);
                        ejbBancos.update(pcu);

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

    @POST
    @Path("delete/{idBanco}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteBancos(final RestRequest request,
            @PathParam("idBanco") Integer idBanco
    ) {

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

                        if (idBanco == null) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                            return r;
                        }

                        Bancos pcu = new Bancos(idBanco);
                        if (ejbBancos.hasCuentas(pcu)) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.BANCO_CUENTA_RELACION));
                            return r;
                        }

                        ejbBancos.remove(pcu);

                        ejbLogger.add(Accion.DELETE, t.getUserName(), request.getFormName(), user.getIp());

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
    @Path("delete-link/{idCuentaBanco}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteLink(final RestRequest request,
            @PathParam("idCuentaBanco") final Integer idCuentaBanco) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        if (idCuentaBanco == null) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                            return r;
                        }

                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("1", idCuentaBanco);

                        ejbBancos.remove(Queries.DELETE_CUENTA_BANCO, parameters);

                        ejbLogger.add(Accion.DELETE, t.getUserName(), request.getFormName(), user.getIp());

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
    @Path("link-grid")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getLinkGrid(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));

                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        BancoJSON json = new BancoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), BancoJSON.class);

                        Bancos b = BancoJSON.toBancos(json);

                        List<Bancos> l = ejbBancos.get(b, Queries.GET_BANCO_CUENTA_LINK);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(),Formulario.BANCOS, user.getIp());

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
    @Path("add-link")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse addLink(final RestRequest request) {

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

                        CuentaBancoJSON json = new CuentaBancoJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), CuentaBancoJSON.class);

                        CuentaBanco b = CuentaBancoJSON.toCuentaBanco(json);

                        ejbBancos.insert(b);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.BANCOS, user.getIp());

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
