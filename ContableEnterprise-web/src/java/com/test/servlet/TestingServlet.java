/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.servlet;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.entities.Modulo;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.RolFormulario;
import com.seguridad.control.entities.User;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpleadoRemote;
import com.seguridad.control.remote.EmpresaRemote;
import com.seguridad.control.remote.FormularioRemote;
import com.seguridad.control.remote.ModuloRemote;
import com.seguridad.control.remote.RolFormularioRemote;
import com.seguridad.control.remote.RolRemoto;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.utils.DateContable;
import com.view.login.LoginManagedBean;
import com.view.menu.Menu;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cheyo
 */
public class TestingServlet extends HttpServlet {

    @EJB
    private RolRemoto ejbRol;
    @EJB
    private RolFormularioRemote ejbRolFormulario;
    @EJB
    private FormularioRemote ejbFormulario;

    @EJB
    private ModuloRemote ejbModulo;

    static final long serialVersionUID = 42L;
    @EJB
    private EmpresaRemote remote;

    @EJB
    private EmpleadoRemote ejbEmpleado;

    @EJB
    private UsuarioRemote ejbUsuario;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestingServlet at " + request.getContextPath() + "</h1>");

            //crearEmpresa(out);
            //crearModulo(out);
            crearFormulario(out);
            //crearRol(out);
            //crearUsuario(out);
            crearRolFormulario(out);
            //getMenu(out);
            //createNavegacion(null);

