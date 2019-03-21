package com.contabilidad.entities;

import com.agencia.entities.Cliente;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-20T19:01:10")
@StaticMetamodel(ComprobanteContable.class)
public class ComprobanteContable_ { 

    public static volatile SingularAttribute<ComprobanteContable, Integer> idNumeroGestion;
    public static volatile SingularAttribute<ComprobanteContable, String> idUsuarioAnulado;
    public static volatile SingularAttribute<ComprobanteContable, String> estado;
    public static volatile SingularAttribute<ComprobanteContable, String> tipo;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idNotaDebito;
    public static volatile SingularAttribute<ComprobanteContable, BigDecimal> totalHaberExt;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idNotaCredito;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idPagoAnticipado;
    public static volatile SingularAttribute<ComprobanteContable, BigDecimal> totalDebeExt;
    public static volatile SingularAttribute<ComprobanteContable, Date> fechaInsert;
    public static volatile SingularAttribute<ComprobanteContable, String> nombre;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idIngresoCaja;
    public static volatile SingularAttribute<ComprobanteContable, BigDecimal> factorCambiario;
    public static volatile SingularAttribute<ComprobanteContable, Integer> gestion;
    public static volatile SingularAttribute<ComprobanteContable, Date> fecha;
    public static volatile SingularAttribute<ComprobanteContable, String> conErrores;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idLibro;
    public static volatile SingularAttribute<ComprobanteContable, Cliente> idCliente;
    public static volatile SingularAttribute<ComprobanteContable, BigDecimal> totalHaberNac;
    public static volatile SingularAttribute<ComprobanteContable, String> concepto;
    public static volatile SingularAttribute<ComprobanteContable, String> idUsuarioCreador;
    public static volatile SingularAttribute<ComprobanteContable, BigDecimal> totalDebeNac;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idEmpresa;
    public static volatile SingularAttribute<ComprobanteContable, Integer> idDevolucion;

}