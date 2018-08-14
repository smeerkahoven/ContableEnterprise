/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.resource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:TokenResource
 * [api-token/{value}]<br>
 * USAGE:
 * <pre>
 *        TokenService client = new TokenService();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Cheyo
 */
public class TokenService {

    static final long serialVersionUID = 42L;
    
    private WebTarget webTarget;
    private Client client;
    private String BASE_URI ;//= "http://localhost:8080/ContableEntrepriseToken-app/auth";

    public TokenService(String value) {
        readResource();
        client = javax.ws.rs.client.ClientBuilder.newClient();
        String resourcePath = java.text.MessageFormat.format("api-token/{0}", new Object[]{value});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void setResourcePath(String value) {
        String resourcePath = java.text.MessageFormat.format("api-token/{0}", new Object[]{value});
        webTarget = client.target(BASE_URI).path(resourcePath);
    }

    public void putJson(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    private Response getJson(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }
    
    private void readResource(){
        try {
            Properties prop = new Properties();
                        
            InputStream realPath =this.getClass().getResourceAsStream("Parameters.properties");           
            prop.load(realPath);
            BASE_URI = prop.getProperty("TOKEN_API");
            System.out.println(BASE_URI);
            realPath.close();
            
        } catch (Exception ex) {
            Logger.getLogger(TokenService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String getToken(String value){
         try {
            TokenService token = new TokenService(value);
            Response r = token.getJson(value);
            if (r.getStatus() == Response.Status.CREATED.getStatusCode()) {
                String str = Entity.json(r.readEntity(String.class)).getEntity();
                JsonFactory f = new JsonFactory();
                JsonParser p = f.createParser(str);
                p.nextValue();
                p.nextValue();
                //System.out.println("Valor del Token:" + p.getValueAsString());
                
                return p.getValueAsString();
            }

        } catch (IOException ex) {
            Logger.getLogger(TokenService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null ;
    }


}
