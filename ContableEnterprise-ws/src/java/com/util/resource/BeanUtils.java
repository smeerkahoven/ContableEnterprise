/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.response.json.boletaje.IngresoCajaSearchJson;
import com.response.json.contabilidad.AsientoContableJSON;
import com.response.json.contabilidad.CargoBoletoJSON;
import com.response.json.contabilidad.IngresoCajaJSON;
import com.response.json.contabilidad.NotaDebitoJSON;
import com.response.json.contabilidad.NotaDebitoTransaccionJson;
import com.response.json.seguridad.UserPersonalJSON;
import com.services.seguridad.util.RestRequest;

/**
 *
 * @author xeio
 */
public class BeanUtils {
    
       public static IngresoCajaSearchJson convertoToIngresoCajaSearchJson(final RestRequest request) {
        IngresoCajaSearchJson bjson;
        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse((String) request.getContent()).getAsJsonObject();
        System.out.println((String) request.getContent());
        bjson = gson.fromJson(object.toString(), IngresoCajaSearchJson.class);

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
}
