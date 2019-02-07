/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.contabilidad;

import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.contabilidad.remote.PagoAnticipadoRemote;
import com.response.json.boletaje.PagoAnticipadoSearchJson;
import com.response.json.contabilidad.DevolucionJson;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.response.json.contabilidad.PagoAnticipadoJson;
import com.response.json.contabilidad.PagoAnticipadoTransaccionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import java.math.BigDecimal;
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
@Path("pago-anticipado")
public class PagoAnticipadoResource extends TemplateResource {

    /**
     * Creates a new instance of PagoAnticipado
     */
    public PagoAnticipadoResource() {
    }
    @EJB
    private PagoAnticipadoRemote ejbPagoAnticipado;

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    /**
     * Retrieves representation of an instance of
     * com.services.contabilidad.PagoAnticipadoResource
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
     * PUT method for updating or creating an instance of PagoAnticipadoResource
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
    public RestResponse getAllPagosAnticipados(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

            try {
                PagoAnticipadoSearchJson search = BeanUtils.convertToPagoAnticipadoSearchJson(request);
                search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                List<PagoAnticipado> list = ejbPagoAnticipado.findAllPagoAnticipado(search);
                List<PagoAnticipadoJson> r = new LinkedList<>();
                for (PagoAnticipado p : list) {
                    r.add(PagoAnticipadoJson.toPagoAnticipadoJson(p));
                }

                if (list.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BUSQUEDA_VACIA));
                } else {

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(r);
                }

                ejbLogger.add(Accion.SEARCH, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp());

            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
                Logger.getLogger(PagoAnticipadoRemote.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response;
    }

    @POST
    @Path("crear-devolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse crearDevolucion(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                PagoAnticipadoJson json = BeanUtils.convertToPagoAnticipadoJson(request);
                PagoAnticipado fromDb = (PagoAnticipado) ejbPagoAnticipado.get(json.getIdPagoAnticipado(), PagoAnticipado.class);

                if (fromDb == null) {
                    response.setContent("No existe el Pago Anticipado %s".replace("%s", json.getIdPagoAnticipado().toString()));
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                } else {

                    DevolucionJson devJson = new DevolucionJson();
                    devJson.setFactor(fromDb.getFactorCambiario());
                    devJson.setIdCliente(fromDb.getIdCliente().getIdCliente());
                    devJson.setNombreCliente(fromDb.getIdCliente().getNombre());
                    devJson.setIdPagoAnticipado(fromDb.getIdPagoAnticipado());
                    devJson.setIdEmpresa(fromDb.getIdEmpresa());
                    devJson.setIdUsuarioCreador(user.getUserName());
                    devJson.setMoneda(fromDb.getMoneda());

                    Double montMaximo = fromDb.getMontoTotalAcreditado() != null
                            ? fromDb.getMontoAnticipado().subtract(fromDb.getMontoTotalAcreditado()).doubleValue()
                            : fromDb.getMontoAnticipado().doubleValue();

                    devJson.setMontoMaximoDevolucion(new BigDecimal(montMaximo));

                    response.setContent(devJson);
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                }

            } catch (CRUDException ex) {
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
                Logger.getLogger(PagoAnticipadoResource.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                PagoAnticipadoTransaccionJson json = BeanUtils.convertToPagoAnticipadoTransaccionJson(request);

                PagoAnticipadoTransaccion data = ejbPagoAnticipado.getPagoAnticipadoTransaccion(json.getIdPagoAnticipadoTransaccion());

                json = PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccionJsOn(data);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

            } catch (CRUDException ex) {
                Logger.getLogger(PagoAnticipadoResource.class.getName()).log(Level.SEVERE, null, ex);
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
                PagoAnticipadoJson json = BeanUtils.convertToPagoAnticipadoJson(request);

                List<PagoAnticipadoTransaccion> list = ejbPagoAnticipado.findAllPagoAnticipadoTransacciones(json.getIdPagoAnticipado());

                if (list.isEmpty()) {
                    response.setContent("No se encontraron Transacciones para el Pago Anticipado %s".replace("%s", json.getIdPagoAnticipado().toString()));
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    return response;
                }

                List<PagoAnticipadoTransaccionJson> r = new LinkedList<>();
                for (PagoAnticipadoTransaccion i : list) {
                    PagoAnticipadoTransaccionJson trJson = PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccionJsOn(i);
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
                PagoAnticipadoTransaccionJson json = BeanUtils.convertToPagoAnticipadoTransaccionJson(request);

                PagoAnticipadoTransaccion trx = PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccion(json);
                
                // Este metodo viene directo para una transaccion del tipo ACREDITACION
                trx.setTipo(PagoAnticipadoTransaccion.Tipo.ACREDITACION);

                trx = ejbPagoAnticipado.saveTransaccion(trx, user.getUserName());

                json = PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccionJsOn(trx);

                PagoAnticipadoJson tmpJson = PagoAnticipadoJson.toPagoAnticipadoJson((PagoAnticipado) ejbPagoAnticipado.get(json.getIdPagoAnticipado(), PagoAnticipado.class));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);
                response.setEntidad(tmpJson);

                String mensaje = Log.PAGO_ANTICIPADO_NUEVA_TRANSACION.replace("trx", trx.getIdPagoAnticipadoTransaccion().toString());
                mensaje = mensaje.replace("<id>", trx.getIdPagoAnticipado().getIdPagoAnticipado().toString());
                
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(), mensaje);

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
                PagoAnticipado nota = ejbPagoAnticipado.createNewPagoAnticipado(user.getUserName(), user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                Optional op = Optional.ofNullable(nota);
                if (!op.isPresent()) {
                    throw new CRUDException("No se puede crear el Pago Anticipado.");
                }

                PagoAnticipadoJson json = PagoAnticipadoJson.toPagoAnticipadoJson(nota);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(json);

                ejbLogger.add(Accion.INSERT, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(),
                        Log.PAGO_ANTICIPADO_NUEVO.replace("<id>", nota.getIdPagoAnticipado().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse savePago(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                PagoAnticipadoJson json = BeanUtils.convertToPagoAnticipadoJson(request);
                PagoAnticipado data = PagoAnticipadoJson.toPagoAnticipado(json);

                ejbPagoAnticipado.guardar(data);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.INSERT, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(),
                        Log.PAGO_ANTICIPADO_FINALIZAR.replace("<id>", data.getIdPagoAnticipado().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);

                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updatePago(final RestRequest request) {
        RestResponse response = doValidations(request);

        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                PagoAnticipadoJson json = BeanUtils.convertToPagoAnticipadoJson(request);
                PagoAnticipado data = PagoAnticipadoJson.toPagoAnticipado(json);

                ejbPagoAnticipado.updatePago(data);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.UPDATE, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(),
                        Log.PAGO_ANTICIPADO_FINALIZAR.replace("<id>", data.getIdPagoAnticipado().toString()));

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
                PagoAnticipadoJson json = BeanUtils.convertToPagoAnticipadoJson(request);

                ejbPagoAnticipado.anularPagoAnticipado(json.getIdPagoAnticipado(), user.getUserName());

                String mensaje = Log.PAGO_ANTICIPADO_ANULAR.replace("<id>", json.getIdPagoAnticipado().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(), mensaje);

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
    public RestResponse anularPagoAnticipadoTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                PagoAnticipadoTransaccionJson json = BeanUtils.convertToPagoAnticipadoTransaccionJson(request);
                PagoAnticipadoTransaccion tmp = PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccion(json);

                ejbPagoAnticipado.anularTransaccion(tmp, user.getUserName());

                PagoAnticipado pReturn = (PagoAnticipado) ejbPagoAnticipado.get(json.getIdPagoAnticipado(), PagoAnticipado.class);
                PagoAnticipadoJson rReturnJson = PagoAnticipadoJson.toPagoAnticipadoJson(pReturn);

                String mensaje = Log.PAGO_ANTICIPADO_ANULAR_TRANSACCION.replace("<id>", json.getIdPagoAnticipadoTransaccion().toString());
                
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);
                response.setEntidad(rReturnJson);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotasCreditoResource.class.getName()).log(Level.SEVERE, null, ex);
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

                //ejbPagoAnticipado.pendiente(data);
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

    @POST
    @Path("save-devolucion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveDevolucion(final RestRequest request) {
        RestResponse response = doValidations(request);
        if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
            try {
                DevolucionJson json = BeanUtils.convertToDevolucionJson(request);

                Devolucion data = DevolucionJson.toDevolucion(json);
                data.setIdUsuarioCreador(user.getUserName());
                data.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                //pasamos a registrar la devolucion
                data = ejbPagoAnticipado.devolver(data, user.getUserName());

                String msg = Log.PAGO_ANTICIPADO_DEVOLUCION;
                msg = msg.replace("monto", data.getMonto().toString());
                msg = msg.replace("id", data.getIdPagoAnticipado().getIdPagoAnticipado().toString());

                ejbLogger.add(Accion.DEVOLUCION, user.getUserName(),
                        com.view.menu.Formulario.PAGOS_ANTICIPADOS, user.getIp(), msg);

                json = DevolucionJson.toDevolucionJson(data );
                
                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                response.setEntidad(json);
                
            } catch (CRUDException ex) {
                Logger.getLogger(PagoAnticipadoResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }

        }
        return response;
    }
}
