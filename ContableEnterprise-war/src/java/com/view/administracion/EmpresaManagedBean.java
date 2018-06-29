/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.administracion;

import com.security.SessionUtils;
import com.seguridad.control.entities.Empresa;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.EmpresaRemote;
import com.view.menu.Formulario;
import com.view.resources.Mensajes;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Cheyo
 */
@Named(value = "empresa")
@RequestScoped
public class EmpresaManagedBean implements Serializable {

    

    private String errorMessage;

    private String successMessage;

    private String warningMessage;

    private Mensajes m;

    private Formulario formSucursal;

    static final long serialVersionUID = 42L;
    /**
     * Creates a new instance of EmpresaManagedBean
     */
    @EJB
    private EmpresaRemote ejbEmpresa;

    private Empresa data;

    @PostConstruct
    public void init() {
        try {
            Empresa e = new Empresa();
            e.setTipo(Empresa.PRINCIPAL);
            data = ejbEmpresa.getPrincipal(data);
            System.out.print(data);

            this.formSucursal = SessionUtils.getFormulario(Formulario.SUCURSAL);
        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public EmpresaManagedBean() {

    }

    public Empresa getData() {
        return data;
    }

    public void setData(Empresa data) {
        this.data = data;
    }

    public void guardar() {
        try {
            m = Mensajes.getMensajes();

            FacesContext fc = FacesContext.getCurrentInstance().getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
            System.out.println(params.get("txtPaginaWeb"));
            data.setRazonSocial(params.get("txtRazonSocial"));
            data.setDireccion(params.get("txtDireccion"));
            data.setNit(params.get("txtNIT"));
            data.setTelefonoFijo(params.get("txtTelefonoFijo"));
            data.setTelefonoCelular(params.get("txtTelefonoCelular"));
            data.setNroIata(params.get("txtNroIata"));
            data.setEmail(params.get("txtEmail"));
            data.setPaginaWeb(params.get("txtPaginaWeb"));

            ejbEmpresa.update(data);
            successMessage = m.getProperty(Mensajes.DATOS_GUARDADOS_EXITOSAMENTE);

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage = m.getProperty(Mensajes.ERROR_ACTUALIZAR_DATOS);
        }
    }

    public void guardarSucursal() {
        try {
            m = Mensajes.getMensajes();

            FacesContext fc = FacesContext.getCurrentInstance().getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
            System.out.println(params.get("txtPaginaWeb"));
            data.setRazonSocial(params.get("txtRazonSocial"));
            data.setDireccion(params.get("txtDireccion"));
            data.setNit(params.get("txtNIT"));
            data.setTelefonoFijo(params.get("txtTelefonoFijo"));
            data.setTelefonoCelular(params.get("txtTelefonoCelular"));
            data.setNroIata(params.get("txtNroIata"));
            data.setEmail(params.get("txtEmail"));
            data.setPaginaWeb(params.get("txtPaginaWeb"));
            data.setTipo(Empresa.SUCURSAL);

            ejbEmpresa.insert(data);
            successMessage = m.getProperty(Mensajes.DATOS_GUARDADOS_EXITOSAMENTE);

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage = m.getProperty(Mensajes.ERROR_ACTUALIZAR_DATOS);
        }
    }

    public void actualizarSucursal() {
        try {
            m = Mensajes.getMensajes();

            FacesContext fc = FacesContext.getCurrentInstance().getCurrentInstance();
            Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
            System.out.println(params.get("txtPaginaWeb"));
            data.setRazonSocial(params.get("txtRazonSocial"));
            data.setDireccion(params.get("txtDireccion"));
            data.setNit(params.get("txtNIT"));
            data.setTelefonoFijo(params.get("txtTelefonoFijo"));
            data.setTelefonoCelular(params.get("txtTelefonoCelular"));
            data.setNroIata(params.get("txtNroIata"));
            data.setEmail(params.get("txtEmail"));
            data.setPaginaWeb(params.get("txtPaginaWeb"));
            data.setIdEmpresa(Integer.parseInt(params.get("txtIdEmpresa")));
            data.setTipo(Formulario.SUCURSAL);

            ejbEmpresa.update(data);
            successMessage = m.getProperty(Mensajes.DATOS_GUARDADOS_EXITOSAMENTE);

        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            errorMessage = m.getProperty(Mensajes.ERROR_ACTUALIZAR_DATOS);
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public Formulario getFormSucursal() {

        return formSucursal;
    }

    public void setFormSucursal(Formulario formSucursal) {
        this.formSucursal = SessionUtils.getFormulario(Formulario.SUCURSAL);
    }

}
