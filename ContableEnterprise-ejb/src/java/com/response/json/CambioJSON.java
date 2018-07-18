/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json;

import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.CambioUfv;
import com.seguridad.utils.DateContable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xeio
 */
public class CambioJSON implements Serializable {

    private String fecha;
    private BigDecimal valor;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public static CambioJSON toJSON(CambioDolar c) {
        CambioJSON json = new CambioJSON();
        json.setFecha(DateContable.getDateFormat(c.getFecha(), "dd/MM/yyyy"));
        json.setValor(c.getValor());

        return json;
    }

    public static CambioJSON toJSON(CambioUfv c) {
        CambioJSON json = new CambioJSON();
        if (c != null) {
            json.setFecha(DateContable.getDateFormat(c.getFecha(), "dd/MM/yyyy"));
            json.setValor(c.getValor());
        }
        return json;
    }

    public static CambioDolar toCambioDolar(CambioJSON json) {
        CambioDolar c = new CambioDolar();
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c.setFecha(dt.parse(json.getFecha()));
            c.setValor(json.getValor());
        } catch (ParseException ex) {
            Logger.getLogger(CambioJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

    public static CambioUfv toCambioUfv(CambioJSON json) {
        CambioUfv c = new CambioUfv();
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            c.setFecha(dt.parse(json.getFecha()));
            c.setValor(json.getValor());
        } catch (ParseException ex) {
            Logger.getLogger(CambioJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

        return c;
    }

}
