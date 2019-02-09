/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.TipoComprobante;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.NotasCreditoRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.agencia.BoletoJSON;
import com.response.json.contabilidad.AsientoAnswerJSON;
import com.response.json.contabilidad.AsientoContableJSON;
import com.response.json.contabilidad.CargoBoletoJSON;
import com.response.json.contabilidad.ComprobanteContableJSON;
import com.response.json.contabilidad.IngresoTransaccionJson;
import com.response.json.contabilidad.NotaCreditoTransaccionJson;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.Estado;
import com.services.notadebito.NotadebitoResource;
import com.util.resource.BeanUtils;
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

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @EJB
    private IngresoCajaRemote ejbIngresoCaja;

    @EJB
    private NotasCreditoRemote ejbNotaCredito;

    @EJB
    private BoletoRemote ejbBoleto;

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

    @POST
    @Path("transaccion-boleto/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getTransaccionBoleto(final RestRequest request) {
        RestResponse response = doValidations(request);

        AsientoContableJSON json = BeanUtils.convertToAsientoContable(request);
        Optional op = Optional.ofNullable(json);

        try {
            NotaDebitoTransaccion ndtr = null;
            Boleto b = null;
            IngresoTransaccion itr = null;
            NotaCreditoTransaccion nctr = null;

            if (json.getIdNotaTransaccion() != null) {
                ndtr = ejbNotaDebito.getNotaDebitoTransaccion(json.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
            }

            if (json.getIdBoleto() != null) {
                b = (Boleto) ejbBoleto.get(new Boleto(json.getIdBoleto().getIdBoleto()));
            }

            if (json.getIdIngresoCajaTransaccion() != null) {
                itr = ejbIngresoCaja.getIngresoTransaccion(new IngresoTransaccion(json.getIdIngresoCajaTransaccion().getIdTransaccion()));
            }

            if (json.getIdNotaCreditoTransaccion() != null) {
                nctr = ejbNotaCredito.getNotaCreditoTransaccion(new NotaCreditoTransaccion(json.getIdNotaCreditoTransaccion().getIdNotaCreditoTransaccion()));
            }

            AsientoAnswerJSON asw = new AsientoAnswerJSON();

            op = Optional.ofNullable(ndtr);
            if (op.isPresent()) {
                asw.setNotaDebito(NotaDebitoTransaccionJson.toNotaDebitoTransaccionJson(ndtr));
            }

            op = Optional.ofNullable(b);
            if (op.isPresent()) {
                asw.setBoleto(BoletoJSON.toBoletoJSON(b));
            }

            op = Optional.ofNullable(nctr);
            if (op.isPresent()) {
                asw.setNotaCredito(NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(nctr));
            }

            op = Optional.ofNullable(itr);
            if (op.isPresent()) {
                asw.setIngreso(IngresoTransaccionJson.toIngresoTransaccionJson(itr));
            }

            response.setContent(asw);
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.NOTA_DEBITO, user.getIp());

        } catch (CRUDException ex) {
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }
        return response;
    }

    @POST
    @Path("transaccion-cargo/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getTransaccionCargo(final RestRequest request) {
        RestResponse response = doValidations(request);

        AsientoContableJSON json = BeanUtils.convertToAsientoContable(request);
        Optional op = Optional.ofNullable(json);

        try {
            NotaDebitoTransaccion ndtr = null;
            Boleto b = null;
            IngresoTransaccion itr = null;
            NotaCreditoTransaccion nctr = null;
            CargoBoleto cb = null;
            IngresoCaja ic = null;

            if (json.getIdNotaTransaccion() != null) {
                ndtr = ejbNotaDebito.getNotaDebitoTransaccion(json.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
            }

            if (json.getIdBoleto() != null) {
                b = (Boleto) ejbBoleto.get(new Boleto(json.getIdBoleto().getIdBoleto()));
            }

            if (json.getIdIngresoCajaTransaccion() != null) {
                itr = ejbIngresoCaja.getIngresoTransaccion(new IngresoTransaccion(json.getIdIngresoCajaTransaccion().getIdTransaccion()));
            }

            if (json.getIdNotaCreditoTransaccion() != null) {
                nctr = ejbNotaCredito.getNotaCreditoTransaccion(new NotaCreditoTransaccion(json.getIdNotaCreditoTransaccion().getIdNotaCreditoTransaccion()));
            }

            if (json.getIdCargo() != null) {
                cb = ejbNotaDebito.getCargo(new CargoBoleto(json.getIdCargo().getIdCargo()));
            }

            AsientoAnswerJSON asw = new AsientoAnswerJSON();

            op = Optional.ofNullable(ndtr);
            if (op.isPresent()) {
                asw.setNotaDebito(NotaDebitoTransaccionJson.toNotaDebitoTransaccionJson(ndtr));
            }

            op = Optional.ofNullable(b);
            if (op.isPresent()) {
                asw.setBoleto(BoletoJSON.toBoletoJSON(b));
            }

            op = Optional.ofNullable(nctr);
            if (op.isPresent()) {
                asw.setNotaCredito(NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(nctr));
            }

            op = Optional.ofNullable(itr);
            if (op.isPresent()) {
                asw.setIngreso(IngresoTransaccionJson.toIngresoTransaccionJson(itr));
            }

            op = Optional.ofNullable(cb);
            if (op.isPresent()) {
                asw.setCargo(CargoBoletoJSON.toCargoBoletoJSON(cb));
            }

            response.setContent(asw);
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.NOTA_DEBITO, user.getIp());

        } catch (CRUDException ex) {
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }
        return response;
    }

    @POST
    @Path("transaccion-paquete/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getTransaccionPaquete(final RestRequest request) {
        RestResponse response = doValidations(request);

        AsientoContableJSON json = BeanUtils.convertToAsientoContable(request);
        Optional op = Optional.ofNullable(json);

        try {
            NotaDebitoTransaccion ndtr = null;
            Boleto b = null;
            IngresoTransaccion itr = null;
            NotaCreditoTransaccion nctr = null;
            CargoBoleto cb = null;

            if (json.getIdNotaTransaccion() != null) {
                ndtr = ejbNotaDebito.getNotaDebitoTransaccion(json.getIdNotaTransaccion().getIdNotaDebitoTransaccion());
            }

            if (json.getIdBoleto() != null) {
                b = (Boleto) ejbBoleto.get(new Boleto(json.getIdBoleto().getIdBoleto()));
            }

            if (json.getIdIngresoCajaTransaccion() != null) {
                itr = ejbIngresoCaja.getIngresoTransaccion(new IngresoTransaccion(json.getIdIngresoCajaTransaccion().getIdTransaccion()));
            }

            if (json.getIdNotaCreditoTransaccion() != null) {
                nctr = ejbNotaCredito.getNotaCreditoTransaccion(new NotaCreditoTransaccion(json.getIdNotaCreditoTransaccion().getIdNotaCreditoTransaccion()));
            }

            if (json.getIdCargo() != null) {
                cb = ejbNotaDebito.getCargo(new CargoBoleto(json.getIdCargo().getIdCargo()));
            }

            AsientoAnswerJSON asw = new AsientoAnswerJSON();

            op = Optional.ofNullable(ndtr);
            if (op.isPresent()) {
                asw.setNotaDebito(NotaDebitoTransaccionJson.toNotaDebitoTransaccionJson(ndtr));
            }

            op = Optional.ofNullable(b);
            if (op.isPresent()) {
                asw.setBoleto(BoletoJSON.toBoletoJSON(b));
            }

            op = Optional.ofNullable(nctr);
            if (op.isPresent()) {
                asw.setNotaCredito(NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(nctr));
            }

            op = Optional.ofNullable(itr);
            if (op.isPresent()) {
                asw.setIngreso(IngresoTransaccionJson.toIngresoTransaccionJson(itr));
            }

            op = Optional.ofNullable(cb);
            if (op.isPresent()) {
                asw.setCargo(CargoBoletoJSON.toCargoBoletoJSON(cb));
            }

            response.setContent(asw);
            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

            ejbLogger.add(Accion.SEARCH, user.getUserName(), Formulario.NOTA_DEBITO, user.getIp());

        } catch (CRUDException ex) {
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
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
    public RestResponse insertComprobante(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                ComprobanteContableJSON json = BeanUtils.convertToComprobanteContableJson(request);
                ComprobanteContable cc = ComprobanteContableJSON.toComprobanteContable(json);
                cc = ejbComprobante.procesarComprobante(cc);
                List<AsientoContableJSON> transacciones = json.getTransacciones();
                for (AsientoContableJSON t : transacciones) {

                    AsientoContable a = AsientoContableJSON.toAsientoContable(t);
                    a = ejbComprobante.procesarAsientoContable(a, cc);
                    t.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
                    t.setIdLibro(ComprobanteContableJSON.toComprobanteContableJSON(cc));
                    t.setGestion(a.getGestion());
                    t.setIdAsiento(a.getIdAsiento());
                }

                json.setIdNumeroGestion(cc.getIdNumeroGestion());
                json.setFechaInsert(DateContable.getDateFormat(cc.getFechaInsert(), DateContable.LATIN_AMERICA_TIME_FORMAT));
                json.setIdLibro(cc.getIdLibro());
                json.setGestion(cc.getGestion());

                //ejbComprobante.endTransaction();
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                //retorna el objeto
                response.setEntidad(json);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());

                switch (cc.getEstado()) {
                    case ComprobanteContable.EMITIDO:
                        response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_GENERADO_SUCCESS));
                        break;
                    case ComprobanteContable.PENDIENTE:
                        response.setContent(mensajes.getProperty(RestResponse.RESTFUL_COMPROBANTE_PENDIENTE_SUCCESS));
                        break;
                }

                String m = Log.COMPROBANTE_SAVE.replace("id", json.getGestion().toString() + "-" + json.getIdNumeroGestion());
                
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.COMPROBANTES, m );
            }

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
            insert.setIdLibro(new ComprobanteContable(pc.getIdLibro()));
            insert.setFechaMovimiento(DateContable.getCurrentDate());

            String m = Log.COMPROBANTE_ADD_TRANSACCION.replace("id", pc.getIdLibro().toString()) ;
            if (insert.getMontoDebeExt() != null) {
                m = m.replace("monto", " monto debe ext: " + insert.getMontoDebeExt().toString());
            }
            if (insert.getMontoDebeNac()!= null) {
                m = m.replace("monto", "monto debe nac: " +insert.getMontoDebeNac().toString());
            }
            if (insert.getMontoHaberExt()!= null) {
                m = m.replace("monto", "monto haber ext: " +insert.getMontoHaberExt().toString());
            }
            if (insert.getMontoHaberNac()!= null) {
                m = m.replace("monto", "monto haber nac: " + insert.getMontoHaberNac().toString());
            }
            
            insert = ejbComprobante.addTransaccion(insert);
            ejbLogger.add(Accion.INSERT, user.getUserName(), Formulario.COMPROBANTES, user.getIp(),m);

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
    @Path("add-correccion")
    public RestResponse addCorreccion(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            ComprobanteContableJSON pc = new ComprobanteContableJSON();
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

            AsientoContable insert = AsientoContableJSON.toAsientoContable(pc.getTransacciones().get(pc.getTransacciones().size() - 1));
            insert.setGestion(pc.getGestion());
            insert.setIdLibro(new ComprobanteContable(pc.getIdLibro()));
            insert.setFechaMovimiento(DateContable.getCurrentDate());
            insert.setEstado(Estado.EMITIDO);

            insert = ejbComprobante.addTransaccion(insert);

            String m = Log.COMPROBANTE_ADD_CORRECTION.replace("id", pc.getIdLibro().toString());
            if (insert.getMontoDebeExt() != null) {
                m = m.replace("monto", " monto debe ext: " + insert.getMontoDebeExt().toString());
            }
            if (insert.getMontoDebeNac()!= null) {
                m = m.replace("monto", "monto debe nac: " +insert.getMontoDebeNac().toString());
            }
            if (insert.getMontoHaberExt()!= null) {
                m = m.replace("monto", "monto haber ext: " +insert.getMontoHaberExt().toString());
            }
            if (insert.getMontoHaberNac()!= null) {
                m = m.replace("monto", "monto haber nac: " + insert.getMontoHaberNac().toString());
            }

            ejbLogger.add(Accion.CORRECT, user.getUserName(), Formulario.COMPROBANTES, user.getIp(), m);

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
            ejbLogger.add(Accion.UPDATE, user.getUserName(), Formulario.COMPROBANTES, user.getIp(), Log.COMPROBANTE_UPDATE.replace("id", pc.getIdLibro().toString()));

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

            String m = "Elimino la transaccion <id> del Comprobante <s>.".replace("id", id.toString());
            m = m.replace("s", pc.getIdLibro().toString());
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
            /* System.out.println("json.getDebeMonExt():" + json.getDebeMonExt());
            System.out.println("json.getDebeMonNac():" + json.getDebeMonNac());
            System.out.println("json.getHaberMonExt():" + json.getHaberMonExt());
            System.out.println("json.getHaberMonNac():" + json.getHaberMonNac());
            System.out.println("------------------");*/

            totalDebeMonExt = totalDebeMonExt.add(json.getDebeMonExt());
            totalDebeMonNac = totalDebeMonNac.add(json.getDebeMonNac());
            totalHaberMonExt = totalHaberMonExt.add(json.getHaberMonExt());
            totalHaberMonNac = totalHaberMonNac.add(json.getHaberMonNac());
        }

        c.setTotalDebeMonExt(totalDebeMonExt);
        c.setTotalDebeMonNac(totalDebeMonNac);
        c.setTotalHaberMonExt(totalHaberMonExt);
        c.setTotalHaberMonNac(totalHaberMonNac);

        /*System.out.println("totalDebeMonExt:" + totalDebeMonExt);
        System.out.println("totalDebeMonNac:" + totalDebeMonNac);
        System.out.println("totalHaberMonExt:" + totalHaberMonExt);
        System.out.println("totalHaberMonNac:" + totalHaberMonNac);*/
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

            String m = "ACTUALIZO el comprobante <id>.".replace("id", cc.getIdLibro().toString());

            ejbComprobante.update(cc);

            ejbLogger.add(Accion.UPDATE, c.getIdUsuarioCreador(), Formulario.COMPROBANTES, user.getIp());

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

                String m = Log.COMPROBANTE_ANULAR;
                m = m.replace("<id>", pc.getIdLibro().toString());

                ejbComprobante.executeNative(Queries.UPDATE_COMPROBANTE_CONTABLE_ESTADO, parameters);
                ejbLogger.add(Accion.ANULAR, user.getUserName(), Formulario.COMPROBANTES, user.getIp(),
                        m);

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

                ejbComprobante.pendiente(pc.getIdLibro(), user.getUserName());

                String m = "Establecio el comprobante <id> como PENDIENTE.".replace("<id>", pc.getIdLibro().toString());
                ejbLogger.add(Accion.PENDIENTE, user.getUserName(), Formulario.COMPROBANTES, user.getIp(), m);

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

            parameters.put("idLibro", new ComprobanteContable(id));
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
