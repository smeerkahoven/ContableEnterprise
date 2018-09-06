/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.token.app;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Cheyo
 */
@Path("api-token")
public class TokenResource {

    @Context
    private ServletContext context;

    /**
     * Creates a new instance of TokenResource
     */
    public TokenResource() {
    }

    /**
     * Retrieves representation of an instance of com.token.app.TokenResource
     *
     * @param value
     * @return an instance of java.lang.String
     */
    @POST
    @Path("{value}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getJson(@PathParam("value") String value) {

        Properties prop = new Properties();

        try {
            String realPath = context.getRealPath("/WEB-INF/config/Key.properties");

            prop.load(new FileInputStream(new File(realPath)));

            //TODO return proper representation object
            String key = prop.getProperty("KEY");
            String expiration = prop.getProperty("EXPIRATION");
            System.out.println(key);

            long time = System.currentTimeMillis();
            Algorithm algorithm = Algorithm.HMAC256(key);
            String token = JWT.create()
                    .withIssuedAt(new Date(time))
                    .withExpiresAt(new Date(time + Integer.parseInt(expiration)))
                    .withIssuer("auth0")
                    .withClaim("value", value)
                    .sign(algorithm);
            System.out.println(token);
            JsonObject json = (JsonObject) Json.createObjectBuilder().add("token", token).build();

            return Response.status(Response.Status.CREATED).entity(json).build();

        } catch (IOException ex) {
            Logger.getLogger(TokenResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @POST
    @Path("token/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getToken(@PathParam("value") String value) {

        Properties prop = new Properties();

        try {
            String realPath = context.getRealPath("/WEB-INF/config/Key.properties");

            prop.load(new FileInputStream(new File(realPath)));

            //TODO return proper representation object
            String key = prop.getProperty("KEY");
            String expiration = prop.getProperty("EXPIRATION");
            System.out.println(key);

            long time = System.currentTimeMillis();
            Algorithm algorithm = Algorithm.HMAC256(key);
            String token = JWT.create()
                    .withIssuedAt(new Date(time))
                    .withExpiresAt(new Date(time + Integer.parseInt(expiration)))
                    .withIssuer("auth0")
                    .withClaim("value", value)
                    .sign(algorithm);
            System.out.println(token);
            JsonObject json = (JsonObject) Json.createObjectBuilder().add("token", token).build();

            return Response.status(Response.Status.CREATED).entity(json).build();

        } catch (IOException ex) {
            Logger.getLogger(TokenResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * PUT method for updating or creating an instance of TokenResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
