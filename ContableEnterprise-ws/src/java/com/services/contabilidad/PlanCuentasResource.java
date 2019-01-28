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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("contabilidad")
public class PlanCuentasResource extends TemplateResource {

    @EJB
    private PlanCuentasRemote ejbPlanCuentas;

    /**
     * Creates a new instance of ContabilidadResource
     */
    public PlanCuentasResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.PlanCuentasResource
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
     * PUT method for updating or creating an instance of PlanCuentasResource
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
    @Path("plan-cuentas/all/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getPlanCuentasAll(final RestRequest request,
            @PathParam("idEmpresa") final Integer idEmpresa) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                List<PlanCuentas> l = ejbPlanCuentas.get(new Empresa(idEmpresa));
                List<PlanCuentasJSON> json = getPlanCuentasJSon(l);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                response.setContent(json);

                ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.PLAN_CUENTAS, user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response;

    }

    /**
     *
     * @param request
     * @param idEmpresa
     * @return
     */
    @POST
    @Path("plan-cuentas/combo/{idEmpresa}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getCombo(final RestRequest request,
            @PathParam("idEmpresa") final Integer idEmpresa) {

        RestResponse response = doValidations(request);
        {
            try {
                List<Object[]> l = ejbPlanCuentas.getForCombo(new Empresa(idEmpresa));

                ArrayList hm = new ArrayList();
                for (Object[] o : l) {
                    ComboSelect cm = new ComboSelect();
                    cm.setId((Integer) o[0]);
                    cm.setName((String) o[1]);
                    cm.setComodin((String) o[2]);
                    hm.add(cm);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(hm);

                return response;
            } catch (CRUDException ex) {
                Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return response;

    }

    @POST
    @Path("plan-cuentas/combo-plan/{cuenta}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getComboPlan(final RestRequest request, @PathParam("cuenta") Integer cuenta) {

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
                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("1", cuenta);

                        List<PlanCuentas> l = ejbPlanCuentas.getNative(Queries.GET_PLAN_CUENTAS_NRO_PLANCUENTA, PlanCuentas.class, parameters);

                        ArrayList hm = new ArrayList();
                        for (PlanCuentas p : l) {
                            ComboSelect cm = new ComboSelect();
                            cm.setId(p.getIdPlanCuentas());
                            cm.setName(p.getCuenta());

                            hm.add(cm);
                        }

                        /*for (Object[] o : l) {
                            ComboSelect cm = new ComboSelect();
                            cm.setId(o[0]);
                            cm.setName((String) o[1] + "-" + (String) o[2] + "-" + (String) o[3]);
                            hm.add(cm);
                        }*/
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
    @Path("plan-cuentas/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse savePlanCuentas(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                PlanCuentasJSON pc = new PlanCuentasJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);
                ejbPlanCuentas.insert(pcu);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }

        }
        return response;

    }

    @POST
    @Path("plan-cuentas/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePlanCuentas(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                PlanCuentasJSON pc = new PlanCuentasJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);
                ejbPlanCuentas.update(pcu);

                ejbLogger.add(Accion.UPDATE, user.getUserName(), request.getFormName(), user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            } catch (CRUDException ex) {
                Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;
    }

    @POST
    @Path("plan-cuentas/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deletePlanCuentas(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                //TODO
                // Hay q revisar que no existan transacciones para la cuenta
                //en la Eliminacion
                PlanCuentasJSON pc = new PlanCuentasJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                pc = gson.fromJson(object.toString(), PlanCuentasJSON.class);

                //TODO
                // Antes de hacer esta eliminacion, se debe verificar las relaciones
                //con las otras tablas
                PlanCuentas pcu = PlanCuentasJSON.toPlanCuentas(pc);

                ejbPlanCuentas.remove(pcu);

                ejbLogger.add(Accion.DELETE, user.getUserName(), request.getFormName(), user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            } catch (CRUDException ex) {
                Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    @POST
    @Path("plan-cuentas/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getPlanCuentas(final RestRequest request) {

        Queries queries = Queries.getQueries();

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

                        HashMap parameters = new HashMap();
                        parameters.put("1", pcu.getNroPlanCuentaPadre());
                        parameters.put("2", pcu.getIdEmpresa());

                        List pcPadre = ejbPlanCuentas.getNative(Queries.GET_PLAN_CUENTA_PADRE, PlanCuentas.class, parameters);
                        PlanCuentasJSON returnValue = PlanCuentasJSON.createJson(pcu);

                        if (!pcPadre.isEmpty()) {
                            PlanCuentas o = (PlanCuentas) pcPadre.get(0);
                            returnValue.setNroPlanCuentaPadreNombre(o.getCuenta());
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(returnValue);

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
            Logger.getLogger(PlanCuentasResource.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

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
                if (p.getNroPlanCuentaPadre() == null) {
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
            if (json.getNroPlanCuenta().equals(pc.getNroPlanCuentaPadre())) {
                json = PlanCuentasJSON.addCuenta(pc, json);
                break;
                //return l;
            } else {
                String padre = pc.getNroPlanCuentaPadre().toString();
                String cuenta = json.getNroPlanCuenta().toString();
                if (padre.startsWith(cuenta)) {
                    getPlanCuentasJSon(json.getChildren(), pc);
                    break;
                }
            }

        }

    }

}
