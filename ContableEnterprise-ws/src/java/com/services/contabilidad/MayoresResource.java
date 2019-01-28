/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.agencia.search.dto.MayoresSearch;
import com.contabilidad.remote.MayoresRemote;
import com.response.json.boletaje.GridMayores;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("mayores")
public class MayoresResource extends TemplateResource {

    @EJB
    private MayoresRemote ejbMayores;

    /**
     * Creates a new instance of MayoresResource
     */
    public MayoresResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.MayoresResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MayoresResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("find")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllPagosAnticipados(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                MayoresSearch search = BeanUtils.convertToMayoresSearchJson(request);
                search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                GridMayores grid = ejbMayores.getGridMayores(search);

                if (grid.getMayores().isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BUSQUEDA_VACIA));
                } else {

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(grid);
                }

                ejbLogger.add(Accion.SEARCH, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp());

            } catch (Exception ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getCause());
                Logger.getLogger(MayoresResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;
    }
}
