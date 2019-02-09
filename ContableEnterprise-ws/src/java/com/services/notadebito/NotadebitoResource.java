/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.notadebito;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
import com.agencia.search.dto.BoletoSearchForm;
import com.contabilidad.entities.AsociacionBoletoNotaDebitoJSON;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.contabilidad.CargoBoletoJSON;
import com.response.json.contabilidad.NotaDebitoJSON;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.seguridad.control.entities.Log;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import com.seguridad.utils.ResponseCode;
import com.services.TemplateResource;
import com.services.agencia.BoletoResource;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.BeanUtils;
import com.view.menu.Formulario;
import java.math.BigDecimal;
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
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("notadebito")
public class NotadebitoResource extends TemplateResource {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @EJB
    private BoletoRemote ejbBoleto;

    /**
     * Creates a new instance of NotadebitoResource
     */
    public NotadebitoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.notadebito.NotadebitoResource
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
     * PUT method for updating or creating an instance of NotadebitoResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    @POST
    @Path("cargos/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse saveCargos(RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                CargoBoletoJSON json = BeanUtils.convertToCargoJSON(request);
                Optional op = Optional.ofNullable(json);
                if (op.isPresent()) {
                    json.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                    json.setUsuarioCreador(user.getUserName());

                    CargoBoleto cargo = CargoBoletoJSON.toCargoBoleto(json);
                    cargo.setFechaInsert(DateContable.getCurrentDate());

                    cargo = ejbNotaDebito.saveCargo(cargo);

                    NotaDebito n = (NotaDebito) ejbNotaDebito.get(new NotaDebito(cargo.getIdNotaDebito()));

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                    response.setEntidad(n);

                    String mensaje = Log.NOTA_DEBITO_CARGO_GUARDAR.replace("<cargo>", cargo.getIdCargo().toString());
                    mensaje = mensaje.replace("<nota>", cargo.getIdNotaDebito().toString());

                    ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

                }
            }
        } catch (Exception ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("cargos/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse updateCargos(RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                CargoBoletoJSON json = BeanUtils.convertToCargoJSON(request);
                Optional op = Optional.ofNullable(json);
                if (op.isPresent()) {
                    json.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());
                    json.setUsuarioCreador(user.getUserName());

                    CargoBoleto cargo = CargoBoletoJSON.toCargoBoleto(json);
                    cargo.setFechaInsert(DateContable.getCurrentDate());

                    cargo = ejbNotaDebito.updateCargo(cargo);

                    NotaDebito n = (NotaDebito) ejbNotaDebito.get(new NotaDebito(cargo.getIdNotaDebito()));

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));
                    response.setEntidad(n);

                    String mensaje = Log.NOTA_DEBITO_CARGO_EDITAR.replace("<cargo>", cargo.getIdCargo().toString());
                    mensaje = mensaje.replace("<nota>", cargo.getIdNotaDebito().toString());

                    ejbLogger.add(Accion.UPDATE, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

                }
            }
        } catch (Exception ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    @POST
    @Path("cargos/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getCargosBoleto(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {
            CargoBoletoJSON json = BeanUtils.convertToCargoJSON(request);

            Optional op = Optional.ofNullable(json);
            if (op.isPresent()) {
                CargoBoleto cargo = new CargoBoleto(json.getIdCargo());
                cargo = ejbNotaDebito.getCargo(cargo);
                op = Optional.ofNullable(cargo);
                if (!op.isPresent()) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_VALUE_NOT_FOUND));
                } else {
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(CargoBoletoJSON.toCargoBoletoJSON(cargo));
                }

                ejbLogger.add(Accion.EDIT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp());

            }

        } catch (Exception ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
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
                NotaDebitoJSON json = BeanUtils.convertToNotaDebitoJSON(request);

                NotaDebito n = NotaDebitoJSON.toNotaDebito(json);

                ejbNotaDebito.finalizar(n);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

                ejbLogger.add(Accion.FINALIZAR, user.getUserName(), Formulario.NOTA_DEBITO, user.getIp(), Log.NOTA_DEBITO_FINALIZAR.replace("<id>", n.getIdNotaDebito().toString()));

            } catch (CRUDException ex) {
                Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                response.setContent(ex.getMessage());
            }
        }

        return response;
    }

    @POST
    @Path("boleto/asociar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public synchronized RestResponse asociarBoleto(final RestRequest request) {
        RestResponse response = doValidations(request);

        try {
            AsociacionBoletoNotaDebitoJSON json;
            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
            json = gson.fromJson(object.toString(), AsociacionBoletoNotaDebitoJSON.class); //Anhadimos cada boleto a una transaccion

            NotaDebito n = (NotaDebito) ejbNotaDebito.get(new NotaDebito(json.getIdNotaDebito()));

            if (json.getEstado().equals(Estado.CREADO)) {
                n.setIdCliente(new Cliente(json.getIdCliente()));
                n.setFactorCambiario(json.getFactor());
                n.setIdCounter(new Promotor(json.getIdPromotor()));
                n.setFechaEmision(DateContable.toLatinAmericaDateFormat(json.getFechaEmision()));
                n.setEstado(Estado.PENDIENTE);

                ejbNotaDebito.update(n);
            }

            ejbLogger.add(Accion.TRANSACTION, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), Log.NOTA_DEBITO_NUEVA_TRANSACCION_INICIO.replace("<id>", n.getIdNotaDebito().toString()));

            if (json.getBoletos().size() > 0) {
                for (Integer id : json.getBoletos()) {

                    Boleto boleto =ejbNotaDebito.asociarBoletoNotaDebito( n, id, user.getUserName());

                    String mensaje = Log.BOLETO_ASOCIAR_AUTOMATICO.replace("<boleto>", String.valueOf(boleto.getNumero()));
                    mensaje = mensaje.replace("<id>", json.getIdNotaDebito().toString());

                    ejbLogger.add(Accion.TRANSACTION, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);
                }

                //actualizamos los montos con los boletos cargados
                ejbNotaDebito.actualizarMontosNotaDebito(json.getIdNotaDebito());

                //devolvemos la nota de debito con los montos actualizados
                NotaDebito notaResult = (NotaDebito) ejbNotaDebito.get(new NotaDebito(json.getIdNotaDebito()));

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BOLETO_INSERTADO));
                response.setEntidad(notaResult);

                ejbLogger.add(Accion.TRANSACTION, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), Log.NOTA_DEBITO_NUEVA_TRANSACCION_FIN.replace("<id>", n.getIdNotaDebito().toString()));
            }

        } catch (Exception ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("all/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllTransactions(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                HashMap<String, BigDecimal> content = (HashMap<String, BigDecimal>) request.getContent();
                if (content.get("idNotaDebito") == null) {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_PARAMETERS_SENT));
                    return response;
                }

                BigDecimal idNotaDebito = content.get("idNotaDebito");

                List l = ejbNotaDebito.getAllTransacciones(idNotaDebito.intValue());

                if (l.isEmpty()) {
                    response.setCode(ResponseCode.RESTFUL_WARNING.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
                }

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(l);

                ejbLogger.add(Accion.SEARCH, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp());
            }
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse newNotaDebito(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {

                NotaDebito n = ejbNotaDebito.createNotaDebito(user.getIdEmpleado().getIdEmpresa().getIdEmpresa(), user.getUserName());
                Optional op = Optional.ofNullable(n);
                if (op.isPresent()) {
                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(NotaDebitoJSON.toNotaDebitoJSON(n));
                } else {
                    response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_CANT_CREATE_NOTA_DEBITO));
                }
                ejbLogger.add(Accion.INSERT, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), Log.NOTA_DEBITO_NUEVO.replace("<id>", n.getIdNotaDebito().toString()));

            }
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);

            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_CANT_CREATE_NOTA_DEBITO));
        }

        return response;
    }

    @POST
    @Path("pendiente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse pendienteNotaDebito(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            NotaDebitoJSON json = BeanUtils.convertToNotaDebitoJSON(request);

            NotaDebito n = NotaDebitoJSON.toNotaDebito(json);

            ejbNotaDebito.pendiente(n);

            String mensaje = Log.NOTA_DEBITO_PENDIENTE.replace("<id>", json.getIdNotaDebito().toString());
            ejbLogger.add(Accion.PENDIENTE, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

            response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_SUCCESS));

        } catch (Exception ex) {
            Logger.getLogger(BoletoResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
        }

        return response;
    }

    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAllBoletos(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {

            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                BoletoSearchForm search;
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
                search = gson.fromJson(object.toString(), BoletoSearchForm.class);

                search.setIdEmpresa(user.getIdEmpleado().getIdEmpresa().getIdEmpresa());

                List<NotaDebito> l = ejbNotaDebito.getAllNotaDebito(search);

                if (!l.isEmpty()) {
                    List r = new LinkedList();

                    l.forEach(x -> {
                        NotaDebitoJSON j = NotaDebitoJSON.toNotaDebitoJSON(x);
                        r.add(j);
                    });

                    response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                    response.setContent(r);

                    ejbLogger.add(Accion.SEARCH, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp());

                } else {
                    response.setCode(ResponseCode.RESTFUL_WARNING.getCode());
                    response.setContent(mensajes.getProperty(RestResponse.RESTFUL_BUSQUEDA_VACIA));
                }
            }

        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(mensajes.getProperty(RestResponse.RESTFUL_ERROR));
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("anular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse anularNotaDebito(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                NotaDebitoJSON json = BeanUtils.convertToNotaDebitoJSON(request);

                NotaDebito nota = NotaDebitoJSON.toNotaDebito(json);

                ejbNotaDebito.anularNotaDebito(nota, user.getUserName());

                String mensaje = Log.NOTA_DEBITO_ANULAR.replace("<id>", nota.getIdNotaDebito().toString());
                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    @POST
    @Path("anular/transaccion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse anularTransaccion(final RestRequest request) {
        RestResponse response = doValidations(request);
        try {
            if (response.getCode() == ResponseCode.RESTFUL_SUCCESS.getCode()) {
                NotaDebitoTransaccionJson json = BeanUtils.convertToNotaDebitoTransaccionJSON(request);

                NotaDebitoTransaccion nota = NotaDebitoTransaccionJson.toNotaDebitoTransaccion(json);

                ejbNotaDebito.anularTransaccion(nota,user.getUserName());

                String mensaje = mensajes.getProperty(RestResponse.RESTFUL_NOTA_DEBITO_TRANSACCION_ANULADA_OK).replace("<id>", nota.getIdNotaDebitoTransaccion().toString());
                mensaje = mensaje.replace("<nota>", nota.getIdNotaDebito().toString());

                NotaDebito n = (NotaDebito) ejbNotaDebito.get(new NotaDebito(nota.getIdNotaDebito().getIdNotaDebito()));
                NotaDebitoJSON njson = NotaDebitoJSON.toNotaDebitoJSON(n);

                response.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                response.setContent(mensaje);
                response.setEntidad(njson);

                ejbLogger.add(Accion.ANULAR, user.getUserName(), com.view.menu.Formulario.NOTA_DEBITO, user.getIp(), mensaje);

            }
        } catch (CRUDException ex) {
            response.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            response.setContent(ex.getMessage());
            Logger.getLogger(NotadebitoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
}
