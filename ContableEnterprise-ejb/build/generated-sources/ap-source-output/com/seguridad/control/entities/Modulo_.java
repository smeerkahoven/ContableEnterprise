package com.seguridad.control.entities;

import com.seguridad.control.entities.Formulario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-08T19:29:25")
@StaticMetamodel(Modulo.class)
public class Modulo_ { 

    public static volatile SingularAttribute<Modulo, Date> fechaBaja;
    public static volatile SingularAttribute<Modulo, Date> fechaAlta;
    public static volatile SingularAttribute<Modulo, String> urlAcceso;
    public static volatile ListAttribute<Modulo, Formulario> formulariosList;
    public static volatile SingularAttribute<Modulo, Integer> idModulo;
    public static volatile SingularAttribute<Modulo, String> nombre;
    public static volatile SingularAttribute<Modulo, String> status;
    public static volatile SingularAttribute<Modulo, String> classMenu;

}