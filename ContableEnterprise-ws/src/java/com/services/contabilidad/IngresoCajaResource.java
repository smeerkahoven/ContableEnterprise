/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.response.json.contabilidad.IngresoCajaJSON;
import com.response.json.contabilidad.IngresoTransaccionJson;
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
import java.util.HashMap;
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
@Path("ingreso-caja")
public class IngresoCajaResource extends TemplateResource {

    @EJB
    private IngresoCajaRemote ejbIngresoCaja;

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    /**
     * Creates a new instance of IngresoCajaResource
     */
    public IngresoCajaResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.IngresoCajaResource
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
     * PUT method for updating or creating an instance of IngresoCajaResource
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

            IngresoCajaSearchJson search = BeanUtils.convertoToIngresoCajaSearchJson(request);
            search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

            List<IngresoCaja> list = ejbIngresoCaja.findAllIngresoCaja(search);

            List<IngresoCajaJSON> r = new LinkedList<>();

            for (IngresoCaja ic : list) {
                r.add(IngresoCajaJSON.toJSON(ic));
            }

            if (list.isEmpty()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BUSQUEDA_VACIA));
            } else {

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(r);
            }

            ejbLogger.add(Accion.SEARCH, user.getUserName(),
                    com.view.menu.Formulario.INGRESO_CAJA, user.getIp());

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);

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
                IngresoTransaccionJson json = BeanUtils.convertoToIngresoCajaTransaccionJson(request);

                IngresoTransaccion data = IngresoTransaccionJson.toIngresoCaja(json);

                data = ejbIngresoCaja.getIngresoTransaccion(data);

                json = IngresoTransaccionJson.toIngresoTransaccionJson(data);

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
                IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);

                List<IngresoTransaccion> list = ejbIngresoCaja.findAllIngresoCajaTransacciones(json.getIdIngresoCaja());

                if (list.isEmpty()) {
                    response.setContent("No se encontraron Transacciones para el Ingreso de Caja %s".replace("%s", json.getIdIngresoCaja().toString()));
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    return response;
                }

                List<IngresoTransaccionJson> r = new LinkedList<>();
                for (IngresoTransaccion i : list) {
                    IngresoTransaccionJson trJson = IngresoTransaccionJson.toIngresoTransaccionJson(i);
                    r.add(trJson);
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

    @POST
    @Path("save/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                IngresoTransaccionJson json = BeanUtils.convertoToIngresoCajaTransaccionJson(request);

                IngresoTransaccion trx = IngresoTransaccionJson.toIngresoCaja(json);

                trx = ejbIngresoCaja.saveTransaccion(trx);

                IngresoCajaJSON tmpJson = IngresoCajaJSON.toJSON((IngresoCaja) ejbIngresoCaja.get(trx.getIdIngresoCaja().getIdIngresoCaja(), IngresoCaja.class));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(tmpJson);

                String mensaje = Log.INGRESO_CAJA_NUEVA_TRANSACION.replace("trx", trx.getIdTransaccion().toString());
                mensaje = mensaje.replace("<id>", trx.getIdIngresoCaja().getIdIngresoCaja().toString());
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);

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
                IngresoTransaccionJson json = BeanUtils.convertoToIngresoCajaTransaccionJson(request);

                IngresoTransaccion trx = IngresoTransaccionJson.toIngresoCaja(json);

                trx = ejbIngresoCaja.updateTransaccion(trx);

                IngresoCajaJSON tmpJson = IngresoCajaJSON.toJSON((IngresoCaja) ejbIngresoCaja.get(trx.getIdIngresoCaja().getIdIngresoCaja(), IngresoCaja.class));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(tmpJson);

                String mensaje = Log.INGRESO_CAJA_UPDATE_TRANSACCION.replace("trx", trx.getIdTransaccion().toString());
                mensaje = mensaje.replace("<id>", trx.getIdIngresoCaja().getIdIngresoCaja().toString());
                mensaje = mensaje.replace("<monto>", trx.getMontoBs() != null ? trx.getMontoBs().toString() : trx.getMontoUsd().toString());
                ejbLogger.add(Accion.UPDATE, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);

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
                IngresoCaja caja = ejbIngresoCaja.createNewIngresoCaja(user.getUserName(), user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Optional op = Optional.ofNullable(caja);
                if (!op.isPresent()) {
                    throw new CRUDException("No se puede crear el Ingreso a Caja.");
                }

                IngresoCajaJSON json = IngresoCajaJSON.toJSON(caja);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), Log.INGRESO_CAJA_NUEVO.replace("<id>", caja.getIdIngresoCaja().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);

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
                IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);

                ejbIngresoCaja.anularIngresoCaja(json.getIdIngresoCaja(), user.getUserName());

                String mensaje = Log.INGRESO_CAJA_ANULAR.replace("<id>", json.getIdIngresoCaja().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("anular/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse anularIngresoCajaTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                IngresoTransaccionJson json = BeanUtils.convertoToIngresoCajaTransaccionJson(request);

                ejbIngresoCaja.anularTransaccion(json.getIdTransaccion(), user.getUserName());

                String mensaje = Log.INGRESO_CAJA_ANULAR_TRANSACCION.replace("<id>", json.getIdTransaccion().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
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
                IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);
                IngresoCaja tmp = IngresoCajaJSON.toIngresoCaja(json);

                IngresoCaja tmpFRomDb = ejbIngresoCaja.finalizar(tmp);

                json = IngresoCajaJSON.toJSON(tmpFRomDb);
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(json);

                ejbLogger.add(Accion.FINALIZAR, user.getUserName(), Formulario.INGRESO_CAJA, user.getIp(), Log.INGRESO_CAJA_FINALIZAR.replace("<id>", json.getIdIngresoCaja().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
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

            IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);

            IngresoCaja caja = IngresoCajaJSON.toIngresoCaja(json);

            ejbIngresoCaja.pendiente(caja);

            String mensaje = Log.INGRESO_CAJA_PENDIENTE.replace("<id>", json.getIdIngresoCaja().toString());
            ejbLogger.add(Accion.PENDIENTE, user.getUserName(), com.view.menu.Formulario.INGRESO_CAJA, user.getIp(), mensaje);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (Exception ex) {
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
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

                IngresoCajaJSON json = BeanUtils.convertoToIngresoCajaJson(request);
                IngresoCaja data = IngresoCajaJSON.toIngresoCaja(json);

                ejbIngresoCaja.pendiente(data);

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

    @GET
    @Path("getall/notadebito/{idNota}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getIngresosCajaByNotaDebito(@PathParam("idNota") Integer idNota) {
        RestResponse response = new RestResponse();
        try {
            Optional op = Optional.ofNullable(idNota);
            if (!op.isPresent()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            HashMap<String, Integer> parameters = new HashMap<>();
            parameters.put("idNotaDebito", idNota);

            List<IngresoCaja> l = ejbIngresoCaja.getIngresoCajaByNotaDebito(idNota);

            List r = new LinkedList();
            for (IngresoCaja c : l) {
                IngresoCajaJSON json = IngresoCajaJSON.toJSON(c);
                r.add(json);
            }

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(r);

        } catch (CRUDException ex) {
            Logger.getLogger(IngresoCajaResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }
}
