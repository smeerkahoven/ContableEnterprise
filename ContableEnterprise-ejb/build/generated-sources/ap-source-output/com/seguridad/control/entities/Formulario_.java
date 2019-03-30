package com.seguridad.control.entities;

import com.reportes.entities.Reportes;
import com.seguridad.control.entities.Modulo;
import com.seguridad.control.entities.RolFormulario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T06:25:15")
@StaticMetamodel(Formulario.class)
public class Formulario_ { 

    public static volatile SingularAttribute<Formulario, Date> fechaBaja;
    public static volatile SingularAttribute<Formulario, Date> fechaAlta;
    public static volatile ListAttribute<Formulario, RolFormulario> rolFormularioList;
    public static volatile SingularAttribute<Formulario, String> urlAcceso;
    public static volatile SingularAttribute<Formulario, String> restfulUrl;
    public static volatile SingularAttribute<Formulario, Integer> idFormulario;
    public static volatile SingularAttribute<Formulario, Modulo> idModulo;
    public static volatile SingularAttribute<Formulario, String> nombre;
    public static volatile ListAttribute<Formulario, Reportes> reportesList;
    public static volatile SingularAttribute<Formulario, String> status;

}