            out.println("</body>");
            out.println("</html>");
        }
    }

    private void getMenu(PrintWriter out) {
        try {
            HashMap<String, Object> h = new HashMap<>();
            h.put("1", 1);

            List<Object[]> l = ejbUsuario.get("GET_MENU", h);
            List<Menu> modulos = new ArrayList<>();
            for (Object[] o : l) {
                System.out.println(o[0]);
                if (modulos.isEmpty()) {
                    Menu menu = new Menu();
                    menu.setNombre((String) o[0]);
                    menu.setStatus((String) o[1]);

                    Menu submenu = new Menu();
                    submenu.setNombre((String) o[2]);
                    submenu.setAction((String) o[3]);
                    submenu.setStatus((String) o[5]);

                    com.view.menu.Formulario f = new com.view.menu.Formulario();
                    f.setNombre((String) o[2]);
                    f.setRestful((String) o[4]);
                    f.setStatus((String) o[5]);

                    f.setAcceder((int) o[6]);
                    f.setActualizar((int) o[7]);
                    f.setBuscar((int) o[8]);
                    f.setCrear((int) o[9]);
                    f.setEliminar((int) o[10]);

                    submenu.setFormulario(f);

                    menu.getSubmenus().add(submenu);

                    modulos.add(menu);
                } else {
                    Iterator i = modulos.iterator();
                    boolean ok = false;
                    while (i.hasNext() && !ok) {
                        Menu modulo = (Menu) i.next();

                        if (modulo.compareTo((String) o[0]) == Menu.IGUAL) {

                            Menu submenu = new Menu();
                            submenu.setNombre((String) o[2]);
                            submenu.setAction((String) o[3]);
                            submenu.setStatus((String) o[5]);

                            com.view.menu.Formulario f = new com.view.menu.Formulario();
                            f.setNombre((String) o[2]);
                            f.setRestful((String) o[4]);
                            f.setStatus((String) o[5]);

                            f.setAcceder((int) o[6]);
                            f.setActualizar((int) o[7]);
                            f.setBuscar((int) o[8]);
                            f.setCrear((int) o[9]);
                            f.setEliminar((int) o[10]);

                            submenu.setFormulario(f);

                            modulo.getSubmenus().add(submenu);

                            //m.add(menu);
                            ok = true;
                        }
                    }

                    if (!ok) {
                        Menu menu = new Menu();
                        menu.setNombre((String) o[0]);
                        menu.setStatus((String) o[1]);

                        Menu submenu = new Menu();
                        submenu.setNombre((String) o[2]);
                        submenu.setAction((String) o[3]);
                        submenu.setStatus((String) o[5]);

                        com.view.menu.Formulario f = new com.view.menu.Formulario();
                        f.setNombre((String) o[2]);
                        f.setRestful((String) o[4]);
                        f.setStatus((String) o[5]);

                        f.setAcceder((int) o[6]);
                        f.setActualizar((int) o[7]);
                        f.setBuscar((int) o[8]);
                        f.setCrear((int) o[9]);
                        f.setEliminar((int) o[10]);

                        submenu.setFormulario(f);

                        menu.getSubmenus().add(submenu);

                        modulos.add(menu);
                    }
                }

            }

            for (Menu me : modulos) {
                System.out.println(me.getNombre());
                for (Menu mi : me.getSubmenus()) {
                    System.out.println("---" + mi.getNombre() + ":" + mi.getUrlAcceso());
                    //System.out.println(mi.getFormulario().getNombre());
                }
            }

        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private com.view.menu.Formulario crearFormulario(Object[] o) {
        com.view.menu.Formulario f = new com.view.menu.Formulario();
        f.setNombre((String) o[2]);
        f.setRestful((String) o[4]);
        f.setStatus((String) o[5]);

        f.setAcceder((int) o[6]);
        f.setActualizar((int) o[7]);
        f.setBuscar((int) o[8]);
        f.setCrear((int) o[9]);
        f.setEliminar((int) o[10]);
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
            HashMap<String, Object> h = new HashMap<>();
            h.put("1", 1);

            List<Object[]> l = ejbUsuario.get("GET_MENU", h);
            List<Menu> modulos = new ArrayList<>();
            for (Object[] o : l) {
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

            for (Menu me : modulos) {
                System.out.println(me.getNombre() + ":" + me.getUrlAcceso() + ":" + me.getClassMenu());
                for (Menu mi : me.getSubmenus()) {
                    System.out.println("---" + mi.getNombre() + ":" + mi.getUrlAcceso());
                    //System.out.println(mi.getFormulario().getNombre());
                }
            }
             
            return modulos;
        } catch (CRUDException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void crearRolFormulario(PrintWriter out) {
        RolFormulario rf = new RolFormulario();
        rf.setIdFormularios(new Formulario(15));
        rf.setIdRol(new Rol(1));
        rf.setAcceder(new Short("1"));
        rf.setActualizar(new Short("1"));
        rf.setBuscar(new Short("1"));
        rf.setCrear(new Short("1"));
        rf.setEliminar(new Short("1"));

        try {
            rf.setIdRolFormulario(ejbRolFormulario.insert(rf));
            out.println("<h1>Se creo el formulario " + rf.getIdRolFormulario() + "</h1>");
        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void crearFormulario(PrintWriter out) {
        Formulario f = new Formulario();
        f.setFechaAlta(DateContable.getCurrentDate());
        f.setIdModulo(new Modulo(5));
        f.setNombre("Contabilidad");
        f.setUrlAcceso("reportes/contabilidad");
        f.setRestfulUrl("http://localhost:8080/ContableEnterprise-ws/ws-api/reportes/contabilidad");
        f.setStatus("ACTIVO");

        try {
            ejbFormulario.insert(f);
            out.println("<h1>Se creo el formulario " + f.getIdFormulario() + "</h1>");
        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearModulo(PrintWriter ot) {
        try {
            Modulo m = new Modulo();
            m.setFechaAlta(DateContable.getCurrentDate());
            m.setNombre("Reportes");
            m.setStatus("ACTIVE");
            m.setClassMenu("");

            ejbModulo.insert(m);
        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearEmpresa(PrintWriter out) {
        Empresa e = new Empresa();
        e.setRazonSocial("Golondrina Tours");
        e.setDireccion("Calle Bolivar 123");
        e.setNit("11223344");
        e.setTelefonoFijo("33333");
        e.setTelefonoCelular("447785");
        e.setEmail("golondrina@tours.com");
        e.setPaginaWeb("www.golondrina.com");
        e.setNroIata("1231256");
        e.setTipo("PRINCIPAL");

        try {
            e.setIdEmpresa(1);
            int id = remote.insert(e);
            e = remote.get(e);
            out.println("<h1>Se creo la empresa " + e.getRazonSocial() + "</h1>");
        } catch (Exception ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crearUsuario(PrintWriter out) {
        try {
            Empleado e = new Empleado();
            e.setNombre("adminastrador");
            e.setApellido("administrador");
            e.setEmail("administrador@admin.com");
            e.setTelefono("777777");
            e.setFechaAlta(DateContable.getCurrentDate());
            e.setStatus("ACTIVO");
            //e.setIdEmpresa(1);
            e.setIdEmpleado(ejbEmpleado.insert(e));
            //e.setIdEmpleado(1);

            //Rol r = new Rol(1); //;
            Rol r = crearRol(out);

            User u = new User();
            u.setUserName("admin");
            u.setPassword("admin2");
            u.setStatus("ACTIVE");
            u.setFechaAlta(DateContable.getCurrentDate());
            u.setIdRol(r);
            u.setIdEmpleado(e);

            ejbUsuario.insert(u);

            //jbUsuario.get(u) ;
            u = ejbUsuario.get(u);
            System.out.println(u.getIdEmpleado().getNombre());

            out.println("<h1>Se creo la usuario " + u.getUserName() + "</h1>");

        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Rol crearRol(PrintWriter out) {
        try {
            Rol r = new Rol();
            r.setNombre("ADMINISTRADOR");
            r.setStatus("ACTIVO");
            r.setFechaAlta(DateContable.getCurrentDate());
            r.setIdRol(ejbRol.insert(r));
            return r;
        } catch (CRUDException ex) {
            Logger.getLogger(TestingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
