/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control;

import com.seguridad.control.encryption.EncryptionContable;
import com.seguridad.utils.Status;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.LoggerRemote;
import com.seguridad.control.remote.LoginRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.Accion;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Navegacion;
import com.seguridad.utils.ResponseCode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Login
 * @author Cheyo
 */
@Stateful(passivationCapable = false)
public class Login implements LoginRemote {

    static final long serialVersionUID = 42L;

    private int intento = 0;
    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private LoggerRemote ejbLogger;

    @Override
    public ResponseCode autenticar(User u) throws CRUDException {

        User aux = ejbUsuario.get(u);
        // No existe el usuario
        if (aux == null) {
            ejbLogger.add(Accion.LOGIN, u.getUserName(), Navegacion.LOGIN, u.getIp());
            return ResponseCode.USUARIO_INEXISTENTE;
        }

        // Contraseña erronea
        intento++;
        if (!aux.getPassword().equals(EncryptionContable.encrypt(u.getPassword()))) {
            ejbLogger.add(Accion.LOGIN, u.getUserName(), Navegacion.LOGIN, u.getIp());
            ejbLogger.add(u.getIp(), intento, u.getUserName());
            return ResponseCode.CREDENCIALES_INCORRECTA;
        }

        //
        if (aux.getStatus().equals(Status.INACTIVE)) {
            ejbLogger.add(Accion.LOGIN, u.getUserName(), Navegacion.LOGIN, u.getIp());
            ejbLogger.add(u.getIp(), intento, u.getUserName());
            return ResponseCode.USUARIO_INACTIVO;
        }

        return ResponseCode.USUARIO_AUTORIZADO;

    }

    @Override
    public void logOut(User u, UserLogin ul) throws CRUDException {
        try {
            UserToken t = new UserToken();
            t.setIdToken(u.getToken_url());
            t.setStatus(Status.INACTIVE);
            t.setUserName(u.getUserName());
            t.setFechaToken(DateContable.getCurrentDate());

            ejbUsuario.update(t);
            ejbUsuario.update(ul);

            ejbLogger.add(Accion.LOGOUT, u.getUserName(), Navegacion.LOGOUT, u.getIp());
        } catch (CRUDException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
