/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.NotasCreditoRemote;
import com.response.json.boletaje.NotaCreditoSearchJson;
import com.response.json.contabilidad.NotaCreditoJson;
import com.response.json.contabilidad.NotaCreditoTransaccionJson;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import com.view.menu.Formulario;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
@Path("notas-credito")
public class NotasCreditoResource extends TemplateResource {

    @EJB
    private NotasCreditoRemote ejbNotaCredito;

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    /**
     * Creates a new instance of NotasCreditoResource
     */
    public NotasCreditoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.NotasCreditoResource
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
     * PUT method for updating or creating an instance of NotasCreditoResource
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
    public RestResponse getAllIngresosCaja(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {

            NotaCreditoSearchJson search = BeanUtils.convertoToNotaCreditoSearchJson(request);
            search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

            List<NotaCredito> list = ejbNotaCredito.findAllNotaCredito(search);

            List<NotaCreditoJson> r = new LinkedList<>();

            for (NotaCredito ic : list) {
                r.add(NotaCreditoJson.toNotaCreditoJson(ic));
            }

            if (list.isEmpty()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BUSQUEDA_VACIA));
            } else {

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(r);
            }

            ejbLogger.add(Accion.SEARCH, user.getUserName(),
                    com.view.menu.Formulario.NOTA_CREDITO, user.getIp());

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

