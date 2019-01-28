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
    @NamedQuery(name = "ContabilidadBoletaje.findAll", query = "SELECT c FROM ContabilidadBoletaje c")
    ,
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

    @Column(name = "cuenta_efectivo_debe_bs")
    private Integer cuentaEfectivoDebeBs;
    @Column(name = "cuenta_efectivo_haber_bs")
    private Integer cuentaEfectivoHaberBs;
    @Column(name = "cuenta_efectivo_haber_usd")
    private Integer cuentaEfectivoDebeUsd;
    @Column(name = "cuenta_efectivo_debe_usd")
    private Integer cuentaEfectivoHaberUsd;

    @Column(name = "deposito_banco_haber_bs")
    private Integer depositoBancoHaberBs;
    @Column(name = "deposito_banco_debe_bs")
    private Integer depositoBancoDebeBs;
    @Column(name = "deposito_banco_debe_usd")
    private Integer depositoBancoDebeUsd;
    @Column(name = "deposito_banco_haber_usd")
    private Integer depositoBancoHaberUsd;

    @Column(name = "cuenta_efectivo_no_bsp_debe_bs")
    private Integer cuentaEfectivoNoBspDebeBs;

    @Column(name = "cuenta_efectivo_no_bsp_debe_usd")
    private Integer cuentaEfectivoNoBspDebeUsd;

    @Column(name = "tarjeta_credito_bsp_debe_bs")
    private Integer tarjetaCreditoBspDebeBs;

    @Column(name = "tarjeta_credito_bsp_debe_usd")
    private Integer tarjetaCreditoBspDebeUsd;

    @Column(name = "cuenta_efectivo_no_bsp_haber_bs")
    private Integer cuentaEfectivoNoBspHaberBs;

    @Column(name = "cuenta_efectivo_no_bsp_haber_usd")
    private Integer cuentaEfectivoNoBspHaberUsd;

    @Column(name = "tarjeta_credito_bsp_haber_bs")
    private Integer tarjetaCreditoBspHaberBs;

    @Column(name = "tarjeta_credito_bsp_haber_usd")
    private Integer tarjetaCreditoBspHaberUsd;

    @Column(name = "otros_cargos_cliente_cobrar_debe_bs")
    private Integer otrosCargosClienteCobrarDebeBs;

    @Column(name = "otros_cargos_cliente_cobrar_debe_usd")
    private Integer otrosCargosClienteCobrarDebeUsd;

    //Nota Credito
    @Column(name = "nota_credito_haber_bs")
    private Integer notaCreditoHaberBs;

    @Column(name = "nota_credito_haber_usd")
    private Integer notaCreditoHaberUsd;

    //Depositos Anticipados
    @Column(name = "deposito_cliente_anticipado_bs")
    private Integer depositoClienteAnticipadoBs;

    @Column(name = "deposito_cliente_anticipado_usd")
    private Integer depositoClienteAnticipadoUsd;

    //Acreditacion
    @Column(name = "acreditacion_deposito_anticipado_debe_bs")
    private Integer acreditacionDepositoAnticipadoDebeBs;

    @Column(name = "acreditacion_deposito_anticipado_haber_bs")
    private Integer acreditacionDepositoAnticipadoHaberBs;

    @Column(name = "acreditacion_deposito_anticipado_debe_usd")
    private Integer acreditacionDepositoAnticipadoDebeUsd;

    @Column(name = "acreditacion_deposito_anticipado_haber_usd")
    private Integer acreditacionDepositoAnticipadoHaberUsd;

    @Column(name = "devolucion_deposito_anticipado_haber_bs")
    private Integer devolucionDepositoAnticipadoHaberBs;

    @Column(name = "devolucion_deposito_anticipado_debe_bs")
    private Integer devolucionDepositoAnticipadoDebeBs;

    @Column(name = "devolucion_deposito_anticipado_haber_usd")
    private Integer devolucionDepositoAnticipadoHaberUsd;

    @Column(name = "devolucion_deposito_anticipado_debe_usd")
    private Integer devolucionDepositoAnticipadoDebeUsd;

    public ContabilidadBoletaje() {
    }

    public Integer getNotaCreditoHaberBs() {
        return notaCreditoHaberBs;
    }

    public void setNotaCreditoHaberBs(Integer notaCreditoHaberBs) {
        this.notaCreditoHaberBs = notaCreditoHaberBs;
    }

    public Integer getNotaCreditoHaberUsd() {
        return notaCreditoHaberUsd;
    }

    public void setNotaCreditoHaberUsd(Integer notaCreditoHaberUsd) {
        this.notaCreditoHaberUsd = notaCreditoHaberUsd;
    }

    public Integer getDepositoClienteAnticipadoBs() {
        return depositoClienteAnticipadoBs;
    }

    public void setDepositoClienteAnticipadoBs(Integer depositoClienteAnticipadoBs) {
        this.depositoClienteAnticipadoBs = depositoClienteAnticipadoBs;
    }

    public Integer getDepositoClienteAnticipadoUsd() {
        return depositoClienteAnticipadoUsd;
    }

    public void setDepositoClienteAnticipadoUsd(Integer depositoClienteAnticipadoUsd) {
        this.depositoClienteAnticipadoUsd = depositoClienteAnticipadoUsd;
    }

    public Integer getAcreditacionDepositoAnticipadoDebeBs() {
        return acreditacionDepositoAnticipadoDebeBs;
    }

    public void setAcreditacionDepositoAnticipadoDebeBs(Integer acreditacionDepositoAnticipadoDebeBs) {
        this.acreditacionDepositoAnticipadoDebeBs = acreditacionDepositoAnticipadoDebeBs;
    }

    public Integer getAcreditacionDepositoAnticipadoHaberBs() {
        return acreditacionDepositoAnticipadoHaberBs;
    }

    public void setAcreditacionDepositoAnticipadoHaberBs(Integer acreditacionDepositoAnticipadoHaberBs) {
        this.acreditacionDepositoAnticipadoHaberBs = acreditacionDepositoAnticipadoHaberBs;
    }

    public Integer getAcreditacionDepositoAnticipadoDebeUsd() {
        return acreditacionDepositoAnticipadoDebeUsd;
    }

    public void setAcreditacionDepositoAnticipadoDebeUsd(Integer acreditacionDepositoAnticipadoDebeUsd) {
        this.acreditacionDepositoAnticipadoDebeUsd = acreditacionDepositoAnticipadoDebeUsd;
    }

    public Integer getAcreditacionDepositoAnticipadoHaberUsd() {
        return acreditacionDepositoAnticipadoHaberUsd;
    }

    public void setAcreditacionDepositoAnticipadoHaberUsd(Integer acreditacionDepositoAnticipadoHaberUsd) {
        this.acreditacionDepositoAnticipadoHaberUsd = acreditacionDepositoAnticipadoHaberUsd;
    }

    public Integer getDevolucionDepositoAnticipadoHaberBs() {
        return devolucionDepositoAnticipadoHaberBs;
    }

    public void setDevolucionDepositoAnticipadoHaberBs(Integer devolucionDepositoAnticipadoHaberBs) {
        this.devolucionDepositoAnticipadoHaberBs = devolucionDepositoAnticipadoHaberBs;
    }

    public Integer getDevolucionDepositoAnticipadoDebeBs() {
        return devolucionDepositoAnticipadoDebeBs;
    }

    public void setDevolucionDepositoAnticipadoDebeBs(Integer devolucionDepositoAnticipadoDebeBs) {
        this.devolucionDepositoAnticipadoDebeBs = devolucionDepositoAnticipadoDebeBs;
    }

    public Integer getDevolucionDepositoAnticipadoHaberUsd() {
        return devolucionDepositoAnticipadoHaberUsd;
    }

    public void setDevolucionDepositoAnticipadoHaberUsd(Integer devolucionDepositoAnticipadoHaberUsd) {
        this.devolucionDepositoAnticipadoHaberUsd = devolucionDepositoAnticipadoHaberUsd;
    }

    public Integer getDevolucionDepositoAnticipadoDebeUsd() {
        return devolucionDepositoAnticipadoDebeUsd;
    }

    public void setDevolucionDepositoAnticipadoDebeUsd(Integer devolucionDepositoAnticipadoDebeUsd) {
        this.devolucionDepositoAnticipadoDebeUsd = devolucionDepositoAnticipadoDebeUsd;
    }

    public Integer getOtrosCargosClienteCobrarDebeBs() {
        return otrosCargosClienteCobrarDebeBs;
    }

    public void setOtrosCargosClienteCobrarDebeBs(Integer otroCargosClienteCobrarDebeBs) {
        this.otrosCargosClienteCobrarDebeBs = otroCargosClienteCobrarDebeBs;
    }

    public Integer getOtrosCargosClienteCobrarDebeUsd() {
        return otrosCargosClienteCobrarDebeUsd;
    }

    public void setOtrosCargosClienteCobrarDebeUsd(Integer otrosCargosClienteCobrarDebeUsd) {
        this.otrosCargosClienteCobrarDebeUsd = otrosCargosClienteCobrarDebeUsd;
    }

    public Integer getCuentaEfectivoNoBspDebeBs() {
        return cuentaEfectivoNoBspDebeBs;
    }

    public void setCuentaEfectivoNoBspDebeBs(Integer cuentaEfectivoNoBspDebeBs) {
        this.cuentaEfectivoNoBspDebeBs = cuentaEfectivoNoBspDebeBs;
    }

    public Integer getCuentaEfectivoNoBspDebeUsd() {
        return cuentaEfectivoNoBspDebeUsd;
    }

    public void setCuentaEfectivoNoBspDebeUsd(Integer cuentaEfectivoNoBspDebeUsd) {
        this.cuentaEfectivoNoBspDebeUsd = cuentaEfectivoNoBspDebeUsd;
    }

    public Integer getTarjetaCreditoBspDebeBs() {
        return tarjetaCreditoBspDebeBs;
    }

    public void setTarjetaCreditoBspDebeBs(Integer tarjetaCreditoBspDebeBs) {
        this.tarjetaCreditoBspDebeBs = tarjetaCreditoBspDebeBs;
    }

    public Integer getTarjetaCreditoBspDebeUsd() {
        return tarjetaCreditoBspDebeUsd;
    }

    public void setTarjetaCreditoBspDebeUsd(Integer tarjetaCreditoBspDebeUsd) {
        this.tarjetaCreditoBspDebeUsd = tarjetaCreditoBspDebeUsd;
    }

    public Integer getCuentaEfectivoNoBspHaberBs() {
        return cuentaEfectivoNoBspHaberBs;
    }

    public void setCuentaEfectivoNoBspHaberBs(Integer cuentaEfectivoNoBspHaberBs) {
        this.cuentaEfectivoNoBspHaberBs = cuentaEfectivoNoBspHaberBs;
    }

    public Integer getCuentaEfectivoNoBspHaberUsd() {
        return cuentaEfectivoNoBspHaberUsd;
    }

    public void setCuentaEfectivoNoBspHaberUsd(Integer cuentaEfectivoNoBspHaberUsd) {
        this.cuentaEfectivoNoBspHaberUsd = cuentaEfectivoNoBspHaberUsd;
    }

    public Integer getTarjetaCreditoBspHaberBs() {
        return tarjetaCreditoBspHaberBs;
    }

    public void setTarjetaCreditoBspHaberBs(Integer tarjetaCreditoBspHaberBs) {
        this.tarjetaCreditoBspHaberBs = tarjetaCreditoBspHaberBs;
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

    public Integer getTarjetaCreditoBspHaberUsd() {
        return tarjetaCreditoBspHaberUsd;
    }

    public void setTarjetaCreditoBspHaberUsd(Integer tarjetaCreditoBspHaberUsd) {
        this.tarjetaCreditoBspHaberUsd = tarjetaCreditoBspHaberUsd;
    }

    public Integer getCuentaEfectivoDebeBs() {
        return cuentaEfectivoDebeBs;
    }

    public void setCuentaEfectivoDebeBs(Integer cuentaEfectivoDebeBs) {
        this.cuentaEfectivoDebeBs = cuentaEfectivoDebeBs;
    }

    public Integer getCuentaEfectivoHaberBs() {
        return cuentaEfectivoHaberBs;
    }

    public void setCuentaEfectivoHaberBs(Integer cuentaEfectivoHaberBs) {
        this.cuentaEfectivoHaberBs = cuentaEfectivoHaberBs;
    }

    public Integer getCuentaEfectivoDebeUsd() {
        return cuentaEfectivoDebeUsd;
    }

    public void setCuentaEfectivoDebeUsd(Integer cuentaEfectivoDebeUsd) {
        this.cuentaEfectivoDebeUsd = cuentaEfectivoDebeUsd;
    }

    public Integer getCuentaEfectivoHaberUsd() {
        return cuentaEfectivoHaberUsd;
    }

    public void setCuentaEfectivoHaberUsd(Integer cuentaEfectivoHaberUsd) {
        this.cuentaEfectivoHaberUsd = cuentaEfectivoHaberUsd;
    }

    public Integer getDepositoBancoHaberBs() {
        return depositoBancoHaberBs;
    }

    public void setDepositoBancoHaberBs(Integer depositoBancoHaberBs) {
        this.depositoBancoHaberBs = depositoBancoHaberBs;
    }

    public Integer getDepositoBancoDebeBs() {
        return depositoBancoDebeBs;
    }

    public void setDepositoBancoDebeBs(Integer depositoBancoDebeBs) {
        this.depositoBancoDebeBs = depositoBancoDebeBs;
    }

    public Integer getDepositoBancoDebeUsd() {
        return depositoBancoDebeUsd;
    }

    public void setDepositoBancoDebeUsd(Integer depositoBancoDebeUsd) {
        this.depositoBancoDebeUsd = depositoBancoDebeUsd;
    }

    public Integer getDepositoBancoHaberUsd() {
        return depositoBancoHaberUsd;
    }

    public void setDepositoBancoHaberUsd(Integer depositoBancoHaberUsd) {
        this.depositoBancoHaberUsd = depositoBancoHaberUsd;
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
        return idEmpresa;
    }

    @Override
    public String toString() {
        return "com.configuracion.ejb.ContabilidadBoletaje[ idEmpresa=" + idEmpresa + " ]";
    }

}
