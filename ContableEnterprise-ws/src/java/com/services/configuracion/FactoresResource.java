/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.configuracion;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.CambioUfv;
import com.configuracion.remote.CambioRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.CambioJSON;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.TemplateResource;
import com.services.seguridad.EmpresaServices;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.Mensajes;
import com.view.menu.Formulario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import jdk.nashorn.internal.parser.JSONParser;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("factores")
public class FactoresResource extends TemplateResource {

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private CambioRemote ejbCambio;

    @EJB
    private LoggerRemote ejbLogger;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FactoresResource
     */
    public FactoresResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.configuracion.FactoresResource
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
     * PUT method for updating or creating an instance of FactoresResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Path("dollar/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getDollaAll(final RestRequest request) {

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

                        List<CambioDolar> l = ejbCambio.get(new CambioDolar(), "CambioDolar.findAll");

                        Iterator i = l.iterator();
                        List<CambioJSON> list = new ArrayList<>();
                        while (i.hasNext()) {
                            CambioJSON aux = CambioJSON.toJSON((CambioDolar) i.next());
                            list.add(aux);
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(list);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, user.getIp());

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("dollar/today")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getDollarToday(final RestRequest request) {

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
                        Date d = DateContable.getCurrentDate();

                        //Date date = Date.from(LocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        LocalDate localDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(localDate.format(formatter));

                        CambioDolar dollarToday = (CambioDolar) ejbCambio.get(new CambioDolar(date));

                        if (dollarToday == null) {
                            r.setCode(ResponseCode.VALOR_DOLAR_NO_ESTABLECIDO.getCode());
                            r.setContent(RestResponse.VALOR_DOLAR_NO_ESTABLECIDO);

                            return r;
                        } else if (dollarToday.getValor().intValue() == 0) {
                            r.setCode(ResponseCode.VALOR_DOLAR_NO_ESTABLECIDO.getCode());
                            r.setContent(RestResponse.VALOR_DOLAR_NO_ESTABLECIDO);

                            return r;
                        }

                        CambioJSON today = CambioJSON.toJSON(dollarToday);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(today);

                        //ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, "");

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        } catch (ParseException ex) {
            Logger.getLogger(FactoresResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return r;
    }

    @POST
    @Path("dollar/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse dollarUpdate(final RestRequest request) {

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

                        CambioJSON json = new CambioJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), CambioJSON.class);

                        CambioDolar c = CambioJSON.toCambioDolar(json);

                        ejbCambio.update(c);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, user.getIp());

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("dollar/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse dollarSave(final RestRequest request) {

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

                        CambioJSON json = new CambioJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), CambioJSON.class);

                        CambioDolar c = CambioJSON.toCambioDolar(json);
                        c.setFecha(new Date());

                        ejbCambio.insert(c);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, user.getIp());

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("ufv/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse ufvUpdate(final RestRequest request) {

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

                        CambioJSON json = new CambioJSON();
                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        json = gson.fromJson(object.toString(), CambioJSON.class);

                        CambioUfv c = CambioJSON.toCambioUfv(json);

                        ejbCambio.update(c);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, user.getIp());

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("ufv/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getUFVAll(final RestRequest request) {

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

                        List<CambioUfv> l = ejbCambio.get(new CambioUfv(), "CambioUfv.findAll");

                        Iterator i = l.iterator();
                        List<CambioJSON> list = new ArrayList<>();
                        while (i.hasNext()) {
                            CambioJSON aux = CambioJSON.toJSON((CambioUfv) i.next());
                            list.add(aux);
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(list);

                        ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, user.getIp());

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }

    @POST
    @Path("ufv/today")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getUFVToday(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));

                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        CambioUfv ufvToday = (CambioUfv) ejbCambio.get(new CambioUfv(new Date()));

                        CambioJSON today ;
                        if (ufvToday != null) {
                            today = CambioJSON.toJSON(ufvToday);
                        }else {
                            today= new CambioJSON();
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(today);

                        //ejbLogger.add(Accion.ACCESS, t.getUserName(), Formulario.FACTORES, "");

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
            ex.printStackTrace();
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return r;
    }
}
