/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.TipoComprobante;
import com.contabilidad.remote.ComprobanteRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.contabilidad.AsientoContableJSON;
import com.response.json.contabilidad.ComprobanteContableJSON;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.view.menu.Formulario;
import java.math.BigDecimal;
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
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("comprobantes")
public class ComprobantesResource extends TemplateResource {

    @EJB
    private ComprobanteRemote ejbComprobante;

    /**
     * Creates a new instance of ComprobantesResource
     */
    public ComprobantesResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.ComprobantesResource
     *
     * @return an instance of java.lang.String
     */
    /**
     * PUT method for updating or creating an instance of ComprobantesResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
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

            List<ComprobanteContable> l = ejbComprobante.getComprobantesByNotaDebito(idNota);

            List r = new LinkedList();
            for (ComprobanteContable c : l) {
                ComprobanteContableJSON json = ComprobanteContableJSON.toComprobanteContableJSON(c);
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("all-tipo-comprobantes")
    public RestResponse getAllTipoComprobantes(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                List<TipoComprobante> l = (List<TipoComprobante>) ejbComprobante.get("TipoComprobante.findAll", ComprobanteRemote.class);
                List<ComboSelect> lreturn = new LinkedList<>();
                if (!l.isEmpty()) {
                    for (Iterator<TipoComprobante> iterator = l.iterator(); iterator.hasNext();) {
                        TipoComprobante next = iterator.next();
                        ComboSelect select = new ComboSelect();
                        select.setId(next.getIdTipoComprobante());
                        select.setName(next.getValor());

                        lreturn.add(select);

                    }
                }
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(lreturn);
            } catch (CRUDException ex) {
                Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }
        return response;
    }

    /**
     * No se esta pidiendo la validacion del token debido a un error con los
     * formatos de los numeros Se debe depurar valor por valor mas adelante con
     * tiempo para saber cual es el valor Numerico que esta generando la
     * exception
     *
     * Tambien revisar los begin y end Trnsaction para commit y rollback
     *
     * @param c
     * @return
     * @TODO
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public RestResponse insertComprobante(final ComprobanteContableJSON c) {
        RestResponse response = new RestResponse();
        try {
            //RestResponse response = //doValidations(request);
            /*if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
            ComprobanteContableJSON pc = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);
            
            ComprobanteContablePK numero = ejbComprobante.getNextComprobantePK(pc.getFecha(), pc.getTipo());
            
            ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(pc);
            
            cc.setIdNumeroGestion(numero.getIdLibro());
            cc.setIdUsuarioCreador(user.getUserName());
            cc.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
            cc.setFechaInsert(DateContable.getCurrentDate());
            cc.setGestion(numero.getGestion());
            
            ejbComprobante.beginTransaction();
            
            cc.setIdLibro(ejbComprobante.insert(cc));
            //insertamos las transacciones.
            List<AsientoContable>transacciones = cc.getTransacciones();
            for (AsientoContable t : transacciones) {
            t.setFechaMovimiento(DateContable.getCurrentDate());
            t.setGestion(cc.getGestion());
            t.setIdLibro(cc.getIdLibro());

            t.setIdAsiento(ejbComprobante.insert(t));
            }
            
            ejbComprobante.endTransaction();
            
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            response.setEntidad(cc);
            
            } catch (CRUDException ex) {
            try {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            ejbComprobante.rollback();
            } catch (CRUDException ex1) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex1);
            }
            }
            }*/

            ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(c);

            //ComprobanteContablePK numero = ejbComprobante.getNextComprobantePK(c.getFecha(), c.getTipo());

