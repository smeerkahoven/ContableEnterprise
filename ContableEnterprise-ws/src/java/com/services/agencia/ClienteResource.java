/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.agencia;

import com.agencia.control.remote.ClienteRemote;
import com.agencia.entities.Cliente;
import com.agencia.entities.ClienteGrupo;
import com.agencia.entities.ClienteSolicitado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.ClienteJSON;
import com.response.json.agencia.ClienteSolicitadoJSON;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@Path("clientes")
public class ClienteResource extends TemplateResource {

    @EJB
    private ClienteRemote ejbCliente;

    /**
     * Creates a new instance of ClienteResource
     */
    public ClienteResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.agencia.ClienteResource
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
     * PUT method for updating or creating an instance of ClienteResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                response.setContent(ejbCliente.get());
            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }

        return response;
    }
    
    @GET
    @Path("get/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getClienteId(@PathParam("idCliente") Integer idCliente){
         RestResponse response = new RestResponse();
        try {
           
            Optional op = Optional.ofNullable(idCliente);
            if (!op.isPresent()){
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response ;
            }
            
            
            Cliente c = (Cliente) ejbCliente.get(new Cliente(idCliente));
            
            op = Optional.ofNullable(c);
            if (!op.isPresent()){
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                return response ;
            }
            
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(c);
            
        } catch (CRUDException ex) {
            Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response ;
    }

    @POST
    @Path("all-cliente-combo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllClienteCombo(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {

                List<Cliente> l = ejbCliente.getAllClienteCombo();
                List<ComboSelect> listCombo = new LinkedList<>();
                Iterator i = l.iterator();
                while (i.hasNext()) {
                    Cliente c = (Cliente) i.next();
                    ComboSelect combo = new ComboSelect();
                    combo.setId(c.getIdCliente());
                    combo.setName(c.getNombre());
                    listCombo.add(combo);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(listCombo);

            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }

        return response;
    }

    @POST
    @Path("all-tipo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllTipo(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                List<ClienteGrupo> l = ejbCliente.get("ClienteGrupo.findAll", ClienteGrupo.class);
                List<ComboSelect> list = new LinkedList<>();

                Iterator i = l.iterator();
                while (i.hasNext()) {
                    ClienteGrupo c = (ClienteGrupo) i.next();

                    ComboSelect select = new ComboSelect();
                    select.setId(c.getIdClienteGrupo());
                    select.setName(c.getNombre());

                    list.add(select);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(list);
            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }

        return response;
    }

    @POST
    @Path("get-solicitados/{idCliente}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getSolicitados(final RestRequest request, @PathParam("idCliente") final Integer idCliente) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                if (idCliente == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }
                
                HashMap<String,Object> parameters = new HashMap<String,Object>();
                parameters.put("idCliente", idCliente);
                
                List<ClienteSolicitado> list = ejbCliente.get("ClienteSolicitado.getAllFromClient", ClienteSolicitado.class ,parameters);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(list);

            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("save-tipo/{nombre}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveTipoCliente(final RestRequest request, @PathParam("nombre") final String nombre) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                if (nombre.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }
                ClienteGrupo g = new ClienteGrupo();
                g.setNombre(nombre);

                ejbCliente.insert(g);

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("delete/{idCliente}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteCliente(final RestRequest request, @PathParam("idCliente") final Integer idCliente) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                if (idCliente == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", idCliente);

                ejbCliente.remove(Queries.DELETE_CLIENTE, parameters);

                ejbLogger.add(Accion.DELETE, user.getUserName(), request.getFormName(), user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("delete-solicitado/{idClienteSolicitado}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse deleteClienteSolicitado(final RestRequest request, @PathParam("idClienteSolicitado") final Integer idClienteSolicitado) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                if (idClienteSolicitado == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", idClienteSolicitado);

                ejbCliente.remove(Queries.DELETE_CLIENTE_SOLICITADO, parameters);

                ejbLogger.add(Accion.DELETE, user.getUserName(), request.getFormName(), user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveCliente(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                ClienteJSON ajson = new ClienteJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), ClienteJSON.class);

                Cliente newCliente = ClienteJSON.toCliente(ajson);
                newCliente.setIdCliente(ejbCliente.insert(newCliente));

                // se insertan los solicitados
                if (!ajson.getListSolicitadoPor().isEmpty()) {
                    Iterator i = ajson.getListSolicitadoPor().iterator();
                    while (i.hasNext()) {
                        ClienteSolicitadoJSON csjson = (ClienteSolicitadoJSON) i.next();
                        ClienteSolicitado cnew = ClienteSolicitadoJSON.toClienteSolicitado(csjson);
                        cnew.setIdCliente(newCliente.getIdCliente());

                        ejbCliente.insert(cnew);
                    }
                }

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateCliente(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                ClienteJSON ajson = new ClienteJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), ClienteJSON.class);

                Cliente newCliente = ClienteJSON.toCliente(ajson);

                ejbCliente.update(newCliente);

                ejbLogger.add(Accion.UPDATE, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }

    @POST
    @Path("save-solicitado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveSolicitado(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                ClienteSolicitado ajson = new ClienteSolicitado();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                ajson = gson.fromJson(object.toString(), ClienteSolicitado.class);

                ejbCliente.insert(ajson);

                ejbLogger.add(Accion.INSERT, user.getUserName(), request.getFormName(), user.getIp());
            } catch (CRUDException ex) {
                Logger.getLogger(ClienteResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return response;
    }
}
