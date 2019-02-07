/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_com_promotor_aerolinea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComisionPromotorAerolinea.findAll", query = "SELECT c FROM ComisionPromotorAerolinea c")
    ,
@NamedQuery(name = "ComisionPromotorAerolinea.findComisiones", query = "SELECT c FROM ComisionPromotorAerolinea c WHERE c.idPromotor=:idPromotor and c.tipoAerolinea=:tipoAerolinea"),
@NamedQuery(name = "ComisionPromotorAerolinea.existComision", query = "SELECT c FROM ComisionPromotorAerolinea c WHERE c.idPromotor=:idPromotor and c.idAerolinea=:idAerolinea")
}
)
public class ComisionPromotorAerolinea extends Entidad {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id_comision_promotor")
    private Integer idComisionPromotor;

    @Column(name = "id_promotor", updatable = false)
    private Integer idPromotor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aerolinea", updatable = false)
    private Aerolinea idAerolinea;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_aerolinea", updatable = false)
    private String tipoAerolinea;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_comision", updatable = false)
    private BigDecimal montoComision;

    public ComisionPromotorAerolinea() {
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Aerolinea getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Aerolinea idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public ComisionPromotorAerolinea(Integer idComisionPromotor) {
        this.idComisionPromotor = idComisionPromotor;
    }

    public ComisionPromotorAerolinea(Integer idComisionPromotor, String tipoAerolinea, BigDecimal montoComision) {
        this.idComisionPromotor = idComisionPromotor;
        this.tipoAerolinea = tipoAerolinea;
        this.montoComision = montoComision;
    }

    public Integer getIdComisionPromotor() {
        return idComisionPromotor;
    }

    public void setIdComisionPromotor(Integer idComisionPromotor) {
        this.idComisionPromotor = idComisionPromotor;
    }

    public String getTipoAerolinea() {
        return tipoAerolinea;
    }

    public void setTipoAerolinea(String tipoAerolinea) {
        this.tipoAerolinea = tipoAerolinea;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComisionPromotor != null ? idComisionPromotor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComisionPromotorAerolinea)) {
            return false;
        }
        ComisionPromotorAerolinea other = (ComisionPromotorAerolinea) object;
        if ((this.idComisionPromotor == null && other.idComisionPromotor != null) || (this.idComisionPromotor != null && !this.idComisionPromotor.equals(other.idComisionPromotor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.ComisionPromotorAerolinea[ idComisionPromotor=" + idComisionPromotor + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return idComisionPromotor;
    }

}