            /*String mensaje = Logger.NOTA_DEBITO_CARGO_GUARDAR.replace("<cargo>", cargo.getIdCargo().toString());
                    mensaje = mensaje.replace("<nota>", cargo.getIdNotaDebito().toString());
             */
            // ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);
        }

        return response;
    }

    @POST
    @Path("get/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCreditoTransaccionJson json = BeanUtils.convertToNotaCreditoTransaccionJson(request);

                NotaCreditoTransaccion data = NotaCreditoTransaccionJson.toNotaCreditoTransaccion(json);

                data = ejbNotaCredito.getNotaCreditoTransaccion(data);

                json = NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(data);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("all/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllTransacciones(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCreditoJson json = BeanUtils.convertToNotaCreditoJson(request);

                List<NotaCreditoTransaccion> list = ejbNotaCredito.findAllNotaCreditoTransacciones(json.getIdNotaCredito());

                if (list.isEmpty()) {
                    response.setContent("No se encontraron Transacciones para la Nota de Credito %s".replace("%s", json.getIdNotaCredito().toString()));
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    return response;
                }

                List<NotaCreditoTransaccionJson> r = new LinkedList<>();
                for (NotaCreditoTransaccion i : list) {
                    NotaCreditoTransaccionJson trJson = NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(i);
                    r.add(trJson);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(r);

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("save/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCreditoTransaccionJson json = BeanUtils.convertToNotaCreditoTransaccionJson(request);

                NotaCreditoTransaccion trx = NotaCreditoTransaccionJson.toNotaCreditoTransaccion(json);

                trx = ejbNotaCredito.saveTransaccion(trx, user.getUserName());

                NotaCreditoJson tmpJson = NotaCreditoJson.toNotaCreditoJson((NotaCredito) ejbNotaCredito.get(trx.getIdNotaCredito().getIdNotaCredito(), NotaCredito.class));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(tmpJson);

                String mensaje = Log.NOTA_CREDITO_NUEVA_TRANSACION.replace("trx", trx.getIdNotaCreditoTransaccion().toString());
                mensaje = mensaje.replace("<id>", trx.getIdNotaCredito().getIdNotaCredito().toString());
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_CREDITO, user.getIp(), mensaje);

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }

        }

        return response;
    }

    @POST
    @Path("update/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCreditoTransaccionJson json = BeanUtils.convertToNotaCreditoTransaccionJson(request);

                NotaCreditoTransaccion trx = NotaCreditoTransaccionJson.toNotaCreditoTransaccion(json);

                trx = ejbNotaCredito.updateTransaccion(trx);

                NotaCreditoJson tmpJson = NotaCreditoJson.toNotaCreditoJson((NotaCredito) ejbNotaCredito.get(trx.getIdNotaCredito().getIdNotaCredito(), NotaCredito.class));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(tmpJson);

                String mensaje = Log.NOTA_CREDITO_UPDATE_TRANSACCION.replace("trx", trx.getIdNotaCreditoTransaccion().toString());
                mensaje = mensaje.replace("<id>", trx.getIdNotaCredito().getIdNotaCredito().toString());
                mensaje = mensaje.replace("<monto>", trx.getMontoBs() != null ? trx.getMontoBs().toString() : trx.getMontoUsd().toString());
                ejbLogger.add(Accion.UPDATE, user.getUserName(), com.view.menu.Formulario.NOTA_CREDITO, user.getIp(), mensaje);

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }

        }

        return response;
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse newIngreso(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCredito nota = ejbNotaCredito.createNewNotaCredito(user.getUserName(), user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Optional op = Optional.ofNullable(nota);
                if (!op.isPresent()) {
                    throw new CRUDException("No se puede crear la Nota de Crédito.");
                }

                NotaCreditoJson json = NotaCreditoJson.toNotaCreditoJson(nota);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

                ejbLogger.add(Accion.INSERT, user.getUserName(),
                        com.view.menu.Formulario.NOTA_CREDITO, user.getIp(), Log.NOTA_CREDITO_NUEVO.replace("<id>", nota.getIdNotaCredito().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("anular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse anular(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                NotaCreditoJson json = BeanUtils.convertToNotaCreditoJson(request);

                ejbNotaCredito.anularNotaCredito(json.getIdNotaCredito(), user.getUserName());

                String mensaje = Log.NOTA_CREDITO_ANULAR.replace("<id>", json.getIdNotaCredito().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("anular/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse anularNotaCreditoTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                NotaCreditoTransaccionJson json = BeanUtils.convertToNotaCreditoTransaccionJson(request);
                NotaCreditoTransaccion tmp = NotaCreditoTransaccionJson.toNotaCreditoTransaccion(json);

                ejbNotaCredito.anularTransaccion(tmp, user.getUserName());

                String mensaje = Log.NOTA_CREDITO_ANULAR_TRANSACCION.replace("<id>", json.getIdNotaCreditoTransaccion().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.NOTA_CREDITO, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("finalizar")
    public RestResponse finalizar(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                NotaCreditoJson json = BeanUtils.convertToNotaCreditoJson(request);

                ejbNotaCredito.finalizar(json.getIdNotaCredito());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.FINALIZAR, user.getUserName(), Formulario.NOTA_CREDITO, 
                        user.getIp(), Log.NOTA_CREDITO_FINALIZAR.replace("<id>", json.getIdNotaCredito().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("pendiente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse pendiente(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            NotaCreditoJson json = BeanUtils.convertToNotaCreditoJson(request);

            NotaCredito nota = NotaCreditoJson.toNotaCredito(json);

            ejbNotaCredito.pendiente(nota);

            String mensaje = Log.NOTA_CREDITO_PENDIENTE.replace("<id>", json.getIdNotaCredito().toString());
            ejbLogger.add(Accion.PENDIENTE, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (Exception ex) {
            Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    @POST
    @Path("get/notadebito/credito/{idCliente}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getNotaDebitosCredito(@PathParam("idCliente") final Integer idCliente,
            final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {

                NotaCreditoJson json = BeanUtils.convertToNotaCreditoJson(request);
                NotaCredito data = NotaCreditoJson.toNotaCredito(json);

                ejbNotaCredito.pendiente(data);

                Optional op = Optional.ofNullable(idCliente);
                if (!op.isPresent()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                List<NotaDebitoTransaccion> list = ejbNotaDebito.getAllNotaDebitoCreditoByCliente(idCliente, user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                if (list.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_LISTA_VACIA));
                    return response;
                }

                List<NotaDebitoTransaccionJson> r = new LinkedList<>();
                for (NotaDebitoTransaccion n : list) {
                    r.add(NotaDebitoTransaccionJson.toNotaDebitoTransaccionJson(n));
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(r);

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }
        return response;
    }

}
