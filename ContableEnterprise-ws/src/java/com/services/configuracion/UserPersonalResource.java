/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.configuracion;

import com.response.json.seguridad.UserPersonalJSON;
import com.seguridad.control.ejb.EmpleadoEJB;
import com.seguridad.control.ejb.UsuarioEJB;
import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.User;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.PersonalResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import javax.ejb.EJB;
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
@Path("user-personal")
public class UserPersonalResource extends TemplateResource {

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private EmpleadoRemote ejbEmpleado;

    /**
     * Creates a new instance of UserPersonalResource
     */
    public UserPersonalResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.configuracion.UserPersonalResource
     *
     * @param request
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getUserPersonal(final RestRequest request) {

        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                UserPersonalJSON u = ejbUsuario.getUsuario(user);
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(u);
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("update-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePassword(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                UserPersonalJSON u = BeanUtils.convertoToUserPersonalJson(request);
                User userDto = UserPersonalJSON.toUser(u);
                //update password
                ejbUsuario.updatePassword(userDto);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.UPDATE, user.getUserName(), com.view.menu.Formulario.CONFIGURACION_USER, user.getIp(), "Update Password");

            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        return response;
    }

    @POST
    @Path("update-info")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateInfo(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                UserPersonalJSON u = BeanUtils.convertoToUserPersonalJson(request);
                Empleado emp = UserPersonalJSON.toEmpleado(u);
                //update password
                ejbEmpleado.updateInfo(emp);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.UPDATE, user.getUserName(), com.view.menu.Formulario.CONFIGURACION_USER, user.getIp(), "Update Info");
            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        return response;
    }

    /**
     * PUT method for updating or creating an instance of UserPersonalResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
