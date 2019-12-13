package com.contabilidad.entities;

import com.contabilidad.entities.NotaCredito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PlanCuentas;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-13T00:38:26")
@StaticMetamodel(NotaCreditoTransaccion.class)
public class NotaCreditoTransaccion_ { 

    public static volatile SingularAttribute<NotaCreditoTransaccion, String> descripcion;
    public static volatile SingularAttribute<NotaCreditoTransaccion, BigDecimal> montoUsd;
    public static volatile SingularAttribute<NotaCreditoTransaccion, String> estado;
    public static volatile SingularAttribute<NotaCreditoTransaccion, PlanCuentas> idPlanCuenta;
    public static volatile SingularAttribute<NotaCreditoTransaccion, NotaCredito> idNotaCredito;
    public static volatile SingularAttribute<NotaCreditoTransaccion, String> moneda;
    public static volatile SingularAttribute<NotaCreditoTransaccion, String> idUsuarioCreador;
    public static volatile SingularAttribute<NotaCreditoTransaccion, Date> fechaInsert;
    public static volatile SingularAttribute<NotaCreditoTransaccion, NotaDebitoTransaccion> idNotaTransaccion;
    public static volatile SingularAttribute<NotaCreditoTransaccion, Integer> idNotaCreditoTransaccion;
    public static volatile SingularAttribute<NotaCreditoTransaccion, BigDecimal> montoBs;

}