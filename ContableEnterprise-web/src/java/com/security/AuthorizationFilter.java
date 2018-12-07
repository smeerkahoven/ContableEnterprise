/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security;

import com.view.menu.Menu;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    static final long serialVersionUID = 42L;

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            System.out.println("Filtering ..");
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String servletPath = reqt.getServletPath();
            System.out.println("Accessing to .." + servletPath);
            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute(SessionUtils.SESION_USUARIO) != null)
                    || reqURI.indexOf("/public/") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                if (ses != null) {
                    if (servletPath.equals("/pages/index/index.xhtml")
                            || servletPath.equals("/pages/index/error404.xhtml")
                            || servletPath.equals("/pages/index/error500.xhtml")
                            || servletPath.equals("/pages/index/error505.xhtml")) {
                        chain.doFilter(request, response);
                    } else {
                        Optional op = Optional.ofNullable(ses.getAttribute(SessionUtils.SESION_MENU));
                        if (servletPath.contains("/javax.faces.resource/jsf.js.xhtml")) {
                            chain.doFilter(request, response);
                        } else {
                            if (op.isPresent()) {
                                boolean ok = false;
                                List<Menu> l = (List<Menu>) ses.getAttribute(SessionUtils.SESION_MENU);
                                for (Menu m : l) {
                                    if (m.getSubmenus() != null) {
                                        for (Menu sb : m.getSubmenus()) {
                                            if (servletPath.equals(sb.getFormulario().getFullPath())) {
                                                if (sb.getFormulario().getAcceder() == Menu.IGUAL) {
                                                    ok = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (ok) {
                                        break;
                                    }
                                }
                                /*if (l.size() > 0) {
                                chain.doFilter(request, response);
                            } else {
                                resp.sendRedirect(reqt.getContextPath() + "/pages/index/error505.xhtml");
                            }*/
                                //
                                if (!ok) {
                                    System.out.println("Sigue ingresando al ERROR 505");
                                    resp.sendRedirect(reqt.getContextPath() + "/pages/index/error505.xhtml");
                                } else {
                                    System.out.println("Sigue ingresando al Ingresa al doFilter");
                                    chain.doFilter(request, response);
                                }
                            } else {
                                chain.doFilter(request, response);
                            }
                        }

                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
