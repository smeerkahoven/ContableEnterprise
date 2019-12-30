/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.resource;

import com.agencia.search.dto.MayoresSearch;
import com.cobranzas.json.KardexClienteSearchJson;
import com.cobranzas.json.ReporteEstadoClienteSearchJson;
import com.contabilidad.entities.SumasSaldosDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.response.json.boletaje.NotaCreditoSearchJson;
import com.response.json.boletaje.PagoAnticipadoSearchJson;
import com.response.json.configuracion.GestionJson;
import com.response.json.configuracion.ParametrosJson;
import com.response.json.contabilidad.AsientoContableJSON;
import com.response.json.contabilidad.BalanceGeneralSearchJson;
import com.response.json.contabilidad.CargoBoletoJSON;
import com.response.json.contabilidad.ComprobanteContableJSON;
import com.response.json.contabilidad.DevolucionJson;
import com.response.json.contabilidad.EstadosResultadosSearchJson;
import com.response.json.contabilidad.IngresoCajaJSON;
import com.response.json.contabilidad.IngresoTransaccionJson;
import com.response.json.contabilidad.NotaCreditoJson;
import com.response.json.contabilidad.NotaCreditoTransaccionJson;
import com.response.json.contabilidad.NotaDebitoJSON;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.response.json.contabilidad.PagoAnticipadoJson;
import com.response.json.contabilidad.PagoAnticipadoTransaccionJson;
import com.response.json.contabilidad.SumasSaldosSearchJson;
import com.response.json.seguridad.UserPersonalJSON;
import com.seguridad.search.LogSearch;
import com.services.seguridad.util.RestRequest;

/**
 *
 * @author xeio
 */
public class BeanUtils {

