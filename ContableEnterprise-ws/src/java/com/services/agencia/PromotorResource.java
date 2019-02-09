/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.PromotorRemote;
import com.agencia.entities.ComisionPromotorAerolinea;
import com.agencia.entities.Promotor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.ComisionPromotorAerolineaJSON;
import com.response.json.agencia.PromotorJSON;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.util.resource.Mensajes;
import com.view.menu.Formulario;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("promotores")
public class PromotorResource extends TemplateResource {

    @EJB
    private PromotorRemote ejbPromotor;

    /**
     * Creates a new instance of PromotorResource
     */
    public PromotorResource() {
    }

    @GET
    @Path("combo/all-counters/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllCounterCombo(@PathParam("idEmpresa") Integer idEmpresa) {
        RestResponse response = new RestResponse();
        try {

            Optional op = Optional.ofNullable(idEmpresa);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            List l = ejbPromotor.get("Promotor.comboAllCounter", Promotor.class);
            List r = new LinkedList();

            l.forEach((x) -> {
                Promotor p = (Promotor) x;
                ComboSelect s = new ComboSelect();
                s.setId(p.getIdPromotor());
                s.setName(p.getNombre() + " " + p.getApellido());

                r.add(s);
            });

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(PromotorResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {
        Mensajes m = Mensajes.getMensajes();
        RestResponse r = new RestResponse();
        doValidations(request);
        try {
            /*Verificamos el ID Token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                User u = ejbUsuario.get(new User(t.getUserName()));

                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<Promotor> l = ejbPromotor.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.PROMOTORES, user.getIp());

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
            Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("all-promotor-combo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllClienteCombo(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {

                List<Promotor> l = ejbPromotor.get();
                List<ComboSelect> listCombo = new LinkedList<>();
                Iterator i = l.iterator();
                while (i.hasNext()) {
                    Promotor c = (Promotor) i.next();
                    ComboSelect combo = new ComboSelect();
                    combo.setId(c.getIdPromotor());
                    combo.setName(c.getNombre() + " " + c.getApellido());
                    listCombo.add(combo);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(listCombo);

            } catch (CRUDException ex) {
                Logger.getLogger(PromotorRemote.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }

        return response;
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse savePromotor(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                PromotorJSON ajson = new PromotorJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), PromotorJSON.class);

                Promotor pcu = PromotorJSON.toPromotor(ajson);
                pcu.setIdPromotor(ejbPromotor.insert(pcu));

                //Comisiones de Aerolineas Nacionales
                if (!ajson.getListComisionNacional().isEmpty()) {
                    Iterator i = ajson.getListComisionNacional().iterator();
                    while (i.hasNext()) {
                        ComisionPromotorAerolineaJSON aijson = (ComisionPromotorAerolineaJSON) i.next();
                        ComisionPromotorAerolinea aim = ComisionPromotorAerolineaJSON.toComisionPromotor(aijson);
                        aim.setIdPromotor(pcu.getIdPromotor());

                        ejbPromotor.insert(aim);
                    }
                }

                //comisiones de Aerolineas Internacionales
                if (!ajson.getListComisionInternacional().isEmpty()) {
                    Iterator i = ajson.getListComisionInternacional().iterator();
                    while (i.hasNext()) {
                        ComisionPromotorAerolineaJSON aijson = (ComisionPromotorAerolineaJSON) i.next();
                        ComisionPromotorAerolinea aim = ComisionPromotorAerolineaJSON.toComisionPromotor(aijson);
                        aim.setIdPromotor(pcu.getIdPromotor());

                        ejbPromotor.insert(aim);
                    }
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PromotorResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePromotor(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                PromotorJSON ajson = new PromotorJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), PromotorJSON.class);

                Promotor pcu = PromotorJSON.toPromotor(ajson);
                ejbPromotor.update(pcu);

                //se debe insertar los impuestos nuevos
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PromotorResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        return response;

    }

    @POST
    @Path("save-comision")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveComision(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                ComisionPromotorAerolineaJSON ajson = new ComisionPromotorAerolineaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), ComisionPromotorAerolineaJSON.class);

                ComisionPromotorAerolinea pcu = ComisionPromotorAerolineaJSON.toComisionPromotor(ajson);

                HashMap parameters = new HashMap<String, Object>();
                parameters.put("idPromotor", pcu.getIdPromotor());
                parameters.put("idAerolinea", pcu.getIdAerolinea());
                List exists = ejbPromotor.get("ComisionPromotorAerolinea.existComision", ComisionPromotorAerolinea.class, parameters);

                if (!exists.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_RECORD_EXISTS));

                    return response;

                }
                ejbPromotor.insert(pcu);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PromotorResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    @POST
    @Path("show-comision/{idPromotor}/{tipoAerolinea}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getComisionNacional(final RestRequest request,
            @PathParam("idPromotor") Integer idPromotor,
            @PathParam("tipoAerolinea") String tipoAerolinea) {
        Mensajes m = Mensajes.getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID Token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                User u = ejbUsuario.get(new User(t.getUserName()));

                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        if (idPromotor == null) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                            return r;
                        }

                        if (tipoAerolinea.isEmpty()) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                            return r;
                        }

                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("idPromotor", idPromotor);
                        parameters.put("tipoAerolinea", tipoAerolinea);

                        List<ComisionPromotorAerolinea> l = ejbPromotor.get("ComisionPromotorAerolinea.findComisiones", ComisionPromotorAerolinea.class, parameters);

                        List<ComisionPromotorAerolineaJSON> lreturn = ComisionPromotorAerolineaJSON.toAerolineaComision(l);
                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(lreturn);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.PROMOTORES, user.getIp());

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
            Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("delete-comision")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteComision(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                ComisionPromotorAerolineaJSON ajson = new ComisionPromotorAerolineaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), ComisionPromotorAerolineaJSON.class);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", ajson.getIdComisionPromotor());

                ejbPromotor.remove(Queries.DELETE_COMISION_PROMOTOR_AEROLINEA, parameters);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.DELETE, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(PromotorResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }
}
