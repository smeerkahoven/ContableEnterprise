/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "tb_contabilidad_boletaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContabilidadBoletaje.findAll", query = "SELECT c FROM ContabilidadBoletaje c"),
    @NamedQuery(name = "ContabilidadBoletaje.find", query = "SELECT c FROM ContabilidadBoletaje c WHERE c.idEmpresa=:idEmpresa")

})
public class ContabilidadBoletaje extends Entidad {
    
    public static final String SI = "S";
    public static final String NO = "N";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa", updatable = false)
    private Integer idEmpresa;
    @Column(name = "id_total_boleto_bs")
    private Integer idTotalBoletoBs;
    @Column(name = "id_total_boleto_us")
    private Integer idTotalBoletoUs;
    @Column(name = "id_cuenta_fee")
    private Integer idCuentaFee;
    @Column(name = "id_descuentos")
    private Integer idDescuentos;
    @Size(max = 1)
    @Column(name = "emision_bolivianos")
    private String emisionBolivianos;
    @Size(max = 1)
    @Column(name = "emision_dolares")
    private String emisionDolares;

    public ContabilidadBoletaje() {
    }

    public ContabilidadBoletaje(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdTotalBoletoBs() {
        return idTotalBoletoBs;
    }

    public void setIdTotalBoletoBs(Integer idTotalBoletoBs) {
        this.idTotalBoletoBs = idTotalBoletoBs;
    }

    public Integer getIdTotalBoletoUs() {
        return idTotalBoletoUs;
    }

    public void setIdTotalBoletoUs(Integer idTotalBoletoUs) {
        this.idTotalBoletoUs = idTotalBoletoUs;
    }

    public Integer getIdCuentaFee() {
        return idCuentaFee;
    }

    public void setIdCuentaFee(Integer idCuentaFee) {
        this.idCuentaFee = idCuentaFee;
    }

    public Integer getIdDescuentos() {
        return idDescuentos;
    }

    public void setIdDescuentos(Integer idDescuentos) {
        this.idDescuentos = idDescuentos;
    }

    public String getEmisionBolivianos() {
        return emisionBolivianos;
    }

    public void setEmisionBolivianos(String emisionBolivianos) {
        this.emisionBolivianos = emisionBolivianos;
    }

    public String getEmisionDolares() {
        return emisionDolares;
    }

    public void setEmisionDolares(String emisionDolares) {
        this.emisionDolares = emisionDolares;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContabilidadBoletaje)) {
            return false;
        }
        ContabilidadBoletaje other = (ContabilidadBoletaje) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public int getId() throws CRUDException {
        return idEmpresa ;
    }
    
    

    @Override
    public String toString() {
        return "com.configuracion.ejb.ContabilidadBoletaje[ idEmpresa=" + idEmpresa + " ]";
    }
    
}
