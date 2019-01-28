/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.resource;

import com.agencia.search.dto.MayoresSearch;
import com.contabilidad.entities.PagoAnticipado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.EntidadJSON;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.response.json.boletaje.NotaCreditoSearchJson;
import com.response.json.boletaje.PagoAnticipadoSearchJson;
import com.response.json.contabilidad.AsientoContableJSON;
import com.response.json.contabilidad.CargoBoletoJSON;
import com.response.json.contabilidad.ComprobanteContableJSON;
import com.response.json.contabilidad.DevolucionJson;
import com.response.json.contabilidad.IngresoCajaJSON;
import com.response.json.contabilidad.IngresoTransaccionJson;
import com.response.json.contabilidad.NotaCreditoJson;
import com.response.json.contabilidad.NotaCreditoTransaccionJson;
import com.response.json.contabilidad.NotaDebitoJSON;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.response.json.contabilidad.PagoAnticipadoJson;
import com.response.json.contabilidad.PagoAnticipadoTransaccionJson;
import com.response.json.seguridad.UserPersonalJSON;
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
    public static MayoresSearch convertToMayoresSearchJson(final RestRequest request) {
        MayoresSearch pc = new MayoresSearch();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), MayoresSearch.class);

        return pc;

    }

    public static PagoAnticipadoTransaccionJson convertToPagoAnticipadoTransaccionJson(final RestRequest request) {
        PagoAnticipadoTransaccionJson pc = new PagoAnticipadoTransaccionJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoTransaccionJson.class);

        return pc;

    }

    public static PagoAnticipadoSearchJson convertToPagoAnticipadoSearchJson(final RestRequest request) {
        PagoAnticipadoSearchJson pc = new PagoAnticipadoSearchJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoSearchJson.class);

        return pc;

    }

    public static DevolucionJson convertToDevolucionJson(final RestRequest request) {
        DevolucionJson pc = new DevolucionJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), DevolucionJson.class);

        return pc;

    }

    public static PagoAnticipadoJson convertToPagoAnticipadoJson(final RestRequest request) {
        PagoAnticipadoJson pc = new PagoAnticipadoJson();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), PagoAnticipadoJson.class);

        return pc;

    }

    public static ComprobanteContableJSON convertToComprobanteContableJson(final RestRequest request) {
        ComprobanteContableJSON pc = new ComprobanteContableJSON();
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        pc = gson.fromJson(object.toString(), ComprobanteContableJSON.class);

        return pc;
    }

    public static NotaCreditoSearchJson convertoToNotaCreditoSearchJson(final RestRequest request) {
        NotaCreditoSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        if (request.getContent() == null) {
            return new NotaCreditoSearchJson();
        }
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), NotaCreditoSearchJson.class);

        return bjson;
    }

    public static IngresoCajaSearchJson convertoToIngresoCajaSearchJson(final RestRequest request) {
        IngresoCajaSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        if (request.getContent() == null) {
            return new IngresoCajaSearchJson();
        }
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), IngresoCajaSearchJson.class);

        return bjson;
    }

    public static IngresoTransaccionJson convertoToIngresoCajaTransaccionJson(final RestRequest request) {
        IngresoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), IngresoTransaccionJson.class);

        return bjson;
    }

    public static IngresoCajaJSON convertoToIngresoCajaJson(final RestRequest request) {
        IngresoCajaJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), IngresoCajaJSON.class);

        return bjson;
    }

    public static UserPersonalJSON convertoToUserPersonalJson(final RestRequest request) {
        UserPersonalJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), UserPersonalJSON.class);

        return bjson;
    }

    public static NotaDebitoTransaccionJson convertToNotaDebitoTransaccionJSON(final RestRequest request) {
        NotaDebitoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), NotaDebitoTransaccionJson.class);

        return bjson;
    }

    public static NotaDebitoJSON convertToNotaDebitoJSON(final RestRequest request) {
        NotaDebitoJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), NotaDebitoJSON.class);

        return bjson;
    }

    public static CargoBoletoJSON convertToCargoJSON(final RestRequest request) {
        CargoBoletoJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), CargoBoletoJSON.class);

        return bjson;
    }

    public static AsientoContableJSON convertToAsientoContable(final RestRequest request) {
        AsientoContableJSON bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), AsientoContableJSON.class);

        return bjson;
    }

    public static NotaCreditoTransaccionJson convertToNotaCreditoTransaccionJson(RestRequest request) {
        NotaCreditoTransaccionJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), NotaCreditoTransaccionJson.class);

        return bjson;
    }

    public static NotaCreditoJson convertToNotaCreditoJson(RestRequest request) {
        NotaCreditoJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), NotaCreditoJson.class);

        return bjson;
    }
}
