package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.NotaCreditoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T06:25:15")
@StaticMetamodel(NotaCredito.class)
public class NotaCredito_ { 

    public static volatile SingularAttribute<NotaCredito, String> estado;
    public static volatile SingularAttribute<NotaCredito, Cliente> idCliente;
    public static volatile SingularAttribute<NotaCredito, String> idUsuario;
    public static volatile SingularAttribute<NotaCredito, Integer> idNotaCredito;
    public static volatile SingularAttribute<NotaCredito, BigDecimal> montoAbonadoBs;
    public static volatile SingularAttribute<NotaCredito, String> concepto;
    public static volatile SingularAttribute<NotaCredito, Date> fechaEmision;
    public static volatile SingularAttribute<NotaCredito, Date> fechaInsert;
    public static volatile SingularAttribute<NotaCredito, Integer> idEmpresa;
    public static volatile ListAttribute<NotaCredito, NotaCreditoTransaccion> notaCreditoTransaccionList;
    public static volatile SingularAttribute<NotaCredito, BigDecimal> montoAbonadoUsd;
    public static volatile SingularAttribute<NotaCredito, BigDecimal> factorCambiario;

}