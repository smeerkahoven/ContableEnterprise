/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security;

/**
 *
 * @author Cheyo
 */
import com.view.login.LoginManagedBean;
import com.view.menu.Formulario;
import com.view.menu.Menu;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    static final long serialVersionUID = 42L;
    public static final String SESION_USUARIO = "SESION_USUARIO";
    public static final String SESION_MENU = "SESION_MENU";
    public static final String SESION_LOGIN = "SESION_LOGIN";

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    /**
     *
     * @param s Nombre de la sesion
     * @param o Objecto de la Session
     */
    public static void setSession(String s, Object o) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        session.setAttribute(s, o);
    }

    /**
     *
     * @param s Nombre de la session
     * @return
     */
    public static Object getSession(String s) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute(s);
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userid");
        } else {
            return null;
        }
    }

    /**
     * Este meotodo sera cambiado mas adelante para poder cargar los datos del
     * formuario
     *
     * @param page
     * @return
     */
    public static Formulario getFormulario(String page) {
        List<Menu> l = (List<Menu>) SessionUtils.getSession(SESION_MENU);
        if (l == null) {
            return new Formulario();
        }
        for (Menu me : l) {
            for (Menu sub : me.getSubmenus()) {
                if (sub.getAction() != null) {
                    if (sub.getAction().equals(page)) {
                        return sub.getFormulario();
                    }
                }
            }

        }

        return new Formulario();
    }
}
