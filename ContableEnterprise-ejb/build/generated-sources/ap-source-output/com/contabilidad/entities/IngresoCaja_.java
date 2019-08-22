package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.IngresoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-21T21:34:17")
@StaticMetamodel(IngresoCaja.class)
public class IngresoCaja_ { 

    public static volatile SingularAttribute<IngresoCaja, Integer> idBanco;
    public static volatile SingularAttribute<IngresoCaja, String> estado;
    public static volatile SingularAttribute<IngresoCaja, String> idUsuario;
    public static volatile SingularAttribute<IngresoCaja, String> nroDeposito;
    public static volatile SingularAttribute<IngresoCaja, Short> combinadoTarjeta;
    public static volatile SingularAttribute<IngresoCaja, Integer> idCuentaDeposito;
    public static volatile SingularAttribute<IngresoCaja, Date> fechaEmision;
    public static volatile SingularAttribute<IngresoCaja, Date> fechaInsert;
    public static volatile SingularAttribute<IngresoCaja, Integer> idIngresoCaja;
    public static volatile SingularAttribute<IngresoCaja, BigDecimal> montoAbonadoUsd;
    public static volatile SingularAttribute<IngresoCaja, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<IngresoCaja, Short> combinadoContado;
    public static volatile SingularAttribute<IngresoCaja, String> nroCheque;
    public static volatile SingularAttribute<IngresoCaja, Cliente> idCliente;
    public static volatile CollectionAttribute<IngresoCaja, IngresoTransaccion> ingresoTransaccionCollection;
    public static volatile SingularAttribute<IngresoCaja, String> nroTarjeta;
    public static volatile SingularAttribute<IngresoCaja, BigDecimal> montoAbonadoBs;
    public static volatile SingularAttribute<IngresoCaja, Integer> idEmpresa;
    public static volatile SingularAttribute<IngresoCaja, String> formaPago;
    public static volatile SingularAttribute<IngresoCaja, Integer> idTarjetaCredito;

}