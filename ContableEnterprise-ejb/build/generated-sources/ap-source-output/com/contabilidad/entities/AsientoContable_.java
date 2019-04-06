package com.contabilidad.entities;

import com.agencia.entities.Boleto;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaCreditoTransaccion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-06T09:45:55")
@StaticMetamodel(AsientoContable.class)
public class AsientoContable_ { 

    public static volatile SingularAttribute<AsientoContable, String> estado;
    public static volatile SingularAttribute<AsientoContable, String> tipo;
    public static volatile SingularAttribute<AsientoContable, BigDecimal> montoDebeExt;
    public static volatile SingularAttribute<AsientoContable, PagoAnticipadoTransaccion> idPagoAnticipadoTransaccion;
    public static volatile SingularAttribute<AsientoContable, Boleto> idBoleto;
    public static volatile SingularAttribute<AsientoContable, IngresoTransaccion> idIngresoCajaTransaccion;
    public static volatile SingularAttribute<AsientoContable, PagoAnticipado> idPagoAnticipado;
    public static volatile SingularAttribute<AsientoContable, String> idUsuarioAnular;
    public static volatile SingularAttribute<AsientoContable, BigDecimal> montoHaberExt;
    public static volatile SingularAttribute<AsientoContable, Date> fechaMovimiento;
    public static volatile SingularAttribute<AsientoContable, NotaCreditoTransaccion> idNotaCreditoTransaccion;
    public static volatile SingularAttribute<AsientoContable, Integer> gestion;
    public static volatile SingularAttribute<AsientoContable, Integer> idAsiento;
    public static volatile SingularAttribute<AsientoContable, BigDecimal> montoDebeNac;
    public static volatile SingularAttribute<AsientoContable, CargoBoleto> idCargo;
    public static volatile SingularAttribute<AsientoContable, ComprobanteContable> idLibro;
    public static volatile SingularAttribute<AsientoContable, Integer> idPlanCuenta;
    public static volatile SingularAttribute<AsientoContable, String> moneda;
    public static volatile SingularAttribute<AsientoContable, BigDecimal> montoHaberNac;
    public static volatile SingularAttribute<AsientoContable, NotaDebitoTransaccion> idNotaTransaccion;
    public static volatile SingularAttribute<AsientoContable, Devolucion> idDevolucion;

}