package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.agencia.entities.Promotor;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T14:50:47")
@StaticMetamodel(NotaDebito.class)
public class NotaDebito_ { 

    public static volatile SingularAttribute<NotaDebito, Integer> idBanco;
    public static volatile SingularAttribute<NotaDebito, String> estado;
    public static volatile SingularAttribute<NotaDebito, Integer> idNotaDebito;
    public static volatile SingularAttribute<NotaDebito, Date> fechaInsert;
    public static volatile SingularAttribute<NotaDebito, Short> combinadoCredito;
    public static volatile SingularAttribute<NotaDebito, Date> creditoVencimiento;
    public static volatile SingularAttribute<NotaDebito, String> nroCheque;
    public static volatile SingularAttribute<NotaDebito, Cliente> idCliente;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> montoAdeudadoBs;
    public static volatile SingularAttribute<NotaDebito, String> combinadoContadoTipo;
    public static volatile SingularAttribute<NotaDebito, String> nroTarjeta;
    public static volatile SingularAttribute<NotaDebito, String> idUsuarioCreador;
    public static volatile SingularAttribute<NotaDebito, Integer> idEmpresa;
    public static volatile SingularAttribute<NotaDebito, Integer> creditoDias;
    public static volatile SingularAttribute<NotaDebito, Integer> idTarjetaCredito;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> montoAdeudadoUsd;
    public static volatile SingularAttribute<NotaDebito, String> nroDeposito;
    public static volatile SingularAttribute<NotaDebito, Short> combinadoTarjeta;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> montoTotalBs;
    public static volatile SingularAttribute<NotaDebito, Integer> idCuentaDeposito;
    public static volatile SingularAttribute<NotaDebito, Date> fechaEmision;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<NotaDebito, Integer> gestion;
    public static volatile SingularAttribute<NotaDebito, Short> combinadoContado;
    public static volatile SingularAttribute<NotaDebito, BigDecimal> montoTotalUsd;
    public static volatile SingularAttribute<NotaDebito, Promotor> idCounter;
    public static volatile SingularAttribute<NotaDebito, String> formaPago;

}