/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.FormasPago;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.IngresoCajaRemote;
import java.math.BigDecimal;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xeio
 */
public class IngresoCajaEJBTest {
    
    public IngresoCajaEJBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of createIngresoCaja method, of class IngresoCajaEJB.
     */
    @Test
    public void testCreateIngresoCaja_3args_1() throws Exception {
        System.out.println("createIngresoCaja");
        Boleto boleto = new Boleto();
        boleto.setFormaPago(FormasPago.CONTADO);
        boleto.setContadoTipo(FormasPago.CONTADO_EFECTIVO);
        boleto.setIdEmpresa(1);
        boleto.setIdUsuarioCreador("admin");
        //boleto.setIdCliente(238);
        boleto.setTipoCupon(Boleto.Cupon.INTERNACIONAL);
        
        
        NotaDebito nota = new NotaDebito();
        nota.setFactorCambiario(new BigDecimal(6.96));
        
        nota.setMontoTotalUsd(new BigDecimal(520));
        
        NotaDebitoTransaccion transacciones = new NotaDebitoTransaccion();
        transacciones.setDescripcion("AA/#114455887799/MARIA CACERES/VVI/MIA///");
        
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        IngresoCajaRemote instance = (IngresoCajaRemote)container.getContext().lookup("java:global/classes/IngresoCajaEJB");
        
        IngresoCaja expResult = null;
        container.close();
        // TODO review the generated test code and remove the default call to fail.
    }

}
