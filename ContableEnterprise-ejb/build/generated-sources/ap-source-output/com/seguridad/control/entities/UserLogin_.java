package com.seguridad.control.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-20T00:49:52")
@StaticMetamodel(UserLogin.class)
public class UserLogin_ { 

    public static volatile SingularAttribute<UserLogin, Integer> nroIntento;
    public static volatile SingularAttribute<UserLogin, Date> fechaLogin;
    public static volatile SingularAttribute<UserLogin, Date> fechaLogout;
    public static volatile SingularAttribute<UserLogin, String> ip;
    public static volatile SingularAttribute<UserLogin, String> userName;
    public static volatile SingularAttribute<UserLogin, Integer> idUserLogin;

}