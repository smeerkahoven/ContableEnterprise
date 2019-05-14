package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.PagoAnticipado;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-13T21:07:52")
@StaticMetamodel(Devolucion.class)
public class Devolucion_ { 

    public static volatile SingularAttribute<Devolucion, String> estado;
    public static volatile SingularAttribute<Devolucion, NotaDebito> idNotaDebito;
    public static volatile SingularAttribute<Devolucion, String> nroDeposito;
    public static volatile SingularAttribute<Devolucion, PagoAnticipado> idPagoAnticipado;
    public static volatile SingularAttribute<Devolucion, Integer> idCuentaDeposito;
    public static volatile SingularAttribute<Devolucion, Date> fechaEmision;
    public static volatile SingularAttribute<Devolucion, Date> fechaInsert;
    public static volatile SingularAttribute<Devolucion, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<Devolucion, BigDecimal> monto;
    public static volatile SingularAttribute<Devolucion, String> nroCheque;
    public static volatile SingularAttribute<Devolucion, Cliente> idCliente;
    public static volatile SingularAttribute<Devolucion, String> tipoDevolucion;
    public static volatile SingularAttribute<Devolucion, String> concepto;
    public static volatile SingularAttribute<Devolucion, String> moneda;
    public static volatile SingularAttribute<Devolucion, String> idUsuarioCreador;
    public static volatile SingularAttribute<Devolucion, Integer> idEmpresa;
    public static volatile SingularAttribute<Devolucion, Integer> idDevolucion;

}