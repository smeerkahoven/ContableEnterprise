/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.agencia;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.ComisionPromotorAerolinea;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class ComisionPromotorAerolineaJSON implements Serializable {

    private Integer idComisionPromotor;
    private Integer idPromotor;
    private Integer idAerolinea;
    private String nombreAerolinea;
    private String tipoAerolinea ;
    private BigDecimal montoComision;

    public static ComisionPromotorAerolineaJSON toNewAerolineaComision(Object[] o) {
        ComisionPromotorAerolineaJSON a = new ComisionPromotorAerolineaJSON();
        a.setIdAerolinea((Integer) o[0]);
        a.setNombreAerolinea((String) o[1] + "-" + (String)o[2]);
        a.setTipoAerolinea((String)o[3]);
        a.setIdPromotor(0);
        a.setMontoComision(BigDecimal.ZERO);

        return a;

    }
    
    public static ComisionPromotorAerolineaJSON toAerolineaComision(ComisionPromotorAerolinea o) {
        ComisionPromotorAerolineaJSON a = new ComisionPromotorAerolineaJSON();
        a.setIdAerolinea(o.getIdAerolinea().getIdAerolinea());
        a.setNombreAerolinea(o.getIdAerolinea().getNombre());
        a.setTipoAerolinea(o.getTipoAerolinea());
        a.setIdPromotor(o.getIdPromotor());
        a.setMontoComision(o.getMontoComision());
        a.setIdComisionPromotor(o.getIdComisionPromotor());

        return a;

    }
    
    public static List<ComisionPromotorAerolineaJSON> toNewAerolineaComision(List<Object[]> o){
        List<ComisionPromotorAerolineaJSON> l = new LinkedList<>();
        Iterator i = o.iterator();
        while (i.hasNext()){
            Object[] index = (Object[])i.next();
            l.add(toNewAerolineaComision(index));
        }
        return l ;
    }
    
        public static List<ComisionPromotorAerolineaJSON> toAerolineaComision(List<ComisionPromotorAerolinea> o){
        List<ComisionPromotorAerolineaJSON> l = new LinkedList<>();
        Iterator i = o.iterator();
        while (i.hasNext()){
            ComisionPromotorAerolinea index = (ComisionPromotorAerolinea)i.next();
            l.add(toAerolineaComision(index));
        }
        return l ;
    }
    
    public static ComisionPromotorAerolinea toComisionPromotor(ComisionPromotorAerolineaJSON json){
        ComisionPromotorAerolinea newC = new ComisionPromotorAerolinea();
        newC.setIdAerolinea(new Aerolinea(json.getIdAerolinea()) );
        newC.setIdComisionPromotor(json.getIdComisionPromotor());
        newC.setIdPromotor(json.getIdPromotor());
        newC.setMontoComision(json.getMontoComision());
        newC.setTipoAerolinea(json.getTipoAerolinea());
        
        return newC ;
    }

    public String getTipoAerolinea() {
        return tipoAerolinea;
    }

    public void setTipoAerolinea(String tipoAerolinea) {
        this.tipoAerolinea = tipoAerolinea;
    }

    
    public String getNombreAerolinea() {
        return nombreAerolinea;
    }

    public void setNombreAerolinea(String nombreAerolinea) {
        this.nombreAerolinea = nombreAerolinea;
    }

    public Integer getIdComisionPromotor() {
        return idComisionPromotor;
    }

    public void setIdComisionPromotor(Integer idComisionPromotor) {
        this.idComisionPromotor = idComisionPromotor;
    }

    public Integer getIdPromotor() {
        return idPromotor;
    }

    public void setIdPromotor(Integer idPromotor) {
        this.idPromotor = idPromotor;
    }

    public Integer getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(Integer idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

}
