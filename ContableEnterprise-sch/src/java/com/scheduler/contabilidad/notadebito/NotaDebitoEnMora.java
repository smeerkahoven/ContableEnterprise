/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.contabilidad.notadebito;

import com.contabilidad.entities.NotaDebito;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Estado;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * Diariamente a X hora el JOB correra para ver todas las nota de debito donde
 * la forma de Pago sea Credito y el Vencimiento haya sido ayer para ponerse EN
 * MORA
 *
 * @author xeio
 */
@Stateless
@LocalBean
public class NotaDebitoEnMora {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "*/1", dayOfMonth = "*", year = "*", minute = "*", second = "0")

    public void myTimer() {

        try {
            List<NotaDebito> l = ejbNotaDebito.getNotaDebitoCreditoWhereVencimientoWasYesterday();

            if (!l.isEmpty()) {
                for (NotaDebito n : l) {
                    if (!n.getEstado().equals(Estado.EN_MORA)) {
                        n.setEstado(Estado.EN_MORA);
                        ejbNotaDebito.update(n);
                    }
                }
            }

            System.out.println("Nota Debito En Mora Ejecutando: " + new Date());
        } catch (CRUDException ex) {
            Logger.getLogger(NotaDebitoEnMora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
