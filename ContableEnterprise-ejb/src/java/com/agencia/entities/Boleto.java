/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_boleto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boleto.findAll", query = "SELECT b FROM Boleto b")
    ,
    @NamedQuery(name = "Boleto.findNroBoleto", query = "SELECT b FROM Boleto b WHERE b.numero=:numero and b.estado in (:list)")
})
public class Boleto extends Entidad {

    public static class Estado {

        public static final String EMITIDO = "E";
        public static final String PENDIENTE = "P";
        public static final String ANULADO = "A";
        public static final String VOID = "V" ;
    }

    public static class Cupon {

        public static final String INTERNACIONAL = "I";
        public static final String NACIONAL = "N";

    }

    public static final String MULTIPLE = "M";
    public static final String SIMPLE = "S";

    public static final String PENDIENTE = "P";
    public static final String COMPLETADO = "C";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_boleto")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idBoleto;
    @Basic(optional = false)
    @Column(name = "gestion")
    private Integer gestion;
    /*@Basic(optional = false)
    @NotNull
    @Column(name = "id_aerolinea")
    private int idAerolinea;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aerolinea")
    private Aerolinea idAerolinea;
    @Basic(optional = false)
    @Column(name = "id_boleto_padre")
    private Integer idBoletoPadre;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_promotor")
    private Promotor idPromotor;
    @Basic(optional = false)
    @Column(name = "id_ingreso_caja")
    private Integer idIngresoCaja;
    @Column(name = "id_ingreso_caja_transaccion")
    private Integer idIngresoCajaTransaccion;
    @Column(name="id_libro")
    private Integer idLibro ;
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @Basic(optional = false)
    @Size(min = 1, max = 4)
    @Column(name = "emision")
    private String emision;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "tipo_boleto")
    private String tipoBoleto;
    @Size(max = 1)
    @Column(name = "tipo_cupon")
    private String tipoCupon;
    @Basic(optional = false)
    @Column(name = "numero")
    private long numero;
    @Basic(optional = false)
    @Size(min = 1, max = 4)
    @Column(name = "id_ruta_1")
    private String idRuta1;
    @Basic(optional = false)
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
    /*@Basic(optional = false)
    @NotNull
    @Column(name = "id_cliente")
    private Integer idCliente;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "nombre_pasajero")
    private String nombrePasajero;
    @Basic(optional = false)
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Basic(optional = false)
    @Column(name = "fecha_viaje")
    @Temporal(TemporalType.DATE)
    private Date fechaViaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "factor_cambiario")
    private BigDecimal factorCambiario;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "forma_pago")
    private String formaPago;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "id_nota_debito")
    private Integer idNotaDebito;
    @Column(name = "id_nota_debito_transaccion")
    private Integer idNotaDebitoTransaccion;
    @Basic(optional = false)
    @Column(name = "fecha_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsert;
    @Basic(optional = false)
    @Size(min = 1, max = 16)
    @Column(name = "id_usuario_creador")
    private String idUsuarioCreador;
    @Column(name = "credito_dias")
    private Integer creditoDias;
    @Column(name = "credito_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date creditoVencimiento;
    @Size(max = 1)
    @Column(name = "moneda" , updatable = false)
    private String moneda;
    @Column(name = "importe_neto" , updatable = false)
    private BigDecimal importeNeto;
    @Column(name = "impuesto_bob", updatable = false)
    private BigDecimal impuestoBob;
    @Column(name = "impuesto_qm", updatable = false)
    private BigDecimal impuestoQm;
    @Column(name = "impuesto_1", updatable = false)
    private BigDecimal impuesto1;
    @Column(name = "impuesto_2" , updatable = false)
    private BigDecimal impuesto2;
    @Column(name = "impuesto_3" , updatable = false)
    private BigDecimal impuesto3;
    @Column(name = "impuesto_4" , updatable = false)
    private BigDecimal impuesto4;
    @Column(name = "impuesto_5" , updatable = false)
    private BigDecimal impuesto5;
    @Column(name = "total_boleto" , updatable = false)
    private BigDecimal totalBoleto;
    @Column(name = "comision" )
    private BigDecimal comision;
    @Column(name = "monto_comision" )
    private BigDecimal montoComision;
    @Column(name = "fee" , updatable = false)
    private BigDecimal fee;
    @Column(name = "monto_fee" , updatable = false)
    private BigDecimal montoFee;
    @Column(name = "descuento" , updatable = false)
    private BigDecimal descuento;
    @Column(name = "monto_descuento" , updatable = false)
    private BigDecimal montoDescuento;
    @Column(name = "total_monto_cancelado", updatable = false)
    private BigDecimal totalMontoCancelado;
    @Size(max = 1)
    @Column(name = "contado_tipo")
    private String contadoTipo;
    @Size(max = 45)
    @Column(name = "contado_nro_cheque")
    private String contadoNroCheque;
    @Column(name = "contado_id_banco")
    private Integer contadoIdBanco;
    @Column(name = "contado_id_cuenta")
    private Integer contadoIdCuenta;
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
    @Column(name = "combinado_credito")
    private Short combinadoCredito;
    @Column(name = "combinado_credito_dias")
    private Integer combinadoCreditoDias;
    @Column(name = "combinado_credito_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date combinadoCreditoVencimiento;
    @Column(name = "combinado_credito_monto")
    private BigDecimal combinadoCreditoMonto;
    @Column(name = "combinado_contado")
    private Short combinadoContado;
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
    @Column(name = "combinado_contado_id_cuenta")
    private Integer combinadoContadoIdCuenta;
    @Size(max = 20)
    @Column(name = "combinado_contado_nro_deposito")
    private String combinadoContadoNroDeposito;
    @Column(name = "combinado_tarjeta")
    private Short combinadoTarjeta;
    @Column(name = "combinado_tarjeta_id")
    private Integer combinadoTarjetaId;
    @Size(max = 16)
    @Column(name = "combinado_tarjeta_numero")
    private String combinadoTarjetaNumero;
    @Column(name = "combinado_tarjeta_monto")
    private BigDecimal combinadoTarjetaMonto;

    public Boleto() {
    }
    
    public Boleto (Integer idBoleto){
        this.idBoleto = idBoleto ;
    }

    public Boleto(long numero) {
        this.numero = numero;
    }

    public Boleto(int idAerolinea, int idEmpresa, String emision, String tipoBoleto, long numero, String idRuta1, String idRuta2, int idCliente, String nombrePasajero, Date fechaEmision, Date fechaViaje, BigDecimal factorCambiario, String formaPago, String estado, Date fechaInsert, String idUsuarioCreador) {
        this.idEmpresa = idEmpresa;
        this.emision = emision;
        this.tipoBoleto = tipoBoleto;
        this.numero = numero;
        this.idRuta1 = idRuta1;
        this.idRuta2 = idRuta2;
        this.nombrePasajero = nombrePasajero;
        this.fechaEmision = fechaEmision;
        this.fechaViaje = fechaViaje;
        this.factorCambiario = factorCambiario;
        this.formaPago = formaPago;
        this.estado = estado;
        this.fechaInsert = fechaInsert;
        this.idUsuarioCreador = idUsuarioCreador;
    }

    public Integer getIdIngresoCaja() {
        return idIngresoCaja;
    }

    public void setIdIngresoCaja(Integer idIngresoCaja) {
        this.idIngresoCaja = idIngresoCaja;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Integer idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getContadoIdCuenta() {
        return contadoIdCuenta;
    }

    public void setContadoIdCuenta(Integer contadoIdCuenta) {
        this.contadoIdCuenta = contadoIdCuenta;
    }

    public Integer getCombinadoContadoIdCuenta() {
        return combinadoContadoIdCuenta;
    }

    public void setCombinadoContadoIdCuenta(Integer combinadoContadoIdCuenta) {
        this.combinadoContadoIdCuenta = combinadoContadoIdCuenta;
    }

    public Promotor getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Promotor idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
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

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getMontoFee() {
        return montoFee;
    }

    public void setMontoFee(BigDecimal montoFee) {
        this.montoFee = montoFee;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
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

    public Short getCombinadoCredito() {
        return combinadoCredito;
    }

    public void setCombinadoCredito(Short combinadoCredito) {
        this.combinadoCredito = combinadoCredito;
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

    public Short getCombinadoContado() {
        return combinadoContado;
    }

    public void setCombinadoContado(Short combinadoContado) {
        this.combinadoContado = combinadoContado;
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

    public Short getCombinadoTarjeta() {
        return combinadoTarjeta;
    }

    public void setCombinadoTarjeta(Short combinadoTarjeta) {
        this.combinadoTarjeta = combinadoTarjeta;
    }

    public Integer getCombinadoTarjetaId() {
        return combinadoTarjetaId;
    }

    public void setCombinadoTarjetaId(Integer combinadoTarjetaId) {
        this.combinadoTarjetaId = combinadoTarjetaId;
    }

    public String getCombinadoTarjetaNumero() {
        return combinadoTarjetaNumero;
    }

    public void setCombinadoTarjetaNumero(String combinadoTarjetaNumero) {
        this.combinadoTarjetaNumero = combinadoTarjetaNumero;
    }

    public BigDecimal getCombinadoTarjetaMonto() {
        return combinadoTarjetaMonto;
    }

    public void setCombinadoTarjetaMonto(BigDecimal combinadoTarjetaMonto) {
        this.combinadoTarjetaMonto = combinadoTarjetaMonto;
    }

    public Integer getIdBoletoPadre() {
        return idBoletoPadre;
    }

    public void setIdBoletoPadre(Integer idBoletoPadre) {
        this.idBoletoPadre = idBoletoPadre;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Aerolinea getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Aerolinea idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    @Override
    public String toString() {
        return "com.agencia.entities.Boleto[ boletoPK=" + idBoleto + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return getIdBoleto();
    }

    public Integer getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(Integer idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public Integer getIdNotaDebitoTransaccion() {
        return idNotaDebitoTransaccion;
    }

    public void setIdNotaDebitoTransaccion(Integer idNotaDebitoTransaccion) {
        this.idNotaDebitoTransaccion = idNotaDebitoTransaccion;
    }
    
    

}
