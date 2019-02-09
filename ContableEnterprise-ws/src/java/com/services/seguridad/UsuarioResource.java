/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad;

import com.configuracion.entities.Parametros;
import com.configuracion.remote.ParametrosRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.UsuarioJSON;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.PasswordRecover;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import com.seguridad.control.remote.RolRemoto;
import com.seguridad.control.remote.SendMailRemote;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.TemplateResource;
import com.services.resource.TokenService;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.util.resource.Mensajes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.mail.MessagingException;
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
@Path("usuario")
public class UsuarioResource extends TemplateResource {

    @EJB
    private EmpleadoRemote ejbEmpleado;

    @EJB
    private RolRemoto ejbRol;

    @EJB
    private SendMailRemote ejbSendMail;

    @EJB
    private ParametrosRemote ejbParametros;

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.seguridad.UsuarioResource
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
     * PUT method for updating or creating an instance of UsuarioResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Path("personal")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getSucursales(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        List<Object[]> l = ejbEmpleado.getForCombo();
                        ArrayList lst = new ArrayList();
                        Iterator i = l.iterator();
                        while (i.hasNext()) {
                            Object[] emp = (Object[]) i.next();
                            ComboSelect c = new ComboSelect();
                            c.setId(emp[0]);
                            c.setName((String) emp[1] + " " + (String) emp[2]);
                            lst.add(c);
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(lst);

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
    @Path("roles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getRoles(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        List<Object[]> l = ejbRol.getForCombo();
                        ArrayList lst = new ArrayList();
                        Iterator i = l.iterator();
                        while (i.hasNext()) {
                            Object[] emp = (Object[]) i.next();
                            ComboSelect c = new ComboSelect();
                            c.setId(emp[0]);
                            c.setName((String) emp[1]);
                            lst.add(c);
                        }

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(lst);

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
    @Path("guardar")
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

                        UsuarioJSON e = new UsuarioJSON();

                        Gson gson = new GsonBuilder().create();
                        JsonParser parser = new JsonParser();
                        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                        e = gson.fromJson(object.toString(), UsuarioJSON.class);

                        User user = UsuarioJSON.convertoToUsuario(e);
                        user.setFechaAlta(DateContable.getCurrentDate());
                        ejbUsuario.insert(user);

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
            LoggerContable.log(ex.getMessage(), this, Level.SEVERE);

            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

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

                        HashMap<String, Object> hmap = (HashMap<String, Object>) request.getContent();
                        String username = (String) hmap.get("username");
                        String comando = (String) hmap.get("comando");
                        String estado = (String) hmap.get("status");
                        BigDecimal idRol = (BigDecimal) hmap.get("idRol");

                        User u = new User();
                        u.setUserName(username);
                        u.setStatus(estado);
                        u.setIdRol(new Rol(idRol.intValue()));

                        ejbUsuario.update(u, comando);

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
            LoggerContable.log(ex.getMessage(), this, Level.SEVERE);

            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {

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

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(UsuarioJSON.convertToJSON(ejbUsuario.get()));

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

            ejbLogger.add(Accion.SEARCH, user.getUserName(),
                    com.view.menu.Formulario.USUARIO, user.getIp());

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
    @Path("combo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllCombo(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            List<User> luser = ejbUsuario.get();
            List returnList = new LinkedList();
            if (!luser.isEmpty()) {

                for (User u : luser) {
                    ComboSelect c = new ComboSelect();
                    c.setId(u.getUserName());
                    c.setName(u.getUserName());
                    returnList.add(c);
                }
            }

            response.setContent(returnList);
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            return response;

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;

    }

    @POST
    @Path("validate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getValidate(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                HashMap<String, Object> hmap = (HashMap<String, Object>) request.getContent();
                User ux = new User((String) hmap.get("username"));
                User aux = ejbUsuario.get(ux);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

                if (aux == null) {
                    response.setContent(mensajes.getProperty(Status.USER_AVAILABLE));
                    response.setCode(ResponseCode.USUARIO_AVAILABLE.getCode());
                } else {
                    response.setContent(mensajes.getProperty(Status.USER_EXISTS));
                    response.setCode(ResponseCode.USUARIO_EXISTS.getCode());
                }

            } catch (CRUDException ex) {
                Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response;
    }

    @POST
    @Path("recover-pwd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePassword(final RestRequest request) {
        RestResponse response = new RestResponse();
        try {

            User u = new User();

            HashMap<String, Object> parametros = (HashMap<String, Object>) request.getContent();
            u.setUserName((String) parametros.get("usr"));
            u.setPassword((String) parametros.get("pwd"));

            ejbUsuario.updatePassword(u);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

            return response;
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    private String password_content = "<table style=\"height: 428px; width: 100%;\">\n"
            + "<tbody>\n"
            + "<tr style=\"height: 71px;\">\n"
            + "<td style=\"background-color: #346ad8; color: white; text-align: center;\">\n"
            + "<h2><strong>Setricon - Recuperaci&oacute;n de Contrase&ntilde;a</strong></h2>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr style=\"height: 345px;\">\n"
            + "<td>\n"
            + "<p>Hola <strong>[usuario]</strong>,</p>\n"
            + "<p>Queremos hacerte saber que has solicitado un cambio de contrase&ntilde;a.</p>\n"
            + "<p>Para realizar dicho cambio, ingresa solamente una vez al siguiente enlace y actualiza tu contrase&ntilde;a.</p>\n"
            + "<h3><a title=\"Recupera tu Contrase&ntilde;a\" href=\"[url]\">"
            + "<span style=\"text-decoration: underline;\"><strong>Click Para Recuperar Contrase&ntilde;a</strong></span></a></h3>\n"
            + "<p>Si t&uacute; no realizaste esta solicitud, ignora el mensaje y desechalo de tu bandeja.</p>\n"
            + "<p>Por favor no respondas a este email con tu contrase&ntilde;a, ya que nosotros <strong>nunca te pediremos</strong> que nos env&iacute;es tus datos personales por este medio y recomendarte que nunca compartas esa informaci&oacute;n con nadie.</p>\n"
            + "<p>Atte:</p>\n"
            + "<p>Soporte Serticon.</p>\n"
            + "</td>\n"
            + "</tr>\n"
            + "<tr>\n"
            + "<td style=\"height: 71px; background-color: #c5cee0;\">&nbsp;</td>\n"
            + "</tr>\n"
            + "</tbody>\n"
            + "</table>";

    @POST
    @Path("send-recover-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse sendRecoverPassword(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();

                String username = object.get("username").getAsString();
                if (username == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }
                //usuario to
                User toUser = ejbUsuario.get(new User(username));
                if (toUser == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                    return response;
                }

                if (toUser.getIdEmpleado().getEmail() == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_INVALID_MAIL));
                    return response;
                }

                User fromUser = ejbUsuario.get(new User(user.getUserName()));
                Parametros url = (Parametros) ejbParametros.get(new Parametros(Parametros.URL_RECOVER_PASSWORD));
                if (url == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent("URL Recover Password:" + mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                    return response;
                }

                String content = password_content.replace("[usuario]", toUser.getIdEmpleado().getNombre());
                TokenService token = new TokenService(username);
                String idPasswordRecover = token.getToken(username);

                content = content.replace("[url]", url.getValor());
                content = content.replace("[id]", idPasswordRecover);

                PasswordRecover recover = new PasswordRecover();
                recover.setIdPasswordRecover(idPasswordRecover);
                recover.setEstado(PasswordRecover.ACTIVO);
                recover.setFecha(DateContable.getCurrentDate());

                ejbEmpleado.insert(recover);

                ejbSendMail.sendMail(fromUser.getIdEmpleado().getEmail(),
                        toUser.getIdEmpleado().getEmail(), "Recuperacion de contraseña ", content);

                ejbLogger.add(Accion.INSERT, user.getUserName(), "Recover Password", user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCES_MAIL_SENT));
                return response;
            } catch (CRUDException | MessagingException ex) {
                Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_INVALID_MAIL));
                return response;
            }
        }
        return response;
    }
}
