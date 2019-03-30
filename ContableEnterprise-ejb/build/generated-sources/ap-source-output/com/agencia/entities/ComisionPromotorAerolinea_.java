package com.agencia.entities;

import com.agencia.entities.Aerolinea;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-03-30T06:25:15")
@StaticMetamodel(ComisionPromotorAerolinea.class)
public class ComisionPromotorAerolinea_ { 

    public static volatile SingularAttribute<ComisionPromotorAerolinea, String> tipoAerolinea;
    public static volatile SingularAttribute<ComisionPromotorAerolinea, Aerolinea> idAerolinea;
    public static volatile SingularAttribute<ComisionPromotorAerolinea, Integer> idComisionPromotor;
    public static volatile SingularAttribute<ComisionPromotorAerolinea, BigDecimal> montoComision;
    public static volatile SingularAttribute<ComisionPromotorAerolinea, Integer> idPromotor;

}