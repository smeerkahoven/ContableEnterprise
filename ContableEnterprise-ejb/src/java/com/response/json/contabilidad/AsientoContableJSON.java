/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.contabilidad;

import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.ComprobanteContable;
import com.response.json.agencia.BoletoJSON;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class AsientoContableJSON implements Serializable {

    private Integer idAsiento;
    private Integer gestion;
    private String estado;
    private String fechaMovimiento;
    private ComprobanteContableJSON idLibro;
    private ComboSelect idPlanCuenta;
    private String moneda;
    private BigDecimal debeMonExt;
    private BigDecimal debeMonNac;
    private BigDecimal haberMonExt;
    private BigDecimal haberMonNac;
    private String action;

    private BoletoJSON idBoleto;
    private CargoBoletoJSON idCargo;
    private NotaDebitoTransaccionJson idNotaTransaccion;
    private IngresoTransaccionJson idIngresoCajaTransaccion;
    private NotaCreditoTransaccionJson idNotaCreditoTransaccion;
    private PagoAnticipadoJson idPagoAnticipado;
    private PagoAnticipadoTransaccionJson idPagoAnticipadoTransaccion;
    private DevolucionJson idDevolucion;
    private String idUsuarioAnular;
    private String tipo;

    private String concepto;
    private Integer idTransaccion;
    private String tipoTransaccion;

    public static AsientoContable toAsientoContable(AsientoContableJSON a) {
        System.out.println(a.getIdPlanCuenta().getId());
        Number idPlanCuenta = a.getIdPlanCuenta().getId() instanceof Double ? (Double) a.getIdPlanCuenta().getId() : (BigDecimal) a.getIdPlanCuenta().getId();
        AsientoContable anew = new AsientoContable();
        anew.setEstado(a.getEstado());
        anew.setFechaMovimiento(DateContable.getCurrentDate());
        /*anew.setGestion(a.getGestion());
        anew.setIdAsiento(a.getIdAsiento());*/
        anew.setIdAsiento(a.getIdAsiento());
        anew.setGestion(a.getGestion());

        if (a.getIdLibro() != null) {
            anew.setIdLibro(new ComprobanteContable(a.getIdLibro().getIdLibro()));
        }

        anew.setIdPlanCuenta(idPlanCuenta.intValue());
        anew.setMoneda(a.getMoneda());
        anew.setMontoDebeExt(a.getDebeMonExt());
        anew.setMontoDebeNac(a.getDebeMonNac());
        anew.setMontoHaberExt(a.getHaberMonExt());
        anew.setMontoHaberNac(a.getHaberMonNac());

        return anew;
    }

    public static List<AsientoContableJSON> toAsientoContableJSON(List<AsientoContable> l) {
        List<AsientoContableJSON> json = new LinkedList<>();
        for (AsientoContable a : l) {
            json.add(toAsientoContableJSON(a));
        }

        return json;
    }

    public static AsientoContableJSON toAsientoContableJSON(AsientoContable a) {
        AsientoContableJSON json = new AsientoContableJSON();
        json.setIdAsiento(a.getIdAsiento());
        json.setGestion(a.getGestion());

        json.setDebeMonExt(a.getMontoDebeExt());
        json.setDebeMonNac(a.getMontoDebeNac());
        json.setEstado(a.getEstado());
        json.setFechaMovimiento(DateContable.getDateFormat(a.getFechaMovimiento(), DateContable.LATIN_AMERICA_TIME_FORMAT));
        json.setHaberMonExt(a.getMontoHaberExt());
        json.setHaberMonNac(a.getMontoHaberNac());
        json.setIdLibro(ComprobanteContableJSON.toComprobanteContableJSON(a.getIdLibro()));
        ComboSelect s = new ComboSelect();
        s.setId(a.getIdPlanCuenta());
        json.setIdPlanCuenta(s);
        json.setMoneda(a.getMoneda());

        json.setTipo(a.getTipo());

        if (a.getIdCargo() != null) {
            json.setIdCargo(CargoBoletoJSON.toCargoBoletoJSON(a.getIdCargo()));
        }

        if (a.getIdPagoAnticipado() != null) {
            json.setIdPagoAnticipado(PagoAnticipadoJson.toPagoAnticipadoJson(a.getIdPagoAnticipado()));
        }

        if (a.getIdBoleto() != null) {
            json.setIdBoleto(BoletoJSON.toBoletoJSON(a.getIdBoleto()));
        }

        if (a.getIdNotaTransaccion() != null) {
            json.setIdNotaTransaccion(NotaDebitoTransaccionJson.toNotaDebitoTransaccionJson(a.getIdNotaTransaccion()));
        }

        if (a.getIdIngresoCajaTransaccion() != null) {
            json.setIdIngresoCajaTransaccion(IngresoTransaccionJson.toIngresoTransaccionJson(a.getIdIngresoCajaTransaccion()));
        }

        if (a.getIdNotaCreditoTransaccion() != null) {
            json.setIdNotaCreditoTransaccion(NotaCreditoTransaccionJson.toNotaCreditoTransaccionJsOn(a.getIdNotaCreditoTransaccion()));
        }

        if (a.getIdPagoAnticipadoTransaccion() != null) {
            json.setIdPagoAnticipadoTransaccion(PagoAnticipadoTransaccionJson.toPagoAnticipadoTransaccionJsOn(a.getIdPagoAnticipadoTransaccion()));
        }

        if (a.getIdDevolucion() != null) {
            json.setIdDevolucion(DevolucionJson.toDevolucionJson(a.getIdDevolucion()));
        }

        if (a.getIdNotaTransaccion() != null) {
            json.setConcepto(a.getIdNotaTransaccion().getDescripcion());
            json.setIdTransaccion(a.getIdNotaTransaccion().getIdNotaDebito().getIdNotaDebito());
            json.setTipoTransaccion("ND");//NOTA DEBITO
        } else if (a.getIdNotaCreditoTransaccion() != null) {
            json.setConcepto(a.getIdNotaCreditoTransaccion().getDescripcion());
            json.setIdTransaccion(a.getIdNotaCreditoTransaccion().getIdNotaCredito().getIdNotaCredito());
            json.setTipoTransaccion("NC"); // NOTA CREDITO
        } else if (a.getIdIngresoCajaTransaccion() != null) {
            json.setConcepto(a.getIdIngresoCajaTransaccion().getDescripcion());
            json.setIdTransaccion(a.getIdIngresoCajaTransaccion().getIdIngresoCaja().getIdIngresoCaja());
            json.setTipoTransaccion("IC");// INGRESO CAJA
        } else if (a.getIdPagoAnticipadoTransaccion() != null) {
            json.setConcepto(a.getIdPagoAnticipadoTransaccion().getDescripcion());
            json.setIdTransaccion(a.getIdPagoAnticipadoTransaccion().getIdPagoAnticipado().getIdPagoAnticipado());
            json.setTipoTransaccion("PA"); //PAGO ANTICIPADO
        } else if (a.getIdPagoAnticipado() != null) {
            json.setConcepto(a.getIdPagoAnticipado().getConcepto());
            json.setIdTransaccion(a.getIdPagoAnticipado().getIdPagoAnticipado());
            json.setTipoTransaccion("PA"); //PAGO ANTICIPADO
        } else if (a.getIdDevolucion() != null) {
            json.setConcepto(a.getIdDevolucion().getConcepto());
            json.setIdTransaccion(a.getIdDevolucion().getIdDevolucion());
            json.setTipoTransaccion("DE"); // DEVOLUCION
        } else {
            json.setConcepto(a.getIdLibro().getConcepto());
        }

        json.setIdUsuarioAnular(a.getIdUsuarioAnular());

        return json;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public CargoBoletoJSON getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(CargoBoletoJSON idCargo) {
        this.idCargo = idCargo;
    }

    public PagoAnticipadoJson getIdPagoAnticipado() {
        return idPagoAnticipado;
    }

    public void setIdPagoAnticipado(PagoAnticipadoJson idPagoAnticipado) {
        this.idPagoAnticipado = idPagoAnticipado;
    }

    public PagoAnticipadoTransaccionJson getIdPagoAnticipadoTransaccion() {
        return idPagoAnticipadoTransaccion;
    }

    public void setIdPagoAnticipadoTransaccion(PagoAnticipadoTransaccionJson idPagoAnticipadoTransaccion) {
        this.idPagoAnticipadoTransaccion = idPagoAnticipadoTransaccion;
    }

    public DevolucionJson getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(DevolucionJson idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public String getIdUsuarioAnular() {
        return idUsuarioAnular;
    }

    public void setIdUsuarioAnular(String idUsuarioAnular) {
        this.idUsuarioAnular = idUsuarioAnular;
    }

    public NotaCreditoTransaccionJson getIdNotaCreditoTransaccion() {
        return idNotaCreditoTransaccion;
    }

    public void setIdNotaCreditoTransaccion(NotaCreditoTransaccionJson idNotaCreditoTransaccion) {
        this.idNotaCreditoTransaccion = idNotaCreditoTransaccion;
    }

    public BoletoJSON getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(BoletoJSON idBoleto) {
        this.idBoleto = idBoleto;
    }

    public NotaDebitoTransaccionJson getIdNotaTransaccion() {
        return idNotaTransaccion;
    }

    public void setIdNotaTransaccion(NotaDebitoTransaccionJson idNotaTransaccion) {
        this.idNotaTransaccion = idNotaTransaccion;
    }

    public IngresoTransaccionJson getIdIngresoCajaTransaccion() {
        return idIngresoCajaTransaccion;
    }

    public void setIdIngresoCajaTransaccion(IngresoTransaccionJson idIngresoCajaTransaccion) {
        this.idIngresoCajaTransaccion = idIngresoCajaTransaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public ComprobanteContableJSON getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(ComprobanteContableJSON idLibro) {
        this.idLibro = idLibro;
    }

    public ComboSelect getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(ComboSelect idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getDebeMonExt() {
        return debeMonExt;
    }

    public void setDebeMonExt(BigDecimal debeMonExt) {
        this.debeMonExt = debeMonExt;
    }

    public BigDecimal getDebeMonNac() {
        return debeMonNac;
    }

    public void setDebeMonNac(BigDecimal debeMonNac) {
        this.debeMonNac = debeMonNac;
    }

    public BigDecimal getHaberMonExt() {
        return haberMonExt;
    }

    public void setHaberMonExt(BigDecimal haberMonExt) {
        this.haberMonExt = haberMonExt;
    }

    public BigDecimal getHaberMonNac() {
        return haberMonNac;
    }

    public void setHaberMonNac(BigDecimal haberMonNac) {
        this.haberMonNac = haberMonNac;
    }

}
