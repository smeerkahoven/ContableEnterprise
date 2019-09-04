package com.seguridad.control.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-03T23:53:11")
@StaticMetamodel(UserToken.class)
public class UserToken_ { 

    public static volatile SingularAttribute<UserToken, Date> fechaToken;
    public static volatile SingularAttribute<UserToken, String> idToken;
    public static volatile SingularAttribute<UserToken, String> userName;
    public static volatile SingularAttribute<UserToken, String> status;

}