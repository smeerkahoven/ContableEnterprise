/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.AerolineaRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.AerolineaImpuesto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.AerolineaCuentaJSON;
import com.response.json.agencia.ComisionPromotorAerolineaJSON;
import com.response.json.agencia.AerolineaImpuestoJSON;
import com.response.json.agencia.AerolineaJSON;
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
import com.util.resource.Mensajes;
import com.view.menu.Formulario;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("aerolinea")
public class AerolineaResource extends TemplateResource {

    @EJB
    private AerolineaRemote ejbAerolinea;

    /**
     * Creates a new instance of AerolineaResource
     */
    public AerolineaResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.AerolineaResource
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
     * PUT method for updating or creating an instance of AerolineaResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    public RestResponse getAerolina(@PathParam("id") Integer id) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(id);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            Aerolinea a = (Aerolinea) ejbAerolinea.getAerolinea(new Aerolinea(id));

            op = Optional.ofNullable(a);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_RECORD_EXISTS));
                return response;
            }
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(a);
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("all-combo/{moneda}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAerolineaCombo(final RestRequest request, @PathParam("moneda") String moneda) {
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
                        if (moneda.isEmpty()) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                            return r;
                        }

                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("moneda", moneda);
                        List<Object[]> l = ejbAerolinea.get("Aerolinea.findForCombo", Aerolinea.class, parameters);

                        List<ComisionPromotorAerolineaJSON> lcomsion = ComisionPromotorAerolineaJSON.toNewAerolineaComision(l);
                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(lcomsion);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.AEROLINEA, user.getIp());

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
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAerolineaAll(final RestRequest request) {
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

                        List<Aerolinea> l = ejbAerolinea.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.AEROLINEA, user.getIp());

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
    @Path("combo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse getCombo(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                List l = ejbAerolinea.getCombo();
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(l);
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getCause());
            }
        }
        return response;
    }

    @POST
    @Path("all-impuestos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAerolineaImpuetosAll(final RestRequest request) {
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

                        AerolineaJSON ajson = new AerolineaJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        ajson = gson.fromJson(object.toString(), AerolineaJSON.class);

                        Aerolinea pcu = AerolineaJSON.toAerolinea(ajson);

                        List<AerolineaImpuesto> l = ejbAerolinea.get(pcu);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(l);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.AEROLINEA, user.getIp());

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
    @Path("get-cuentas/{idAerolinea}/{tipo}/{moneda}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAerolineaCuenta(final RestRequest request,
            @PathParam("idAerolinea") final Integer idAerolinea,
            @PathParam("tipo") final String tipo,
            @PathParam("moneda") final String moneda) {
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

                        if (idAerolinea == null) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));

                            return r;
                        }

                        if (tipo.isEmpty()) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));

                            return r;
                        }

                        if (moneda.isEmpty()) {
                            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                            r.setContent(m.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));

                            return r;
                        }

                        HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("1", idAerolinea);
                        parameters.put("2", tipo);
                        parameters.put("3", moneda);

                        List<Object[]> l = ejbAerolinea.getNative(Queries.GET_AEROLINA_CUENTA, parameters);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        //r.setCode(u.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                        r.setContent(AerolineaCuentaJSON.toAerolinaJSON(l));

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.AEROLINEA, user.getIp());

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
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveAerolinea(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                AerolineaJSON ajson = new AerolineaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), AerolineaJSON.class);

                Aerolinea pcu = AerolineaJSON.toAerolinea(ajson);
                pcu.setIdAerolinea(ejbAerolinea.insert(pcu));

                //se debe insertar los impuestos nuevos
                if (!ajson.getAerolineaImpuestoList().isEmpty()) {
                    Iterator i = ajson.getAerolineaImpuestoList().iterator();
                    while (i.hasNext()) {
                        AerolineaImpuestoJSON aijson = (AerolineaImpuestoJSON) i.next();
                        AerolineaImpuesto aim = AerolineaImpuestoJSON.toAerolineaImpuesto(aijson);
                        aim.setIdAerolinea(pcu.getIdAerolinea());

                        ejbAerolinea.insert(aim);
                    }
                }

                //se insertan las cuentas
                insertCuentas(ajson.getListCtaVentasMonExt(), pcu);
                insertCuentas(ajson.getListCtaVentasMonNac(), pcu);
                insertCuentas(ajson.getListCtaComisionMonExt(), pcu);
                insertCuentas(ajson.getListCtaComisionMonNac(), pcu);
                insertCuentas(ajson.getListCtaDevolucionMonExt(), pcu);
                insertCuentas(ajson.getListCtaDevolucionMonNac(), pcu);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    private void insertCuentas(List<AerolineaCuentaJSON> l, final Aerolinea pcu) throws CRUDException {
        if (!l.isEmpty()) {
            Iterator i = l.iterator();
            while (i.hasNext()) {
                AerolineaCuentaJSON aijson = (AerolineaCuentaJSON) i.next();
                AerolineaCuenta aim = AerolineaCuentaJSON.toAerolinea(aijson);
                aim.setIdAerolinea(pcu.getIdAerolinea());

                ejbAerolinea.insert(aim);
            }
        }

    }

    @POST
    @Path("save-cuenta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveAerolineaCuenta(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaCuentaJSON ajson = new AerolineaCuentaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                System.out.println((String) request.getContent());
                ajson = gson.fromJson(object.toString(), AerolineaCuentaJSON.class);

                AerolineaCuenta pcu = AerolineaCuentaJSON.toAerolinea(ajson);

                HashMap<String, Object> parameteres = new HashMap<>();
                parameteres.put("idAerolinea", pcu.getIdAerolinea());
                parameteres.put("idPlanCuentas", pcu.getIdPlanCuentas());
                parameteres.put("moneda", pcu.getMoneda());
                parameteres.put("tipo", pcu.getTipo());

                List<AerolineaCuenta> exist = ejbAerolinea.get("AerolineaCuenta.exists", AerolineaCuenta.class, parameteres);
                if (!exist.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_RECORD_EXISTS));
                    return response;
                }

                ejbAerolinea.insert(pcu);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());

            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return response;
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateAerolinea(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaJSON ajson = new AerolineaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), AerolineaJSON.class);

                Aerolinea pcu = AerolineaJSON.toAerolinea(ajson);
                ejbAerolinea.update(pcu);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    @POST
    @Path("update-impuestos-insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateOtrosImpuestos(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaImpuestoJSON ajson = new AerolineaImpuestoJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), AerolineaImpuestoJSON.class);

                AerolineaImpuesto pcu = AerolineaImpuestoJSON.toAerolineaImpuesto(ajson);
                Integer id = ejbAerolinea.insert(pcu);
                
                pcu.setIdAerolineaImpuesto(id);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(pcu);

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse delete(final RestRequest request
    ) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaJSON ajson = new AerolineaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), AerolineaJSON.class);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", ajson.getIdAerolinea());

                ejbAerolinea.remove(Queries.DELETE_AEROLINEA, parameters);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response;

    }

    @POST
    @Path("impuesto-delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteOtrosImpuestos(final RestRequest request
    ) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaImpuestoJSON ajson = new AerolineaImpuestoJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), AerolineaImpuestoJSON.class);

                AerolineaImpuesto pcu = new AerolineaImpuesto(ajson.getIdAerolineaImpuesto());
                ejbAerolinea.remove(pcu, Queries.DELETE_IMPUESTO_AEROLINEA);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;

    }

    @POST
    @Path("delete-cuenta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteCuenta(final RestRequest request
    ) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                AerolineaCuentaJSON ajson = new AerolineaCuentaJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), AerolineaCuentaJSON.class);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", ajson.getIdAerolineaCuenta());

                ejbAerolinea.remove(Queries.DELETE_AEROLINEA_CUENTA, parameters);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(AerolineaResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response;

    }
}
