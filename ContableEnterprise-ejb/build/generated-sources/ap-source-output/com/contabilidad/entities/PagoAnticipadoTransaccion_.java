package com.contabilidad.entities;

import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T14:50:47")
@StaticMetamodel(PagoAnticipadoTransaccion.class)
public class PagoAnticipadoTransaccion_ { 

    public static volatile SingularAttribute<PagoAnticipadoTransaccion, String> descripcion;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, BigDecimal> montoUsd;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, String> estado;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, String> tipo;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, Integer> idPagoAnticipadoTransaccion;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, PagoAnticipado> idPagoAnticipado;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, Date> fechaInsert;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, BigDecimal> montoBs;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, BigDecimal> monto;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, String> idUsuarioCreador;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, String> moneda;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, NotaDebitoTransaccion> idNotaTransaccion;
    public static volatile SingularAttribute<PagoAnticipadoTransaccion, Devolucion> idDevolucion;

}