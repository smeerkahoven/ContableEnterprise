/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p")
    ,
    @NamedQuery(name = "Parametros.find", query = "SELECT p FROM Parametros p where p.idParametro =:idParametro")
})
public class Parametros extends Entidad {

    public static final String COMBO_CUENTA_VENTAS_AEROLINEA = "COMBO_CUENTA_VENTAS_AEROLINEA";
    public static final String COMBO_CUENTA_COMISIONES_AEROLINEA = "COMBO_CUENTA_COMISIONES_AEROLINEA";
    public static final String COMBO_CUENTA_DEVOLUCIONES_AEROLINEA = "COMBO_CUENTA_DEVOLUCIONES_AEROLINEA";
    public static final String COMBO_CUENTA_PASIVO_MAYORISTAS = "COMBO_CUENTA_PASIVO_MAYORISTAS";
    public static final String COMBO_CUENTA_COMISION_AGENCIA = "COMBO_CUENTA_COMISION_AGENCIA";
    public static final String COMBO_CUENTA_COMISION_PROMOTOR = "COMBO_CUENTA_COMISION_PROMOTOR";
    //-------------------------------------------------//
    public static final String URL_CAMBIO_UFV = "URL_CAMBIO_UFV";
    public static final String URL_CAMBIO_DOLAR = "URL_CAMBIO_DOLAR";
    //------------SISTEMA------------------------------//
    public static final String SYSTEM_NAME = "SYSTEM_NAME";
    public static final String SYSTEM_DESCRIPTION = "SYSTEM_DESCRIPTION";
    public static final String SYSTEM_VERSION = "SYSTEM_VERSION";
    public static final String SYSTEM_FULL_URL = "SYSTEM_FULL_URL";
    public static final String SYSTEM_RECOVER_PASSWORD_URL = "SYSTEM_RECOVER_PASSWORD_URL";
    public static final String SYSTEM_WEBSERVICES_URL = "SYSTEM_WEBSERVICES_URL";

    public static final String URL_RECOVER_PASSWORD = "URL_RECOVER_PASSWORD";
    public static final String EMAIL_ACCOUNT = "EMAIL_ACCOUNT";

    public static final String FACTOR_CAMBIARO_MAX_MIN = "FACTOR_CAMBIARO_MAX_MIN";
    public static final String DIFERENCIA_CAMBIO_INGRESOS="DIFERENCIA_CAMBIO_INGRESOS";
    public static final String DIFERENCIA_CAMBIO_EGRESOS="DIFERENCIA_CAMBIO_EGRESOS";
    public static final String CUENTA_AITB="CUENTA_AITB";
    
    //------------boletaje------------------------------------//
    public static final String PORCENTAJE_COMISION = "PORCENTAJE_COMISION";
    
    //------------archivos------------------------------------//
    public static final String FOLDER_FILES_AMADEUS="FOLDER_FILES_AMADEUS";
    public static final String FOLDER_FILES_SABRE="FOLDER_FILES_SABRE";
    
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_parametro", length = 64)
    private String idParametro;
    @Column(name = "valor", length = 128)
    private String valor;

    public Parametros() {
    }

    public Parametros(String idParametro) {
        this.idParametro = idParametro;
    }

    public Parametros(String idParametro, String valor) {
        this.idParametro = idParametro;
        this.valor = valor;
    }

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.configuracion.entities.Parametros[ idParametro=" + idParametro + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return 0;
    }

}
