/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.administracion;

import com.configuracion.entities.Parametros;
import com.configuracion.remote.ParametrosRemote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seguridad.control.entities.PasswordRecover;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import com.seguridad.utils.DateContable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author xeio
 */
@Named(value = "recover")
@Dependent
public class RecoverPasswordManagedBean {

    private Parametros parametros;

    private String username;

    @EJB
    private EmpleadoRemote ejbEmpleado;

    @EJB
    private ParametrosRemote ejbParametros;

    /**
     * Creates a new instance of RecoverPasswordManagedBean
     */
    public RecoverPasswordManagedBean() {

    }

    @PostConstruct
    private void init() {
        try {
            parametros = (Parametros) ejbParametros.get(new Parametros(Parametros.SYSTEM_RECOVER_PASSWORD_URL));

            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext external = context.getExternalContext();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("idPasswordRecover", external.getRequestParameterMap().get("p_id"));

            List l = (List) ejbEmpleado.get("PasswordRecover.findAt", PasswordRecover.class, parameters);
            if (!l.isEmpty()) {
                PasswordRecover password = (PasswordRecover) l.get(0);
                password.setEstado(PasswordRecover.INACTIVO);
                password.setFechaAcceso(DateContable.getCurrentDate());
                
                ejbEmpleado.update(password);
                
                if (password != null) {
                    String[] split = password.getIdPasswordRecover().split("\\.");
                    String base64EncodedHeader = split[0];
                    String base64EncodedBody = split[1];
                    String base64EncodedSignature = split[2];

                    Base64 base64Url = new Base64(true);
                    String body = new String(base64Url.decode(base64EncodedBody));

                    Gson gson = new GsonBuilder().create();
                    JsonParser parser = new JsonParser();
                    JsonObject object = parser.parse(body).getAsJsonObject();
                    username = (String) object.get("value").getAsString();

                } else {
                    //reenvia pagina de error
                    System.out.println("No hay PasswordFindAt");
                }
                System.out.println("Viene del usuario:" + external.getRequestParameterMap().get("p_id"));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(RecoverPasswordManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarContrasena() {

    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