    /*
    public static Class<?> convertTo(final RestRequest request, Class<?> typeClass){
        Class<?> pc = new Class<?>() ;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), typeClass);

        return pc;
        
    }*/
    public static KardexClienteSearchJson convertToKardexClienteSearchJson(final RestRequest request) {

        if (request.getContent() == null) {
            return new KardexClienteSearchJson();
        }

        KardexClienteSearchJson pc = new KardexClienteSearchJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), KardexClienteSearchJson.class);

        return pc;

    }
    
       public static ReporteEstadoClienteSearchJson convertToReporteEstadoCliente(final RestRequest request) {

        if (request.getContent() == null) {
            return new ReporteEstadoClienteSearchJson();
        }

        ReporteEstadoClienteSearchJson pc = new ReporteEstadoClienteSearchJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), ReporteEstadoClienteSearchJson.class);

        return pc;

    }

    public static LogSearch convertToLogSearchJson(final RestRequest request) {

        if (request.getContent() == null) {
            return new LogSearch();
        }

        LogSearch pc = new LogSearch();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), LogSearch.class);

        return pc;

    }

    public static MayoresSearch convertToMayoresSearchJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new MayoresSearch();
        }

        MayoresSearch pc = new MayoresSearch();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), MayoresSearch.class);

        return pc;

    }

    public static PagoAnticipadoTransaccionJson convertToPagoAnticipadoTransaccionJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new PagoAnticipadoTransaccionJson();
        }

        PagoAnticipadoTransaccionJson pc = new PagoAnticipadoTransaccionJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoTransaccionJson.class);

        return pc;

    }

    public static PagoAnticipadoSearchJson convertToPagoAnticipadoSearchJson(final RestRequest request) {

        if (request.getContent() == null) {
            return new PagoAnticipadoSearchJson();
        }

        PagoAnticipadoSearchJson pc = new PagoAnticipadoSearchJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoSearchJson.class);

        return pc;

    }

    public static DevolucionJson convertToDevolucionJson(final RestRequest request) {

        if (request.getContent() == null) {
            return new DevolucionJson();
        }

        DevolucionJson pc = new DevolucionJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), DevolucionJson.class);

        return pc;

    }

    public static PagoAnticipadoJson convertToPagoAnticipadoJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new PagoAnticipadoJson();
        }

        PagoAnticipadoJson pc = new PagoAnticipadoJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoJson.class);

        return pc;

    }

    public static ComprobanteContableJSON convertToComprobanteContableJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new ComprobanteContableJSON();
        }

        ComprobanteContableJSON pc = new ComprobanteContableJSON();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

        return pc;
    }

    public static NotaCreditoSearchJson convertoToNotaCreditoSearchJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new NotaCreditoSearchJson();
        }

        NotaCreditoSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        if (request.getContent() == null) {
            return new NotaCreditoSearchJson();
        }
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), NotaCreditoSearchJson.class);

        return bjson;
    }

    public static IngresoCajaSearchJson convertoToIngresoCajaSearchJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new IngresoCajaSearchJson();
        }

        IngresoCajaSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        if (request.getContent() == null) {
            return new IngresoCajaSearchJson();
        }
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), IngresoCajaSearchJson.class);

        return bjson;
    }

    public static IngresoTransaccionJson convertoToIngresoCajaTransaccionJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new IngresoTransaccionJson();
        }

        IngresoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), IngresoTransaccionJson.class);

        return bjson;
    }

    public static IngresoCajaJSON convertoToIngresoCajaJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new IngresoCajaJSON();
        }

        IngresoCajaJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), IngresoCajaJSON.class);

        return bjson;
    }

    public static UserPersonalJSON convertoToUserPersonalJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new UserPersonalJSON();
        }

        UserPersonalJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), UserPersonalJSON.class);

        return bjson;
    }

    public static NotaDebitoTransaccionJson convertToNotaDebitoTransaccionJSON(final RestRequest request) {

        if (request.getContent() == null) {
            return new NotaDebitoTransaccionJson();
        }

        NotaDebitoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), NotaDebitoTransaccionJson.class);

        return bjson;
    }

    public static NotaDebitoJSON convertToNotaDebitoJSON(final RestRequest request) {

        if (request.getContent() == null) {
            return new NotaDebitoJSON();
        }

        NotaDebitoJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), NotaDebitoJSON.class);

        return bjson;
    }

    public static CargoBoletoJSON convertToCargoJSON(final RestRequest request) {
        if (request.getContent() == null) {
            return new CargoBoletoJSON();
        }

        CargoBoletoJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), CargoBoletoJSON.class);

        return bjson;
    }

    public static ParametrosJson convertToParametroJson(final RestRequest request) {
        if (request.getContent() == null) {
            return new ParametrosJson();
        }

        ParametrosJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), ParametrosJson.class);

        return bjson;
    }

     public static GestionJson converToGestion(final RestRequest request) {
        if (request.getContent() == null) {
            return new GestionJson();
        }

        GestionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), GestionJson.class);

        return bjson;
    }
    
    public static AsientoContableJSON convertToAsientoContable(final RestRequest request) {

        if (request.getContent() == null) {
            return new AsientoContableJSON();
        }

        AsientoContableJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), AsientoContableJSON.class);

        return bjson;
    }

    public static NotaCreditoTransaccionJson convertToNotaCreditoTransaccionJson(RestRequest request) {

        if (request.getContent() == null) {
            return new NotaCreditoTransaccionJson();
        }

        NotaCreditoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), NotaCreditoTransaccionJson.class);

        return bjson;
    }

    public static SumasSaldosSearchJson convertToSumasySaldos(RestRequest request) {

        if (request.getContent() == null) {
            return new SumasSaldosSearchJson();
        }

        SumasSaldosSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), SumasSaldosSearchJson.class);

        return bjson;
    }

    public static EstadosResultadosSearchJson convertToEstadosResultadosSearchJson(RestRequest request) {

        if (request.getContent() == null) {
            return new EstadosResultadosSearchJson();
        }

        EstadosResultadosSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), EstadosResultadosSearchJson.class);

        return bjson;
    }
    
    
    
    public static BalanceGeneralSearchJson convertToBalanceGeneralSearchJson(RestRequest request) {

        if (request.getContent() == null) {
            return new BalanceGeneralSearchJson();
        }

        BalanceGeneralSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), BalanceGeneralSearchJson.class);

        return bjson;
    }

    public static NotaCreditoJson convertToNotaCreditoJson(RestRequest request) {

        if (request.getContent() == null) {
            return new NotaCreditoJson();
        }

        NotaCreditoJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        bjson = gson.fromJson(object.toString(), NotaCreditoJson.class);

        return bjson;
    }
}
