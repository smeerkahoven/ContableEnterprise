package com.configuracion.entities;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-03T19:46:57")
@StaticMetamodel(LogArchivos.class)
public class LogArchivos_ { 

    public static volatile SingularAttribute<LogArchivos, Date> fecha;
    public static volatile SingularAttribute<LogArchivos, String> nombreArchivo;
    public static volatile SingularAttribute<LogArchivos, String> tipo;
    public static volatile SingularAttribute<LogArchivos, BigInteger> numeroBoleto;
    public static volatile SingularAttribute<LogArchivos, String> usuario;
    public static volatile SingularAttribute<LogArchivos, String> mensaje;
    public static volatile SingularAttribute<LogArchivos, String> tipoArchivo;
    public static volatile SingularAttribute<LogArchivos, Integer> idLog;

}