package com.contabilidad.entities;

import com.contabilidad.entities.PlanCuentas;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-20T23:19:07")
@StaticMetamodel(CargoBoleto.class)
public class CargoBoleto_ { 

    public static volatile SingularAttribute<CargoBoleto, BigDecimal> comisionAgencia;
    public static volatile SingularAttribute<CargoBoleto, PlanCuentas> idCuentaPromotor;
    public static volatile SingularAttribute<CargoBoleto, BigDecimal> comisionMayorista;
    public static volatile SingularAttribute<CargoBoleto, String> tipo;
    public static volatile SingularAttribute<CargoBoleto, String> estado;
    public static volatile SingularAttribute<CargoBoleto, Integer> idNotaDebitoTransaccion;
    public static volatile SingularAttribute<CargoBoleto, Integer> idIngresoCajaTransaccion;
    public static volatile SingularAttribute<CargoBoleto, Integer> idNotaDebito;
    public static volatile SingularAttribute<CargoBoleto, Date> fechaInsert;
    public static volatile SingularAttribute<CargoBoleto, String> usuarioCreador;
    public static volatile SingularAttribute<CargoBoleto, Integer> idIngresoCaja;
    public static volatile SingularAttribute<CargoBoleto, BigDecimal> comisionPromotor;
    public static volatile SingularAttribute<CargoBoleto, Integer> idCargo;
    public static volatile SingularAttribute<CargoBoleto, Integer> idLibro;
    public static volatile SingularAttribute<CargoBoleto, String> concepto;
    public static volatile SingularAttribute<CargoBoleto, String> moneda;
    public static volatile SingularAttribute<CargoBoleto, Integer> idEmpresa;
    public static volatile SingularAttribute<CargoBoleto, PlanCuentas> idCuentaMayorista;
    public static volatile SingularAttribute<CargoBoleto, PlanCuentas> idCuentaAgencia;

}