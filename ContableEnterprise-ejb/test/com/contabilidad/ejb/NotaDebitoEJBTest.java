/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Boleto;
import com.agencia.search.dto.BoletoSearchForm;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.entities.Entidad;
import java.util.HashMap;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xeio
 */
public class NotaDebitoEJBTest {
    
    public NotaDebitoEJBTest() {
    }



    

    @Test
    public void test_getAllNotaDebito() throws Exception {
        System.out.println("test_getAllNotaDebito");
        Entidad e = null;
        BoletoSearchForm search = new BoletoSearchForm();
        
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        NotaDebitoRemote instance = (NotaDebitoRemote)container.getContext().lookup("java:global/classes/NotaDebitoEJB");
        
        search.setIdEmpresa(1);
        search.setNotaDebito(1000014);
        List l = instance.getAllNotaDebito(search);
        
        l.forEach(x -> {
            System.out.println ("HOLAAAA"+x.toString());
        });
        
        container.close();
        
        assertEquals(1, l.size());
    }

 
}
