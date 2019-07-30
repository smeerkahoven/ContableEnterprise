package com.reportes.entities;

import com.seguridad.control.entities.Formulario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-07-26T23:24:53")
@StaticMetamodel(Reportes.class)
public class Reportes_ { 

    public static volatile SingularAttribute<Reportes, String> path;
    public static volatile SingularAttribute<Reportes, Boolean> estado;
    public static volatile SingularAttribute<Reportes, String> icon;
    public static volatile SingularAttribute<Reportes, Formulario> idFormulario;
    public static volatile SingularAttribute<Reportes, Integer> idReporte;
    public static volatile SingularAttribute<Reportes, String> nombre;
    public static volatile SingularAttribute<Reportes, String> parametros;

}