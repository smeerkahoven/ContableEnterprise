/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_boletaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boleto.findAll", query = "SELECT b FROM Boleto b") , 
    @NamedQuery(name = "Boleto.findNroBoleto", query = "SELECT b FROM Boleto b WHERE b.numero=:numero" )

})
public class Boleto extends Entidad {
    
    /*
     * Tipos de BOletos
    **/
    public static final String  MULTIPLE = "M" ;
    public static final String  SIMPLE = "S" ;
    
    /**
     * Tipos de Cupones
     */
    public static final String INTERNACIONAL = "I" ; 
    public static final String NACIONAL = "N" ; 
    

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BoletoPK boletoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_aerolinea")
    private int idAerolinea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_promotor")
    private int idPromotor;
    @Column(name = "id_libro")
    private Integer idLibro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empresa")
    private int idEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "emision")
    private String emision;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_boleto")
    private String tipoBoleto;
    @Size(max = 1)
    @Column(name = "tipo_cupon")
    private String tipoCupon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private long numero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_ruta_1")
    private String idRuta1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "id_ruta_2")
    private String idRuta2;
    @Size(max = 4)
    @Column(name = "id_ruta_3")
    private String idRuta3;
    @Size(max = 4)
    @Column(name = "id_ruta_4")
    private String idRuta4;
    @Size(max = 4)
    @Column(name = "id_ruta_5")
    private String idRuta5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre_pasajero")
    private String nombrePasajero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_viaje")
    @Temporal(TemporalType.DATE)
    private Date fechaViaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "forma_pago")
    private String formaPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_nota_debito")
    private int idNotaDebito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;
    @Column(name = "credito_dias")
    private Integer creditoDias;
    @Column(name = "credito_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date creditoVencimiento;
    @Size(max = 1)
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "importe_neto")
    private BigDecimal importeNeto;
    @Column(name = "impuesto_bob")
    private BigDecimal impuestoBob;
    @Column(name = "impuesto_qm")
    private BigDecimal impuestoQm;
    @Column(name = "impuesto_1")
    private BigDecimal impuesto1;
    @Column(name = "impuesto_2")
    private BigDecimal impuesto2;
    @Column(name = "impuesto_3")
    private BigDecimal impuesto3;
    @Column(name = "impuesto_4")
    private BigDecimal impuesto4;
    @Column(name = "impuesto_5")
    private BigDecimal impuesto5;
    @Column(name = "total_boleto")
    private BigDecimal totalBoleto;
    @Column(name = "comision")
    private BigDecimal comision;
    @Column(name = "monto_fee")
    private BigDecimal montoFee;
    @Column(name = "monto_descuento")
    private BigDecimal montoDescuento;
    @Column(name = "total_monto_cancelado")
    private BigDecimal totalMontoCancelado;
    @Size(max = 1)
    @Column(name = "contado_tipo")
    private String contadoTipo;
    @Size(max = 45)
    @Column(name = "contado_nro_cheque")
    private String contadoNroCheque;
    @Column(name = "contado_id_banco")
    private Integer contadoIdBanco;
    @Size(max = 45)
    @Column(name = "contado_nro_deposito")
    private String contadoNroDeposito;
    @Size(max = 16)
    @Column(name = "tarjeta_numero")
    private String tarjetaNumero;
    @Column(name = "tarjeta_id")
    private Integer tarjetaId;
    @Size(max = 1)
    @Column(name = "combinado_tipo")
    private String combinadoTipo;
    @Column(name = "combinado_credito_dias")
    private Integer combinadoCreditoDias;
    @Column(name = "combinado_credito_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date combinadoCreditoVencimiento;
    @Column(name = "combinado_credito_monto")
    private BigDecimal combinadoCreditoMonto;
    @Size(max = 1)
    @Column(name = "combinado_contado_tipo")
    private String combinadoContadoTipo;
    @Column(name = "combinado_contado_monto")
    private BigDecimal combinadoContadoMonto;
    @Size(max = 20)
    @Column(name = "combinado_contado_nro_cheque")
    private String combinadoContadoNroCheque;
    @Column(name = "combinado_contado_id_banco")
    private Integer combinadoContadoIdBanco;
    @Size(max = 20)
    @Column(name = "combinado_contado_nro_deposito")
    private String combinadoContadoNroDeposito;

    public Boleto() {
    }
    
    public Boleto (long numero){
        this.numero = numero ;
    }

    public Boleto(BoletoPK boletoPK) {
        this.boletoPK = boletoPK;
    }

    public Boleto(BoletoPK boletoPK, int idAerolinea, int idPromotor, int idEmpresa, String emision, String tipoBoleto, long numero, String idRuta1, String idRuta2, int idCliente, String nombrePasajero, Date fechaEmision, Date fechaViaje, BigDecimal factorCambiario, String formaPago, String estado, int idNotaDebito, Date fechaInsert, String idUsuarioCreador) {
        this.boletoPK = boletoPK;
        this.idAerolinea = idAerolinea;
        this.idPromotor = idPromotor;
        this.idEmpresa = idEmpresa;
        this.emision = emision;
        this.tipoBoleto = tipoBoleto;
        this.numero = numero;
        this.idRuta1 = idRuta1;
        this.idRuta2 = idRuta2;
        this.idCliente = idCliente;
        this.nombrePasajero = nombrePasajero;
        this.fechaEmision = fechaEmision;
        this.fechaViaje = fechaViaje;
        this.factorCambiario = factorCambiario;
        this.formaPago = formaPago;
        this.estado = estado;
        this.idNotaDebito = idNotaDebito;
        this.fechaInsert = fechaInsert;
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Boleto(int idBoleto, int gestion) {
        this.boletoPK = new BoletoPK(idBoleto, gestion);
    }

    public BoletoPK getBoletoPK() {
        return boletoPK;
    }

    public void setBoletoPK(BoletoPK boletoPK) {
        this.boletoPK = boletoPK;
    }

    public int getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(int idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public int getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(int idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getTipoBoleto() {
        return tipoBoleto;
    }

    public void setTipoBoleto(String tipoBoleto) {
        this.tipoBoleto = tipoBoleto;
    }

    public String getTipoCupon() {
        return tipoCupon;
    }

    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getIdRuta1() {
        return idRuta1;
    }

    public void setIdRuta1(String idRuta1) {
        this.idRuta1 = idRuta1;
    }

    public String getIdRuta2() {
        return idRuta2;
    }

    public void setIdRuta2(String idRuta2) {
        this.idRuta2 = idRuta2;
    }

    public String getIdRuta3() {
        return idRuta3;
    }

    public void setIdRuta3(String idRuta3) {
        this.idRuta3 = idRuta3;
    }

    public String getIdRuta4() {
        return idRuta4;
    }

    public void setIdRuta4(String idRuta4) {
        this.idRuta4 = idRuta4;
    }

    public String getIdRuta5() {
        return idRuta5;
    }

    public void setIdRuta5(String idRuta5) {
        this.idRuta5 = idRuta5;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public BigDecimal getFactorCambiario() {
        return factorCambiario;
    }

    public void setFactorCambiario(BigDecimal factorCambiario) {
        this.factorCambiario = factorCambiario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(int idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Date getFechaInsert() {
        return fechaInsert;
    }

    public void setFechaInsert(Date fechaInsert) {
        this.fechaInsert = fechaInsert;
    }

    public String getIdUsuarioCreador() {
        return idUsuarioCreador;
    }

    public void setIdUsuarioCreador(String idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Integer getCreditoDias() {
        return creditoDias;
    }

    public void setCreditoDias(Integer creditoDias) {
        this.creditoDias = creditoDias;
    }

    public Date getCreditoVencimiento() {
        return creditoVencimiento;
    }

    public void setCreditoVencimiento(Date creditoVencimiento) {
        this.creditoVencimiento = creditoVencimiento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImpuestoBob() {
        return impuestoBob;
    }

    public void setImpuestoBob(BigDecimal impuestoBob) {
        this.impuestoBob = impuestoBob;
    }

    public BigDecimal getImpuestoQm() {
        return impuestoQm;
    }

    public void setImpuestoQm(BigDecimal impuestoQm) {
        this.impuestoQm = impuestoQm;
    }

    public BigDecimal getImpuesto1() {
        return impuesto1;
    }

    public void setImpuesto1(BigDecimal impuesto1) {
        this.impuesto1 = impuesto1;
    }

    public BigDecimal getImpuesto2() {
        return impuesto2;
    }

    public void setImpuesto2(BigDecimal impuesto2) {
        this.impuesto2 = impuesto2;
    }

    public BigDecimal getImpuesto3() {
        return impuesto3;
    }

    public void setImpuesto3(BigDecimal impuesto3) {
        this.impuesto3 = impuesto3;
    }

    public BigDecimal getImpuesto4() {
        return impuesto4;
    }

    public void setImpuesto4(BigDecimal impuesto4) {
        this.impuesto4 = impuesto4;
    }

    public BigDecimal getImpuesto5() {
        return impuesto5;
    }

    public void setImpuesto5(BigDecimal impuesto5) {
        this.impuesto5 = impuesto5;
    }

    public BigDecimal getTotalBoleto() {
        return totalBoleto;
    }

    public void setTotalBoleto(BigDecimal totalBoleto) {
        this.totalBoleto = totalBoleto;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public BigDecimal getMontoFee() {
        return montoFee;
    }

    public void setMontoFee(BigDecimal montoFee) {
        this.montoFee = montoFee;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getTotalMontoCancelado() {
        return totalMontoCancelado;
    }

    public void setTotalMontoCancelado(BigDecimal totalMontoCancelado) {
        this.totalMontoCancelado = totalMontoCancelado;
    }

    public String getContadoTipo() {
        return contadoTipo;
    }

    public void setContadoTipo(String contadoTipo) {
        this.contadoTipo = contadoTipo;
    }

    public String getContadoNroCheque() {
        return contadoNroCheque;
    }

    public void setContadoNroCheque(String contadoNroCheque) {
        this.contadoNroCheque = contadoNroCheque;
    }

    public Integer getContadoIdBanco() {
        return contadoIdBanco;
    }

    public void setContadoIdBanco(Integer contadoIdBanco) {
        this.contadoIdBanco = contadoIdBanco;
    }

    public String getContadoNroDeposito() {
        return contadoNroDeposito;
    }

    public void setContadoNroDeposito(String contadoNroDeposito) {
        this.contadoNroDeposito = contadoNroDeposito;
    }

    public String getTarjetaNumero() {
        return tarjetaNumero;
    }

    public void setTarjetaNumero(String tarjetaNumero) {
        this.tarjetaNumero = tarjetaNumero;
    }

    public Integer getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Integer tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    public String getCombinadoTipo() {
        return combinadoTipo;
    }

    public void setCombinadoTipo(String combinadoTipo) {
        this.combinadoTipo = combinadoTipo;
    }

    public Integer getCombinadoCreditoDias() {
        return combinadoCreditoDias;
    }

    public void setCombinadoCreditoDias(Integer combinadoCreditoDias) {
        this.combinadoCreditoDias = combinadoCreditoDias;
    }

    public Date getCombinadoCreditoVencimiento() {
        return combinadoCreditoVencimiento;
    }

    public void setCombinadoCreditoVencimiento(Date combinadoCreditoVencimiento) {
        this.combinadoCreditoVencimiento = combinadoCreditoVencimiento;
    }

    public BigDecimal getCombinadoCreditoMonto() {
        return combinadoCreditoMonto;
    }

    public void setCombinadoCreditoMonto(BigDecimal combinadoCreditoMonto) {
        this.combinadoCreditoMonto = combinadoCreditoMonto;
    }

    public String getCombinadoContadoTipo() {
        return combinadoContadoTipo;
    }

    public void setCombinadoContadoTipo(String combinadoContadoTipo) {
        this.combinadoContadoTipo = combinadoContadoTipo;
    }

    public BigDecimal getCombinadoContadoMonto() {
        return combinadoContadoMonto;
    }

    public void setCombinadoContadoMonto(BigDecimal combinadoContadoMonto) {
        this.combinadoContadoMonto = combinadoContadoMonto;
    }

    public String getCombinadoContadoNroCheque() {
        return combinadoContadoNroCheque;
    }

    public void setCombinadoContadoNroCheque(String combinadoContadoNroCheque) {
        this.combinadoContadoNroCheque = combinadoContadoNroCheque;
    }

    public Integer getCombinadoContadoIdBanco() {
        return combinadoContadoIdBanco;
    }

    public void setCombinadoContadoIdBanco(Integer combinadoContadoIdBanco) {
        this.combinadoContadoIdBanco = combinadoContadoIdBanco;
    }

    public String getCombinadoContadoNroDeposito() {
        return combinadoContadoNroDeposito;
    }

    public void setCombinadoContadoNroDeposito(String combinadoContadoNroDeposito) {
        this.combinadoContadoNroDeposito = combinadoContadoNroDeposito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boletoPK != null ? boletoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boleto)) {
            return false;
        }
        Boleto other = (Boleto) object;
        if ((this.boletoPK == null && other.boletoPK != null) || (this.boletoPK != null && !this.boletoPK.equals(other.boletoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.Boleto[ boletoPK=" + boletoPK + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return this.getBoletoPK().getIdBoleto() ;
    }
    
}