            /*cc.setIdNumeroGestion(numero.getIdLibro());
            //cc.setIdUsuarioCreador(user.getUserName());
            //cc.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
            cc.setIdUsuarioCreador(c.getIdUsuarioCreador());
            cc.setIdEmpresa(c.getIdEmpresa());
            cc.setFechaInsert(DateContable.getCurrentDate());
            cc.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));

            //  ejbComprobante.beginTransaction();
            Integer idLibro = ejbComprobante.insert(cc);

            cc.getComprobanteContablePK().setIdLibro(idLibro);*/
            //insertamos las transacciones.
            cc = ejbComprobante.procesarComprobante(cc);
            List<AsientoContableJSON> transacciones = c.getTransacciones();
            for (AsientoContableJSON t : transacciones) {

                AsientoContable a = AsientoContableJSON.toAsientoContable(t);
                /*a.setFechaMovimiento(DateContable.getCurrentDate());
                a.setIdLibro(idLibro);
                a.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));

                ejbComprobante.insert(a);*/

                a = ejbComprobante.procesarAsientoContable(a, cc);

                t.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
                t.setIdLibro(c.getIdLibro());
                t.setGestion(a.getGestion());
                t.setIdAsiento(a.getIdAsiento());
            }

            c.setIdNumeroGestion(cc.getIdNumeroGestion());
            c.setFechaInsert(DateContable.getDateFormat(cc.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
            c.setIdLibro(cc.getIdLibro());
            c.setGestion(cc.getGestion());

            //ejbComprobante.endTransaction();
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            //retorna el objeto
            response.setEntidad(c);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            switch (cc.getEstado()) {
                case ComprobanteContable.EMITIDO:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_GENERADO_SUCCESS));
                    break;
                case ComprobanteContable.PENDIENTE:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS));
                    break;
            }

            ejbLogger.add(Accion.INSERT, c.getIdUsuarioCreador(), com.view.menu.Formulario.COMPROBANTES, "");
            return response;
        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add-transaction")
    public RestResponse addTransaction(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            ComprobanteContableJSON pc = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

            AsientoContable insert = AsientoContableJSON.toAsientoContable(pc.getTransacciones().get(pc.getTransacciones().size() - 1));
            insert.setGestion(pc.getGestion());
            insert.setIdLibro(pc.getIdLibro());
            insert.setFechaMovimiento(DateContable.getCurrentDate());

            Integer idAsiento = ejbComprobante.insert(insert);
            ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.COMPROBANTES, user.getIp());

            insert.setIdAsiento(idAsiento);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            response.setEntidad(AsientoContableJSON.toAsientoContableJSON(insert));

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update-transaction")
    public RestResponse updateTransaction(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            ComprobanteContableJSON pc = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

            AsientoContable insert = AsientoContableJSON.toAsientoContable(pc.getTransacciones().get(pc.getTransacciones().size() - 1));

            //actualiza
            ejbComprobante.update(insert);
            ejbLogger.add(Accion.UPDATE, user.getUserName(), Formulario.COMPROBANTES, user.getIp());

            //actualiza montos totales
            pc = actualizarTotales(pc);

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("1", pc.getTotalDebeMonNac());
            parameters.put("2", pc.getTotalHaberMonNac());
            parameters.put("3", pc.getTotalDebeMonExt());
            parameters.put("4", pc.getTotalHaberMonNac());
            parameters.put("5", pc.getIdLibro());

            ejbComprobante.executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_TOTALES, parameters);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete-transaction/{id}")
    public RestResponse deleteTransaction(final RestRequest request, @PathParam("id") Integer id) {
        RestResponse response = doValidations(request);
        try {

            if (id == 0) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            ComprobanteContableJSON pc = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("1", id);

            //elimina
            ejbComprobante.executeNative(Queries.DELETE_COMPROBANTE_TRANSACTION, parameters);
            ejbLogger.add(Accion.DELETE, user.getUserName(), Formulario.COMPROBANTES, user.getIp());

            //actualiza montos totales
            pc = actualizarTotales(pc);

            parameters = new HashMap<>();
            parameters.put("1", pc.getTotalDebeMonNac());
            parameters.put("2", pc.getTotalHaberMonNac());
            parameters.put("3", pc.getTotalDebeMonExt());
            parameters.put("4", pc.getTotalHaberMonNac());
            parameters.put("5", pc.getIdLibro());

            ejbComprobante.executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_TOTALES, parameters);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }
        return response;
    }

    private ComprobanteContableJSON actualizarTotales(ComprobanteContableJSON c) {
        BigDecimal totalDebeMonNac = new BigDecimal(0);
        BigDecimal totalHaberMonNac = new BigDecimal(0);
        BigDecimal totalDebeMonExt = new BigDecimal(0);
        BigDecimal totalHaberMonExt = new BigDecimal(0);

        for (AsientoContableJSON json : c.getTransacciones()) {
            System.out.println("json.getDebeMonExt():" + json.getDebeMonExt());
            System.out.println("json.getDebeMonNac():" + json.getDebeMonNac());
            System.out.println("json.getHaberMonExt():" + json.getHaberMonExt());
            System.out.println("json.getHaberMonNac():" + json.getHaberMonNac());
            System.out.println("------------------");

            totalDebeMonExt = totalDebeMonExt.add(json.getDebeMonExt());
            totalDebeMonNac = totalDebeMonNac.add(json.getDebeMonNac());
            totalHaberMonExt = totalHaberMonExt.add(json.getHaberMonExt());
            totalHaberMonNac = totalHaberMonNac.add(json.getHaberMonNac());
        }

        c.setTotalDebeMonExt(totalDebeMonExt);
        c.setTotalDebeMonNac(totalDebeMonNac);
        c.setTotalHaberMonExt(totalHaberMonExt);
        c.setTotalHaberMonNac(totalHaberMonNac);

        System.out.println("totalDebeMonExt:" + totalDebeMonExt);
        System.out.println("totalDebeMonNac:" + totalDebeMonNac);
        System.out.println("totalHaberMonExt:" + totalHaberMonExt);
        System.out.println("totalHaberMonNac:" + totalHaberMonNac);

        return c;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("update")
    public RestResponse updateComprobante(final RestRequest request) {
        //RestResponse response = new RestResponse();
        RestResponse response = doValidations(request);
        try {
            //RestResponse response = //doValidations(request);
            /*if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {*/
            ComprobanteContableJSON c = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            c = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

            ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(c);

            switch (cc.getEstado()) {
                case ComprobanteContable.PENDIENTE:
                    cc.setEstado(ComprobanteContable.EMITIDO);
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_GENERADO_SUCCESS));
                    break;
            }

            ejbComprobante.update(cc);

            /* ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(c);

            ejbComprobante.beginTransaction();
            Integer idLibro = ejbComprobante.insert(cc);

            cc.getComprobanteContablePK().setIdLibro(idLibro);
            //insertamos las transacciones.
            List<AsientoContableJSON> transacciones = c.getTransacciones();
            for (AsientoContableJSON t : transacciones) {

                AsientoContable a = AsientoContableJSON.toAsientoContable(t);
                a.setFechaMovimiento(DateContable.getCurrentDate());
                a.setIdLibro(idLibro);
                a.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));

                ejbComprobante.insert(a);

                t.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
                t.setIdLibro(idLibro);
                t.setGestion(a.getAsientoContablePK().getGestion());
                t.setIdAsiento(a.getAsientoContablePK().getIdAsiento());
            }

            cc.setIdNumeroGestion(cc.getIdNumeroGestion());
            

            ejbComprobante.endTransaction();
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            //retorna el objeto
            response.setEntidad(c);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            switch (cc.getEstado()) {
                case ComprobanteContable.APROBADO:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_GENERADO_SUCCESS));
                    break;
                case ComprobanteContable.PENDIENTE:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS));
                    break;
            }*/

 /*ComprobanteContablePK numero = ejbComprobante.getNextComprobantePK(c.getFecha(), c.getTipo());

            ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(c);

            cc.setIdNumeroGestion(numero.getIdLibro());
            //cc.setIdUsuarioCreador(user.getUserName());
            //cc.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
            cc.setIdUsuarioCreador(c.getIdUsuarioCreador());
            cc.setIdEmpresa(c.getIdEmpresa());
            cc.setFechaInsert(DateContable.getCurrentDate());
            cc.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));

            ejbComprobante.beginTransaction();
            Integer idLibro = ejbComprobante.insert(cc);

            cc.getComprobanteContablePK().setIdLibro(idLibro);
            //insertamos las transacciones.
            List<AsientoContableJSON> transacciones = c.getTransacciones();
            for (AsientoContableJSON t : transacciones) {

                AsientoContable a = AsientoContableJSON.toAsientoContable(t);
                a.setFechaMovimiento(DateContable.getCurrentDate());
                a.setIdLibro(idLibro);
                a.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));

                ejbComprobante.insert(a);

                t.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
                t.setIdLibro(idLibro);
                t.setGestion(a.getAsientoContablePK().getGestion());
                t.setIdAsiento(a.getAsientoContablePK().getIdAsiento());
            }

            c.setIdNumeroGestion(cc.getIdNumeroGestion());
            c.setFechaInsert(DateContable.getDateFormat(cc.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
            c.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
            c.setGestion(cc.getComprobanteContablePK().getGestion());

            ejbComprobante.endTransaction();
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
            //retorna el objeto
            response.setEntidad(c);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            
            switch (cc.getEstado()){
                case ComprobanteContable.APROBADO:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_GENERADO_SUCCESS));
                    break;
                case ComprobanteContable.PENDIENTE:
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS));
                    break ;
            }*/
            ejbLogger.add(Accion.INSERT, c.getIdUsuarioCreador(), Formulario.COMPROBANTES, "");
            return response;
        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("anular")
    public RestResponse anular(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                ComprobanteContableJSON pc = new ComprobanteContableJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", ComprobanteContable.ANULADO);
                parameters.put("2", user.getUserName());
                parameters.put("3", pc.getIdLibro());

                ejbComprobante.executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_ESTADO, parameters);
                ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.COMPROBANTES, user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_ANULADO_SUCCESS));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pendiente")
    public RestResponse pendiente(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                ComprobanteContableJSON pc = new ComprobanteContableJSON();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("1", ComprobanteContable.PENDIENTE);
                parameters.put("2", user.getUserName());
                parameters.put("3", pc.getIdLibro());

                ejbComprobante.executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_ESTADO, parameters);
                ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.COMPROBANTES, user.getIp());

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
        }
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("all")
    public RestResponse getComprobantes(/*@QueryParam("tipo") String tipo,
            @QueryParam("fechaI") String fechaI,
            @QueryParam("fechaF") String fechaF,
            @QueryParam("estado") String estado*/final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {

                HashMap<String, Object> parameters = (HashMap<String, Object>) request.getContent();
                String tipo = (String) parameters.get("tipo");
                String estado = (String) parameters.get("estado");
                String fechaI = (String) parameters.get("fechaInicio");
                String fechaF = (String) parameters.get("fechaFin");

                List<ComprobanteContable> l = ejbComprobante.getComprobantes(tipo == null ? "" : tipo,
                        estado == null ? "" : estado,
                        fechaI == null ? "" : fechaI,
                        fechaF == null ? "" : fechaF, user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                List<ComprobanteContableJSON> ljson = new LinkedList<>();
                for (ComprobanteContable cc : l) {
                    ComprobanteContableJSON cjson = ComprobanteContableJSON.toComprobanteContableJSON(cc);
                    ljson.add(cjson);
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(ljson);

            } catch (CRUDException ex) {
                Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            }
        }
        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public RestResponse getComprobante(@PathParam("id") Integer id) {
        RestResponse response = new RestResponse();

        try {

            if (id == null) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                return response;
            }

            if (id <= 0) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                return response;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("idLibro", id);

            List find = (List) ejbComprobante.get("ComprobanteContable.find", ComprobanteContable.class, parameters);

            if (find.isEmpty()) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                return response;
            }

            ComprobanteContable cc = (ComprobanteContable) find.get(0);

            List transacciones = (List) ejbComprobante.get("AsientoContable.find", AsientoContable.class, parameters);

            ComprobanteContableJSON json = ComprobanteContableJSON.toComprobanteContableJSON(cc);
            json.setTransacciones(AsientoContableJSON.toAsientoContableJSON(transacciones));

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(json);

        } catch (CRUDException ex) {
            Logger.getLogger(ComprobantesResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getCause());
        }

        return response;
    }
}
