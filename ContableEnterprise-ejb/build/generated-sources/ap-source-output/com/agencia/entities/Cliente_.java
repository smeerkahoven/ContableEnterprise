package com.agencia.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T06:25:15")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Integer> idClienteGrupo;
    public static volatile SingularAttribute<Cliente, String> estado;
    public static volatile SingularAttribute<Cliente, String> monedaCredito;
    public static volatile SingularAttribute<Cliente, String> representanteTelefono;
    public static volatile SingularAttribute<Cliente, Date> fechaAlta;
    public static volatile SingularAttribute<Cliente, Integer> idClienteCorporativo;
    public static volatile SingularAttribute<Cliente, String> interesDesde;
    public static volatile SingularAttribute<Cliente, String> ci;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, BigDecimal> limiteCredito;
    public static volatile SingularAttribute<Cliente, String> representanteDireccion;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, Integer> plazoMaximo;
    public static volatile SingularAttribute<Cliente, String> telefonoFijo;
    public static volatile SingularAttribute<Cliente, String> telefonoCelular;
    public static volatile SingularAttribute<Cliente, String> representanteCelular;
    public static volatile SingularAttribute<Cliente, Integer> idCliente;
    public static volatile SingularAttribute<Cliente, BigDecimal> interesMora;
    public static volatile SingularAttribute<Cliente, String> representante;
    public static volatile SingularAttribute<Cliente, String> representanteCi;
    public static volatile SingularAttribute<Cliente, String> nit;
    public static volatile SingularAttribute<Cliente, Boolean> calcAutomaticoInteres;
    public static volatile SingularAttribute<Cliente, String> email;
    public static volatile SingularAttribute<Cliente, Integer> idPromotor;

}