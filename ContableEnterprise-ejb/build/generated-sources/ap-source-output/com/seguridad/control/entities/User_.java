package com.seguridad.control.entities;

import com.seguridad.control.entities.Empleado;
import com.seguridad.control.entities.Rol;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-06T09:45:55")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Date> fechaBaja;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Rol> idRol;
    public static volatile SingularAttribute<User, Date> fechaAlta;
    public static volatile SingularAttribute<User, Empleado> idEmpleado;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, String> status;

}