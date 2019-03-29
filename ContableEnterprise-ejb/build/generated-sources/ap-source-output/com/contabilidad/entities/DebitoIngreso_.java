package com.contabilidad.entities;

import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.NotaDebito;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-28T13:18:01")
@StaticMetamodel(DebitoIngreso.class)
public class DebitoIngreso_ { 

    public static volatile SingularAttribute<DebitoIngreso, Integer> idDebitoIngreso;
    public static volatile SingularAttribute<DebitoIngreso, NotaDebito> idNotaDebito;
    public static volatile SingularAttribute<DebitoIngreso, IngresoCaja> idIngresoCaja;

}