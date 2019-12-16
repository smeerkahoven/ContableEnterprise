package com.contabilidad.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-16T01:24:55")
@StaticMetamodel(PlanCuentas.class)
public class PlanCuentas_ { 

    public static volatile SingularAttribute<PlanCuentas, Long> nroPlanCuentaPadre;
    public static volatile SingularAttribute<PlanCuentas, Long> nroPlanCuenta;
    public static volatile SingularAttribute<PlanCuentas, String> tipoRegularizacion;
    public static volatile SingularAttribute<PlanCuentas, String> marco;
    public static volatile SingularAttribute<PlanCuentas, BigDecimal> saldo;
    public static volatile SingularAttribute<PlanCuentas, Integer> idCuentaRegularizacion;
    public static volatile SingularAttribute<PlanCuentas, Integer> idPlanCuentas;
    public static volatile SingularAttribute<PlanCuentas, String> cuenta;
    public static volatile SingularAttribute<PlanCuentas, Integer> ctaItb;
    public static volatile SingularAttribute<PlanCuentas, String> moneda;
    public static volatile SingularAttribute<PlanCuentas, Integer> idEmpresa;
    public static volatile SingularAttribute<PlanCuentas, String> mantenimientoValor;
    public static volatile SingularAttribute<PlanCuentas, Integer> nivel;
    public static volatile SingularAttribute<PlanCuentas, String> comodin;
    public static volatile SingularAttribute<PlanCuentas, String> aplicaMovimiento;

}