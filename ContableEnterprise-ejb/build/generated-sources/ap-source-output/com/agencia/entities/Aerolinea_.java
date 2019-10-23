package com.agencia.entities;

import com.agencia.entities.AerolineaImpuesto;
import com.agencia.entities.ComisionPromotorAerolinea;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-23T16:33:29")
@StaticMetamodel(Aerolinea.class)
public class Aerolinea_ { 

    public static volatile SingularAttribute<Aerolinea, String> numero;
    public static volatile SingularAttribute<Aerolinea, Boolean> impuestoValorNeto;
    public static volatile SingularAttribute<Aerolinea, Boolean> ivaItComision;
    public static volatile SingularAttribute<Aerolinea, String> personaContacto;
    public static volatile SingularAttribute<Aerolinea, Boolean> registraPnr;
    public static volatile SingularAttribute<Aerolinea, Boolean> roundComisionBob;
    public static volatile SingularAttribute<Aerolinea, BigDecimal> comisionPromInt;
    public static volatile SingularAttribute<Aerolinea, String> nombre;
    public static volatile SingularAttribute<Aerolinea, Boolean> boletosMonExt;
    public static volatile SingularAttribute<Aerolinea, Boolean> cargoNoFiscal;
    public static volatile SingularAttribute<Aerolinea, Boolean> modalidadBoleto;
    public static volatile SingularAttribute<Aerolinea, String> nit;
    public static volatile SingularAttribute<Aerolinea, Integer> idAerolinea;
    public static volatile SingularAttribute<Aerolinea, String> celular;
    public static volatile SingularAttribute<Aerolinea, String> moneda;
    public static volatile SingularAttribute<Aerolinea, Boolean> roundComisionUsd;
    public static volatile SingularAttribute<Aerolinea, String> emitirFacturaA;
    public static volatile SingularAttribute<Aerolinea, String> telefono;
    public static volatile ListAttribute<Aerolinea, AerolineaImpuesto> aerolineaImpuestoList;
    public static volatile SingularAttribute<Aerolinea, String> email;
    public static volatile SingularAttribute<Aerolinea, String> comisionPromIntTipo;
    public static volatile SingularAttribute<Aerolinea, String> comisionPromNacTipo;
    public static volatile ListAttribute<Aerolinea, ComisionPromotorAerolinea> comisionPromotorList;
    public static volatile SingularAttribute<Aerolinea, String> direccion;
    public static volatile SingularAttribute<Aerolinea, BigDecimal> comisionPromNac;
    public static volatile SingularAttribute<Aerolinea, Boolean> impuestoQm;
    public static volatile SingularAttribute<Aerolinea, String> iata;
    public static volatile SingularAttribute<Aerolinea, String> representante;
    public static volatile SingularAttribute<Aerolinea, Boolean> boletosMonNac;
    public static volatile SingularAttribute<Aerolinea, Boolean> bsp;

}