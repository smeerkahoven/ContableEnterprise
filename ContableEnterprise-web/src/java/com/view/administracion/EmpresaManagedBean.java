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
import com.view.ViewManagedBean;
import com.view.menu.Formulario;
import com.view.resources.Mensajes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Cheyo
 */
@Named(value = "empresa")
@RequestScoped
public class EmpresaManagedBean extends ViewManagedBean {

    private String errorMessage;

    private String successMessage;

    private String warningMessage;

    private Mensajes m;

    private Formulario formSucursal;

    private Part file;

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

            this.formulario = SessionUtils.getFormulario(Formulario.EMPRESA);

            this.formSucursal = SessionUtils.getFormulario(Formulario.SUCURSAL);
        } catch (CRUDException ex) {
            Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void validateFile(FacesContext ctx,
            UIComponent comp,
            Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        //KB m
        if ((file.getSize() / 1024 / 1024) > 1024) {
            msgs.add(new FacesMessage("Ingrese un archivo de Max 1 MB"));
        }
        if (!"image/png".equals(file.getContentType())
                && (!"image/jpg".equals(file.getContentType()))) {
            msgs.add(new FacesMessage("Ingrese un archivo con extension JPG o PNG"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    public EmpresaManagedBean() {
        this.formName = "empresa" ;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

            try {
                long size = file.getSize();
                InputStream input = file.getInputStream();
                byte[] buffer = new byte[(int) size];
                input.read(buffer, 0, (int) size);
                input.close();

                data.setLogo(buffer);
            } catch (IOException ex) {
                Logger.getLogger(EmpresaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }

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
