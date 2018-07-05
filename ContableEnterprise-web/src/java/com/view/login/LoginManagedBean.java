/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.login;

import com.security.SessionUtils;
import com.seguridad.control.LoggerContable;
import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.User;
import com.seguridad.control.entities.UserLogin;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpresaRemote;
import com.seguridad.control.remote.LoginRemote;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.mensajes.BeanMensaje;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Navegacion;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.service.resource.TokenService;
import com.view.administracion.EmpresaManagedBean;
import com.view.menu.Formulario;
import com.view.menu.Menu;
import com.view.resources.Mensajes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Cheyo
 */
@Named(value = "login")
@ManagedBean
@RequestScoped()
public class LoginManagedBean implements Serializable {

    static final long serialVersionUID = 42L;

    private String usuario;
    private String password;

    private final Mensajes mensaje;

    private String errorMessage;

    private String razonSocial;
    @EJB
    private EmpresaRemote ejbEmpresa;

    @EJB
    private LoginRemote ejbLogin;

    @EJB
    private UsuarioRemote ejbUsuario;

    private Empleado empleado;

    public Empleado getEmpleado() {

        User u = (User) SessionUtils.getSession(SessionUtils.SESION_USUARIO);
        empleado = u.getIdEmpleado();
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
        mensaje = Mensajes.getMensajes();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Metodo que es llamado por el boton de la pantalla de login
     *
     * @return
     */
    public String doLogin() {

        try {
            User u = new User();
            u.setUserName(usuario);
            u.setPassword(password);
            u.setIp(getIp());

            ResponseCode r = ejbLogin.autenticar(u);

            if (null != r) {
                switch (r) {
                    case CREDENCIALES_INCORRECTA:
                        errorMessage = mensaje.getProperty(BeanMensaje.CREDENCIALES_INCORRECTA);
                        return Navegacion.LOGIN;
                    case USUARIO_INEXISTENTE:
                        errorMessage = mensaje.getProperty(BeanMensaje.USUARIO_INEXISTENTE);
                        return Navegacion.LOGIN;
                    case USUARIO_NO_AUTORIZADO:
                        errorMessage = mensaje.getProperty(BeanMensaje.USUARIO_NO_AUTORIZADO);
                        return Navegacion.LOGIN;
                    case USUARIO_INACTIVO:
                        errorMessage = mensaje.getProperty(BeanMensaje.USUARIO_INACTIVO);
                        return Navegacion.LOGIN;
                    case USUARIO_AUTORIZADO:

                        if (acceder(u)) {
                            return Navegacion.INICIO;
                        }
                        errorMessage = mensaje.getProperty(BeanMensaje.GENERAR_TOKEN);

                    default:
                        break;
                }
            } else {
                return Navegacion.LOGIN;
            }

        } catch (CRUDException ex) {
            LoggerContable.log(ex.getMessage(), this, Level.SEVERE);
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Navegacion.LOGIN;

    }

    /**
     * Se obtiene el token, y se establecen en sesion el usuario y el menu
     *
     * @param u
     * @return
     */
    private boolean acceder(User u) {
        try {
            TokenService t = new TokenService(usuario);
            u.setToken_url(t.getToken(usuario));
            if (u.getToken_url() == null || u.getToken_url().isEmpty()) {
                return false;
            }

            UserToken ut = getUserToken(u, Status.ACTIVO);
            ejbUsuario.insert(ut);

            //u= ejbUsuario.get(u);
            UserLogin ul = getUserLogin(u, Status.ACTIVO);
            ul.setIdUserLogin(ejbUsuario.insert(ul));
            
            User sessionUser = ejbUsuario.get(u);
            sessionUser.setToken_url(u.getToken_url());
            sessionUser.setIp(u.getIp());

            SessionUtils.setSession(SessionUtils.SESION_USUARIO, sessionUser);

            SessionUtils.setSession(SessionUtils.SESION_MENU, createNavegacion(u));

            SessionUtils.setSession(SessionUtils.SESION_LOGIN, ul);

            return true;
        } catch (CRUDException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private UserLogin getUserLogin(User u, String status) {
        UserLogin ul = new UserLogin();
        ul.setFechaLogin(DateContable.getCurrentDate());
        ul.setIp(getIp());
        ul.setNroIntento(1);
        ul.setUserName(u.getUserName());

        return ul;
    }

    private UserToken getUserToken(User u, String status) {
        UserToken ut = new UserToken();
        ut.setFechaToken(DateContable.getCurrentDate());
        ut.setStatus(status);
        ut.setUserName(u.getUserName());
        ut.setIdToken(u.getToken_url());
        return ut;
    }

    private Formulario crearFormulario(Object[] o) {
        com.view.menu.Formulario f = new com.view.menu.Formulario();
        f.setNombre((String) o[2]);
        f.setRestful((String) o[4]);
        f.setStatus((String) o[5]);

        f.setAcceder((int) o[6]);
        f.setActualizar((int) o[7]);
        f.setBuscar((int) o[8]);
        f.setCrear((int) o[9]);
        f.setEliminar((int) o[10]);
        f.setModulo((String)o[0]);
        
        return f;
    }

    private Menu crearMenu(Object[] o) {
        Menu submenu = new Menu();
        submenu.setNombre((String) o[2]);
        submenu.setAction((String) o[3]);
        submenu.setStatus((String) o[5]);
        //submenu.setClassMenu((String)o[10]);

        return submenu;
    }

    private Menu crearModulo(Object[] o) {
        Menu submenu = new Menu();
        submenu.setNombre((String) o[0]);
        //submenu.setAction((String) o[3]);
        submenu.setStatus((String) o[1]);
        submenu.setClassMenu((String) o[11]);
        submenu.setUrlAcceso((String) o[12]);

        return submenu;
    }

    /**
     * Crea la navegacion la cual debe subirse a la memoria de la Sesion bajo el
     * nombre se SESION_MENU
     *
     * @param u
     * @return
     */
    private List<Menu> createNavegacion(User u) {
        try {
            User ux = ejbUsuario.get(u);
            HashMap<String, Object> h = new HashMap<>();
            h.put("1", ux.getIdRol().getIdRol());

            List<Object[]> l = ejbUsuario.get("GET_MENU", h);
            List<Menu> modulos = new ArrayList<>();
            for (Object[] o : l) {
                System.out.println(o[0]);
                if (modulos.isEmpty()) {
                    Menu menu = crearModulo(o);

                    Menu submenu = crearMenu(o);
                    submenu.setFormulario(crearFormulario(o));

                    menu.getSubmenus().add(submenu);

                    modulos.add(menu);
                } else {
                    Iterator i = modulos.iterator();
                    boolean ok = false;
                    while (i.hasNext() && !ok) {
                        Menu modulo = (Menu) i.next();

                        if (modulo.compareTo((String) o[0]) == Menu.IGUAL) {
                            Menu submenu = crearMenu(o);
                            submenu.setFormulario(crearFormulario(o));

                            modulo.getSubmenus().add(submenu);
                            ok = true;
                        }
                    }

                    if (!ok) {
                        Menu menu = crearModulo(o);

                        Menu submenu = crearMenu(o);
                        submenu.setFormulario(crearFormulario(o));

                        menu.getSubmenus().add(submenu);

                        modulos.add(menu);
                    }
                }

            }

            return modulos;
        } catch (CRUDException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String getIp() {
        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //System.out.println("IP:" + request.getRemoteAddr());
        return request.getRemoteAddr();
    }

    /**
     * Menu de la pantalla de inicio
     *
     * @return
     */
    public String getRazonSocial() {
        try {
            Empresa e = new Empresa();
            e.setIdEmpresa(1);
            razonSocial = ejbEmpresa.get(e).getRazonSocial();

            return razonSocial;

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "<Ingrese una empresa>";
    }

    /**
     * Cierra las sesiones
     *
     * @return
     * @throws CRUDException
     */
    public String logout() throws CRUDException {
        User u = (User) SessionUtils.getSession(SessionUtils.SESION_USUARIO);
        UserLogin ul = (UserLogin) SessionUtils.getSession(SessionUtils.SESION_LOGIN);
        if (u != null) {
            ul.setFechaLogout(DateContable.getCurrentDate());
            ejbLogin.logOut(u, ul);
            SessionUtils.setSession(SessionUtils.SESION_USUARIO, null);
            SessionUtils.setSession(SessionUtils.SESION_MENU, null);
        }

        System.out.println("SEsion del usuario :" + (User) SessionUtils.getSession(SessionUtils.SESION_USUARIO));
        return Navegacion.LOGIN;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
