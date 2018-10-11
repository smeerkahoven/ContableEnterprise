/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
import com.seguridad.control.entities.Entidad;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xeio
 */
public class BoletoEJBTest {
    
    public BoletoEJBTest() {
    }


    private Boleto createBoleto() {
        Boleto b = new Boleto();
        //b.setIdAerolinea(5);
        b.setEmision("TKT");
        b.setTipoBoleto("S");
        b.setTipoCupon("I");
        b.setIdPromotor(5);
        b.setNumero(11447788552l);
        b.setIdRuta1("VVI");
        b.setIdRuta2("BSB");
        //b.setIdCliente(238);
        b.setNombrePasajero("MARIA CACERES");
        b.setFechaEmision (DateContable.toLatinAmericaDateFormat("28/09/2018"));
        b.setFechaViaje(DateContable.toLatinAmericaDateFormat("29/09/2018"));
        b.setFactorCambiario(new BigDecimal( 6.96));
        b.setImporteNeto(new BigDecimal(150));
        b.setTotalBoleto(new BigDecimal(150));
        b.setTotalMontoCancelado(new BigDecimal(150));
        b.setMontoComision(new  BigDecimal(1.72));
        b.setFormaPago(FormasPago.CONTADO);
        b.setContadoTipo(FormasPago.CONTADO_EFECTIVO);
        
        return b ;
    }
    
    @Test
    public void testProcesarBoleto() throws Exception {
        System.out.println("procesarBoleto");
        Boleto b = createBoleto();
        
        
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        BoletoRemote instance = (BoletoRemote)container.getContext().lookup("java:global/classes/BoletoEJB");
        Boleto expResult = null;
        Boleto result = instance.procesarBoleto(b);
        assertNotEquals(0, result.getIdBoleto());
        container.close();
        fail("The test case is a prototype.");
    }

    
}
