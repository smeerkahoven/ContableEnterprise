package com.seguridad.control.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-10T01:04:07")
@StaticMetamodel(Log.class)
public class Log_ { 

    public static volatile SingularAttribute<Log, String> accion;
    public static volatile SingularAttribute<Log, String> formulario;
    public static volatile SingularAttribute<Log, Date> fechaLog;
    public static volatile SingularAttribute<Log, String> ip;
    public static volatile SingularAttribute<Log, String> usuario;
    public static volatile SingularAttribute<Log, String> comentario;
    public static volatile SingularAttribute<Log, Integer> idLog;

}