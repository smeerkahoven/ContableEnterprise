/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.seguridad;

import com.response.json.ModuloJSON;
import com.response.json.RolJSON;
import com.seguridad.control.entities.Formulario;
import com.seguridad.control.entities.Rol;
import com.seguridad.control.entities.RolFormulario;
import com.seguridad.control.entities.UserToken;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.RolRemoto;
import com.seguridad.control.remote.UsuarioRemote;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.ResponseCode;
import com.seguridad.utils.Status;
import com.services.seguridad.util.RestRequest;
import com.services.seguridad.util.RestResponse;
import com.util.resource.Mensajes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author xeio
 */
@Path("roles")
public class RolesResource {

    private Queries queries = Queries.getQueries();

    @EJB
    private UsuarioRemote ejbUsuario;

    @EJB
    private RolRemoto ejbRol;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RolesResource
     */
    public RolesResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.services.seguridad.RolesResource
     *
     * @param request
     * @return an instance of java.lang.String
     */
    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse get(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<Object[]> l = ejbRol.get(queries.getPropertie(Queries.GET_MODULO_NUEVO));
                        LinkedList<ModuloJSON> lm = new LinkedList<>();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(create(l));

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;
    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getEdit(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {
                        Rol rol = new Rol();
                        HashMap<String, Object> h = (HashMap<String, Object>) request.getContent();
                        rol.setIdRol(((BigDecimal) h.get("idRol")).intValue());
                        rol = ejbRol.get(rol);

                        RolJSON rolJson = new RolJSON(rol);

                        List<Object[]> l = (List<Object[]>) ejbRol.get(queries.getPropertie(Queries.GET_MODULO_EDIT), rol);
                        rolJson.setPermisos(create(l));

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(rolJson);

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse guardar(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        guardarPermisos(request.getContent());

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse update(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        updatePermisos(request.getContent());

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse delete(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        HashMap<String, Object> hmap = (HashMap<String, Object>) request.getContent();
                        Rol rol = new Rol();
                        rol.setIdRol(((BigDecimal) hmap.get("rolid")).intValue());

                        ejbRol.remove(rol);

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_SUCCESS));

                        return r;

                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param request
     * @return
     */
    @POST
    @Path("all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse getAll(final RestRequest request) {

        Mensajes m = Mensajes.getMensajes().getMensajes();
        RestResponse r = new RestResponse();
        try {
            /*Verificamos el ID token*/
            if (request.getToken() != null && !request.getToken().isEmpty()) {
                System.out.println(request.getToken());
                UserToken t = ejbUsuario.get(new UserToken(request.getToken()));
                if (t != null) {
                    if (t.getStatus().equals(Status.ACTIVO)) {

                        List<Rol> l = ejbRol.get();

                        r.setCode(ResponseCode.RESTFUL_SUCCESS.getCode());
                        r.setContent(l);

                        return r;
                    } else {
                        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                        r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                    }
                } else {
                    r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                    r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
                }
            } else {
                r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
                r.setContent(m.getProperty(RestResponse.RESTFUL_TOKEN_MANDATORY));
            }

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaServices.class.getName()).log(Level.SEVERE, null, ex);
            r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
            r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));
        }
        r.setCode(ResponseCode.RESTFUL_ERROR.getCode());
        r.setContent(m.getProperty(RestResponse.RESTFUL_ERROR));

        return r;

    }

    /**
     *
     * @param o
     * @param lm
     * @return
     */
    private ModuloJSON getModuloJSON(Object[] o, List<ModuloJSON> lm) {
        Iterator<ModuloJSON> it = lm.iterator();
        while (it.hasNext()) {
            ModuloJSON maux = (ModuloJSON) it.next();
            if (maux.getModuloNombre().equals(o[1])) {
                return maux;
            }
        }
        return null;
    }

    /**
     *
     * @param l
     * @return
     */
    private List<ModuloJSON> create(List<Object[]> l) {
        ArrayList<ModuloJSON> lm = new ArrayList<>();

        for (Object[] o : l) {
            if (lm.isEmpty()) {
                ModuloJSON mjson = new ModuloJSON(o);
                mjson.addFormulario(o);
                lm.add(mjson);
            } else {
                ModuloJSON mjson = getModuloJSON(o, lm);
                if (mjson == null) {
                    mjson = new ModuloJSON(o);
                    mjson.addFormulario(o);
                    lm.add(mjson);
                } else {
                    mjson.addFormulario(o);
                }
            }
        }

        return lm;
    }

    /**
     * PUT method for updating or creating an instance of RolesResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     *
     * @param content
     * @throws CRUDException
     */
    private void guardarPermisos(Object content) throws CRUDException {

        HashMap<String, Object> request = (HashMap<String, Object>) content;
        Rol r = new Rol();
        r.setNombre((String) request.get("rolnombre"));
        r.setStatus((String) request.get("rolstatus"));
        r.setFechaAlta(DateContable.getCurrentDate());
        //insertamos el rol
        r.setIdRol(ejbRol.insert(r));

        ArrayList l = (ArrayList) request.get("list");
        for (Object h : l) {
            HashMap<String, Object> hmap = (HashMap<String, Object>) h;

            ArrayList plist = (ArrayList) hmap.get("flist");
            for (Object ph : plist) {
                HashMap<String, Object> hperm = (HashMap<String, Object>) ph;
                RolFormulario rf = new RolFormulario();
                rf.setIdRol(r);
                rf.setIdFormularios(new Formulario(((BigDecimal) hperm.get("idFormulario")).intValue()));

                rf.setIdFormularios(new Formulario(((BigDecimal) hperm.get("idFormulario")).intValue()));
                rf.setAcceder((((BigDecimal) hperm.get("rolAcceder")).shortValue()));
                rf.setVer((((BigDecimal) hperm.get("rolVer")).shortValue()));
                rf.setActualizar((((BigDecimal) hperm.get("rolActualizar")).shortValue()));
                rf.setEditar((((BigDecimal) hperm.get("rolEditar")).shortValue()));
                rf.setBuscar((((BigDecimal) hperm.get("rolBuscar")).shortValue()));
                rf.setCrear((((BigDecimal) hperm.get("rolCrear")).shortValue()));
                rf.setEliminar((((BigDecimal) hperm.get("rolEliminar")).shortValue()));
                rf.setAnular((((BigDecimal) hperm.get("rolAnular")).shortValue()));

                //insertamos el permiso
                ejbRol.insert(rf);

            }
        }

    }

    /**
     *
     * @param content
     * @throws CRUDException
     */
    private void updatePermisos(Object content) throws CRUDException {

        HashMap<String, Object> request = (HashMap<String, Object>) content;
        Rol r = new Rol();
        r.setNombre((String) request.get("nombre"));
        r.setStatus((String) request.get("status"));
        r.setIdRol((((BigDecimal) request.get("idRol")).intValue()));
        //r.setFechaAlta(DateContable.getCurrentDate());
        //insertamos el rol
        ejbRol.update(r);

        ArrayList l = (ArrayList) request.get("permisos");
        for (Object h : l) {
            HashMap<String, Object> hmap = (HashMap<String, Object>) h;

            ArrayList plist = (ArrayList) hmap.get("flist");
            for (Object ph : plist) {
                HashMap<String, Object> hperm = (HashMap<String, Object>) ph;

                Integer idRolFormulario = ((BigDecimal) hperm.get("idRolFormulario")).intValue();

                RolFormulario rf = ejbRol.get(idRolFormulario);

                if (rf != null) {
                    rf.setIdRol(r);

                    rf.setIdFormularios(new Formulario(((BigDecimal) hperm.get("idFormulario")).intValue()));

                    rf.setAcceder((((BigDecimal) hperm.get("rolAcceder")).shortValue()));
                    rf.setVer((((BigDecimal) hperm.get("rolVer")).shortValue()));
                    rf.setActualizar((((BigDecimal) hperm.get("rolActualizar")).shortValue()));
                    rf.setEditar((((BigDecimal) hperm.get("rolEditar")).shortValue()));
                    rf.setBuscar((((BigDecimal) hperm.get("rolBuscar")).shortValue()));
                    rf.setCrear((((BigDecimal) hperm.get("rolCrear")).shortValue()));
                    rf.setEliminar((((BigDecimal) hperm.get("rolEliminar")).shortValue()));
                    rf.setAnular((((BigDecimal) hperm.get("rolAnular")).shortValue()));

                    //insertamos el permiso
                    ejbRol.update(rf);
                }
            }
        }

    }
}
