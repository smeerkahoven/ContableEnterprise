package com.contabilidad.entities;

import com.contabilidad.entities.NotaDebito;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-17T21:41:18")
@StaticMetamodel(NotaDebitoTransaccion.class)
public class NotaDebitoTransaccion_ { 

    public static volatile SingularAttribute<NotaDebitoTransaccion, String> descripcion;
    public static volatile SingularAttribute<NotaDebitoTransaccion, BigDecimal> montoUsd;
    public static volatile SingularAttribute<NotaDebitoTransaccion, BigDecimal> montoAdeudadoUsd;
    public static volatile SingularAttribute<NotaDebitoTransaccion, String> estado;
    public static volatile SingularAttribute<NotaDebitoTransaccion, String> tipo;
    public static volatile SingularAttribute<NotaDebitoTransaccion, Integer> idNotaDebitoTransaccion;
    public static volatile SingularAttribute<NotaDebitoTransaccion, Integer> idBoleto;
    public static volatile SingularAttribute<NotaDebitoTransaccion, NotaDebito> idNotaDebito;
    public static volatile SingularAttribute<NotaDebitoTransaccion, String> idUsuario;
    public static volatile SingularAttribute<NotaDebitoTransaccion, Date> fechaInsert;
    public static volatile SingularAttribute<NotaDebitoTransaccion, BigDecimal> montoBs;
    public static volatile SingularAttribute<NotaDebitoTransaccion, Integer> gestion;
    public static volatile SingularAttribute<NotaDebitoTransaccion, Integer> idCargo;
    public static volatile SingularAttribute<NotaDebitoTransaccion, BigDecimal> montoAdeudadoBs;
    public static volatile SingularAttribute<NotaDebitoTransaccion, String> moneda;

}