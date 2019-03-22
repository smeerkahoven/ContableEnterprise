package com.contabilidad.entities;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.NotaDebitoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-21T23:51:27")
@StaticMetamodel(IngresoTransaccion.class)
public class IngresoTransaccion_ { 

    public static volatile SingularAttribute<IngresoTransaccion, String> descripcion;
    public static volatile SingularAttribute<IngresoTransaccion, BigDecimal> montoUsd;
    public static volatile SingularAttribute<IngresoTransaccion, Integer> idTransaccion;
    public static volatile SingularAttribute<IngresoTransaccion, String> estado;
    public static volatile SingularAttribute<IngresoTransaccion, String> moneda;
    public static volatile SingularAttribute<IngresoTransaccion, Date> fechaInsert;
    public static volatile SingularAttribute<IngresoTransaccion, NotaDebitoTransaccion> idNotaTransaccion;
    public static volatile SingularAttribute<IngresoTransaccion, BigDecimal> montoBs;
    public static volatile SingularAttribute<IngresoTransaccion, IngresoCaja> idIngresoCaja;

}