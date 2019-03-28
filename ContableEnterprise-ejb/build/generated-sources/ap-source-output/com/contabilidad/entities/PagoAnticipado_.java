package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-28T02:33:15")
@StaticMetamodel(PagoAnticipado.class)
public class PagoAnticipado_ { 

    public static volatile SingularAttribute<PagoAnticipado, Integer> idBanco;
    public static volatile SingularAttribute<PagoAnticipado, String> estado;
    public static volatile SingularAttribute<PagoAnticipado, String> nroDeposito;
    public static volatile SingularAttribute<PagoAnticipado, Integer> idPagoAnticipado;
    public static volatile SingularAttribute<PagoAnticipado, Integer> idCuentaDeposito;
    public static volatile SingularAttribute<PagoAnticipado, Date> fechaEmision;
    public static volatile SingularAttribute<PagoAnticipado, Date> fechaInsert;
    public static volatile SingularAttribute<PagoAnticipado, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<PagoAnticipado, BigDecimal> montoAnticipado;
    public static volatile SingularAttribute<PagoAnticipado, BigDecimal> montoTotalAcreditado;
    public static volatile SingularAttribute<PagoAnticipado, String> nroCheque;
    public static volatile SingularAttribute<PagoAnticipado, Cliente> idCliente;
    public static volatile SingularAttribute<PagoAnticipado, String> nroTarjeta;
    public static volatile CollectionAttribute<PagoAnticipado, PagoAnticipadoTransaccion> pagoAnticipadoTransaccionCollection;
    public static volatile SingularAttribute<PagoAnticipado, String> concepto;
    public static volatile SingularAttribute<PagoAnticipado, String> moneda;
    public static volatile SingularAttribute<PagoAnticipado, String> idUsuarioCreador;
    public static volatile SingularAttribute<PagoAnticipado, Integer> idEmpresa;
    public static volatile SingularAttribute<PagoAnticipado, String> formaPago;
    public static volatile SingularAttribute<PagoAnticipado, Integer> idTarjetaCredito;

}